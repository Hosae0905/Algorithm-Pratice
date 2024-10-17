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
     * @return {@code true} HashSet에서 요소를 삭제할 경우,
     *          else, {@code false} 요소가 없어서 삭제하지 못한 경우
     */
    @Override
    public boolean remove(Object o) {
        return remove(hash(o), o) != null;
    }

    /**
     * HashSet에서 요소를 삭제하는 메서드
     * @param hash 보조 해시 값
     * @param key 삭제할 요소
     * @return 삭제할 요소
     */
    private Object remove(int hash, Object key) {
        int index = hash % table.length;    // 삭제할 요소의 인덱스 값

        Node_1<E> node = table[index];      // 삭제할 요소의 인덱스에 위치한 노드
        Node_1<E> removedNode = null;       // 삭제할 요소를 저장할 임시 노드
        Node_1<E> prev = null;              // 삭제할 요소의 이전 노드

        // 만약 삭제할 노드가 없으면(HashSet에 데이터가 없는 경우) null을 반환
        if (node == null) {
            return null;
        }

        // 삭제할 노드가 있는 경우
        while (node != null) {

            // 삭제할 노드를 찾았을 경우
            if (node.hash == hash && (node.key == key || node.key.equals(key))) {
                removedNode = node;     // 삭제할 노드를 저장

                // 해당 노드의 이전 노드가 존재하지 않을 경우(가장 앞에 있는 노드일 경우)
                if (prev == null) {
                    table[index] = node.next;       // 삭제할 노드의 다음 노드를 인덱스 위치에 저장
                    node = null;                    // 삭제할 노드에 null을 저장(GC)
                } else {        // 이전 노드가 존재할 경우 이전 노드의 next를 삭제할 노드의 다음 노드와 연결
                    prev.next = node.next;
                    node = null;        // 삭제할 노드에 null을 저장(GC)
                }

                size--;     // 요소를 삭제했기 때문에 HashSet의 요소 개수를 하나 감소
                break;      // 삭제가 완료되었기 때문에 break
            }

            prev = node;        // 이전 노드에 현재 노드를 저장
            node = node.next;   // 현재 노드는 다음 노드를 저장
        }

        // 삭제한 요소를 반환
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

        /*
         * 확인할 요소와 같은 내용인지 확인한다.
         * 같은 내용인지만 확인하기 때문에 해시 값은 따로 비교하지 않는다.
         * 확인할 요소가 null이 아닌지는 확인해야 한다.
         */
        while (temp != null) {
            // 만약 확인할 요소와 동일한게 있다면 true를 반환해준다.
            if (o == temp.key || (o != null && (o.equals(temp.key)))) {
                return true;
            }
            temp = temp.next;       // 없다면 다음 요소를 temp에 저장하고 반복문을 돈다.
        }

        // HashSet에 있는 모든 요소를 순회했을때 찾고자 하는 요소가 없다면 false를 반환해준다.
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

    /**
     * HashSet을 모두 비우는 메서드
     */
    public void clear() {
        if (table != null && size > 0) {
            for (int i = 0; i < table.length; i++) {
                table[i] = null;
            }
            size = 0;
        }
    }

    /**
     * 객체를 비교할 메서드
     * @param o HashSet과 비교할 객체
     * @return {@code true} 비교할 객체와 동일한 경우,
     *          else, {@code false} 동일하지 않을 경우
     */
    @Override
    public boolean equals(Object o) {

        // 만약 비교할 객체가 현재 객체와 동일하다면 true를 반환
        if (o == this) {
            return true;
        }

        // 만약 비교할 객체가 HashSet이 아니라면 false를 반환
        if (!(o instanceof HashSet)) {
            return false;
        }

        HashSet<E> oSet;        // 비교할 객체를 담을 임시 변수

        /*
         * HashSet과 비교를 위해 Object를 HashSet<E>로 캐스팅을 진행한다.
         * 만약 캐스팅이 불가능할 경우 ClassCastException이 발생한다.
         */
        try {

            // HashSet<E>로 타입 캐스팅
            oSet = (HashSet<E>) o;

            // 사이즈가 다르면 동일한 객체가 아니므로 false를 반환
            if (oSet.size() != size) {
                return false;
            }

            for (int i = 0; i < oSet.table.length; i++) {
                Node_1<E> oTable = oSet.table[i];

                /*
                 * 서로 다른 Capacity를 가질 수 있기 때문에 index에 연결된 요소들을 비교하는 것이 아닌
                 * contains로 존재 여부를 확인한다.
                 * equals는 값이 동일한지 비교하는 메서드이기 때문에 내용이 같다면 동일한 객체라고 본다.
                 */
                while (oTable != null) {
                    // HashSet에 포함되지 않을 경우 내용이 다르기 때문에 false를 반환한다.
                    if (!contains(oTable)) {
                        return false;
                    }
                    oTable = oTable.next;
                }
            }

        } catch (ClassCastException e) {
            return false;
        }

        // 위의 모든 과정을 거쳤는데 아무 문제가 없다면 동일한 객체이기 때문에 true를 반환한다.
        return true;
    }
}
