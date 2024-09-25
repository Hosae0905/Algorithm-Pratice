package data_structure.Graph;

/**
 * 인접 행렬
 * 인접 행렬은 그래프를 0과 1로 이루어진 행렬로 나타내는 방법이다.
 * 행렬의 boolean 값은 두 개의 정점 사이에 직접 경로가 있는지 여부를 나타낸다.
 *
 * 인접 행렬의 장점
 * 간선 추가, 간선 제거, 정점과 정점간의 간선 여부를 확인과 같은 기본 연산의 속도가 빠르다.
 * 그래프가 밀집되어 있고 간선의 수가 많으면 인접 행렬을 통해 해결할 수 있다.
 *
 * 인접 행렬의 단점
 * 인접 행렬은 노드 x 노드의 공간을 만드는데 이것으로 인하여 메모리에 큰 부담을 줄 수 있다.
 * 실제로 사용되는 그래프는 보통 많은 연결을 가지지 않기 때문에 메모리를 낭비할 수 있다.
 */

public class Adjacency_Matrix {
    private boolean adjMatrix[][];      // 인접 행렬 틀
    private int numVertices;        // 노드의 수

    public Adjacency_Matrix(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new boolean[numVertices][numVertices];      // 인접 행렬 초기화
    }

    // TODO: 인접 행렬의 간선을 추가
    public void addEdge(int i, int j) {
        adjMatrix[i][j] = true;
        adjMatrix[j][i] = true;         // 노드를 간선으로 연결한다.
    }

    // TODO: 인접 행렬의 간선을 삭제
    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = false;
        adjMatrix[j][i] = false;        // 서로 연결되어 있는 노드의 간선을 삭제한다.
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            s.append(i + ": ");
            for (boolean j : adjMatrix[i]) {
                s.append((j ? 1 : 0) + " ");        // j를 확인하여 1 혹은 0값을 넣는다.
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Adjacency_Matrix matrix = new Adjacency_Matrix(4);

        matrix.addEdge(0, 1);
        matrix.addEdge(0, 2);
        matrix.addEdge(1, 2);
        matrix.addEdge(2, 0);
        matrix.addEdge(2, 3);

        System.out.println(matrix.toString());
    }
}
