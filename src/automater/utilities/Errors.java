/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.utilities;

import com.sun.istack.internal.NotNull;

/**
 * Defines commonly used errors.
 * 
 * Calling the methods will always cause an exception to be thrown.
 * 
 * @author Bytevi
 */
public class Errors {
    public static void throwUnknownError(@NotNull String description) throws Exception
    {
        throw new Exception(description);
    }
    
    public static void throwInvalidArgument(@NotNull String description)
    {
        throw new IllegalArgumentException(description);
    }
    
    public static void throwIndexOutOfBounds(@NotNull String description)
    {
        throw new IndexOutOfBoundsException(description);
    }
    
    public static void throwIllegalMathOperation(@NotNull String description)
    {
        throw new ArithmeticException(description);
    }
    
    public static void throwNotImplemented(@NotNull String description) throws RuntimeException
    {
        throw new UnsupportedOperationException(description);
    }
    
    public static void throwIllegalStateError(@NotNull String description) throws RuntimeException
    {
        throw new IllegalStateException(description);
    }
    
    public static void throwInternalLogicError(@NotNull String description) throws RuntimeException
    {
        throw new RuntimeException(description);
    }
    
    public static void throwCannotStartTwice(@NotNull String description) throws RuntimeException
    {
        throw new RuntimeException(description);
    }
    
    public static void throwSerializationFailed(@NotNull String description) throws RuntimeException
    {
        throw new RuntimeException(description);
    }
    
    public static void throwClassNotFound(@NotNull String description) throws ClassNotFoundException
    {
        throw new ClassNotFoundException(description);
    }
}
