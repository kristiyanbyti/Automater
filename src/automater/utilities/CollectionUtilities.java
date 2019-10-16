/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.utilities;

import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Defines commonly used collection methods for copying, wrapping, etc.
 * 
 * @author Bytevi
 */
public class CollectionUtilities {
    public static <T> @NotNull List<T> copy(@NotNull List<T> data)
    {
        return new ArrayList<>(data);
    }
    
    public static <T> @NotNull Set<T> copy(@NotNull Set<T> data)
    {
        return new HashSet<>(data);
    }
    
    public static <T> @NotNull List<T> copyAsImmutable(@NotNull List<T> data)
    {
        return Collections.unmodifiableList(copy(data));
    }
    
    public static <T> @NotNull Set<T> copyAsImmutable(@NotNull Set<T> data)
    {
        return Collections.unmodifiableSet(copy(data));
    }
    
    public static <T> @NotNull List<T> copyAsReversed(@NotNull List<T> data)
    {
        List<T> dataCopy = CollectionUtilities.copy(data);
        Collections.reverse(dataCopy);
        return dataCopy;
    }
}
