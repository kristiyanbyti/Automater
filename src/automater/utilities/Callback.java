/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.utilities;

import java.util.function.Function;

/**
 * A function callback that takes 1 paramter and returns no value.
 * 
 * @author Bytevi
 */
public abstract class Callback<T> {
    abstract public void perform(T argument);
    
    public static <C> Callback createDoNothing()
    {
        return new Callback<C>() {
            @Override
            public void perform(C argument) {
                // Do nothing
            }
        };
    }
}