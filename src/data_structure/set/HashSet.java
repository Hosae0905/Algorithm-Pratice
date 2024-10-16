package data_structure.set;

/**
 * HashSet 자료구조
 * @param <E> HashSet에 저장할 요소의 타입
 */
public class HashSet<E> implements Set<E> {

    private final static int DEFAULT_CAPACITY = 1 << 4;     // HashSet에 저장할 최소 용적
    private final static float LOAD_FACTOR = 0.75f;         // 동적으로 배열을 늘려주기 위한 데이터 적재율
    Node_1<E>[] table;      // 요소의 정보를 담고 있는 Node를 저장할 배열
    private int size;       // 요소의 개수

    public HashSet() {
        table = (Node_1<E>[]) new Node_1[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * 보조 해시 함수 메서드
     * @param key 보조 해시를 만들때 필요한 키
     * @return 보조 해시 함수를 통해 도출된 해시값
     */
    private static int hash(Object key) {
        int hash;
        if (key == null) {
            return 0;
        } else {
            return Math.abs(hash = key.hashCode()) ^ (hash >>> 16);
        }
    }

    /**
     * HashSet에 요소를 추가하는 메서드
     * @param e Set에 추가할 요소
     * @return {@code true} 만약 새로운 요소가 정상적으로 추가되었을 경우,
     *          else {@code false} 중복으로 인해 추가되지 못할 경우
     */
    @Override
    public boolean add(E e) {
        return add(hash(e), e) == null;
    }

    /**
     * HashSet에 요소를 추가하는 메서드
     * @param hash 보조 해시값
     * @param key 추가할 요소
     * @return HashSet에 추가된 요소
     */
    private E add(int hash, E key) {
        int index = hash % table.length;        // 해시 값을 통해서 배열의 인덱스를 구한다.

        /*
         * 테이블의 인덱스에 이미 요소가 있을 경우 해시 충돌이 발생한 것이기 때문에 Separate Chaining을 진행해준다.
         * 테이블의 인덱스에 요소가 없을 경우 새로운 Node를 만들어서 저장한다.
         */
        if (table[index] == null) {
            table[index] = new Node_1<>(hash, key, null);
        } else {
            Node_1<E> temp = table[index];      // 현재 인덱스의 노드
            Node_1<E> prev = null;      // temp의 이전 노드

            // 첫 노드부터 탐색
            while (temp != null) {
                /*
                 * 만약 추가할 요소가 temp와 같을 경우(hash 값이 같으면서 key 또한 같을 경우)
                 * 중복이 발생했기 때문에 key를 다시 반환
                 */
                if ((temp.hash == hash) && (temp.key == key || temp.key.equals(key))) {
                    return key;
                }
                prev = temp;
                temp = temp.next;       // 다음 노드로 이동
            }

            prev.next = new Node_1<>(hash, key, null);      // 마지막에 새 노드를 연결
        }
        size++;     // 요소으 개수를 증가

        // 현재 HashSet의 적재율이 75%가 넘어가는 경우 동적으로 크기를 늘려준다.
        if (size >= LOAD_FACTOR * table.length) {
            resize();
        }

        return null;        // 정상적으로 추가되었을 경우 null을 반환해준다.
    }

    /**
     * HashSet의 크기를 동적으로 늘려주는 메서드
     */
    private void resize() {
        int newCapacity = table.length * 2;     // 새로운 크기를 저장
        final Node_1<E>[] newTable = (Node_1<E>[]) new Node_1[newCapacity];     // 새로운 크기만큼의 노드 배열을 생성

        // 기존의 HashSet에 있었던 요소들을 새로운 배열로 이동
        for (int i = 0; i < table.length; i++) {
            Node_1<E> value = table[i];     // 각 인덱스의 첫 번째 노드

            // 해당 노드가 없을 경우 다음 인덱스로 넘어간다.
            if (value == null) {
                continue;
            }

            table[i] = null;        // 기존의 HashSet의 i번째 인덱스에 있었던 노드를 지워준다(GC)

            Node_1<E> nextNode;     // 현재 노드의 다음 노드를 가리키는 변수 선언

            // 인덱스에 위치한 노드와 연결된 리스트를 순회
            while (value != null) {
                int index = value.hash % newCapacity;       // 새로운 인덱스를 구한다.

                /*
                 * 새로 담을 인덱스에 요소가 이미 존재하는 경우 해시 충돌이 발생했기 때문에 Separate Chaining 수행
                 */
                if (newTable[index] != null) {
                    Node_1<E> tail = newTable[index];       // 현재 인덱스에 위치한 노드를 tail에 저장

                    // tail의 next가 null이 아니면 아직 연결된 노드가 있다는 것으로 가장 마지막 노드까지 이동할 수 있도록 반복한다.
                    while (tail.next != null) {
                        tail = tail.next;
                    }

                    /*
                     * 마지막 노드까지 도착했다면 추가할 노드와 연결시켜준다.
                     * 반드시 value가 참조하고 있는 다음 노드와의 연결을 끊어줘야 한다.
                     * 왜냐하면 끊어주지 않으면 각 인덱스의 마지막 노드도 다른 노드를 참조하게 되버려 잘못될 수 있다.
                     */
                    nextNode = value.next;
                    value.next = null;
                    tail.next = value;
                } else {
                    /*
                     * 충돌이 발생하지 않는다면 노드를 추가해준다.
                     */
                    nextNode = value.next;
                    value.next = null;
                    newTable[index] = value;
                }

                value = nextNode;
            }
        }

        // 모든 노드가 새로운 배열로 이동이 완료되면 기존의 table을 지워주고 새로운 테이블을 저장한다.
        table = null;
        table = newTable;
    }

    /**
     * HashSet에서 요소를 삭제하는 메서드
     * @param o Set에서 삭제할 요소
     * @return
     */
    @Override
    public boolean remove(Object o) {
        return remove(hash(o), o) != null;
    }

    /**
     * HashSet에서 요소를 삭제하는 메서드
     * @param hash 보조 해시 값
     * @param key 추가할 요소
     * @return
     */
    private Object remove(int hash, Object key) {
        int index = hash % table.length;

        Node_1<E> node = table[index];
        Node_1<E> removedNode = null;
        Node_1<E> prev = null;

        if (node == null) {
            return null;
        }

        while (node != null) {
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                removedNode = node;

                if (prev == null) {
                    table[index] = node.next;
                    node = null;
                } else {
                    prev.next = node.next;
                    node = null;
                }

                size--;
                break;
            }

            prev = node;
            node = node.next;
        }

        return removedNode;
    }

    /**
     * HashSet에 특정 요소가 포함되어 있는지 확인하는 메서드
     * @param o 포함되어있는지 확인할 특정 요소
     * @return {@code true} 만약 HashSet에 요소가 포함되어 있을 경우,
     *          else, {@code false} 요소가 포함되지 않았을 경우
     */
    @Override
    public boolean contains(Object o) {
        int index = hash(o) % table.length;
        Node_1<E> temp = table[index];

        while (temp != null) {
            if (o == temp.key || (o != null && (o.equals(temp.key)))) {
                return true;
            }
            temp = temp.next;
        }

        return false;
    }

    /**
     * HashSet이 비어있는지 확인하는 메서드
     * @return {@code true} 만약 비어있을 경우,
     *          else, {@code false} 비어있지 않을 경우
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * HashSet의 요소 개수를 반환해주는 메서드
     * @return HashSet에 저장된 요소 개수를 반환
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof HashSet)) {
            return false;
        }

        HashSet<E> oSet;

        try {
            oSet = (HashSet<E>) o;
            if (oSet.size() != size) {
                return false;
            }

            for (int i = 0; i < oSet.table.length; i++) {
                Node_1<E> oTable = oSet.table[i];
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