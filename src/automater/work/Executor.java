/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.work;

import automater.utilities.Logger;
import automater.work.model.ExecutorState;
import automater.work.model.Macro;
import automater.work.model.MacroParameters;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import java.awt.Robot;

/**
 * Executes macros. Can execute multiple macros simultaneously.
 *
 * @author Bytevi
 */
public class Executor {
    @NotNull private static Executor singleton;
    @NotNull private final Object _lock = new Object();
    
    @NotNull private final ExecutorState _state = new ExecutorState();
    
    @Nullable private Robot _robot;
    
    private Executor()
    {
        
    }
    
    synchronized public static @NotNull Executor getDefault()
    {
        if (singleton == null)
        {
            singleton = new Executor();
        }
        
        return singleton;
    }
    
    public boolean isPerforming()
    {
        synchronized (_lock)
        {
            return !_state.isIdle();
        }
    }
    
    public BaseExecutorProcess performMacro(@NotNull Macro macro, @NotNull ExecutorListener listener) throws Exception
    {
        return performMacro(macro, MacroParameters.defaultValues(), listener);
    }
    
    public BaseExecutorProcess performMacro(@NotNull Macro macro, @NotNull MacroParameters parameters, @NotNull ExecutorListener listener) throws Exception
    {
        return performMacro(macro, parameters, listener, new StandardExecutorTimer());
    }
    
    public BaseExecutorProcess performMacro(@NotNull Macro macro, @NotNull MacroParameters parameters, @NotNull ExecutorListener listener, @NotNull BaseExecutorTimer timer) throws Exception
    {
        Logger.messageEvent(this, "Perform macro '" + macro.getName() + "' with parameters " + parameters.toString());
        
        synchronized (_lock)
        {
            if (_robot == null)
            {
                initRobot();
            }
            
            BaseExecutorProcess process = ExecutorProcess.create(_robot, macro.actions, timer);
            process.setListener(listener);
            
            _state.startProcess(process, macro, parameters);
            
            return process;
        }
    }
    
    public void stopAll()
    {
        Logger.messageEvent(this, "Stop all processes");
        
        synchronized (_lock)
        {
            _state.stopAll();
        }
    }
    
    // # Private
    
    private void initRobot() throws Exception
    {
        if (_robot == null)
        {
            _robot = new Robot();
        }
    }
}
