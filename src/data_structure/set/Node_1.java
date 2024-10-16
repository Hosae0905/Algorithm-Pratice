package data_structure.set;

/**
 * HashSet에 저장할 노드
 * @param <E> HashSet에 저장할 노드의 타입 
 */
public class Node_1<E> {
    final int hash;     // 해시값
    final E key;        // 저장할 데이터
    Node_1<E> next;     // 해시 충돌 시 연결을 시켜줄 수 있는 변수

    public Node_1(int hash, E key, Node_1<E> next) {
        this.hash = hash;
        this.key = key;
        this.next = next;
    }
}
