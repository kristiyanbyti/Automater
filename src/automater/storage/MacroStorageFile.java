/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.storage;

import automater.TextValue;
import automater.utilities.Archiver;
import automater.utilities.Errors;
import automater.utilities.FileSystem;
import automater.utilities.StringFormatting;
import automater.work.model.Macro;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A single macro file.
 *
 * @author Bytevi
 */
public class MacroStorageFile {
    public static String FILE_NAME_EXTENSION = ".txt";
    public static final int MACRO_NAME_MIN_LENGTH = 3;
    public static final int MACRO_NAME_MAX_LENGTH = 32;
    
    @NotNull private final Object _lock = new Object();
    
    @NotNull private Macro _macro;
    
    public static @Nullable MacroStorageFile createFromMacro(@NotNull Macro macro)
    {
        return new MacroStorageFile(macro);
    }
    
    public static @Nullable MacroStorageFile createFromFile(@NotNull File file)
    {
        String data = MacroStorageFile.readFromFile(file);
        
        if (data == null)
        {
            return null;
        }
        
        Macro macro = null;
        
        try {
            macro = MacroStorageFile.parseMacroFromData(data);
        } catch (Exception e) {
            
        }
        
        if (macro == null)
        {
            return null;
        }
        
        return new MacroStorageFile(macro);
    }
    
    private MacroStorageFile(@NotNull Macro macro)
    {
        _macro = macro;
    }
    
    // # Validators
    
    public static @NotNull List<Character> getAllowedSpecialCharacters()
    {
        ArrayList<Character> chars = new ArrayList<>();
        chars.add(' ');
        chars.add('_');
        chars.add('-');
        chars.add('(');
        chars.add(')');
        chars.add('[');
        chars.add(']');
        chars.add('+');
        return chars;
    }
    
    public static @NotNull Exception getMacroNameIsUnavailableError(@NotNull String name)
    {
        if (name.isEmpty())
        {
            return new Exception(TextValue.getText(TextValue.Error_NameIsEmpty));
        }
        
        if (name.length() < MACRO_NAME_MIN_LENGTH)
        {
            return new Exception(TextValue.getText(TextValue.Error_NameIsTooShort,  String.valueOf(MACRO_NAME_MIN_LENGTH)));
        }
        
        if (name.length() > MACRO_NAME_MAX_LENGTH)
        {
            return new Exception(TextValue.getText(TextValue.Error_NameIsTooLong,  String.valueOf(MACRO_NAME_MAX_LENGTH)));
        }
        
        if (!isMacroCharsValid(name))
        {
            String allowedChars = getAllowedSpecialCharacters().toString();
            return new Exception(TextValue.getText(TextValue.Error_NameMustBeAlphaNumericWithSpecialSymbols, allowedChars));
        }
        
        return null;
    }
    
    public static boolean isMacroCharsValid(@NotNull String name)
    {
        List<Character> allowedChars = getAllowedSpecialCharacters();
        
        for (int e = 0; e < name.length(); e++)
        {
            char c = name.charAt(e);
            
            if (StringFormatting.isStringAlphanumeric(String.valueOf(c)))
            {
                continue;
            }
            
            if (!allowedChars.contains(c))
            {
                return false;
            }
        }
        
        return true;
    }
    
    // # Properties
    
    public @NotNull Macro getMacro()
    {
        synchronized (_lock)
        {
            return _macro;
        }
    }
    
    public @NotNull String name()
    {
        return _macro.name;
    }
    
    public @NotNull String fileName()
    {
        return name() + FILE_NAME_EXTENSION;
    }
    
    public @NotNull String filePath()
    {
        String path = FileSystem.getLocalFilePath();
        return FileSystem.createFilePathWithBasePath(path, fileName());
    }
    
    // # Operations
    
    public void create() throws Exception
    {
        Macro macro;
        
        synchronized (_lock)
        {
            macro = _macro;
        }
        
        String data = Archiver.serializeObject(macro);
        
        if (data == null)
        {
            Errors.throwSerializationFailed("Failed to serialize macro '" + macro.name + "'");
        }
        
        File file = getFile();
        
        if (file.exists())
        {
            Errors.throwSerializationFailed("Failed to create macro '" + macro.name + "', already exists");
        }
        
        // Update file
        synchronized (_lock)
        {
            writeToFile(getFile(), data);
        }
    }
    
    public void update(@NotNull Macro macro) throws Exception
    {
        File file = getFile();
        
        if (!file.exists())
        {
            Errors.throwIllegalStateError("Failed to update macro '" + macro.name + "', macro file doesn't exists");
        }
        
        // Override the contents of this macro
        String data = Archiver.serializeObject(macro);
        
        if (data == null)
        {
            Errors.throwSerializationFailed("Failed to serialize macro '" + macro.name + "'");
        }
        
        // Update local variable
        // Update file
        synchronized (_lock)
        {
            _macro = macro;
            writeToFile(getFile(), data);
        }
    }
    
    public void delete() throws Exception
    {
        File file = getFile();
        
        if (file != null)
        {
            file.delete();
        }
    }
    
    // # Parsing
    
    private static @NotNull Macro parseMacroFromData(@NotNull String data) throws Exception
    {
        return Archiver.deserializeObject(Macro.class, data);
    }
    
    // # Private
    
    private @NotNull File getFile() throws Exception
    {
        File file = new File(filePath());
        return file;
    }
    
    private static void writeToFile(@NotNull File file, @NotNull String data) throws Exception
    {
        PrintWriter writer = null;
        
        try {
            writer = new PrintWriter(file);
            String[] lines = data.split("\n");
            
            for (String line : lines)
            {
                writer.println(line);
            } 
            
            writer.println();
            writer.close();
        } catch (Exception e) {
            try {
                if (writer != null)
                {
                    writer.close();
                }
            } catch (Exception e2) {
                
            }
        }
    }
    
    private static @NotNull String readFromFile(@NotNull File file)
    {
        String data = "";
        
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            
            String line = reader.readLine();
            
            while (line != null)
            {
                data = data.concat(line);
                line = reader.readLine();
            }
            
            reader.readLine();
            reader.close();
        } catch (Exception e) {
            try {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (Exception e2) {
            
            }
        }
        
        return data;
    }
}
