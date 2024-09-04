package io.p4r53c.telran.util;

/**
 * A interface for maps of objects. It provides basic operations for
 * adding, removing elements and checking the collection's properties.
 * 
 * @author p4r53c
 * 
 * @param <K> type of keys in the map
 * @param <V> type of values in the map
 */
public interface Map<K, V> {

    /**
     * An entry in a map. It provides methods to get and set key and value.
     * 
     * @author p4r53c
     * 
     * @param <K> type of key in the entry
     * @param <V> type of value in the entry
     */
    public static class Entry<K, V> implements Comparable<Entry<K, V>> {

        private final K key;

        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key of the entry.
         * 
         * @return the key of the entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value of the entry.
         * 
         * @return the value of the entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value of the entry.
         * 
         * @param value the new value of the entry
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Compares the specified object with this entry for equality.
         * 
         * I think this is more type-safe.
         * 
         * @param o object to be compared for equality with this entry
         * @return {@code true} if the specified object is equal to this entry
         */
        @Override
        public boolean equals(Object o) {
            return this == o || (o instanceof Entry<?, ?> && key.equals(((Entry<?, ?>) o).key));
        }

        /**
         * Compares the given entry to this entry for order. The order is
         * determined by the natural order of the key.
         * 
         * @param o the entry to compare to this entry
         * @return a negative integer, zero, or a positive integer as this entry
         *         is less than, equal to, or greater than the given entry
         */
        @SuppressWarnings("unchecked")
        @Override
        public int compareTo(Entry<K, V> o) {
            return ((Comparable<K>) key).compareTo(o.getKey());
        }

        /**
         * Returns the hash code of this entry.
         * 
         * @return the hash code of this entry
         */
        @Override
        public int hashCode() {
            return key.hashCode();
        }

    }

    /**
     * Returns the value associated with the given key or null if the map does
     * not contain the key.
     * 
     * @param key the key to look up
     * @return the value associated with the key or null if the key is not
     *         present
     */
    V get(Object key);

    /**
     * Associates the given key with the given value in the map. If the key is
     * already present in the map, the value is replaced.
     * 
     * @param key   the key to associate with the value
     * @param value the value to associate with the key
     * @return the previous value associated with the key or null if the key was
     *         not present
     */
    V put(K key, V value);

    /**
     * Removes the entry with the given key from the map.
     * 
     * @param key the key of the entry to remove
     * @return the value associated with the key or null if the key was not
     *         present
     */
    V remove(K key);

    /**
     * Returns true if the map contains the given key.
     * 
     * @param key the key to look up
     * @return true if the map contains the key, false otherwise
     */
    boolean containsKey(Object key);

    /**
     * Returns true if the map contains the given value.
     * 
     * @param value the value to look up
     * @return true if the map contains the value, false otherwise
     */
    boolean containsValue(Object value);

    /**
     * Returns a set of keys in the map.
     * 
     * @return a set of keys in the map
     */
    Set<K> keySet();

    /**
     * Returns a set of entries in the map.
     * 
     * @return a set of entries in the map
     */
    Set<Entry<K, V>> entrySet();

    /**
     * Returns a collection of values in the map.
     * 
     * @return a collection of values in the map
     */
    Collection<V> values();

    /**
     * Returns the number of elements in the map.
     * 
     * @return the number of elements in the map
     */
    int size();

    /**
     * Returns true if the map is empty.
     * 
     * @return true if the map is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the value associated with the given key or the default value if
     * the key is not present in the map.
     * 
     * @param key          the key to look up
     * @param defaultValue the default value to return if the key is not present
     * @return the value associated with the key or the default value if the key
     *         is not present
     */
    default V getOrDefault(K key, V defaultValue) {
        V result = get(key);
        return result == null ? defaultValue : result;
    }

    /**
     * Associates the given key with the given value in the map if the key is
     * not already present in the map.
     * 
     * @param key   the key to associate with the value
     * @param value the value to associate with the key
     * @return the previous value associated with the key or null if the key was
     *         not present
     */
    default V putIfAbsent(K key, V value) {
        V result = get(key);
        return result == null ? put(key, value) : result;
    }
}
