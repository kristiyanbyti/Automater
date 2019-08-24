/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.utilities;

import javax.swing.SwingUtilities;

/**
 * Contains utility methods for performing callbacks on the UI thread (swing thread).
 * Used this to make changes to the UI, since making changes from background threads
 * is not allowed.
 * 
 * @author Bytevi
 */
public class LooperSwing {
    private static LooperSwing singleton;
    
    private LooperSwing()
    {
        
    }

    synchronized public static LooperSwing getShared()
    {
        if (singleton == null)
        {
            singleton = new LooperSwing();
        }

        return singleton;
    }
    
    public void performCallback(final SimpleCallback callback)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                callback.perform();
            }
        });
    }
    
    public <T> void performCallback(final Callback<T> callback, final T parameter)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                callback.perform(parameter);
            }
        });
    }
}