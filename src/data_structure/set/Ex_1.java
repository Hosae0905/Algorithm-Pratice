package data_structure.set;

/**
 * 출처 : https://st-lab.tistory.com/240
 */

public class Ex_1 {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("AAA");
        hashSet.add("BBB");
        hashSet.add("CCC");
        hashSet.add("DDD");
        hashSet.add("AAA");

        System.out.println(hashSet.size());
        System.out.println(hashSet.contains("FFF"));
        System.out.println(hashSet.contains("AAA"));

        hashSet.clear();
        System.out.println(hashSet.size());

        Set_Test_1 test1 = new Set_Test_1("aaa", 10);
        Set_Test_1 test2 = new Set_Test_1("aaa", 10);

        HashSet<Set_Test_1> hashSet2 = new HashSet<>();
        hashSet2.add(test1);
        hashSet2.add(test2);

        /*
         * 사용자 정의 객체에 equals와 hashCdoe를 재정의하지 않으면 내용이 같은데 중복해서 추가되는 문제가 발생한다.
         * 왜냐하면 같은 내용이라면 같은 해시값이 되어야 하는데 재정의하지 않으면 같은 내용이라도 다른 해시값이 나오기 때문이다.
         */
        System.out.println(hashSet2.size());        // 같은 내용이지만 중복 추가가되서 사이즈가 2가 된다.

        hashSet2.clear();
        hashSet2.add(test1);
        hashSet2.add(test2);

        System.out.println(hashSet2.size());

        System.out.println("================================================");

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("AAA");
        linkedHashSet.add("BBB");
        linkedHashSet.add("CCC");
        linkedHashSet.add("DDD");
        linkedHashSet.add("AAA");

        System.out.println(linkedHashSet.size());
        System.out.println(linkedHashSet.contains("FFF"));
        for (Node_2<String> node : linkedHashSet.table) {
            if (node != null) {
                System.out.println(node.key);
            }
        }

        System.out.println("================================================");

        LinkedHashSet<Set_Test_1> linkedHashSet2 = new LinkedHashSet<>();
        Set_Test_1 test_1 = new Set_Test_1("aaa", 10);
        Set_Test_1 test_2 = new Set_Test_1("bbb", 20);
        Set_Test_1 test_3 = new Set_Test_1("ccc", 30);
        Set_Test_1 test_4 = new Set_Test_1("aaa", 20);

        linkedHashSet2.add(test_1);
        linkedHashSet2.add(test_2);
        linkedHashSet2.add(test_3);
        linkedHashSet2.add(test_4);

        System.out.println(linkedHashSet2.size());
        Node_2<Set_Test_1> node = linkedHashSet2.get();
        while (node != null) {
            if (node.nextLink != null) {
                System.out.print(node.key + " --> ");
            } else {
                System.out.print(node.key);
                break;
            }

            node = node.nextLink;
        }
    }
}
