package data_structure.Graph;

import java.util.ArrayList;

/**
 * 인접 리스트
 * 인접 리스트는 그래프를 표현하는 방법 중 하나이다. --> 기본적으로 배열 or 리스트로 표현
 * 배열의 각 요소는 그래프의 노드를 뜻하며 연결 리스트를 통해 해당 노드와 연결된 다른 노드를 나타낸다.
 *
 * 인접 리스트의 장점
 * 인접 리스트를 사용함으로 많은 저장 공간을 절약할 수 있다. --> 간선의 값을 저장하기만 하면 된다.
 * 특정 정점과 직접 연결된 다른 정점들을 빠르게 검색할 수 있어 그래프의 탐색 및 분석 작업을 간단하게 만들어준다.
 *
 * 인접 리스트의 단점
 * 두 개의 특정 정점 사이의 간선 존재 여부를 확인해야 하는 주된 경우에는 인접 행렬이 더 빠를 수 있다.
 *
 */

public class Adjacency_List {

    static void addEdge(ArrayList<ArrayList<Integer>> list, int s, int d) {     // s = 시작 노드 | d = 대상 노드
        list.get(s).add(d);
        list.get(d).add(s);     // 시작 노드와 대상 노드를 연결한다.
    }

    public static void main(String[] args) {
        int V = 5;      // 노드의 수
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>(V);

        for (int i = 0; i < V; i++) {
            list.add(new ArrayList<Integer>());
        }

        addEdge(list, 0, 1);
        addEdge(list, 0, 2);
        addEdge(list, 0, 3);
        addEdge(list, 1, 2);

        printGraph(list);
    }

    private static void printGraph(ArrayList<ArrayList<Integer>> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\nVertex " + i + ":");
            for (int j = 0; j < list.get(i).size(); j++) {      // 연결된 리스트의 사이즈를 기준으로 반복문을 수행한다.
                System.out.print(" -> " + list.get(i).get(j));  // list.get(i)를 통해 먼저 노드를 가져오고 get(j)를 통해 해당 노드와 연결된 노드를 가져온다.
            }
            System.out.println();
        }
    }
}
