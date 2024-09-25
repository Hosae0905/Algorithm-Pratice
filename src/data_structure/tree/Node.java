package data_structure.tree;

/**
 * Tree 자료구조에 저장되는 Node 타입의 데이터
 * vale - Node에 값을 저장할 변수
 * left - Node의 왼쪽 자식 Node
 * right - Node의 오른쪽 자식 Node
 */

public class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {       // 기본적으로 Node를 생성하면 자식 노드는 모두 null 값이다.
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public void addLeft(Node node) {        // 현재 노드의 왼쪽 자식 노드로 데이터를 저장
        if (node == null) {
            return;
        }
        this.left = node;
    }

    public void addRight(Node node) {       // 현재 노드의 오른쪽 자식 노드로 데이터를 저장
        if (node == null) {
            return;
        }
        this.right = node;
    }

    public void preOrder(Node node) {       // 이진 트리의 전위 순회 코드
        if (node == null) {
            return;
        }

        System.out.println(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(Node node) {        // 이진 트리의 중위 순회 코드
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.value);
        inOrder(node.right);
    }

    public void postOrder(Node node) {      // 이진 트리의 후위 순회 코드
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.value);
    }
}
