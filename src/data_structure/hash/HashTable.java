package data_structure.hash;


import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTable<K, V> {

    private final int DEFAULT_CAPACITY = 10;
    private LinkedList<HashNode<K, V>>[] buckets;
    private int capacity;

    HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.buckets = new LinkedList[DEFAULT_CAPACITY];
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    HashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int hash(K key) {
        System.out.println(key.hashCode());
        return key.hashCode() % capacity;
    }

    private int doubleHash(K key) {
        return key.hashCode() % (capacity - 1);
    }

    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> bucket = buckets[index];

//        for (HashNode<K, V> hashNode : bucket) {
//            if (hashNode.key.equals(key)) {
//                hashNode.value = value;
//                return;
//            }
//        }

        if (bucket.size() >= 1) {
            int newIndex = doubleHash(key);
            bucket = buckets[newIndex];
        }

        bucket.add(new HashNode<>(key, value));
    }

    public V get(K key) {
        int index = hash(key);
        LinkedList<HashNode<K, V>> bucket = buckets[index];

        for (HashNode<K, V> hashNode : bucket) {
            if (hashNode.key.equals(key)) {
                return hashNode.value;
            }
        }

        throw new NoSuchElementException();
    }

    public boolean isEmpty() {
        if (buckets.length == 0) {
            return true;
        } else {
            return false;
        }
    }

}
