package data_structure.set;

/**
 * LinkedHashSet 클래스 구현
 * Set 인터페이스 구현이 필요
 * @param <E>
 */

public class LinkedHashSet<E> implements Set<E> {

    private final static int DEFAULT_CAPACITY = 1 << 4;     // 요소를 담을 배열의 크기(2^n 기준)
    private static final float LOAD_FACTOR = 0.75f;     // 배열에 데이터가 담긴 용적률(75% 넘어가면 resize())

    Node_2<E>[] table;      // 요소를 담을 배열
    private int size;       // 배열에 담긴 전체 요소의 개수

    private Node_2<E> head; // 연결 리스트의 가장 첫 노드
    private Node_2<E> tail; // 연결 리스트의 가장 마지막 노드

    /**
     * 기본 생성자
     */
    LinkedHashSet() {
        table = (Node_2<E>[]) new Node_2[DEFAULT_CAPACITY];
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * 보조 해시 함수
     * @param key
     * @return
     */
    private static int hash(Object key) {
        int hash;
        if (key == null) {
            return 0;
        } else {
            // hashCode()의 경우 음수가 나올 수 있어서 절댓값을 통해 양수로 변환
            // key의 해시코드 절댓값과 그 값을 16비트 왼쪽으로 밀어낸 값의 XOR한 값을 반환
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }

    /**
     * 연결 리스트에 요소를 추가하는 메서드
     * @param node
     */
    private void linkLastNode(Node_2<E> node) {
        Node_2<E> last = tail;      // 현재 기준으로 가장 마지막 노드를 가져온다.
        tail = node;        // tail에 추가할 노드를 저장한다.

        /*
         * 만약 마지막 노드가 null이라면 현재 저장된 데이터가 없다는 뜻이다.
         * 따라서 추가할 노드가 가장 첫 번째 노드가 되기 때문에 head에 노드를 저장한다.
         */
        if (last == null) {
            head = node;
        } else {        // last가 null이 아닐 경우 저장된 데이터가 있다는 뜻이다.
            node.prevLink = last;       // 추가할 노드의 이전 노드로 기존의 마지막 노드를 저장한다.
            last.nextLink = node;       // 기존의 마지막 노드의 다음 노드로 추가할 노드를 저장한다.
        }
    }

    public Node_2<E> get() {
        return head;
    }

    /**
     * 새로운 데이터를 추가하는 메서드
     * @param e
     * @return true | false
     */
    @Override
    public boolean add(E e) {
        return add(hash(e), e) == null;
    }

    /**
     * 새로운 데이터를 추가하는 메서드
     * @param hash 추가할 데이터의 해시값
     * @param key 추가할 데이터
     * @return 추가한 데이터 | null
     */
    private E add(int hash, E key) {
        int index = hash % table.length;        // 인덱스 값을 구한다.
        Node_2<E> newNode = new Node_2<E>(hash, key, null);     // 추가할 데이터의 해시값과 키를 가지고 새로운 노드를 만든다.

        /*
         * 현재 배열에서 이전에 구한 인덱스에 데이터가 없을 경우 해당 인덱스에 newNode를 저장한다.
         */
        if (table[index] == null) {
            table[index] = newNode;
        } else {    // 해시 충돌이 발생할 경우 Separate Chaining을 통해서 데이터를 저장한다.
            Node_2<E> temp = table[index];
            Node_2<E> prev = null;

            while (temp != null) {
                // 만약 추가할 데이터와 동일한 데이터가 있다면 저장하지 않고 키를 반환해준다.
                if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
                    return key;
                }

                prev = temp;
                temp = temp.next;
            }

            prev.next = newNode;
        }

        size++;     // 데이터 저장이 완료되면서 사이즈를 증가시킨다.
        linkLastNode(newNode);      // 노드를 연결 리스트로 연결시켜준다.

        // 데이터의 개수가 현재 배열의 용적의 75%가 넘어가면 용적을 늘려준다.
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }

        return null;
    }

    /**
     * 동적으로 배열의 크기를 늘려주는 메서드
     */
    private void resize() {
        int newCapacity = table.length * 2;     // 새로운 배열 크기를 구한다.
        final Node_2<E>[] newTable = (Node_2<E>[]) new Node_2[newCapacity];     // newCapacity만큼의 배열을 새로 만든다.

        for (int i = 0; i < table.length; i++) {

            // 각 인덱스의 처 번째 노드를 value에 저장한다.
            Node_2<E> value = table[i];

            // 만약 값이 없다면 다음으로 넘어간다.
            if (value == null) {
                continue;
            }

            table[i] = null;        // 기존의 데이터를 제거하면서 GC 발동
            Node_2<E> nextNode;     // 현재 노드의 다음 노드를 가리키는 변수

            // 현재 인덱스에 연결 된 노드들을 순회한다.
            while (value != null) {
                int index = value.hash % newCapacity;       // 새로운 인덱스를 구한다.

                /*
                 * 새로 담을 인덱스에 이미 요소가 존재할 경우 해시 충돌이 발생한다.
                 */
                if (newTable[index] != null) {
                    Node_2<E> tail = newTable[index];

                    // Separate Chaining을 하기 위해서 가장 마지막 노드까지 간다.
                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    /*
                     * 마지막 노드까지 가서 연결 리스트와 연결을 진행한다.
                     * 여기서 주의할 점으로 value의 다음 노드와의 연결을 무조건 끊어줘야 한다.
                     * 끊어주지 않으면 각 인덱스의 마지막 노드(tail)도 다른 노드를 참조하게 되면서 잘못된 참조가 발생하게 된다.
                     */
                    nextNode = value.next;
                    value.next = null;
                    tail.next = value;
                } else {    // 충돌이 발생하지 않으면 데이터를 추가한다.
                    nextNode = value.next;
                    value.next = null;
                    newTable[index] = value;
                }

                value = nextNode;       // 다음 노드로 이동한다.
            }
        }

        table = null;
        table = newTable;       // 새로 생성한 table을 저장한다.
    }

    private void unlinkNode(Node_2<E> node) {
        Node_2<E> prevNode = node.prevLink;
        Node_2<E> nextNode = node.nextLink;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.nextLink = nextNode;
            node.prevLink = null;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prevLink = prevNode;
            node.nextLink = null;
        }
    }

    @Override
    public boolean remove(Object o) {
        return remove(hash(o), o) != null;
    }

    private Object remove(int hash, Object key) {
        int index = hash % table.length;

        Node_2<E> node = table[index];
        Node_2<E> removedNode = null;
        Node_2<E> prev = null;

        if (node == null) {
            return null;
        }

        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                removedNode = node;

                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }

                unlinkNode(node);
                node = null;

                size--;
                break;
            }

            prev = node;
            node = node.next;
        }

        return removedNode;
    }

    @Override
    public boolean contains(Object o) {
        int index = hash(o) % table.length;
        Node_2<E> temp = table[index];

        while (temp != null) {
            if (o == temp.key || (o != null && temp.key.equals(o))) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public void clear() {
        if (table != null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof LinkedHashSet)) {
            return false;
        }

        LinkedHashSet<E> oSet;

        try {
            oSet = (LinkedHashSet<E>) obj;
            if (oSet.size() != size) {
                return false;
            }

            for (int i = 0; i < oSet.table.length; i++) {
                Node_2<E> oTable = oSet.table[i];

                while (oTable != null) {
                    if (!contains(oTable)) {
                        return false;
                    }
                    oTable = oTable.next;
                }
            }

        } catch (ClassCastException e) {
            return false;
        }

        return true;
    }
}
