package io.p4r53c.telran.util;

import java.util.Iterator;

import java.util.Arrays;
import java.util.Objects;

import java.util.function.Predicate;

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
        checkIndex(index, true);

        if (size == array.length) {
            reallocate();
        }

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
     * Removes elements from the collection that match the given predicate.
     * 
     * Algorithm complexity: O(n) - two pointers, left - starting from the
     * beginning, right - passing through the array
     * 
     * Memory complexity: O(1) - in-place, no additional memory allocated
     * 
     * I think it looks like deque: https://en.wikipedia.org/wiki/Double-ended_queue
     *
     * @param predicate a predicate to test elements for removal
     * @return true if any elements were removed, false otherwise
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean removeIf(Predicate<T> predicate) {
        int left = 0;
        int right = 0;

        while (right < size) {
            if (predicate.test((T) array[right])) {

                right++;
            } else {
                array[left++] = array[right++];
            }
        }

        int removed = size - left;
        size = left;

        return removed > 0;
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
        while (index < size && !Objects.equals(array[index], pattern)) {
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
        while (index >= 0 && !Objects.equals(array[index], pattern)) {
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
}
