/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.input;

/**
 * Represents a system action, thats not a keyboard or mouse input.
 * 
 * @author Bytevi
 */
public interface InputSpecialAction extends Input {
    public boolean isCloseWindow();
}