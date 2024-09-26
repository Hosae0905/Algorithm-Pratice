package data_structure.hash;

public class Ex_1 {
    public static void main(String[] args) {
        HashTable<Integer, String> hash = new HashTable<>();
        hash.put(1, "AAA");
        hash.put(11, "ABC");
        hash.put(2, "BBB");
        hash.put(3, "CCC");
        hash.put(4, "DDD");
        hash.put(5, "EEE");

        System.out.println(hash.get(1));
        System.out.println(hash.get(2));
    }
}
