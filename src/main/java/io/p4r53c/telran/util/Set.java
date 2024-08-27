package io.p4r53c.telran.util;

/**
 * An interface for sets of objects. It provides basic operations for
 * adding, removing elements and checking the collection's properties.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the set
 */
public interface Set<T> extends Collection<T> {

    /**
     * Returns the first occurrence of the given element in the set, if it is
     * present. If the set does not contain the element, null is returned.
     *
     * @param pattern the element to be searched for
     * @return the first occurrence of the given element, or null if the set
     *         does not contain the element
     */
    T get(Object pattern);
}
