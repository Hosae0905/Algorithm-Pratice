package algorithm.Combination;

import java.util.Scanner;

/**
 * 브론즈 1 (백준 11050)
 */

public class Ex_1 {
    static int N, K;
    static int D[][];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();
        D = new int[N+1][N+1];
        for (int i = 0; i <= N; i++) {
            D[i][i] = 1;
            D[i][0] = 1;
            D[i][1] = i;
        }

        for (int i = 2; i <= N; i++) {
            for (int j = 1; j < i; j++) {
                D[i][j] = D[i - 1][j] + D[i - 1][j-1];
            }
        }
        System.out.println(D[N][K]);
    }
}
