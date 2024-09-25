package data_structure.Array_List;

import java.util.Scanner;

/**
 * 백준 1546
 * 평균 구하기
 * 브론즈 1
 */

public class Ex_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int score = sc.nextInt();   //시험 본 과목의 개수 - int 입력
        long sum = 0;
        long max = 0;

        for (int i = 0; i < score; i++) {
            int temp = sc.nextInt();    //현재 성적 - int 입력
            if (temp > max) max = temp;
            sum = sum + temp;
        }

        System.out.println(sum * 100.0 / max / score);
    }
}
