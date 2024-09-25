package data_structure.tree;

public class Ex_1 {
    public static void main(String[] args) {
        // 노드 생성
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        
        // node1을 루트노드로 하여 자식 노드를 삽입
        node1.addLeft(node2);
        node1.addRight(node3);
        node2.addLeft(node4);
        node2.addRight(node5);
        node3.addLeft(node6);
        node3.addRight(node7);
        
        // 전위 순회 결과 출력
        System.out.println("==========pre-order==============");
        node1.preOrder(node1);

        // 중위 순회 결과 출력
        System.out.println("==========in-order==============");
        node1.inOrder(node1);

        // 후위 순회 결과 출력
        System.out.println("==========post-order==============");
        node1.postOrder(node1);
    }
}
