package io.p4r53c.telran.util;

import java.util.Iterator;
import java.util.Objects;

import java.util.NoSuchElementException;

/**
 * A doubly-linked list implementation of the {@link List} interface.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the list
 */
public class LinkedList<T> implements List<T> {

    private Node<T> head;

    private Node<T> tail;

    private int size = 0;

    private static class Node<T> {

        T obj;

        Node<T> next;

        Node<T> prev;

        public Node(T obj) {
            this.obj = obj;
        }
    }

    private class LinkedListIterator implements Iterator<T> {

        private Node<T> current = head;

        private Node<T> prev = null;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            prev = current;
            current = current.next;
            return prev.obj;
        }

        @Override
        public void remove() {
            if (prev == null)
                throw new IllegalStateException();

            removeNode(prev);

            prev = null;
        }
    }

    /**
     * Adds the given element to the end of the list.
     *
     * @param obj the element to be added
     * @return true if the element was added, false otherwise
     */
    @Override
    public boolean add(T obj) {
        Node<T> node = new Node<>(obj);
        addNode(node, size);
        return true;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

   

    /**
     * Returns an iterator over the elements of the list.
     *
     * @return an iterator over the elements of the list
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Adds the given element at the given position in the list.
     *
     * @param index the position where the element should be inserted
     * @param obj   the element to be inserted
     */
    @Override
    public void add(int index, T obj) {
        checkIndex(index, true);

        Node<T> node = new Node<>(obj);

        addNode(node, index);
    }

    /**
     * Removes the element at the given position in the list.
     *
     * @param index the position of the element to be removed
     * @return the removed element
     */
    @Override
    public T remove(int index) {
        checkIndex(index, false);

        Node<T> node = getNode(index);
        T obj = node.obj;
        removeNode(node);

        return obj;
    }

    /**
     * Returns the element at the given position in the list.
     *
     * @param index the position of the element to be returned
     * @return the element at the given position in the list
     */
    @Override
    public T get(int index) {
        checkIndex(index, true);
        return getNode(index).obj;
    }

    /**
     * Returns the index of the first occurrence of the given element in the list.
     *
     * @param pattern the element to be searched for
     * @return the index of the first occurrence of the given element in the list,
     *         or -1
     *         if the element is not found
     */
    @Override
    public int indexOf(T pattern) {
        Node<T> current = head;
        int index = 0;

        while (current != null && !Objects.equals(current.obj, pattern)) {
            index++;
            current = current.next;
        }

        return current != null ? index : -1;
    }

    /**
     * Returns the index of the last occurrence of the given element in the list.
     *
     * @param pattern the element to be searched for
     * @return the index of the last occurrence of the given element in the list
     * 
     */
    @Override
    public int lastIndexOf(T pattern) {
        Node<T> current = tail;

        int index = size - 1;

        while (index >= 0 && !Objects.equals(current.obj, pattern)) {
            index--;
            current = current.prev;
        }

        return index;
    }

    /**
     * Returns the node at the specified index in the list.
     *
     * @param index the index of the node to be returned
     * @return the node at the specified index in the list
     */
    private Node<T> getNode(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    /**
     * Returns the node at the specified index from the tail of the linked list.
     *
     * @param index the index of the node to be returned
     * @return the node at the specified index from the tail of the linked list
     */
    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;

        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }

        return current;
    }

    /**
     * Returns the node at the specified index from the head of the linked list.
     *
     * @param index the index of the node to be returned
     * @return the node at the specified index from the head of the linked list
     */
    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
    }

    /**
     * Adds a new node to the linked list at the specified index.
     *
     * @param node  the node to be added
     * @param index the index at which the node is to be added
     */
    private void addNode(Node<T> node, int index) {
        if (index == 0) {
            addHead(node);
        } else if (index == size) {
            addTail(node);
        } else {
            addMiddle(node, index);
        }

        size++;
    }

    /**
     * Inserts a new node at the specified index in the middle of the linked list.
     *
     * @param node  the node to be inserted
     * @param index the index at which the node is to be inserted
     */
    private void addMiddle(Node<T> node, int index) {
        Node<T> nodeBefore = getNode(index);
        Node<T> nodeAfter = nodeBefore.next;

        node.next = nodeBefore;
        node.prev = nodeAfter;

        nodeBefore.prev = node;
        nodeAfter.prev = node;
    }

    /**
     * Adds a new node to the end of the linked list.
     *
     * @param node the node to be added to the end of the list
     */
    private void addTail(Node<T> node) {
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }

    /**
     * Adds a new node to the head of the linked list.
     *
     * @param node the node to be added to the head of the list
     */
    private void addHead(Node<T> node) {
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    /**
     * Removes a node from the linked list.
     *
     * @param node the node to be removed
     * 
     */
    private void removeNode(Node<T> node) {
        if (head == node) {
            removeHead();
        } else if (tail == node) {
            removeTail();
        } else {
            removeMiddle(node);
        }

        node.next = null;
        node.prev = null;
        node.obj = null;

        size--;
    }

    /**
     * Removes a node from the middle of the linked list.
     *
     * @param node the node to be removed from the middle of the list
     * 
     */
    private void removeMiddle(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        prev.next = next;
        next.prev = prev;

    }

    /**
     * Removes the last node from the linked list.
     * 
     * This method updates the tail reference to point to the new last node.
     * 
     */
    private void removeTail() {
        tail = tail.prev;
        tail.next = null;

    }

    /**
     * Removes the first node from the linked list.
     *
     * If the list only contains one node, both the head and tail references are set
     * to null.
     * Otherwise, the head reference is updated to point to the next node, and the
     * previous reference of the new head is set to null.
     *
     */
    private void removeHead() {
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }
}
