package data_structure.Array_List;

import java.util.Arrays;

/**
 * ArrayList 특징
 * 연속적인 데이터의 리스트
 * ArrayList 클래스는 내부적으로 Object[] 배열을 이용하여 요소를 저장
 * 배열을 이용하기 때문에 인덱스를 이용해 요소에 빠르게 접근 가능
 * 크기가 고정되어 있는 배열과는 달리 가변적으로 공간을 늘리거나 줄일 수 있음
 * 배열 공간이 꽉 찰 때마다 배열을 복사하는 방식으로 늘리기 때문에 이 과정에서 지연이 발생
 * 데이터를 중간에 삽입 및 삭제를 하는 경우 요소들의 위치를 앞뒤로 자동으로 이동시켜줘서 삽입 삭제 동작이 느림
 * 조회를 많이 하는 경우에 사용하는 것이 효율적
 *
 * ※ elementData의 크기를 구할때 length가 아닌 size를 구하는 이유
 * - 자료를 담은 배열 elementData의 크기가 data_structure.Array_List 클래스의 크기를 대변해주지 못하기 때문이다.
 *
 * https://inpa.tistory.com/entry/DS-%F0%9F%A7%B1-ArrayList-%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0-%EC%A7%81%EC%A0%91-%EA%B5%AC%ED%98%84-%EA%B0%95%EC%9D%98
 */

public class Array_List<E> implements Array_List_Method<E> {

    private static final int DEFAULT_CAPACITY = 5;      // 배열이 생성될때 기본 용량 (ArrayList 클래스에는 10으로 되어있음)
    private static final Object[] EMPTY_ELEMENTDATA = {};   // 빈 배열

    private int size;           // elementData 배열의 총 개수(크기)를 나타내는 변수
    Object[] elementData;       // 자료를 담을 배열(Object 배열로 담는다.)

    // TODO: 생성자(초기 공간 할당 X)
    public Array_List() {
        this.elementData = new Object[DEFAULT_CAPACITY];        // 디폴트 용량으로 초기화
        this.size = 0;
    }

    // TODO: 생성자(초기 공간 할당 O)
    public Array_List(int capacity) {
        if (capacity > 0) {     // 파라미터가 양수인 경우
            this.elementData = new Object[capacity];
        } else if (capacity == 0) {     // 파라미터가 0인 경우
            this.elementData = new Object[DEFAULT_CAPACITY];
        } else if (capacity < 0) {      // 파라이터가 0 이하인 경우 --> 에러 발생시키도록 설계
            throw new RuntimeException(new IllegalAccessException("리스트 용량이 잘못되었습니다."));
        }

        this.size = 0;
    }

    // TODO: ArrayList 사이즈 조정
    // 데이터 추가, 삭제 등이 실행될 때 기본적으로 호출된다.
    private void resize() {
        int element_capacity = elementData.length;      // 현재 배열의 크기

        if (element_capacity == size) {     // 용량이 꽉 찬 경우
            int new_capacity = element_capacity * 2;        // 현재 배열의 크기를 2배로 늘려준다.

            elementData = Arrays.copyOf(elementData, new_capacity);     // 기존 배열의 데이터를 복사해준다.
            return;
        }

        if ((element_capacity / 2) > size) {        // 용량이 남는 경우
            int half_capacity = element_capacity / 2;       // 현재 용량을 2로 나눠준다.

            // 2로 나눈 용량과 기본 용량을 비교하여 더 큰 쪽으로 설정하고 배열의 데이터를 복사해준다.
            elementData = Arrays.copyOf(elementData, Math.max(half_capacity, DEFAULT_CAPACITY));
            return;
        }

        if (Arrays.equals(elementData, EMPTY_ELEMENTDATA)) {    // 빈 배열인 경우
            elementData = new Object[DEFAULT_CAPACITY];     // 기본 용량으로 배열을 초기화한다.
            return;
        }
    }


    // TODO: 데이터 추가
    @Override
    public boolean add(Object value) {
        resize();       // 현재 배열의 크기 확인

        elementData[size] = value;      // 추가할 수 있는 마지막 배열의 위치(값이 없다면 size는 0부터 시작)
        size++;         // 추가 후 배열의 크기 증가
        return true;
    }

