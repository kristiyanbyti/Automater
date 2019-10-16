/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.work.parser;

import automater.recorder.model.RecorderUserInput;
import automater.work.BaseAction;
import com.sun.istack.internal.NotNull;
import java.util.List;

/**
 * Represents a parser that takes RecorderUserInputs and converts them to
 * BaseActions.
 *
 * @author Bytevi
 */
public interface BaseActionsParser {
    public void onBeginParsing() throws Exception;
    public void onParseInput(@NotNull RecorderUserInput input) throws Exception;
    public @NotNull List<BaseAction> onFinishParsingMacro() throws Exception;
}
