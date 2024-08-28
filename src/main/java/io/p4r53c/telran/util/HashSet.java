package io.p4r53c.telran.util;

import java.util.Iterator;

import java.util.NoSuchElementException;

/**
 * Hash-based implementation of {@link Set} interface.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the set
 */
public class HashSet<T> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    List<T>[] hashTable;

    float loadFactor;

    int size;

    @SuppressWarnings("unchecked")
    public HashSet(int hashTableLength, float loadFactor) {
        hashTable = new List[hashTableLength];
        this.loadFactor = loadFactor;
    }

    public HashSet() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    private class HashSetIterator implements Iterator<T> {

        Iterator<T> current;
        Iterator<T> prev;
        int index;

        public HashSetIterator() {
            index = 0;
            current = extractIterator(0);
            setBoundary();
        }

        @Override
        public boolean hasNext() {
            return index < hashTable.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            prev = current;
            T obj = current.next();

            setBoundary();

            return obj;
        }

        @Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException();
            }

            prev.remove();
            prev = null;
            size--;
        }

        /**
         * Returns an iterator of the list at the given index or null if the list
         * is null.
         *
         * @param index the index of the list
         * @return an iterator of the list or null if the list is null
         */
        private Iterator<T> extractIterator(int index) {
            List<T> list = hashTable[index];
            return list == null ? null : list.iterator();
        }

        /**
         * Sets the boundary for the iterator by moving to the next index or
         * beyond the boundary if there are no more elements in the list. If the
         * current index is beyond the boundary, or the current list is null or
         * does not have a next element, the index is incremented.
         */
        private void setBoundary() {
            int boundary = hashTable.length - 1;

            while (index < boundary && (current == null || !current.hasNext())) {
                index++;
                current = extractIterator(index);
            }

            if (index == boundary && (hashTable[index] == null || !current.hasNext())) {
                index++;
            }
        }
    }

    /**
     * Adds the given object to the hash set.
     * <p>
     * If the object is not already in the hash set, it is added to a new list
     * at the appropriate index in the hash table. If the object is already in
     * the hash set, the hash set is unchanged. If the load factor of the hash
     * table exceeds the specified threshold, the hash table is reallocated.
     * <p>
     * 
     * @param obj the object to be added to the hash set
     * @return true if the object was added to the hash set, false otherwise
     */
    @Override
    public boolean add(T obj) {
        boolean result = false;

        if (!contains(obj)) {
            result = true;

            if (size >= hashTable.length * loadFactor) {
                hashTableReallocation();
            }

            addObjInHashTable(obj, hashTable);
            size++;
        }

        return result;
    }

    /**
     * Removes the first occurrence of the given element from the list, if it is
     * present. If the list does not contain the element, the list is unchanged.
     *
     * @param pattern the element to be removed from the list, if present
     * @return true if the list contained the specified element, false
     *         otherwise
     */
    @Override
    public boolean remove(T pattern) {
        boolean result = contains(pattern);

        if (result) {
            int index = getIndex(pattern, hashTable.length);
            hashTable[index].remove(pattern);
            size--;
        }

        return result;
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if the set is empty.
     *
     * @return true if the set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if the set contains the given element.
     *
     * @param pattern the element to be searched for
     * @return true if the set contains the given element, false otherwise
     */
    @Override
    public boolean contains(T pattern) {
        int index = getIndex(pattern, hashTable.length);
        List<T> list = hashTable[index];
        return list != null && list.contains(pattern);
    }

    /**
     * Returns an iterator over the elements of this set.
     *
     * @return an iterator over the elements of this set
     */
    @Override
    public Iterator<T> iterator() {
        return new HashSetIterator();
    }

    /**
     * Returns the first occurrence of the given element in this set.
     *
     * @param pattern the element to be searched for
     * @return the first occurrence of the given element in this set, or
     *         null if the element is not found
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(Object pattern) {
        T result = null;
        int index = getIndex((T) pattern, hashTable.length);

        List<T> list = hashTable[index];

        if (list != null) {
            Iterator<T> iterator = list.iterator();

            while (iterator.hasNext()) {
                T element = iterator.next();
                
                if (element.equals(pattern)) {
                    result = element;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Adds the given object to the hash table.
     * <p>
     * If the object is not already in the hash table, it is added to a new list
     * at the appropriate index in the hash table. If the object is already in the
     * hash table, it is added to the existing list at the appropriate index in the
     * hash table.
     * <p>
     * 
     * @param obj   the object to add to the hash table
     * @param table the hash table to add the object to
     */
    private void addObjInHashTable(T obj, List<T>[] table) {
        int index = getIndex(obj, table.length);

        List<T> list = table[index];

        if (list == null) {
            list = new ArrayList<>(3);
            table[index] = list;
        }

        list.add(obj);
    }

    /**
     * Returns the index in the hash table where an element with the given hash
     * code should be placed. The index is calculated as the absolute value of the
     * remainder of the hash code divided by the length of the hash table.
     *
     * @param obj    the element to calculate the index for
     * @param length the length of the hash table
     * @return the index in the hash table where the element should be placed
     */
    private int getIndex(T obj, int length) {
        int hashCode = obj.hashCode();
        return Math.abs(hashCode % length);
    }

    /**
     * Reallocates the hash table by creating a new one with twice the size and
     * rehashing all elements into the new table. This method is called when the
     * load factor of the hash table exceeds the specified threshold.
     * 
     * List is not cleared after reallocation. See
     * {@link HashSetTest#testListClearAfterReallocation}.
     */
    @SuppressWarnings("unchecked")
    private void hashTableReallocation() {
        List<T>[] tempTable = new List[hashTable.length * 2];

        for (List<T> list : hashTable) {
            if (list != null) {
                list.forEach(obj -> addObjInHashTable(obj, tempTable));
                list.clear();
            }
        }

        hashTable = tempTable;
    }

}
