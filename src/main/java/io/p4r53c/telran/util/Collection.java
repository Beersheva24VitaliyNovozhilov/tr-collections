package io.p4r53c.telran.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An interface for collections of objects. It provides basic operations for
 * adding, removing elements and checking the collection's properties.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the collection
 */
public interface Collection<T> extends Iterable<T> {

    /**
     * Adds given element to the end of the collection.
     *
     * @param obj the element to be added
     * @return true if the element was added, false otherwise
     */
    boolean add(T obj);

    /**
     * Removes the first occurrence of given element in the collection.
     *
     * @param pattern the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    boolean remove(T pattern);

    /**
     * Returns the number of elements in the collection.
     *
     * @return the number of elements in the collection
     */
    int size();

    /**
     * Returns true if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns true if the collection contains given element.
     *
     * @param pattern the element to be searched for
     * @return true if the collection contains given element, false otherwise
     */
    boolean contains(T pattern);

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream over the elements in this collection
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Returns a parallel Stream with this collection as its source.
     *
     * @return a parallel Stream over the elements in this collection
     */
    default Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