    @Override
    public void add(int index, Object value) {
        if (index < 0 || index > size) {        // 인덱스의 값이 유효한 값인지 확인 --> 예외 설정
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {        // 인덱스가 마지막 위치일 경우
            add(value);             // 데이터를 그대로 추가
        } else {                    // 마지막 위치가 아닐 경우
            resize();               // 현재 배열이 꽉 차 있는지 확인

            // 받아온 인덱스 위치까지 순회하면서 데이터들을 한 칸씩 뒤로 밀기
            for (int i = size; i > index; i--) {
                elementData[i] = elementData[i - 1];
            }

            elementData[index] = value;     // 받아온 인덱스에 받아온 데이터를 넣기
            size++;     // 데이터를 추가했으니 사이즈 증가
        }

    }

    // TODO: 데이터 삭제
    @Override
    public boolean remove(Object value) {
        int idx = indexOf(value);       // 매개변수로 받은 데이터의 인덱스 위치를 확인

        if (idx == -1) return false;    // 값에 해당하는 인덱스가 없을 경우

        remove(idx);        // 인덱스 넘겨주기
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")  // 리스트에서 형 안정성이 확보되므로 클래스 형변환 예외를 무시하도록 설정
    public E remove(int index) {        // 실질적인 삭제 처리
        if (index < 0 || index >= size) {       // 인덱스의 값이 유효한지 확인 --> 예외 설정
            throw new IndexOutOfBoundsException();
        }

        E element = (E) elementData[index];     // 인덱스에 해당하는 값을 따로 저장

        elementData[index] = null;      // 인덱스에 해당하는 값을 null로 초기화

        for (int i = index; i < size - 1; i++) {        // 삭제한 인덱스까지 데이터를 앞으로 당기기
            elementData[i] = elementData[i + 1];
            elementData[i + 1] = null;      // 사이즈는 고정값이기 때문에 앞으로 당겨져서 비어있게 되는 인덱스의 값은 null로 초기화
        }

        size--;         // 삭제했으니 크기 줄이기

        resize();       // 현재 배열의 공간이 많이 남았는지 확인

        return element;     // 삭제된 값을 반환
    }

    @Override
    public void clear() {       // 모든 데이터를 삭제하기
        elementData = new Object[DEFAULT_CAPACITY];     // 현재 배열에 기본 용량의 배열로 초기화 해주기
        size = 0;       // 모든 데이터를 삭제했기 때문에 사이즈는 0으로 초기화
    }

    // TODO: 데이터 가져오기
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {       // 인덱스가 유효한 값인지 확인 --> 예외 설정
            throw new IndexOutOfBoundsException();
        }

        return (E) elementData[index];      // 인덱스에 해당하는 데이터를 반환
    }

    // TODO: 데이터 저장하기
    @Override
    public void set(int index, Object value) {
        if (index < 0 || index >= size) {       // 인덱스가 유효한 값인지 확인 --> 예외 설정
            throw new IndexOutOfBoundsException();
        }

        elementData[index] = value;     // 인덱스에 해당하는 곳에 받아온 데이터를 저장
    }

    // TODO: 데이터 조회
    @Override
    public boolean contains(Object value) {     // 데이터가 있는지 확인
        return indexOf(value) >= 0 ? true : false;      // 데이터를 넘겨줘서 해당하는 인덱스가 넘어오면 true -1이 넘어오면 false
    }

    @Override
    public int indexOf(Object value) {
        if (value == null) {        // 매개변수가 null 일경우(null 비교는 동등 연산자로 진행한다.)
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;       // 인덱스 반환
                }
            }
        } else {        // 매개변수가 null이 아닐 경우
            for (int i = 0; i < size; i++) {
                if (elementData[i].equals(value)) {
                    return i;       // 인덱스 반환
                }
            }
        }

