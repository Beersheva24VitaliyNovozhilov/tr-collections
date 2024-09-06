package io.p4r53c.telran.util;

import java.util.Iterator;

import io.p4r53c.telran.util.LinkedList.Node;

/**
 * A set implementation that combines the advantages of a linked list and a
 * hash table. It uses a linked list to store elements and a hash table to
 * provide fast access to the elements. The elements are stored in the same
 * order as they are added to the set.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the set
 */
public class LinkedHashSet<T> implements Set<T> {

    LinkedList<T> list = new LinkedList<>();

    HashMap<T, Node<T>> map = new HashMap<>();

    /**
     * An iterator over the elements of the set.
     */
    private class LinkedHashSetIterator implements Iterator<T> {

        private Iterator<T> iterator;

        private T obj;

        public LinkedHashSetIterator() {
            iterator = list.iterator();
        }

        @Override
        public boolean hasNext() {

            return iterator.hasNext();
        }

        @Override
        public T next() {
            obj = iterator.next();
            return obj;
        }

        @Override
        public void remove() {
            iterator.remove();
            map.remove(obj);
        }
    }

    /**
     * Adds a new element to the set.
     *
     * @param obj the element to be added
     * @return true if the element was added, false otherwise
     */
    @Override
    public boolean add(T obj) {
        boolean result = false;

        if (!contains(obj)) {
            result = true;

            Node<T> node = new Node<>(obj);

            map.put(obj, node);
            list.addNode(node, list.size());
        }

        return result;
    }

    /**
     * Removes the element from the set.
     *
     * @param pattern the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    @Override
    public boolean remove(T pattern) {
        boolean result = false;

        Node<T> node = map.get(pattern);

        if (node != null) {
            result = true;

            list.removeNode(node);
            map.remove(pattern);
        }

        return result;
    }

    /**
     * Returns the element from the set.
     *
     * @param pattern the element to be returned
     * @return the element from the set, or null if the element was not found
     */
    @Override
    public T get(Object pattern) {
        return map.get(pattern) == null ? null : map.get(pattern).obj;
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns true if the set is empty.
     *
     * @return true if the set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns true if the set contains the element.
     *
     * @param pattern the element to be checked
     * @return true if the set contains the element, false otherwise
     */
    @Override
    public boolean contains(T pattern) {
        return map.get(pattern) != null;
    }

    /**
     * Returns an iterator over the elements of the set.
     *
     * @return an iterator over the elements of the set
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
    }
}
