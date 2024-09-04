package io.p4r53c.telran.util;

/**
 * A class that implements a hash-based map of objects.
 *
 * @author p4r53c
 *
 * @param <K> type of keys in the map
 * @param <V> type of values in the map
 */
public class HashMap<K, V> extends AbstractMap<K, V> {

    public HashMap() {
        set = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<K> getEmptyKeySet() {
        return new HashSet<>();
    }
}