        return -1;      // 찾는 값이 없을 경우
    }

    @Override
    public int lastIndexOf(Object value) {
        if (value == null) {        // 매개변수가 null 일경우(동등 연산자로 진행)
            for (int i = size - 1; i >= 0; i--) {       // 뒤에서 부터 역순으로 값을 조회
                if (elementData[i] == null) {
                    return i;       // 인덱스 반환
                }
            }
        } else {        // 매개변수가 null이 아닐 경우
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i].equals(value)) {
                    return i;       // 인덱스 반환
                }
            }
        }

        return -1;      // 찾는 값이 없을 경우
    }

    @Override
    public int size() {     // 현재 배열의 사이즈
        return size;
    }

    @Override
    public boolean isEmpty() {      // 현재 배열이 비어있는지 확인
        return size == 0;
    }

    // TODO: 데이터 출력
    @Override
    public String toString() {      // 현재 배열의 데이터들을 출력
        return Arrays.toString(elementData);
    }

    // TODO: ArrayList_심화
    class ListIterator implements Array_List_Iterator<E> {

        private int nextIndex = 0;      // 커서 위치

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E) elementData[nextIndex++];        // 리스트 데이터를 반환하고 커서를 다음으로 옮긴다.
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size();      // 커서의 위치가 리스트의 크기보다 작으면 아직 순회할 데이터가 남았다는 뜻이다.
        }

        @Override
        @SuppressWarnings("unchecked")
        public E previous() {
            return (E) elementData[--nextIndex];        // 리스트의 데이터를 반환하고 커서를 앞으로 옮긴다.
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;       // 커서가 0보다 크다는 것은 아직 순회할 데이터가 남았다는 뜻이다.
        }

        @Override
        public void add(Object element) {       // 정규화된 this를 사용하여 구현
            Array_List.this.add(nextIndex, element);    // 외부 클래스의 add를 호출
        }

        @Override
        public void remove() {      // 정규화된 this를 통해 구현
            Array_List.this.remove(nextIndex - 1);      // 외부 클래스의 remove 메서드 호출
            nextIndex--;        // 다음 데이터를 가리키게 되기 때문에 -를 해줘야한다.
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Array_List<?> cloneList = (Array_List<?>) super.clone();        // data_structure.Array_List 복사

            cloneList.elementData = new Object[size];       // Object 배열을 재생성

            cloneList.elementData = Arrays.copyOf(elementData, size);       // copyOf를 사용하여 데이터를 복사

            return cloneList;
        }
    }
    public ListIterator listIterator() {
        return new ListIterator();
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);        // 실제 리스트를 배열로 복사하여 변환
    }

    @SuppressWarnings("checked")
    public <T> T[] toArray(T[] arr) {
        if (arr.length < size) {        // 파라미터 배열의 길이와 현재 리스트의 사이즈를 비교
            return (T[]) Arrays.copyOf(elementData, size);      // 작으면 그래도 복사해서 반환
        } else {            // 큰 경우
            System.arraycopy(elementData, 0, arr, 0, size);     // 리스트 요소들만 삽입해준다.

            if (arr.length > size) arr[size] = null;        // 어디까지 할당되었는지를 null로 표시해서 알려준다.

            return arr;
        }
    }

    public static void main(String[] args) {

        // TODO: 선언 및 초기화
        Array_List array_list1 = new Array_List();
        Array_List array_list2 = new Array_List(5);

        // TODO: 데이터 추가
        array_list1.add("Kim");
        array_list1.add(2);
        array_list1.add("Three");
        array_list1.add(2, 3);
        array_list1.add("Four");
        array_list1.add("Five");

        array_list2.add(1);
        array_list2.add(2);
        array_list2.add(3);
        array_list2.add(4);
        array_list2.add(5);

        // TODO: 데이터 삭제
        array_list1.remove("Kim");
        array_list2.remove(1);

        // TODO: 데이터 가져오기
        array_list1.get(1);
        array_list2.get(1);

        // TODO: 데이터 저장하기
        array_list1.set(1, "Tom");
        array_list2.set(2, "Kang");

        // TODO: 데이터 조회
        array_list1.contains("Tom");
        array_list2.indexOf(1);
        array_list1.lastIndexOf("Three");

        array_list1.size();
        array_list2.size();

        array_list1.toString();
        array_list2.toString();

        // TODO: 이터레이터
        Array_List<Number> array_list3 = new Array_List<Number>();
        array_list3.add(1);
        array_list3.add(2);
        array_list3.add(3);
        array_list3.add(4);
        array_list3.add(5);

        Array_List<Number>.ListIterator iterator = array_list3.listIterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }

        iterator.add(10);
        System.out.println(array_list3);


    }
}
