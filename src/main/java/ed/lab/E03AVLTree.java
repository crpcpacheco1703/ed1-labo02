package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {

    private class Node {
        T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;
    private int size = 0;
    private final Comparator<T> comparator;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node node, T value) {
        if (node == null) {
            size++;
            return new Node(value);
        }

        if (comparator.compare(value, node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        updateHeight(node);
        return balance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private Node delete(Node node, T value) {
        if (node == null) return null;

        if (comparator.compare(value, node.value) < 0) {
            node.left = delete(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = delete(node.right, value);
        } else {
            size--;
            if (node.left == null || node.right == null) {
                return (node.left != null) ? node.left : node.right;
            } else {
                Node minNode = findMin(node.right);
                node.value = minNode.value;
                node.right = delete(node.right, minNode.value);
            }
        }

        updateHeight(node);
        return balance(node);
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public T search(T value) {
        Node result = search(root, value);
        return result == null ? null : result.value;
    }

    private Node search(Node node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) return search(node.left, value);
        else if (cmp > 0) return search(node.right, value);
        else return node;
    }

    public int height() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    public int size() {
        return size;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private int getBalance(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private Node balance(Node node) {
        int balanceFactor = getBalance(node);

        if (balanceFactor > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }

        if (balanceFactor > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balanceFactor < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }

        if (balanceFactor < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node root) {
        Node newRoot = root.left;
        Node temp = newRoot.right;

        newRoot.right = root;
        root.left = temp;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node rotateLeft(Node root) {
        Node newRoot = root.right;
        Node temp = newRoot.left;

        newRoot.left = root;
        root.right = temp;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }
}
