package data_structure.set;

/**
 * LinkedHashSet의 데이터를 담을 Node 클래스
 * @param <E>
 */

public class Node_2<E> {
    final int hash;     // 입력 데이터에 대한 해시값
    final E key;        // 사용자 입력 값

    Node_2<E> next;     // 해시 충돌 발생시 Separate Chaining 을 위한 연결 리스트
    Node_2<E> nextLink; // 연결 리스트(다음 노드)
    Node_2<E> prevLink; // 연결 리스트(이전 노드)

    Node_2(int hash, E key, Node_2<E> next) {
        this.hash = hash;
        this.key = key;
        this.next = next;

        this.nextLink = null;
        this.prevLink = null;
    }
}
