package io.p4r53c.telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public class TreeSetStandard<T> implements SortedSet<T> {

    java.util.TreeSet<T> treeSet;

    private Comparator<T> comparator;

    public TreeSetStandard(Comparator<T> comparator) {
        this.comparator = comparator;
        treeSet = new java.util.TreeSet<>(comparator);
    }

    @SuppressWarnings("unchecked")
    TreeSetStandard() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    /**
     * Returns the first occurrence of the given element in the set, if it is
     * present. If the set does not contain the element, null is returned.
     *
     * @param pattern the element to be searched for
     * @return the first occurrence of the given element, or null if the set
     *         does not contain the element
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(Object pattern) {
        T result = null;
        T floor = treeSet.floor((T) pattern);

        if (floor != null) {
            result = Objects.equals(pattern, floor) ? floor : null;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T obj) {
        return treeSet.add(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(T pattern) {
        return treeSet.remove(pattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return treeSet.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T pattern) {
        return treeSet.contains(pattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<T> iterator() {
        return treeSet.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T first() {
        return treeSet.first();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T last() {
        return treeSet.last();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T floor(T key) {
        return treeSet.floor(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T ceiling(T key) {
        return treeSet.ceiling(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<T> subSet(T from, T to) {
        TreeSet<T> result = new TreeSet<>(comparator);
        treeSet.subSet(from, to).forEach(result::add);
        return result;
    }
}
