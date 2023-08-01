package Array_List;

import java.util.Scanner;

/**
 * 백준 1546
 * 평균 구하기
 * 브론즈 1
 */

public class Ex_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int score = sc.nextInt();
        long sum = 0;
        long max = 0;

        for (int i = 0; i < score; i++) {
            int temp = sc.nextInt();
            if (temp > max) max = temp;
            sum = sum + temp;
        }

        System.out.println(sum * 100.0 / max / score);
    }
}
