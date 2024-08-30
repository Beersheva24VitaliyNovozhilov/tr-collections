package io.p4r53c.telran.util;

import java.util.Iterator;

import java.util.Comparator;

import java.util.NoSuchElementException;

/**
 * A Tree Set implementation of the {@link Set} interface.
 *
 * @author p4r53c
 *
 * @param <T> type of elements in the list
 */
public class TreeSet<T> implements Set<T> {

    private Node<T> root;
    private Comparator<T> comparator;
    int size;

    public TreeSet(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    public TreeSet() {
        this((Comparator<T>) Comparator.naturalOrder());
    }

    private static class Node<T> {
        T obj;
        Node<T> parent;
        Node<T> left;
        Node<T> right;

        Node(T obj) {
            this.obj = obj;
        }
    }

    private class TreeSetIterator implements Iterator<T> {

        Node<T> current;
        Node<T> prev;

        public TreeSetIterator() {
            current = getLeastNodeFrom(root);
        }

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
            current = getCurrentNode(current);

            return prev.obj;

        }

        @Override
        public void remove() {
            if (prev == null) {
                throw new IllegalStateException();
            }

            removeNode(prev);
            prev = null;
        }

        /**
         * Returns the least node from the given node in the tree.
         *
         * @param node the node to start from
         * @return the least node from the given node, or null if the tree is
         *         empty
         */
        private Node<T> getLeastNodeFrom(Node<T> node) {
            if (node != null) {

                while (node.left != null) {
                    node = node.left;
                }
            }

            return node;
        }

        /**
         * Returns the parent of the given node that is greater than the node
         * or null if the node is the greatest in the tree.
         *
         * @param current the node
         * @return the parent of the given node that is greater than the node
         *         or null if the node is the greatest in the tree
         */
        private Node<T> getGreaterParent(Node<T> current) {
            Node<T> parent = current.parent;

            while (parent != null && parent.right == current) {
                current = current.parent;
                parent = current.parent;
            }

            return parent;
        }

        /**
         * Returns the next node in the tree set iteration order.F
         *
         * If the given node has a right child, the next node is the leftmost node
         * in the right subtree. Otherwise, the next node is the closest ancestor
         * of the given node that is to the right of the given node.
         *
         * @param current the current node
         * @return the next node in the tree set iteration order
         */
        private Node<T> getCurrentNode(Node<T> current) {
            return current.right != null ? getLeastNodeFrom(current.right) : getGreaterParent(current);
        }
    }

