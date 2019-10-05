/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.storage;

/**
 * Holds and maintains the storage of the application.
 * 
 * @author Bytevi
 */
public class GeneralStorage {
    private static GeneralStorage singleton;
    
    private final MacroStorage _macrosStorage = new MacroStorage();
    private final PreferencesStorage _preferencesStorage = new PreferencesStorage();
    
    synchronized public static GeneralStorage getDefault()
    {
        if (singleton == null)
        {
            singleton = new GeneralStorage();
        }
        
        return singleton;
    }
    
    public MacroStorage getMacrosStorage()
    {
        return _macrosStorage;
    }
    
    public PreferencesStorage getPreferencesStorage()
    {
        return _preferencesStorage;
    }
}
