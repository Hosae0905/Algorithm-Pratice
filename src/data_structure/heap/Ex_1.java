package data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 참고 자료 : https://st-lab.tistory.com/205
 */

class Member implements Comparable<Member>{
    String name;
    int age;

    Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Member o) {
        if (this.name.compareTo(o.name) == 0) {
            return this.age - o.age;
        }

        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "name: " + name + " age: " + age;
    }
}

public class Ex_1 {

    private static Comparator<Member> comparator = new Comparator<Member>() {
        @Override
        public int compare(Member o1, Member o2) {
            if (o1.age == o2.age) {
                return o1.name.compareTo(o2.name);
            }

            return o2.age - o1.age;
        }
    };

    public static void main(String[] args) {
        Heap<Member> heap1 = new Heap<Member>();
        Heap<Member> heap2 = new Heap<Member>(comparator);

        heap1.add(new Member("AAA", 20));
        heap1.add(new Member("BBB", 30));
        heap1.add(new Member("CCC", 25));
        heap1.add(new Member("AAA", 24));
        heap1.add(new Member("DDD", 50));
        heap1.add(new Member("FFF", 70));

        heap2.add(new Member("AAA", 20));
        heap2.add(new Member("BBB", 30));
        heap2.add(new Member("CCC", 25));
        heap2.add(new Member("AAA", 24));
        heap2.add(new Member("DDD", 50));
        heap2.add(new Member("FFF", 70));

        System.out.println("=====[Heap1]=====");
        while (!heap1.isEmpty()) {
            System.out.println(heap1.remove());
        }
        System.out.println();

        System.out.println("=====[Heap2]=====");
        while (!heap2.isEmpty()) {
            System.out.println(heap2.remove());
        }
        System.out.println();
    }
}
