import java.sql.SQLOutput;

/**
 * Class that implements an AVL tree which implements the MyMap interface.
 * @author Brian S. Borowski
 * @version 1.1 October 24, 2023
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends BSTMap<K, V>
        implements MyMap<K, V> {
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Creates an empty AVL tree map.
     */
    public AVLTreeMap() { }

    public AVLTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a AVL tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created via a divide-and-conquer
     * approach. If sorted is false, the pairs will be inserted in the order
     * they are received, and the tree will be rotated to maintain the AVL tree
     * balance property.
     * @param elements an array of key-value pairs
     */
    public AVLTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        // TODO
        // Write code to create the balanced BST, as you did in the previous assignment.
        // The node reference must be called parent.


        // TODO
        // Write code to set the height field of the parent.
        // This line is critical for being able to add additional nodes or to
        // remove nodes. Forgetting this line leads to incorrectly balanced
        // trees.
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        Pair<K, V> pair = pairs[mid];
        Node<K, V> parent = new Node<>(pair.key, pair.value); // Assuming key and value are field names in your Pair class

        size++;

        parent.left = createBST(pairs, low, mid - 1);
        if (parent.left != null) {
            parent.left.parent = parent;
        }

        parent.right = createBST(pairs, mid + 1, high);
        if (parent.right != null) {
            parent.right.parent = parent;
        }

        return parent;

    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        NodeOldValuePair nvp = new NodeOldValuePair(null, null);
        nvp = insertAndBalance(key, value, root, nvp);
        return nvp.oldValue;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        // Replace the line with the code required for proper removal from an
        NodeOldValuePair nvp = new NodeOldValuePair(null, null);
        root = removeByKey(root, key, nvp);
        return nvp.oldValue;
    }
    private Node<K, V> removeByKey(Node<K, V> node, K key, NodeOldValuePair nvp) {
        if (node == null) {
            // Key not found, nothing to remove.
            return null;
        }

        // Compare the key with the current node's key.
        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            // Key is smaller, go left.
            node.left = removeByKey(node.left, key, nvp);
        } else if (cmp > 0) {
            // Key is larger, go right.
            node.right = removeByKey(node.right, key, nvp);
        } else {
            // Key matches the current node, so remove it.
            nvp.oldValue = node.value;
            if (node.left == null || node.right == null) {
                // One or no child, replace with the non-null child.
                node = (node.left != null) ? node.left : node.right;
            } else {
                // Node has two children, replace with the in-order successor.
                Node<K, V> minNode = findMin(node.right);
                nvp.oldValue = node.value; // Store the old value
                node.key = minNode.key;
                node.value = minNode.value;
                node.right = removeByKey(node.right, minNode.key, nvp);
            }
        }

        // Update height and balance factor, and balance the node.
        node.height = 1 + Math.max(avlHeight(node.left), avlHeight(node.right));
        return balance(node);
    }
    private Node<K, V> findMin(Node<K, V> node) {
        if (node == null) {
            return null; // The tree is empty.
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }
    private NodeOldValuePair insertAndBalance(
            K key, V value, Node<K, V> t, NodeOldValuePair nvp) {
        if (t == null) {
            size++;
            nvp.node = new Node<K, V>(key, value);
            if (root == null) {
                root = nvp.node;
            }
            return nvp;
        }
        int comparison = key.compareTo(t.key);

        // TODO
        if (comparison < 0) {
            nvp = insertAndBalance(key, value, t.left, nvp);
            if (t.left == null) {
                t.left = nvp.node;
                t.left.parent = t; // Set the parent reference for the new node
            }
        } else if (comparison > 0) {
            nvp = insertAndBalance(key, value, t.right, nvp);
            if (t.right == null) {
                t.right = nvp.node;
                t.right.parent = t; // Set the parent reference for the new node
            }
        } else {
            // Key already exists; update the value
            nvp.oldValue = t.value;
            t.value = value;
        }

        Node<K, V> n = balance(t);
        nvp.node = n;
        return nvp;
    }

    private Node<K, V> balance(Node<K, V> t) {
        // TODO
        if (t == null) {
            return null;
        }

        // Update the height of the current node
        t.height = 1 + Math.max(avlHeight(t.left), avlHeight(t.right));

        // Calculate the balance factor for the current node
        int balanceFactor = avlHeight(t.left) - avlHeight(t.right);

        if (balanceFactor > ALLOWED_IMBALANCE) {
            if (avlHeight(t.left.left) >= avlHeight(t.left.right)) {
                // Left-Left case: Perform a single right rotation
                t = rotateWithRightChild(t);
            } else {
                // Left-Right case: Perform a left rotation on the left child and then a right rotation on the current node
                t = doubleWithLeftChild(t);
            }
        } else if (balanceFactor < -ALLOWED_IMBALANCE) {
            if (avlHeight(t.right.right) >= avlHeight(t.right.left)) {
                // Right-Right case: Perform a single left rotation
                t = rotateWithLeftChild(t);
            } else {
                // Right-Left case: Perform a right rotation on the right child and then a left rotation on the current node
                t = doubleWithRightChild(t);
            }
        }

        return t;
    }

    private int avlHeight(Node<K, V> t) {
        return t == null ? -1 : t.height;
    }

    private Node<K, V> rotateWithLeftChild(Node<K, V> k2) {
        // TODO
        if (k2 == null || k2.left == null) {
            // Handle the case where k2 or its left child is null
            return k2;
        }

        Node<K, V> k1 = k2.left;
        k2.left = k1.right;
        if (k1.right != null) {
            k1.right.parent = k2;
        }
        k1.right = k2;

        if (k2.parent != null) {
            if (k2.parent.left == k2) {
                k2.parent.left = k1;
            } else {
                k2.parent.right = k1;
            }
        } else {
            root = k1; // Update the root if k2 was the root
        }

        k1.parent = k2.parent;
        k2.parent = k1;

        // Update the heights of the rotated nodes
        k2.height = Math.max(avlHeight(k2.left), avlHeight(k2.right)) + 1;
        k1.height = Math.max(avlHeight(k1.left), avlHeight(k2)) + 1;

        return k1;
    }

    private Node<K, V> rotateWithRightChild(Node<K, V> k1) {
        // TODO
        if (k1 == null || k1.right == null) {
            // Handle the case where k1 or its right child is null
            return k1;
        }

        Node<K, V> k2 = k1.right;
        k1.right = k2.left;
        if (k2.left != null) {
            k2.left.parent = k1;
        }
        k2.left = k1;
        k2.parent = k1.parent;
        k1.parent = k2;

        if (k2.parent != null) {
            if (k2.parent.left == k1) {
                k2.parent.left = k2;
            } else {
                k2.parent.right = k2;
            }
        } else {
            root = k2; // Update the root if k1 was the root
        }

        // Update the heights of the rotated nodes
        k1.height = Math.max(avlHeight(k1.left), avlHeight(k1.right)) + 1;
        k2.height = Math.max(avlHeight(k2.right), avlHeight(k1)) + 1;

        return k2;
    }

    private Node<K, V> doubleWithLeftChild(Node<K, V> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node<K, V> doubleWithRightChild(Node<K, V> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private class NodeOldValuePair {
        Node<K, V> node;
        V oldValue;

        NodeOldValuePair(Node<K, V> n, V oldValue) {
            this.node = n;
            this.oldValue = oldValue;
        }
    }

    public static void main(String[] args) {
        boolean usingInts = true;
        if (args.length > 0) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                usingInts = false;
            }
        }

        AVLTreeMap avlTree;
        if (usingInts) {
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    int val = Integer.parseInt(args[i]);
                    pairs[i] = new Pair<>(val, val);
                } catch (NumberFormatException nfe) {
                    System.err.println("Error: Invalid integer '" + args[i]
                            + "' found at index " + i + ".");
                    System.exit(1);
                }
            }
            avlTree = new AVLTreeMap<Integer, Integer>(pairs);
        } else {
            @SuppressWarnings("unchecked")
            Pair<String, String>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                pairs[i] = new Pair<>(args[i], args[i]);
            }
            avlTree = new AVLTreeMap<String, String>(pairs);
        }

        System.out.println(avlTree.toAsciiDrawing());
        System.out.println();
        System.out.println("Height:                   " + avlTree.height());
        System.out.println("Total nodes:              " + avlTree.size());
        System.out.printf("Successful search cost:   %.3f\n",
                avlTree.successfulSearchCost());
        System.out.printf("Unsuccessful search cost: %.3f\n",
                avlTree.unsuccessfulSearchCost());
        avlTree.printTraversal(PREORDER);
        avlTree.printTraversal(INORDER);
        avlTree.printTraversal(POSTORDER);
    }
}
