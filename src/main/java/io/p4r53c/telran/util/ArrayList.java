package io.p4r53c.telran.util;

import java.util.Iterator;

import java.util.Arrays;

import java.util.NoSuchElementException;

/**
 * Array-based implementation of {@link List} interface.
 * 
 * @author p4r53c
 *
 * @param <T> type of elements in the list
 */
public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 16;

    private Object[] array;

    private int size;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        array = new Object[capacity];
    }

    /**
     * Adds given element to the end of the list.
     *
     * @param obj the element to be added
     * @return true
     */
    @Override
    public boolean add(T obj) {
        if (size == array.length) {
            reallocate();
        }
        array[size++] = obj;
        return true;
    }

    /**
     * Removes the first occurrence of given element in the list.
     *
     * @param pattern the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    @Override
    public boolean remove(T pattern) {
        boolean removed = false;
        int index = indexOf(pattern);
        if (index >= 0) {
            remove(index);
            removed = true;
        }
        return removed;
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
     * Returns true if the list contains given element.
     *
     * @param pattern the element to be searched for
     * @return true if the list contains given element, false otherwise
     */
    @Override
    public boolean contains(T pattern) {
        return indexOf(pattern) >= 0;
    }

    /**
     * Returns an iterator over the elements of the list.
     *
     * @return an iterator over the elements of the list
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int current = 0;
            boolean hasNext = false;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                hasNext = true;
                return (T) array[current++];
            }

            @Override
            public void remove() {
                if (!hasNext) {
                    throw new IllegalStateException();
                }
                ArrayList.this.remove(--current);
                hasNext = false;
            }
        };
    }

    /**
     * Inserts given element at given position in the list.
     *
     * @param index the position where the element should be inserted
     * @param obj   the element to be inserted
     */
    @Override
    public void add(int index, T obj) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        reallocate();

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;

    }

    /**
     * Removes the element at given position in the list.
     *
     * @param index the position of the element to be removed
     * @return the removed element
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        @SuppressWarnings("unchecked")
        T removedObject = (T) array[index];

        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        size--;

        return removedObject;
    }

    /**
     * Returns the element at given position in the list.
     *
     * @param index the position of the element to be returned
     * @return the element at given position in the list
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (T) array[index];
    }

    /**
     * Returns the index of the first occurrence of given element in the list.
     *
     * @param pattern the element to be searched for
     * @return the index of the first occurrence of given element in the list, or -1
     *         if the element is not found
     */
    @Override
    public int indexOf(T pattern) {
        int index = 0;
        while (index < size && !equals(array[index], pattern)) {
            index++;
        }
        return index == size ? -1 : index;
    }

    /**
     * Returns the index of the last occurrence of given element in the list.
     *
     * @param pattern the element to be searched for
     * @return the index of the last occurrence of given element in the list, or -1
     *         if the element is not found
     */
    @Override
    public int lastIndexOf(T pattern) {
        int index = size - 1;
        while (index >= 0 && !equals(array[index], pattern)) {
            index--;
        }
        return index;
    }

    /**
     * Doubles the capacity of the list if it is full.
     */
    private void reallocate() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    /**
     * Compares two objects for equality.
     *
     * @param obj1 the first object
     * @param obj2 the second object
     * @return true if the objects are equal, false otherwise
     */
    private boolean equals(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }
}
