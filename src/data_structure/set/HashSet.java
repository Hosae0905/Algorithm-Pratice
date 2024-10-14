package data_structure.set;

public class HashSet<E> implements Set<E> {

    private final static int DEFAULT_CAPACITY = 1 << 4;
    private final static float LOAD_FACTOR = 0.75f;
    Node_1<E>[] table;
    private int size;

    public HashSet() {
        table = (Node_1<E>[]) new Node_1[DEFAULT_CAPACITY];
        size = 0;
    }

    private static int hash(Object key) {
        int hash;
        if (key == null) {
            return 0;
        } else {
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }

    @Override
    public boolean add(E e) {
        return add(hash(e), e) == null;
    }

    private E add(int hash, E key) {
        int index = hash % table.length;

        if (table[index] == null) {
            table[index] = new Node_1<>(hash, key, null);
        } else {
            Node_1<E> temp = table[index];
            Node_1<E> prev = null;

            while (temp != null) {
                if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
                    return key;
                }
                prev = temp;
                temp = temp.next;
            }

            prev.next = new Node_1<>(hash, key, null);
        }
        size++;

        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        final Node_1<E>[] newTable = (Node_1<E>[]) new Node_1[newCapacity];

        for (int i = 0; i < table.length; i++) {
            Node_1<E> value = table[i];
            if (value == null) {
                continue;
            }

            table[i] = null;

            Node_1<E> nextNode;

            while (value != null) {
                int index = value.hash % newCapacity;

                if (newTable[index] != null) {
                    Node_1<E> tail = newTable[index];

                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    nextNode = value.next;
                    value.next = null;
                    tail.next = value;
                } else {
                    nextNode = value.next;
                    value.next = null;
                    newTable[index] = value;
                }

                value = nextNode;
            }
        }

        table = null;
        table = newTable;
    }

    @Override
    public boolean remove(Object o) {
        return remove(hash(o), o) != null;
    }

    private Object remove(int hash, Object key) {
        int index = hash % table.length;

        Node_1<E> node = table[index];
        Node_1<E> removedNode = null;
        Node_1<E> prev = null;

        if (node == null) {
            return null;
        }

        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                removedNode = node;

                if (prev == null) {
                    table[index] = node.next;
                    node = null;
                } else {
                    prev.next = node.next;
                    node = null;
                }

                size--;
                break;
            }

            prev = node;
            node = node.next;
        }

        return removedNode;
    }

    @Override
    public boolean contains(Object o) {
        int index = hash(o) % table.length;
        Node_1<E> temp = table[index];

        while (temp != null) {
            if (o == temp.key || (o != null && (o.equals(temp.key)))) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof HashSet)) {
            return false;
        }

        HashSet<E> oSet;

        try {
            oSet = (HashSet<E>) o;
            if (oSet.size() != size) {
                return false;
            }

            for (int i = 0; i < oSet.table.length; i++) {
                Node_1<E> oTable = oSet.table[i];
                while (oTable != null) {
                    if (!contains(oTable)) {
                        return false;
                    }
                    oTable = oTable.next;
                }
            }

        } catch (ClassCastException e) {
            return false;
        }

        return true;
    }
}
