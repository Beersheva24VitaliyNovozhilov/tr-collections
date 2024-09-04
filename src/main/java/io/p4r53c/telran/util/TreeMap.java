package io.p4r53c.telran.util;

/**
 * A class implementing the Map interface based on a tree structure.
 * 
 * @author p4r53c
 * 
 * @param <K> type of keys in the map
 * @param <V> type of values in the map
 */
public class TreeMap<K, V> extends AbstractMap<K, V> {

    public TreeMap() {
        set = new TreeSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<K> getEmptyKeySet() {
        return new TreeSet<>();
    }
}
