package data_structure.set;

/**
 * Set 인터페이스
 * @param <E> Set 자료구조에 저장되는 요소의 타입
 */

public interface Set<E> {

    /**
     * Set 자료구조에 요소를 추가하는 메서드
     * @param e Set에 추가할 요소
     * @return {@code true} 만약 Set에 추가할 요소가 없어서 정상적으로 추가된 경우,
     *          else, {@code false} 중복된 요소가 있는 경우
     */
    boolean add(E e);

    /**
     * Set 자료구조에서 요소를 삭제하는 메서드
     * @param o Set에서 삭제할 요소
     * @return {@code true} 만약 Set에 삭제할 요소가 있어서 정상적으로 삭제된 경우,
     *          else, {@code false} 삭제할 요소가 없는 경우
     */
    boolean remove(Object o);

    /**
     * 현재 Set 자료구조에서 특정 요소가 포함되어있는지 확인하는 메서드
     * @param o 포함되어있는지 확인할 특정 요소
     * @return {@code true} 만약 Set에서 특정 요소가 포함되어 있을 경우,
     *          else, {@code false} 특정 요소가 포함되어 있지 않을 경우
     */
    boolean contains(Object o);

    /**
     * 지정된 객체가 현재 Set 자료구조와 같은지 판단하는 메서드
     * @param o Set과 비교할 객체
     * @return {@code true} 비교할 Set과 동일할 경우,
     *          else {@code false} 동일하지 않을 경우
     */
    boolean equals(Object o);

    /**
     * 현재 Set 자료구조가 빈 상태인지 확인하는 메서드
     * @return {@code true} 만약 Set 자료구조가 비어있을 경우,
     *          else, {@code false} 비어있지 않을 경우
     */
    boolean isEmpty();

    /**
     * 현재 Set 자료구조에 있는 요소 개수를 반환하는 메서드
     * @return Set 자료구조에 있는 요소의 개수를 반환
     */
    int size();
}
