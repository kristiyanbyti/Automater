/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logs various messages to the standard output.
 * 
 * @author Bytevi
 */
public class Logger {
    public static final boolean PRINT_TO_LOG_FILE = true;
    public static final String LOG_FILE_NAME = "logs.txt";
    public static final String LOG_BACKUP_FILE_NAME = "logs-previous.txt";
    public static final boolean DISPLAY_TIMESTAMPS = true;
    
    private static final Object _lock = new Object();
    private static final PrintStream _outStream = System.out;
    private static File _logFile;
    private static FileWriter _logWriter;
    
    public static final String MESSAGE_ACTION_PREFIX = "#ACTION#";
    public static final String MESSAGE_EVENT_PREFIX = "#EVENT#";
    public static final String WARNING_PREFIX = "#WARNING#";
    public static final String ERROR_PREFIX = "#ERROR#";
    public static final String UTILITY_ERROR_PREFIX = "#UTILITY_ERROR#";
    public static final String SYSTEM_ERROR_PREFIX = "#SYSTEM_ERROR#";
    
    public static final String OVERRIDE_ME_MESSAGE = "Calling non-overriden base method ";
    
    public static void printLine(String data)
    {
        _outStream.println(data);
        
        if (PRINT_TO_LOG_FILE)
        {
            writeToLogFile(data);
        }
    }
    
    public static <T> void message(T origin, String string)
    {
        printLine(generateText(origin, "", string));
    }
    
    public static <T> void message(String prefix, String string)
    {
        printLine(generateText(prefix, string));
    }
    
    public static <T> void messageAction(T origin, String string)
    {
        printLine(generateText(origin, MESSAGE_ACTION_PREFIX, string));
    }
    
    public static <T> void messageEvent(T origin, String string)
    {
        printLine(generateText(origin, MESSAGE_EVENT_PREFIX, string));
    }
    
    public static <T> void warning(String prefix, String string)
    {
        prefix = WARNING_PREFIX + " " + prefix;
        
        printLine(generateText(prefix, string));
    }
    
    public static <T> void warning(T origin, String string)
    {
        printLine(generateText(origin, WARNING_PREFIX, string));
    }
    
    public static <T> void error(T origin, String string)
    {
        printLine(generateText(origin, ERROR_PREFIX, string));
    }
    
    public static <T> void error(String prefix, String string)
    {
        prefix = ERROR_PREFIX + " " + prefix;
        
        printLine(generateText(prefix, string));
    }
    
    public static <T> void utilityError(T origin, String string)
    {
        printLine(generateText(origin, UTILITY_ERROR_PREFIX, string));
    }
    
    public static <T> void systemError(T origin, String string)
    {
        printLine(generateText(origin, SYSTEM_ERROR_PREFIX, string));
    }
    
    public static <T> void overrideMe(T origin, String methodName)
    {
        printLine(generateText(origin, WARNING_PREFIX, OVERRIDE_ME_MESSAGE + methodName));
    }
    
    private static String getLogFilePath()
    {
        String path = FileSystem.getLocalFilePath();
        return FileSystem.createFilePathWithBasePath(path, LOG_FILE_NAME);
    }
    
    private static String getLogBackupFilePath()
    {
        if (LOG_BACKUP_FILE_NAME.isEmpty())
        {
            return "";
        }
        
        String path = FileSystem.getLocalFilePath();
        return FileSystem.createFilePathWithBasePath(path, LOG_BACKUP_FILE_NAME);
    }
    
    private static File getLogFile()
    {
        return setupLogFileIfNecessary();
    }
    
    private static void writeToLogFile(String data)
    {
        FileWriter writer = setupLogFileWriterIfNecessary();
        
        if (writer == null)
        {
            return;
        }
        
        try {
            String[] lines = data.split("\n");
            
            for (String line : lines)
            {
                writer.append(line + "\n");
            }
            
            writer.flush();
        } catch (Exception e) {
            
        }
    }
    
    private static File setupLogFileIfNecessary()
    {
        synchronized (_lock)
        {
            boolean initialized = _logFile != null;
            
            if (!initialized)
            {
                // Backup current log file if a backup name is defined
                // Otherwise just delete the current log file
                if (!getLogBackupFilePath().isEmpty())
                {
                    backupLogFile();
                }
                else
                {
                    deleteLogFile();
                }
                
                _logFile = new File(getLogFilePath());
            }
            
            return _logFile;
        }
    }
    
    private static void backupLogFile()
    {
        if (getLogBackupFilePath().isEmpty())
        {
            return;
        }
        
        // Delete backup file (if it exists)
        // Backup current log file
        // Delete log file (if it exists)
        File backup = new File(getLogBackupFilePath());
        File logFile = new File(getLogFilePath());
        
        try {
            backup.delete();
        } catch (Exception e) {
            
        }
        
        if (logFile.exists())
        {
            logFile.renameTo(backup);
            
            try {
                logFile.delete();
            } catch (Exception e) {
            
            }
        }
    }
    
    private static void deleteLogFile()
    {
        File logFile = new File(getLogFilePath());
        
        try {
            logFile.delete();
        } catch (Exception e) {
            
        }
    }
    
    private static FileWriter setupLogFileWriterIfNecessary()
    {
        File logFile = getLogFile();
        
        synchronized (_lock)
        {
            if (_logWriter == null)
            {
                try {
                    _logWriter = new FileWriter(logFile, true);
                } catch (Exception e) {
                    System.out.println("Logger could not start a log writer for the log file: " + e.toString());
                }
            }
            
            return _logWriter;
        }
    }
    
    private static <T> String generateText(T origin, String prefix, String text)
    {
        String reportingClass = origin != null ? origin.getClass().getSimpleName() : "Static";
        String textPrefix = generatePrefix(prefix);
        textPrefix = textPrefix + " " + reportingClass;
        return generateText(textPrefix, text);
    }
    
    private static <T> String generateText(String prefix, String text)
    {
        String textPrefix = generatePrefix(prefix);
        
        return textPrefix + ": " + text;
    }
    
    private static String generatePrefix(String prefix)
    {
        if (!DISPLAY_TIMESTAMPS)
        {
            return prefix;
        }
        
        return prefix.length() > 0 ? getTimestamp() + " " + prefix : getTimestamp();
    }
    
    private static String getTimestamp()
    {
        String pattern = "HH:mm:ss.SSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        return simpleDateFormat.format(new Date());
    }
}
