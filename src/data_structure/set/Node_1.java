package data_structure.set;

public class Node_1<E> {
    final int hash;
    final E key;
    Node_1<E> next;

    public Node_1(int hash, E key, Node_1<E> next) {
        this.hash = hash;
        this.key = key;
        this.next = next;
    }
}
