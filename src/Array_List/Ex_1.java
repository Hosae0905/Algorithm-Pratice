package Array_List;

import java.util.Scanner;

/**
 * 백준 11720번
 * - 숫자 합 구하기
 * - 브론즈 2
 */
public class Ex_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String sNum = sc.next();
        char[] cNum = sNum.toCharArray();
        int sum = 0;

        for (int i = 0; i < cNum.length; i++) {
            sum += cNum[i] - '0';
        }
        System.out.println(sum);
    }
}
