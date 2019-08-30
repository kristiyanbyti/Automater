/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.recorder.parser;

import automater.recorder.model.RecorderUserInput;
import automater.recorder.model.RecorderUserInputMouseMotion;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import automater.recorder.model.UserInputMouseMove;
import automater.utilities.Logger;

/**
 * Optimizes mouse click inputs, by grouping them in a single record event.
 * 
 * This parsers requires to work with a BaseRecorderModel whose
 * allowsDuplicateInputs() returns false.
 * 
 * @author Bytevi
 */
public class RecorderNativeParserSmart extends RecorderNativeParser {
    private RecorderUserInputMouseMotion _currentMouseMotion;
    
    public RecorderNativeParserSmart(List<RecorderParserFlag> flags)
    {
        super(flags);
    }
    
    @Override
    public RecorderUserInput evaluatePress(NativeKeyEvent keyboardEvent)
    {
        clearCurrentMouseMotion();
        return super.evaluatePress(keyboardEvent);
    }
    
    @Override
    public RecorderUserInput evaluateRelease(NativeKeyEvent keyboardEvent)
    {
        clearCurrentMouseMotion();
        return super.evaluateRelease(keyboardEvent);
    }
    
    @Override
    public RecorderUserInput evaluatePress(NativeMouseEvent mouseEvent)
    {
        clearCurrentMouseMotion();
        return super.evaluatePress(mouseEvent);
    }
    
    @Override
    public RecorderUserInput evaluateRelease(NativeMouseEvent mouseEvent)
    {
        clearCurrentMouseMotion();
        return super.evaluateRelease(mouseEvent);
    }
    
    @Override
    public RecorderUserInput evaluateMouseMove(NativeMouseEvent mouseMoveEvent)
    {
        RecorderUserInput result = super.evaluateMouseMove(mouseMoveEvent);
        
        // Not a mouse motion input, clear current mouse motion input
        if (!(result instanceof UserInputMouseMove))
        {
            clearCurrentMouseMotion();
            
            return result;
        }
        
        UserInputMouseMove mouseInput = (UserInputMouseMove)result;
        
        Date timestamp = result.getTimestamp();
        
        // Not currently parsing a mouse motion input, create a new one
        // from scratch
        if (_currentMouseMotion == null)
        {
            _currentMouseMotion = RecorderUserInputMouseMotion.createMouseMovement(mouseInput);
        }
        // Else, just update the current one
        else
        {
            Logger.message(this, "move " + mouseInput.getTimestamp().getTime());
            _currentMouseMotion.addMovementPoint(mouseInput);
        }
        
        return _currentMouseMotion;
    }
    
    @Override
    public RecorderUserInput evaluateMouseWheel(NativeMouseWheelEvent mouseWheelEvent)
    {
        clearCurrentMouseMotion();
        return super.evaluateMouseWheel(mouseWheelEvent);
    }
    
    @Override
    public RecorderUserInput evaluateWindowEvent(WindowEvent windowEvent)
    {
        clearCurrentMouseMotion();
        return super.evaluateWindowEvent(windowEvent);
    }
    
    private void clearCurrentMouseMotion()
    {
        _currentMouseMotion = null;
    }
}