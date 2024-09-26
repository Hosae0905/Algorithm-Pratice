package data_structure.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<E> {

    private final Comparator<? super E> comparator;     // 정렬 방식을 초기화
    private static final int DEFAULT_CAPACITY = 10;     // 초기 힙 용량
    private int size;       // 현재 힙에 저장된 데이터의 갯수
    private Object[] array; // 데이터를 저장할 배열

    /**
     * 기본 생성자(아무런 인자없이 생성했을 경우)
     */
    Heap() {
        this(null);
    }

    /**
     * 정렬 기준을 인자로 넘겨서 힙을 생성할 경우
     * 객체를 정렬해야 하기 때문에 Comparator가 필요하다.
     * @param comparator
     */
    Heap(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.size = 0;
        this.array = new Object[DEFAULT_CAPACITY];
    }

    /**
     * 초기 힙 사이즈와 정렬 기준을 인자로 넘겨서 힙을 생성할 경우
     * @param capacity
     * @param comparator
     */
    Heap(int capacity, Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.size = 0;
        this.array = new Object[capacity];
    }

    /**
     * 부모 노드의 인덱스를 반환(부모 노드 인덱스 = 자식 노드 인덱스 / 2)
     * @param index
     * @return 부모 노드의 인덱스
     */
    private int getParent(int index) {
        return index / 2;
    }

    /**
     * 왼쪽 자식 노드의 인덱스를 반환(왼쪽 자식 노드 = 부모 노드 인덱스 x 2)
     * @param index
     * @return 왼쪽 자식 노드의 인덱스
     */
    private int getLeftChild(int index) {
        return index * 2;
    }

    /**
     * 오른쪽 자식 노드의 인덱스를 반환(오른쪽 자식 노드 인덱스 = 부모 노드 인덱스 x 2 + 1)
     * @param index
     * @return 오른쪽 자식 노드의 인덱스
     */
    private int getRightChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 힙의 크기를 동적으로 늘리기 위한 메서드
     * @param newCapacity 새로운 힙 자료구조 크기
     */
    private void resize(int newCapacity) {
        // 새로운 배열 생성
        Object[] newArray = new Object[newCapacity];

        // 기존에 있던 배열의 요소들을 모두 복사
        for (int i = 1; i <= size; i++) {
            newArray[i] = array[i];
        }

        /*
         * 기존 배열은 GC로 처리될 수 있게 null 값 저장
         */
        this.array = null;
        this.array = newArray;
    }

    /**
     * 힙 자료구조에 데이터를 저장하는 메서드
     * @param value 삽입할 데이터
     */
    public void add(E value) {
        // 만약 힙이 가득 찼다면 동적으로 사이즈 증가
        if (size + 1 == array.length) {
            resize(array.length * 2);
        }
        siftUp(size + 1, value);    // 마지막 위치에 저장해야 되서 현재 size의 +1 값과 삽입할 값을 인자로 넘겨준다.
        size++;     // 데이터를 저장했으면 사이즈 증가
    }

    /**
     * 상향 선별(sift-up) 메서드
     * 배열의 마지막 부분에 데이터를 넣고 부모 노드를 찾아가면서 부모 노드가 삽입된 노드보다 작을 때까지 데이터를 교환한다.
     * @param index 추가할 노드의 인덱스
     * @param target    재배치 할 노드(삽입할 데이터)
     */
    private void siftUp(int index, E target) {
        // comparator가 존재할 경우 인자로 넘겨준다.
        if (comparator != null) {
            siftUpComparator(index, target, comparator);
        } else {
            // comparator가 없을 경우 comparable 방식으로 정렬을 진행한다.
            siftUpComparable(index, target);
        }
    }

    /**
     * Comparator를 이용한 sift-up 메서드
     * @param index 추가할 노드의 인덱스
     * @param target    재배치 할 노드(삽입할 데이터)
     * @param comparator    정렬 방식
     */
    private void siftUpComparator(int index, E target, Comparator<? super E> comparator) {
        // 루트 노드보다 클 때까지만 탐색한다. (루트 노드의 인덱스는 1)
        while (index > 1) {
            int parent = getParent(index);      // 삽입 노드의 부모 노드 인덱스를 구하기
            Object parentValue = array[parent]; // 부모 노드의 값 저장

            /*
             * 사전에 정의해둔 compare 메서드를 통해 비교한다.
             * 삽입한 데이터가 부모 노드보다 크면(즉, 데이터 교환이 일어나지 않으면) 반복문을 종료한다.
             */
            if (comparator.compare(target, (E) parentValue) >= 0) {
                break;
            }

            /*
             * 부모 노드가 삽입할 노드보다 크므로 삽입될 위치에 부모 노드 값을 저장하고
             * 원래 삽입되어야 하는 위치를 부모 노드의 위치로 변경해준다.
             */
            array[index] = parentValue;
            index = parent;
        }

        // 최종적으로 새로 변경된 부모 위치에 기존의 삽입할 값을 저장하여 변경을 완료한다.
        array[index] = target;
    }

    /**
     * comparator 없이 comparable을 통한 sift-up 메서드
     * @param index 삽입할 위치
     * @param target    삽입할 데이터
     */
    private void siftUpComparable(int index, E target) {
        // 삽입할 노드가 부모 노드와 비교할 수 있게 comparable 변수를 생성해서 저장한다. (Comparable은 사전에 정의함.)
        Comparable<? super E> comparable = (Comparable<? super E>) target;
        while (index > 1) {
            int parent = getParent(index);  // comparator와 동일
            Object parentValue = array[parent];

            /*
             * 사전에 정의한 compareTo 메서드를 통해 삽입될 노드와 부모 노드를 비교한다.
             * 삽입한 데이터가 부모 노드보다 크면(즉, 데이터 교환이 일어나지 않으면) 반복문을 종료한다.
             */
            if (comparable.compareTo((E) parentValue) >= 0) {
                break;
            }

            // 부모와 자식의 위치를 서로 변경한다.
            array[index] = parentValue;
            index = parent;
        }
        array[index] = comparable;
    }

    /**
     * 힙 자료구조에서 값을 제거하는 메서드
     * 기본적으로 배열에서 인덱스 1부터 값을 저장하기 때문에 0을 기준으로 잡으면 다른 결과가 발생할 수 있다.
     * @return 제거된 값을 반환해준다.
     */
    public E remove() {
        if (array[1] == null) {     // 첫번째 데이터가 들어가는 곳은 1번 인덱스이기 때문에 1번을 기준으로 값이 있는지 확인한다.
            throw new NoSuchElementException();
        }

        E result = (E) array[1];
        E target;

        /*
         * size가 1이면 힙에 저장된 데이터가 1개라는 의미로 타겟에는 null 값을 저장한다.
         * size가 1이 아니면 데이터가 저장된 힙 자료구조에서 size 값의 인덱스에 위치한 값을 타겟에 저장한다.
         */
        if (size == 1) {
            target = null;
        } else {
            target = (E) array[size];
        }

        // 값을 제거하기 때문에 현재 size 위치에 있는 값을 null로 업데이트한다.
        array[size] = null;

        /*
         * 힙 자료구조에서 삭제는 루트 노드부터 삭제된다.
         * 루트 노드가 삭제되면 힙 자료구조에 저장된 노드들은 재배치가 이루어져야 하기 때문에 재배치할 노드(target)을 인자로 넘겨준다.
         */
        siftDown(1, target);
        return result;      // 삭제된 값을 출력
    }

    /**
     * sift-down(하향 선별) 메서드
     * @param index 삭제할 노드의 인덱스
     * @param target    재배치할 노드
     */
    private void siftDown(int index, E target) {
        // comparator가 존재할 경우
        if (comparator != null) {
            siftDownComparator(index, target, comparator);
        } else {
            // comparator가 없어 comparable을 활용할 경우
            siftDownComparable(index, target);
        }
    }

    /**
     * comparator를 활용한 sift-down 메서드
     * @param index 삭제할 노드의 위치
     * @param target    재배치할 노드
     * @param comparator    정렬 방식
     */
    private void siftDownComparator(int index, E target, Comparator<? super E> comparator) {
        array[index] = null;    // 인덱스에 해당하는 노드를 삭제(루트 노드 삭제)
        size--;     // 데이터를 삭제했기 때문에 사이즈 감소

        int parent = index;     // 삭제한 노드의 인덱스(루트 노드의 인덱스)
        int child;      // 부모 노드와 변경될 자식 노드의 인덱스를 가리키는 변수

        // 왼쪽 자식부터 확인하며 노드의 인덱스가 힙에 저장된 데이터 개수보다 작을 때까지 반복
        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent);      // 오른쪽 자식 인덱스
            Object childValue = array[child];       // 왼쪽 자식 노드

            /*
             * 오른쪽 자식 노드의 인덱스가 size를 넘지 않으면서 왼쪽 자식이 오른쪽 자식보다 큰 경우
             * 재배치 할 노드는 값이 작은 자식 노드와 비교해야 하므로 왼쪽 자식 노드의 값을 오른쪽 자식 노드로 변경
             */
            if (right <= size && comparator.compare((E) childValue, (E) array[right]) > 0) {
                child = right;
                childValue = array[child];
            }

            // 재배치 할 노드가 자식 노드보다 작을 경우 반복문 종료
            if (comparator.compare(target, (E) childValue) <= 0) {
                break;
            }

            /*
             * 현재 부모 인덱스에 자식 노드 값을 저장하고 부모 인덱스를 자식 인덱스로 변경
             */
            array[parent] = childValue;
            parent = child;
        }

        // 최종적으로 재배치 되는 위치에 target 값 저장
        array[parent] = target;

        /*
         * 삭제를 하기 때문에 만약 힙의 사이즈가 기본 사이즈보다 크면서 저장된 데이터의 갯수가 전체 사이즈의 1/4일 경우
         * 힙 사이즈를 축소
         */
        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
    }

    /**
     * comparable을 통해 값을 비교하여 위치를 변경하는 메서드
     * @param index
     * @param target
     */
    private void siftDownComparable(int index, E target) {
        Comparable<? super E> comparable = (Comparable<? super E>) target;

        array[index] = null;
        size--;

        int parent = index;
        int child;

        while ((child = getLeftChild(parent)) <= size) {
            int right = getRightChild(parent);
            Object childValue = array[child];

            if (right <= size && ((Comparable<? super E>) childValue).compareTo((E) array[right]) > 0) {
                child = right;
                childValue = array[child];
            }

            if (comparable.compareTo((E) childValue) <= 0) {
                break;
            }
            array[parent] = childValue;
            parent = child;
        }

        array[parent] = comparable;

        if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
        }
    }

    /**
     * 현재 힙이 비어있는지 체크하는 메서드
     * @return true | false
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 배열로 반환하는 메서드
     * @return Object[]
     */
    public Object[] toArray() {
        return Arrays.copyOf(array, size + 1);
    }

}
