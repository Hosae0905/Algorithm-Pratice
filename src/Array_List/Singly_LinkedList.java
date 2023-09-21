package Array_List;

import java.util.Arrays;
import java.util.Objects;

/**
 * 단일 연결 리스트 특징
 * 노드(객체)를 연결하여 리스트 처럼 만든 컬렉션
 * 데이터의 중간 삽입, 삭제가 빈번할 경우 빠른 성능을 보장
 * 임의의 요소에 대한 접근 성능은 좋지 않음
 * 리스트의 끝 요소를 탐색하려면 처음부터 끝까지 순회하며 탐색해야 하기 때문에 효율이 떨어짐
 * 데이터의 저장 순서가 유지되고 중복을 허용
 * 
 */

public class Singly_LinkedList<E> {
    private Node<E> head;       // 노드의 첫 부분을 가리키는 포인트
    private Node<E> tail;       // 노드의 마지막 부분을 가리키는 포인트

    private int size;           // 데이터의 갯수

    public Singly_LinkedList() {        // 초기화
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // TODO: 노드 객체 정의
    private static class Node<E> {
        private E item;     // 데이터
        private Node<E> next;   // 다음 노드 객체를 가리키는 참조 변수

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    // TODO: 데이터 조회
    private Node<E> search(int index) {
        Node<E> n = head;       // 처음 위치
        for (int i = 0; i < index; i++) {
            n = n.next;     // 다음 노드의 주소를 대입하여 순차적으로 데이터를 탐색
        }

        return n;       // 노드 객체 반환
    }

    
    // TODO: 데이터 추가
    public void addFirst(E value) {
        Node<E> first = head;       // 첫 번째 위치 설정
        Node<E> newNode = new Node<>(value, first);     // 파라미터로 받아온 값으로 새로운 노드 객체를 생성

        size++;     // 노드를 추가함으로 사이즈 증가

        head = newNode;     // head 업데이트(head가 새로운 노드를 가리키게)

        if (first == null) {        // 최초로 데이터가 추가된 것이면 head와 tail이 같은 데이터를 가리키게 된다.
            tail = newNode;
        }
    }

    public void addLast(E value) {
        Node<E> last = tail;        // 마지막 위치 설정
        Node<E> newNode = new Node<>(value, null);      // 마지막 값은 next가 없기 때문에 null로 설정

        size++;     // 사이즈 증가

        tail = newNode;     // tail 업데이트(새로운 노드를 가리키게)

        if (last == null) {     // 최초로 데이터가 추가된 것으로 head와 같은 노드를 가리키게 된다.
            head = newNode;
        } else {                // 처음이 아니라면 추가 되기 전 마지막 노드에서 다음 노드로 새로 생성한 노드를 가리키게 설정한다.
            last.next = newNode;
        }
    }

    public void add(int index, E value) {
        if (index < 0 || index >= size) {       // 인덱스가 유효한 값인지 확인 --> 예외 설정
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {       // index가 0이면 addFirst로 데이터 추가
            addFirst(value);
            return;
        }

        if (index == size - 1) {        // index가 리스트의 크기와 같으면 addLast로 끝에서 데이터를 추가
            addLast(value);
            return;
        }

        Node<E> prev_node = search(index - 1);      // 파라미터로 들어온 인덱스 위치 이전의 데이터를 조회해서 저장
        Node<E> next_node = prev_node.next;              // 저장한 이전 노드에서 next 노드를 새로 저장
        Node<E> newNode = new Node<>(value, next_node);  // 파라미터로 받은 값과 저장한 다음 노드를 가지고 새로운 노드를 생성
        size++;     // 데이터를 추가함으로 사이즈 증가

        prev_node.next = newNode;       // 미리 저장한 이전 노드가 새로 생성한 노드를 가리키게 설정
    }
    
    // TODO: 데이터 삭제
    public E removeFirst() {
        if (head == null) {     // head가 null이면 삭제할 데이터가 없음
            throw new IndexOutOfBoundsException();
        }

        E returnValue = head.item;      // 삭제할 데이터를 저장

        Node<E> first = head.next;      // 삭제할 노드의 다음 노드를 저장

        head.next = null;       // head의 노드를 null로 초기화
        head.item = null;       // head의 데이터를 null로 초기화

        head = first;       // head가 다음 노드를 가리키도록 업데이트
        size--;     // 데이터가 삭제 되므로 사이즈 감소

        if (head == null) {     // 리스트의 값을 삭제했는데 빈 리스트가 되었을 경우 tail도 null처리
            tail = null;
        }

        return returnValue;     // 삭제한 데이터 반환
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {       // 인덱스가 유효한 값인지 확인
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {       // 인덱스가 0이면 첫 번째 요소를 삭제하는 것이기 때문에 메서드 호출
            return removeFirst();
        }

        Node<E> prev_node = search(index - 1);      // 파라미터로 받은 인덱스의 이전 값을 조회하여 이전 노드에 저장
        Node<E> del_node = prev_node.next;          // 이전 노드의 다음 노드를 삭제할 노드에 저장
        Node<E> next_node = del_node.next;          // 삭제할 노드의 다음 노드를 저장

        E returnValue = del_node.item;      // 삭제할 노드의 데이터를 저장

        del_node.next = null;       // 삭제할 노드의 내부 데이터를 null로 초기화
        del_node.item = null;       // 삭제할 노드의 내부 데이터를 null로 초기화

        size--;     // 데이터를 삭제하므로 리스트의 사이즈 감소

        prev_node.next = next_node;     // 미리 저장한 이전 노드의 다음 노드로 미리 저장한 다음 노드를 연결
        return returnValue;     // 삭제한 데이터를 반환
    }

    public boolean remove(Object value) {
        if (head == null) {     // head가 null이면 빈 리스트를 의미
            throw new RuntimeException();
        }

        Node<E> prev_node = null;       // 이전 노드 초기화
        Node<E> del_node = null;        // 삭제 노드 초기화
        Node<E> next_node = null;       // 다음 노드 초기화

        Node<E> i = head;       // head값 저장

        while (i != null) {     // 노드의 next를 순회하며 해당 값을 탐색
            if (Objects.equals(i.item, value)) {        // 노드의 값과 매개변수의 값이 같으면 삭제 노드에 대입하고 break
                del_node = i;
                break;
            } else {
                i = i.next;
            }
        }

        prev_node = i;      // 이전 노드에 i값 대입

//        i = i.next;     // i의 다음 노드를 저장

        if (del_node == null) {     // 삭제할 노드가 null인지 확인
            return false;
        }

        if (del_node == head) {     // 삭제할 노드가 head와 같다면 첫 번째 인덱스 이므로 메서드 호출
            removeFirst();
            return true;
        }

        if (del_node.next == null) {     // 삭제할 노드가 tail과 같다면 마지막 번째 인덱스 이므로 메서드 호출
            removeLast();
            return true;
        }

        next_node = del_node.next;      // 다음 노드에 삭제할 노드의 다음 노드를 저장

        del_node.next = null;       // 삭제할 노드의 데이터를 모두 삭제
        del_node.item = null;

        size--;     // 데이터가 삭제되므로 사이즈 감소

        prev_node.next = next_node;     // 이전 노드의 다음 노드로 미리 저장한 다음 노드를 연결

        return true;
    }

    public E removeLast() {     
        return remove(size - 1);    // 마지막 인덱스에 있는 노드를 삭제
    }

    
    // TODO: 데이터 조회
    public E get(int index) {
        if (index < 0 || index >= size) {       // 인덱스가 유효한 값인지 확인
            throw new IndexOutOfBoundsException();
        }

        return search(index).item;      // 조회한 인덱스의 값을 반환
    }

    // TODO: 데이터 저장
    public void set(int index, E value) {
        if (index < 0 || index >= size) {       // 인덱스가 유효한 값인지 확인
            throw new IndexOutOfBoundsException();
        }

        Node<E> replace_node = search(index);       // 교체 노드에 조호한 노드를 저장

        replace_node.item = null;       // 저장된 노드의 데이터를 삭제
        replace_node.item = value;      // 파라미터로 받은 데이터를 저장
    }

    @Override
    public String toString() {
        if (head == null) {     // head가 null이면 빈 리스트
            return "[]";
        }

        Object[] array = new Object[size];      // 출력할 배열을 새로 생성

        int index = 0;
        Node<E> n = head;
        while (n != null) {     // 노드를 순차적으로 순회하면서 배열에 데이터 저장
            array[index] = (E) n.item;
            index++;
            n = n.next;
        }

        return Arrays.toString(array);      // Arrays 클래스를 이용하여 데이터 출력
    }

    public static void main(String[] args) {
        Singly_LinkedList singly = new Singly_LinkedList();

        // TODO: 데이터 추가
        singly.addFirst("Kim");
        singly.addLast("Tom");
        singly.add(1, "Kang");
        singly.add(2, "Jun");
        singly.addLast("Choi");
        singly.add(3, "James");

        // TODO: 데이터 삭제
        singly.removeFirst();
        singly.removeLast();
        singly.remove("Jun");

        // TODO: 데이터 조회
        singly.get(1);

        // TODO: 데이터 저장
        singly.set(1, "AAA");

        // TODO: 데이터 출력
        System.out.println(singly.toString());
    }
}
