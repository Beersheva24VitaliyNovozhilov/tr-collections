package io.p4r53c.telran.util;

/**
 * An abstract implementation of the {@link Map} interface. This class provides
 * most of the methods of the interface, leaving the implementation of the
 * {@link Set} of entries to the subclasses.
 *
 * @author p4r53c
 *
 * @param <K> type of keys in the map
 * @param <V> type of values in the map
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    protected Set<Entry<K, V>> set;

    /**
     * Returns an empty set of keys.
     *
     * @return an empty set of keys
     */
    protected abstract Set<K> getEmptyKeySet();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        V result = null;

        Entry<K, V> pattern = new Entry<>((K) key, null);
        Entry<K, V> entry = set.get(pattern);

        if (entry != null) {
            result = entry.getValue();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        V result = null;

        Entry<K, V> pattern = new Entry<>(key, null);
        Entry<K, V> entry = set.get(pattern);

        if (entry == null) {
            set.add(new Entry<>(key, value));
        } else {
            result = entry.getValue();
            entry.setValue(value);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key) {
        V result = null;

        Entry<K, V> pattern = new Entry<>(key, null);
        Entry<K, V> entry = set.get(pattern);

        if (entry != null) {
            result = entry.getValue();
            set.remove(entry);

        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean containsKey(Object key) {
        return key != null && set.get(new Entry<>((K) key, null)) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        return set.stream()
                .anyMatch(e -> e.getValue().equals(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {

        Set<K> result = getEmptyKeySet();

        for (Entry<K, V> entry : set) {
            result.add(entry.getKey());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        Collection<V> result = new ArrayList<>();

        for (Entry<K, V> entry : set) {
            result.add(entry.getValue());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return set.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }
}
