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
        int N = sc.nextInt();       //int 입력
        String sNum = sc.next();    //String 형 입력 (토큰을 기준으로 한 단어를 읽음)
        char[] cNum = sNum.toCharArray();       //char 배열을 선언하고 입력 받은 String 값을 char 변환하여 배열에 저장
        int sum = 0;

        for (int i = 0; i < cNum.length; i++) {
            sum += cNum[i] - '0';       //아스키코드를 사용하여 문자를 숫자로 변환
        }
        System.out.println(sum);
    }
}
