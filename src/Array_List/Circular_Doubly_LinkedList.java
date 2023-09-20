package Array_List;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Circular_Doubly_LinkedList<E> {

    private Node<E> head;
    private Node<E> tail;

    private int size;

    public Circular_Doubly_LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> search(int index) {
        Node<E> n;

        if ((size / 2) > index) {
            n = head;
            for (int i = 0; i < index; i++) {
                n = n.next;
            }
        } else {
            n = tail;
            for (int i = 0; i < size; i++) {
                n = n.prev;
            }
        }
        return n;
    }

    public void addFirst(E value) {
        Node<E> first = head;
        Node<E> last = tail;

        Node<E> new_node = new Node<>(null, value, first);

        size++;

        head = new_node;

        if (first == null) {
            tail = new_node;

            new_node.next = new_node;
            new_node.prev = new_node;
        } else {
            first.prev = new_node;

            last.next = new_node;
            new_node.prev = last;
        }
    }

    public void addLast(E value) {
        Node<E> first = head;
        Node<E> last = tail;

        Node<E> new_node = new Node<>(last, value, null);

        size++;

        tail = new_node;

        if (last == null) {
            head = new_node;

            new_node.next = new_node;
            new_node.prev = new_node;
        } else {
            last.next = new_node;

            new_node.next = first;
            first.prev = new_node;
        }
    }

    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void add(int index, E value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size:" + size);
        }

        if (index == 0) {
            addLast(value);
            return;
        }

        if (index == size) {
            addLast(value);
            return;
        }

        Node<E> next_node = search(index);
        Node<E> prev_node = next_node.prev;
        Node<E> new_node = new Node<>(prev_node, value, next_node);

        size++;

        prev_node.next = new_node;
        next_node.prev = new_node;
    }

    public E removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        E value = head.item;

        Node<E> first = head.next;

        head.next = null;
        head.item = null;

        size--;

        head = first;

        if (head == null) {
            tail = null;
        }

        return value;
    }

    public E removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }

        E value = tail.item;

        Node<E> last = tail.prev;
        Node<E> first = head;

        tail.item = null;
        tail.prev = null;
        tail.next = null;

        size--;

        tail = last;

        if (last == null) {
            head = null;
        } else {
            first.prev = last;
            last.next = first;
        }

        return value;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        Node<E> n = head;
        String result = "[";

        while (n.next != null) {
            result += n.item + ", ";
            n = n.next;
        }
        result += n.item + "]";

        return result;
    }

    public static void main(String[] args) {

    }
}
