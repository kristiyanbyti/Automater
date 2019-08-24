/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.work;

import automater.utilities.CollectionUtilities;
import automater.utilities.Errors;
import automater.utilities.Logger;
import automater.utilities.Looper;
import automater.utilities.LooperClient;
import automater.work.model.ActionContext;
import automater.work.model.ExecutorProgress;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Performs actions, when the time is right.
 * 
 * @author Bytevi
 */
public class ExecutorProcess implements BaseExecutorProcess, BaseExecutorTimer, LooperClient, ExecutorProgress {
    private final Object _lock = new Object();
    
    private final Robot _robot;
    private final List<BaseAction> _actions;
    
    private BaseExecutorTimer _timer;
    private ExecutorListener _listener;
    
    private boolean _started = false;
    
    private BaseActionProcess _previousActionProcess;
    private BaseActionProcess _currentActionProcess;
    
    private Date _previousDate = new Date();
    private long _currentTimeValue = 0;
    private double _timeScale = 1.0;
    
    public ExecutorProcess(Robot robot, List<BaseAction> actions) throws Exception
    {
        if (actions.isEmpty())
        {
            Errors.throwInvalidArgument("Process cannot be initialized with zero actions");
        }
        
        this._robot = robot;
        this._actions = CollectionUtilities.copyAsImmutable(actions);
        this._timer = this;
        this._currentActionProcess = null;
    }
    
    // # BaseExecutorProcess
    
    @Override
    public boolean isIdle()
    {
        return _currentActionProcess == null;
    }
    
    @Override
    public boolean isWaiting()
    {
        return _currentActionProcess == null && !isFinished();
    }
    
    @Override
    public boolean isFinished()
    {
        if (_previousActionProcess == null)
        {
            return false;
        }
        
        return isLastAction(_previousActionProcess.getAction());
    }
    
    @Override
    public BaseActionProcess getCurrentActionProcess()
    {
        return _currentActionProcess;
    }
    
    @Override
    public ExecutorProgress getProgress()
    {
        return this;
    }
    
    @Override
    public void setExecutorTimer(BaseExecutorTimer timer)
    {
        this._timer = timer;
    }
    
    @Override
    public void setListener(ExecutorListener listener)
    {
        this._listener = listener;
    }
    
    @Override
    public void start() throws Exception
    {
        Logger.messageEvent(this, "Start.");
        
        int actionsSize;
        
        synchronized (_lock)
        {
            if (_started)
            {
                Errors.throwCannotStartTwice("Executor process already started.");
                return;
            }
            
            // Setup timer
            this._timer.setup(getFirstAction(), getLastAction());
            
            // Start
            _started = true;
            
            Looper.getShared().subscribe(this);
            
            actionsSize = _actions.size();
        }
        
        // Listener alert
        if (_listener != null)
        {
            _listener.onStart(actionsSize);
        }
    }
    
    @Override
    public void stop() throws Exception
    {
        Logger.messageEvent(this, "Stop.");
        
        synchronized (_lock)
        {
            if (!_started)
            {
                Errors.throwInternalLogicError("Executor process cannot stop, never has been started");
                return;
            }
        }
        
        // Listener alert
        if (_listener != null)
        {
            _listener.onCancel();
        }
        
        // Cleanup outside the synchronized
        cleanup();
    }
    
    // # ExecutorTimer
    
    @Override
    public void setup(BaseAction firstAction, BaseAction lastAction) throws Exception {
        _previousDate = new Date();
        
        _currentTimeValue = firstAction.getPerformTime();
    }

    @Override
    public long getCurrentTimeValue() {
        return _currentTimeValue;
    }

    @Override
    public long getFirstTimeValue() {
        return getFirstAction().getPerformTime();
    }

    @Override
    public long getFinalTimeValue() {
        return getLastAction().getPerformTime();
    }

    @Override
    public double getTimeScale() {
        return _timeScale;
    }
    
    @Override
    public void setTimeScale(double scale) {
        _timeScale = scale;
    }

    @Override
    public long updateCurrentTime(long dt) {
        dt *= _timeScale;
        
        _currentTimeValue += dt;
        
        return _currentTimeValue;
    }
    
    @Override
    public boolean canPerformNextAction(BaseAction action) {
        return action.getPerformTime() <= getCurrentTimeValue();
    }
    
    // # LooperClient
    
    @Override
    public void loop()
    {
        update();
    }
    
    // # ExecutorProgress
    
    @Override
    public String getCurrentStatus() {
        if (isFinished())
        {
            return "Finished";
        }
        
        if (isWaiting())
        {
            BaseActionProcess p = getPreviousActionProcessSafely();
            
            if (p == null)
            {
                return "Waiting for next";
            }
            
            return "Performed " + p.getAction().getDescription().getStandart() + ", waiting for next";
        }
        
        BaseActionProcess p = getCurrentActionProcessSafely();
        
        if (p == null)
        {
            return "Idle";
        }
        
        return "Performing " + p.getAction().getDescription().getStandart();
    }

    @Override
    public double getPercentageDone() {
        long start = _timer.getFirstTimeValue();
        double current = _timer.getCurrentTimeValue() - start;
        double end = _timer.getFinalTimeValue() - start;
        
        double result = current / end;
        
        if (result > 1)
        {
            result = 1;
        }
        
        if (result < 0)
        {
            result = 0;
        }
        
        return result;
    }

    @Override
    public int getCurrentActionIndex() {
        BaseActionProcess process = getCurrentActionProcessSafely();
        
        if (process == null)
        {
            process = getPreviousActionProcessSafely();
        }
        
        return _actions.indexOf(process.getAction());
    }
    
