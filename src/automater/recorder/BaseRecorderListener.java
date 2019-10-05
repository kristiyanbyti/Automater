/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.recorder;

import automater.recorder.model.RecorderResult;
import automater.recorder.model.RecorderUserInput;

/**
 * A listener for recorded user input actions.
 * 
 * onRecordedUserInput() indicates new input has been recorded.
 * 
 * onRecordedUserInputChanged() indicates that the recorder model has changed,
 * even when no new input object has been added. This may happen due to optimizations,
 * such as grouping multiple input objects into one.
 * 
 * onFailedRecordedUserInput() indicates failure.
 *
 * @author Bytevi
 */
public interface BaseRecorderListener {
    public void onRecordedUserInput(RecorderUserInput input);
    public void onRecordedUserInputChanged();
    public void onFailedRecordedUserInput(RecorderUserInput input);
    
    public void onFinishedRecording(RecorderResult result, boolean success, Exception exception);
}
