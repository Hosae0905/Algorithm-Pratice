package Greedy;

import java.util.Scanner;

/**
 *  실버2 (백준 1541)
 */

public class Ex_2 {
    static int answer = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String example = sc.nextLine();
        String[] str = example.split("-");
        for (int i = 0; i < str.length; i++) {
            int temp = mySum(str[i]);
            if (i==0) answer += temp;
            else answer -= temp;
        }
        System.out.println(answer);
    }

    private static int mySum(String a) {
        int sum = 0;
        String[] temp = a.split("[+]");
        for (int i = 0; i < temp.length; i++) {
            sum = sum + Integer.parseInt(temp[i]);
        }
        return sum;
    }
}