    // # Private
    
    private BaseActionProcess getCurrentActionProcessSafely()
    {
        synchronized (_lock)
        {
            return _currentActionProcess;
        }
    }
    
    private void setCurrentActionProcessSafely(BaseActionProcess p)
    {
        synchronized (_lock)
        {
            _currentActionProcess = p;
        }
    }
    
    private void setPreviousActionProcessSafely(BaseActionProcess p)
    {
        synchronized (_lock)
        {
            _previousActionProcess = p;
        }
    }
    
    private BaseActionProcess getPreviousActionProcessSafely()
    {
        synchronized (_lock)
        {
            return _previousActionProcess;
        }
    }
    
    private void markCurrentActionProcessAsDone()
    {
        BaseActionProcess current = getCurrentActionProcessSafely();
        
        if (current == null)
        {
            Logger.error(this, "Internal logic error in markCurrentActionProcessAsDone(), no process is running");
            return;
        }
        
        setPreviousActionProcessSafely(current);
        setCurrentActionProcessSafely(null);
    }
    
    private void performCurrentActionProcess()
    {
        BaseActionProcess current = getCurrentActionProcessSafely();
        ActionContext context = new ActionContext(_robot);
        
        try {
            current.perform(context);
        } catch (Exception e) {
            Logger.error(this, "Failed to perform action " + current.toString() + ": + " + e.toString());
        }
    }
    
    private List<BaseAction> getRemainingActions() {
        BaseActionProcess previousActionProcess = getPreviousActionProcessSafely();
        
        if (previousActionProcess == null)
        {
            return _actions;
        }
        
        int index = _actions.indexOf(previousActionProcess.getAction());
        
        if (index < 0)
        {
            Logger.error(this, "Internal logic error in getRemainingActions() for action process " + previousActionProcess.toString());
            return new ArrayList<>();
        }
        
        ArrayList<BaseAction> remaining = new ArrayList<>();
        
        for (int e = index + 1; e < _actions.size(); e++) 
        {
            remaining.add(_actions.get(e));
        }
        
        return remaining;
    }
    
    private BaseAction getFirstAction() {
        return _actions.get(0);
    }
    
    private BaseAction getLastAction() {
        return _actions.get(_actions.size()-1);
    }
    
    private boolean isLastAction(BaseAction action) {
        return action == getLastAction();
    }
    
    private void update()
    {
        if (isFinished())
        {
            return;
        }
        
        // Update time
        // Calculate the delta value (time difference between current and previous update)
        Date current = new Date();
        
        long dt = current.getTime() - _previousDate.getTime();
        
        _previousDate = current;
        
        this._timer.updateCurrentTime(dt);
        
        // Evaluate current action process
        boolean isIdle = isIdle();
        boolean isWaiting = isWaiting();
        
        if (!isIdle)
        {
            BaseActionProcess currentActionProcess = getCurrentActionProcessSafely();
            BaseAction action = currentActionProcess.getAction();
            
            // Listener alert
            if (_listener != null)
            {
                _listener.onActionUpdate(action);
            }
            
            // Process finished? Mark as done
            if (currentActionProcess.isActive())
            {
                String actionDescription = action.getDescription().getStandart();
                
                Logger.messageEvent(this, "Finished action: " + actionDescription + "!");
                
                markCurrentActionProcessAsDone();
                
                isIdle = true;
                
                // Listener alert
                if (_listener != null)
                {
                    _listener.onActionFinish(action);
                }
            }
        }
        
        // Idle? Perform next action
        while (isIdle)
        {
            isIdle = false;
            List<BaseAction> remainingActions = getRemainingActions();
            
            if (remainingActions.isEmpty())
            {
                break;
            }
            
            BaseAction nextAction = remainingActions.get(0);
            
            boolean performNext = _timer.canPerformNextAction(nextAction);
            
            if (performNext)
            {
                BaseActionProcess p = new ActionProcess(nextAction);
                setCurrentActionProcessSafely(p);
                
                String actionDescription = nextAction.getDescription().getStandart();
                Logger.messageEvent(this, "Perform next action: " + actionDescription);
                
                // Listener alert
                if (_listener != null)
                {
                    _listener.onActionExecute(nextAction);
                }
                
                // Perform
                performCurrentActionProcess();
                
                if (p.isActive())
                {
                    return;
                }
                
                // Non-complex action was immediately performed, go to next action
                markCurrentActionProcessAsDone();
                isIdle = true;
                
                // Listener alert
                if (_listener != null)
                {
                    Logger.messageEvent(this, "Finished action: " + actionDescription + "!");
                    _listener.onActionFinish(nextAction);
                }
            }
        }
        
        // Finished? Just log message event
        if (isFinished())
        {
            Logger.messageEvent(this, "Finished.");
            
            // Listener alert
            if (_listener != null)
            {
                _listener.onFinish();
            }
            
            // Cleanup
            cleanup();
            
            return;
        }
        
        // Waiting?
        if (isWaiting())
        {
            if (!isWaiting)
            {
                Logger.messageEvent(this, "Waiting for next action...");
            }
            
            // Listener alert
            if (_listener != null)
            {
                _listener.onWait();
            }
        }
    }
    
    private void cleanup()
    {
        setPreviousActionProcessSafely(new ActionProcess(getLastAction()));
        setCurrentActionProcessSafely(null);
        Looper.getShared().unsubscribe(this);
    }
}