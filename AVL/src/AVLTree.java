/**
 * Clase que implementa un árbol AVL genérico.
 *
 * <p>Un árbol AVL es un árbol binario de búsqueda balanceado en el que la altura de los subárboles
 * izquierdo y derecho de cualquier nodo difiere en, como máximo, uno. Esto asegura que el tiempo
 * de búsqueda, inserción y eliminación sea logarítmico en el peor caso.
 *
 * @param <T> el tipo de datos que se almacenan en el árbol, debe implementar la interfaz Comparable.
 */
public class AVLTree<T extends Comparable<T>> {

    private AVLNode<T> root;

    /**
     * Constructor por defecto de la clase AVLTree.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Inserta un nuevo nodo en el árbol AVL.
     *
     * @param data el dato del nuevo nodo a insertar.
     */
    public void insert(T data) {
        root = insert(data, root);
    }

    /**
     * Método auxiliar recursivo que inserta un nuevo nodo en el árbol AVL.
     *
     * @param data el dato del nuevo nodo a insertar.
     * @param node el nodo actual que está siendo comparado.
     * @return el nodo actualizado con el nuevo nodo insertado.
     */
    private AVLNode<T> insert(T data, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<T>(data);
        }
        if (data.compareTo(node.data) < 0) {
            node.left = insert(data, node.left);
        } else {
            node.right = insert(data, node.right);
        }
        node = rebalance(node);
        updateHeight(node);
        return node;
    }

    /**
     * Rebalancea el árbol AVL si es necesario.
     *
     * @param node el nodo que se debe rebalancear.
     * @return el nodo rebalanceado.
     */
    private AVLNode<T> rebalance(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        if (height(node.left) - height(node.right) > 1) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        } else if (height(node.right) - height(node.left) > 1) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }
        return node;
    }

    /**
     * Realiza una rotación hacia la derecha en el árbol AVL.
     *
     * @param node el nodo alrededor del cual se realiza la rotación.
     * @return el nodo rotado.
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> left = node.left;
        node.left = left.right;
        left.right = node;
        updateHeight(node);
        updateHeight(left);
        return left;
    }

    // Rotate the AVL Tree to the left
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> right = node.right;
        node.right = right.left;
        right.left = node;
        updateHeight(node);
        updateHeight(right);
        return right;
    }

    // Update the height of a node
    private void updateHeight(AVLNode<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // Get the height of a node
    private int height(AVLNode<T> node) {
        return node == null ? -1 : node.height;
    }

    // Inner class for AVL Node
    private static class AVLNode<T> {
        private T data;
        private AVLNode<T> left;
        private AVLNode<T> right;
        private int height;

        public AVLNode(T data) {
            this.data = data;
            this.height = 0;
        }
    }
}
