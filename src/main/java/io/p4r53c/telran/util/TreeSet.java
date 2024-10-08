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
public class TreeSet<T> implements SortedSet<T> {

    private Node<T> root;

    private Comparator<T> comparator;

    int size;

    private String printSymbol = " ";
    private int symbolsPerLevel = 2;

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
    }

    public void setPrintSymbol(String printSymbol) {
        this.printSymbol = printSymbol;
    }

    public void setSymbolsPerLevel(int symbolsPerLevel) {
        this.symbolsPerLevel = symbolsPerLevel;
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
     * {@inheritDoc}
     */
    @Override
    public T first() {
        return root == null ? null : getLeastNodeFrom(root).obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T last() {
        return root == null ? null : getGreatestFrom(root).obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T floor(T key) {
        return getNearestObj(key, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T ceiling(T key) {
        return getNearestObj(key, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SortedSet<T> subSet(T from, T to) {
        TreeSet<T> subSet = new TreeSet<>(comparator);

        Node<T> current = getNode(ceiling(from));

        while (isWithinBounds(current, to)) {
            subSet.add(current.obj);
            current = getNextNode(current);
        }

        return subSet;
    }

    /**
     * Prints the tree in a rotated manner.
     * The output is done to the console.
     */
    public void displayTreeRotated() {
        displayTreeRotated(root, 0);
    }

    /**
     * Prints the tree in a rotated manner with parent and children.
     * The output is done to the console.
     */
    public void displayTreeParentChildren() {
        displayTreeParentChildren(root, 0);
    }

    /**
     * Returns the width of the tree.
     * 
     * @return the width of the tree
     */
    public int width() {
        return width(root);
    }

    /**
     * Returns the height of the tree.
     * 
     * @return the height of the tree
     */
    public int height() {
        return height(root);
    }

    /**
     * Inverts the tree by reversing the comparator and inverting the tree in place.
     */
    public void inversion() {
        comparator = comparator.reversed();
        inversion(root);
    }

    /**
     * Balances the tree by sorting the elements and rebuilding the tree. The
     * resulting tree is a balanced tree with the same elements as the original
     * tree.
     */
    public void balance() {
        Node<T>[] nodes = getSortedNodesArray();
        root = balanceArray(nodes, 0, nodes.length - 1, null);
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
     * Returns the nearest value to the given key in the set. If the given key
     * is not present in the set, the nearest value is either the greatest value
     * less than the given key (if the given key is greater than the given
     * key), or the least value greater than the given key (if the given key is
     * less than the given key). If the given key is equal to the given key,
     * the given key is returned.
     *
     * @param key          the key to look up
     * @param isLowerBound true if the nearest value should be the greatest
     *                     value less than the given key, false if the nearest
     *                     value should be the least value greater than the
     *                     given key
     * @return the nearest value to the given key, or null if the set is empty
     */
    private T getNearestObj(T key, boolean isLowerBound) {
        T result = null;

        int comparatorResult = 0;

        Node<T> current = root;

        while (current != null && (comparatorResult = comparator.compare(key, current.obj)) != 0) {
            if ((comparatorResult < 0 && !isLowerBound) || (comparatorResult > 0 && isLowerBound)) {
                result = current.obj;
            }

            current = comparatorResult < 0 ? current.left : current.right;
        }

        return current == null ? result : current.obj;
    }

    /**
     * Checks if the current node is within the bounds of the subSet operation.
     * The current node is within the bounds if it is not null and its value is
     * less than the given upper bound.
     * 
     * @param current the current node
     * @param to      the upper bound of the subSet
     * @return true if the current node is within the bounds, false otherwise
     */
    private boolean isWithinBounds(Node<T> current, T to) {
        return current != null && comparator.compare(current.obj, to) < 0;
    }

    /**
     * Returns the next node in the tree set iteration order. If the given node
     * has a right child, the next node is the leftmost node in the right
     * subtree. Otherwise, the next node is the closest ancestor of the given
     * node that is to the right of the given node.
     * 
     * @param current the current node
     * @return the next node in the tree set iteration order
     */
    private Node<T> getNextNode(Node<T> current) {
        return getCurrentNode(current);
    }

    /**
     * Displays the tree rotated to the right by the given level, with the root
     * object displayed at the given level. The right subtree is displayed first,
     * then the root object, and finally the left subtree.
     * 
     * @param root  the root node of the tree
     * @param level the level of the root node
     */
    private void displayTreeRotated(Node<T> root, int level) {
        if (root != null) {
            displayTreeRotated(root.right, level + 1);
            displayRootObject(root.obj, level);
            displayTreeRotated(root.left, level + 1);
        }
    }

    /**
     * Displays the tree in parent-children order, with the root object displayed
     * at the given level. The root object is displayed first, then the left
     * subtree, and finally the right subtree.
     * 
     * @param root  the root node of the tree
     * @param level the level of the root node
     */
    private void displayTreeParentChildren(Node<T> root, int level) {
        if (root != null) {
            displayRootObject(root.obj, level);
            displayTreeParentChildren(root.left, level + 1);
            displayTreeParentChildren(root.right, level + 1);
        }
    }

    /**
     * Displays the given object at the given level in the tree. The object is
     * displayed indented with the given level, with each level indented by
     * {@link #symbolsPerLevel} symbols. The object is displayed with a newline
     * character after it.
     * 
     * @param obj   the object to be displayed
     * @param level the level of the object in the tree
     */
    private void displayRootObject(T obj, int level) {
        System.out.printf("%s%s%n", printSymbol.repeat(level * symbolsPerLevel), obj);
    }

    /**
     * Inverts the tree by swapping the left and right subtrees of each node.
     * 
     * @param root the root node of the tree
     */
    private void inversion(Node<T> root) {
        if (root != null) {

            // Swap left and right
            Node<T> node = root.left;
            root.left = root.right;
            root.right = node;

            inversion(root.left);
            inversion(root.right);
        }
    }

    /**
     * Calculates the width of the given tree. The width of the tree is defined
     * as the number of nodes in the widest level of the tree.
     * 
     * @param root the root of the tree
     * @return the width of the tree
     */
    private int width(Node<T> root) {
        int result = 0;

        if (root != null) {
            result = root.left == null && root.right == null ? 1 : width(root.left) + width(root.right);
        }

        return result;
    }

    /**
     * Calculates the height of the given tree. The height of the tree is
     * defined as the number of nodes along the longest path from the root
     * node down to the farthest leaf node.
     * 
     * @param root the root of the tree
     * @return the height of the tree
     */
    private int height(Node<T> root) {
        int result = 0;

        if (root != null) {
            int left = height(root.left);
            int right = height(root.right);
            result = 1 + Math.max(left, right);
        }

        return result;
    }

    /**
     * Creates a balanced tree from the given array of nodes. The tree is
     * balanced by choosing the middle element of the array as the root node
     * and recursively creating the left and right subtrees from the left and
     * right halves of the array, respectively.
     * 
     * @param array  the array of nodes to be balanced
     * @param left   the start index of the array
     * @param right  the end index of the array
     * @param parent the parent of the root node of the tree
     * @return the root node of the balanced tree
     */
    private Node<T> balanceArray(Node<T>[] array, int left, int right, Node<T> parent) {
        Node<T> rootNode = null;

        if (left <= right) {
            int middle = (left + right) / 2;

            rootNode = array[middle];
            rootNode.parent = parent;

            rootNode.left = balanceArray(array, left, middle - 1, rootNode);
            rootNode.right = balanceArray(array, middle + 1, right, rootNode);
        }

        return rootNode;
    }

    /**
     * Returns an array of nodes in the tree in ascending order. The array is
     * created by traversing the tree in an in-order fashion, starting from the
     * least node.
     * 
     * @return the array of nodes
     */
    @SuppressWarnings("unchecked")
    private Node<T>[] getSortedNodesArray() {
        Node<T>[] array = new Node[size];
        Node<T> current = getLeastNodeFrom(root);

        for (int i = 0; i < size; i++) {
            array[i] = current;
            current = getCurrentNode(current);
        }

        return array;
    }

}
