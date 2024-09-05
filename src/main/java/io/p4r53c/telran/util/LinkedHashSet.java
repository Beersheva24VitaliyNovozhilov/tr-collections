package io.p4r53c.telran.util;

import java.util.Iterator;

import io.p4r53c.telran.util.LinkedList.Node;

public class LinkedHashSet<T> implements Set<T> {

    LinkedList<T> list = new LinkedList<>();

    HashMap<T, Node<T>> map = new HashMap<>();

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

    @Override
    public T get(Object pattern) {
        return map.get(pattern) == null ? null : map.get(pattern).obj;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(T pattern) {
        return map.get(pattern) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedHashSetIterator();
    }
}