    /**
     * Adds the given element to the tree set.
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

            if (root == null) {
                addRoot(node);
            } else {
                addAfterParent(node);
            }

            size++;
        }

        return result;
    }

    /**
     * Removes the first occurrence of given element in the set.
     *
     * @param pattern the element to be removed
     * @return true if the element was found and removed, false otherwise
     */
    @Override
    public boolean remove(T pattern) {
        boolean result = false;
        Node<T> node = getNode(pattern);
        if (node != null) {
            removeNode(node);
            result = true;
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
        return getNode(pattern) != null;
    }

    /**
     * Returns an iterator over the elements of the set.
     *
     * @return an iterator over the elements of the set
     */
    @Override
    public Iterator<T> iterator() {
        return new TreeSetIterator();
    }

    /**
     * Returns the first occurrence of the given element in the set, if it is
     * present. If the set does not contain the element, null is returned.
     *
     * @param pattern the element to be searched for
     * @return the first occurrence of the given element, or null if the set
     *         does not contain the element
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(Object pattern) {
        Node<T> node = getNode((T) pattern);
        return node == null ? null : node.obj;
    }

    /**
     * Adds a node after its parent in the tree.
     *
     * @param node the node to be added
     */
    private void addAfterParent(Node<T> node) {
        Node<T> parent = getParent(node.obj);

        if (comparator.compare(node.obj, parent.obj) > 0) {
            parent.right = node;
        } else {
            parent.left = node;
        }

        node.parent = parent;
    }

    /**
     * Adds a node to the root of the tree.
     *
     * @param node the node to be added as the root of the tree
     */
    private void addRoot(Node<T> node) {
        root = node;
    }

    /**
     * Returns the node that is equal to the given pattern, or null if
     * the pattern is not found in the tree.
     *
     * @param pattern the element to be searched for
     * @return the node that is equal to the given pattern, or null
     *         if the pattern is not found in the tree
     */
    private Node<T> getNode(T pattern) {
        Node<T> result = null;
        Node<T> node = getParentOrNode(pattern);

        // We should check Node for Null and equals to pattern!!!
        if (node != null && comparator.compare(node.obj, pattern) == 0) {
            result = node;
        }

        return result;
    }

    /**
     * Returns the parent of the node that is equal to the given pattern, or null
     * if the pattern is not found in the tree.
     *
     * @param pattern the element to be searched for
     * @return the parent of the node that is equal to the given pattern, or null
     *         if the pattern is not found in the tree
     */
    private Node<T> getParent(T pattern) {
        Node<T> node = getParentOrNode(pattern);
        int comparatorResult = comparator.compare(pattern, node.obj);
        return comparatorResult == 0 ? null : node;
    }

    /**
     * Returns the node that is equal to the given pattern, or its parent if
     * the pattern is not found in the tree.
     *
     * @param pattern the element to be searched for
     * @return the node that is equal to the given pattern, or its parent if
     *         the pattern is not found in the tree
     */
    private Node<T> getParentOrNode(T pattern) {
        Node<T> current = root;
        Node<T> parent = null;
        int comparatorResult = 0;

        while (current != null && (comparatorResult = comparator.compare(pattern, current.obj)) != 0) {
            parent = current;
            current = comparatorResult > 0 ? current.right : current.left;
        }

        return current == null ? parent : current;
    }

    /**
     * Removes a node from the tree.
     *
     * If the node has two children, it is a junction and we should find its
     * substitute in the left subtree, and then remove the substitute from the
     * tree.
     *
     * If the node has one or zero children, we should get its parent and
     * substitute the node with its child in the parent's left or right
     * reference.
     *
     * @param node the node to be removed
     */
    private void removeNode(Node<T> node) {
        if (node.left != null && node.right != null) {
            removeJunction(node);
        } else {
            removeNonJunction(node);
        }

        size--;
    }

    /**
     * Removes a node from the tree that has two children, i.e. a junction.
     * 
     * The logic is as follows:
     * 1. Find the greatest node from the left subtree of the node to be removed
     * (the substitute node).
     * 2. Replace the node to be removed with the substitute node in the parent
     * of the node to be removed.
     * 3. Remove the substitute node from the tree by calling
     * {@link #removeNonJunction(Node)}.
     * 
     * @param node the node to be removed
     */
    private void removeJunction(Node<T> node) {
        Node<T> substitute = getGreatestFrom(node.left);

        node.obj = substitute.obj;

        removeNonJunction(substitute);
    }

    /**
     * Removes a node that has either one or zero children from the tree.
     *
     * The logic is as follows:
     * 1. Get the parent of the node to be removed.
     * 2. Get the child of the node to be removed (if it has any).
     * 3. Replace the node to be removed with its child in its parent's left or
     * right reference.
     * 4. If the node to be removed has a child, set the parent of the child to
     * the parent of the node to be removed.
     * 5. Clean up the node to be removed by setting all its references to null.
     */
    private void removeNonJunction(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> child = node.left != null ? node.left : node.right;

        if (parent == null) {
            root = child;
        } else if (node == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        if (child != null) {
            child.parent = parent;
        }

        // Same cleanup for GC as in LinkedList
        node.obj = null;
        node.parent = null;
        node.left = null;
        node.right = null;
    }

    /**
     * Returns the node with the greatest value from the given node.
     *
     * @param node the node from which to search for the greatest node
     * @return the node with the greatest value, or null if the given node is null
     */
    private Node<T> getGreatestFrom(Node<T> node) {
        if (node != null) {
            while (node.right != null) {
                node = node.right;
            }
        }

        return node;
    }
}
