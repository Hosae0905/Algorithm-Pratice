package data_structure.hash;

public class HashNode<K, V> {
    K key;
    V value;

    HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[Key] : " + this.key + " [Value] : " + this.value;
    }
}
