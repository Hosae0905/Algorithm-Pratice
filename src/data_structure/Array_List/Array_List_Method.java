package data_structure.Array_List;

public interface Array_List_Method<T> {

    boolean add(T value);           // 데이터 추가
    void add(int index, T value);   // 특정 인덱스에 데이터 추가

    boolean remove(Object value);   // 데이터 삭제
    T remove(int index);            // 특정 인덱스에 있는 데이터 삭제

    T get(int index);               // 데이터 가져오기
    void set(int index, T value);   // 특정 인덱스에 데이터 저장하기

    boolean contains(Object value); // 특정 데이터가 리스트에 있는지 확인
    int indexOf(Object value);      // 특정 데이터가 몇 번째 위치에 있는지 확인(순차적 확인)
    int lastIndexOf(Object o);      // 특정 데이터가 몇 번째 위치에 있는지 확인(역순으로 확인)

    int size();                     // 데이터의 개수를 반환
    boolean isEmpty();              // 데이터가 없는지 확인

    public void clear();            // 데이터를 모두 삭제
}
