package data_structure.heap;

import java.time.LocalDateTime;
import java.util.Comparator;

class Coupon implements Comparable<Coupon> {
    int per;
    LocalDateTime startedAt;
    LocalDateTime endedAt;

    Coupon(int per, LocalDateTime startedAt, LocalDateTime endedAt) {
        this.per = per;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    @Override
    public int compareTo(Coupon o) {
        if (this.endedAt.getMonth().compareTo(o.endedAt.getMonth()) == 0) {
            return this.endedAt.getDayOfMonth() - o.endedAt.getDayOfMonth();
        } else {
            return this.endedAt.getMonthValue() - o.endedAt.getMonthValue();
        }
    }

    @Override
    public String toString() {
        return "[쿠폰 할인률] : " + this.per + " [쿠폰 시작일] : " + this.startedAt + " [쿠폰 마감일] : " + this.endedAt;
    }
}

public class Ex_2 {

    static Comparator<Coupon> comparator = new Comparator<Coupon>() {
        @Override
        public int compare(Coupon o1, Coupon o2) {      // o1 = target, o2 = parentValue
            // 쿠폰 마감 날짜 중에서 달이 같다면
            if (o1.endedAt.getMonth() == o2.endedAt.getMonth()) {
                if (o1.endedAt.getDayOfMonth() == o2.endedAt.getDayOfMonth()) { // 쿠폰 마감 날짜 중에서 달과 일이 같다면
                    return o1.endedAt.compareTo(o2.endedAt);
                } else {
                    return o1.endedAt.getDayOfMonth() - o2.endedAt.getDayOfMonth();
                }
            }
            return o1.endedAt.getMonthValue() - o2.endedAt.getMonthValue();
        }
    };

    public static void main(String[] args) {
        Coupon coupon1 = new Coupon(10, LocalDateTime.now(), LocalDateTime.of(2024, 10, 25, 12, 0));
        Coupon coupon2 = new Coupon(20, LocalDateTime.now(), LocalDateTime.of(2024, 12, 25, 12, 0));
        Coupon coupon3 = new Coupon(15, LocalDateTime.now(), LocalDateTime.of(2024, 11, 25, 12, 0));
        Coupon coupon4 = new Coupon(25, LocalDateTime.now(), LocalDateTime.of(2024, 9, 30, 12, 0));
        Coupon coupon5 = new Coupon(50, LocalDateTime.now(), LocalDateTime.of(2024, 10, 22, 12, 0));
        Coupon coupon6 = new Coupon(5, LocalDateTime.now(), LocalDateTime.of(2024, 10, 25, 11, 0));

        Heap<Coupon> heap1 = new Heap<Coupon>();
        Heap<Coupon> heap2 = new Heap<Coupon>(comparator);

        heap1.add(coupon1);
        heap1.add(coupon2);
        heap1.add(coupon3);
        heap1.add(coupon4);
        heap1.add(coupon5);
        heap1.add(coupon6);

        heap2.add(coupon1);
        heap2.add(coupon2);
        heap2.add(coupon3);
        heap2.add(coupon4);
        heap2.add(coupon5);
        heap2.add(coupon6);

        System.out.println("=======[Heap1]=======");
        while (!heap1.isEmpty()) {
            System.out.println(heap1.remove());
        }

        System.out.println();
        System.out.println("=======[Heap2]=======");
        while (!heap2.isEmpty()) {
            System.out.println(heap2.remove());
        }
    }
}
