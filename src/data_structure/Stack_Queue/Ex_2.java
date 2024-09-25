package data_structure.Stack_Queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 실버4 (백준 2164)
 */

public class Ex_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<Integer> myQueue = new LinkedList<>();
        int N = sc.nextInt();
        for (int i = 1; i <= N; i++) {
            myQueue.add(i);
        }

        while (myQueue.size() > 1) {
            myQueue.poll();
            myQueue.add(myQueue.poll());
        }
        System.out.println(myQueue.poll());
    }
}
