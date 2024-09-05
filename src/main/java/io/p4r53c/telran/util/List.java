package io.p4r53c.telran.util;

/**
 * A collection of elements that can be accessed by index.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the list
 */
public interface List<T> extends Collection<T> {

    /**
     * Adds given element at given position in the list.
     *
     * @param index the position where the element should be inserted
     * @param obj   the element to be inserted
     */
    void add(int index, T obj);

    /**
     * Removes the element at given position in the list.
     *
     * @param index the position of the element to be removed
     * @return the removed element
     */
    T remove(int index);

    /**
     * Returns the element at given position in the list.
     *
     * @param index the position of the element to be returned
     * @return the element at given position in the list
     */
    T get(int index);

    /**
     * Returns the index of the first occurrence of given element in the list.
     *
     * @param pattern the element to be searched for
     * @return the index of the first occurrence of given element in the list, or -1
     *         if the element is not found
     */
    int indexOf(T pattern);

    /**
     * Returns the index of the last occurrence of given element in the list.
     *
     * @param pattern the element to be searched for
     * @return the index of the last occurrence of given element in the list, or -1
     *         if the element is not found
     */
    int lastIndexOf(T pattern);

    /**
     * Checks if the given index is within the bounds of the list.
     *
     * @param index       the index to be checked
     * @param isInclusive whether the upper bound should be inclusive
     */
    default void checkIndex(int index, boolean isInclusive) {
        int limit = isInclusive ? size() : size() - 1;

        if (index < 0 || index > limit) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * Removes the first occurrence of given element in the list.
     *
     * @param pattern the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    @Override
    default boolean remove(T pattern) {
        boolean removed = false;

        int index = indexOf(pattern);

        if (index >= 0) {
            remove(index);
            removed = true;
        }

        return removed;
    }

    /**
     * Returns true if the list contains the given element.
     *
     * @param pattern the element to be searched for
     * @return true if the list contains the given element, false otherwise
     */
    @Override
    default boolean contains(T pattern) {
        return indexOf(pattern) > -1;
    }
}
