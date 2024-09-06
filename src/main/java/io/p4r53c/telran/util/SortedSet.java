package io.p4r53c.telran.util;

/**
 * A sorted set of objects. The set is sorted by the natural order of the
 * objects
 * or by a custom comparator.
 * 
 * @author p4r53c
 * 
 * @param <T> type of elements in the set
 */
public interface SortedSet<T> extends Set<T> {

    /**
     * Returns the first (smallest) element in this set.
     * 
     * @return the first element in this set
     */
    T first();

    /**
     * Returns the last (largest) element in this set.
     * 
     * @return the last element in this set
     */
    T last();

    /**
     * Returns the greatest element in this set less than or equal to the given
     * element, or null if there is no such element.
     * 
     * @param key the element to compare with
     * @return the greatest element in this set less than or equal to the given
     *         element, or null if there is no such element
     */
    T floor(T key);

    /**
     * Returns the least element in this set greater than or equal to the given
     * element, or null if there is no such element.
     * 
     * @param key the element to compare with
     * @return the least element in this set greater than or equal to the given
     *         element, or null if there is no such element
     */
    T ceiling(T key);

    /**
     * Returns a view of the portion of this set whose elements range from
     * <tt>fromElement</tt> to <tt>toElement</tt>. If <tt>fromElement</tt> and
     * <tt>toElement</tt> are equal, the returned set contains only one element
     * (the one equal to <tt>fromElement</tt> and <tt>toElement</tt>). The
     * returned set is backed by this set, so changes in the returned set are
     * reflected in this set, and vice versa.
     * 
     * @param from the lowest element to be included in the returned set
     * @param to   the highest element to be included in the returned set
     * @return a view of the specified range of this set
     */
    SortedSet<T> subSet(T from, T to);
}
