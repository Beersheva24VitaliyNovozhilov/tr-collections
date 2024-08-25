package io.p4r53c.telran.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.function.Predicate;

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
     * Removes elements from the collection that match the given predicate.
     *
     * @param predicate a predicate that determines whether an element should be
     *                  removed
     * @return true if at least one element was removed, false otherwise
     */
    default boolean removeIf(Predicate<T> predicate) {
        int size = size();
        Iterator<T> iterator = iterator();

        while (iterator.hasNext()) {
            T obj = iterator.next();

            if (predicate.test(obj)) {
                iterator.remove();
            }
        }

        return size() < size;
    }

    /**
     * Clears the entire collection by removing all elements.
     *
     * @return true if the collection was cleared, false otherwise
     */
    default void clear() {
        removeIf(n -> true);
    }

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
