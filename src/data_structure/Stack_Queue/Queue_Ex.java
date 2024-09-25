package data_structure.Stack_Queue;

import java.util.*;

/**
 * 큐(Queue)
 * 선입선출의 구조를 갖고 있다.
 * 데이터가 삽입할 때를 enqueue, 데이터가 삭제될 때를 dequeue라고 부른다.
 * 큐는 두 개의 포인터(front, rear)를 가지고 있다.
 */

public class Queue_Ex<E> {

    static final int DEFAULT_CAPACITY = 6;      // 배열의 기본 값

    private Object[] arr;       // 데이터를 저장할 배열

    private int front;          // 제일 먼저 들어오는 값
    private int rear;           // 제일 마지막에 들어오는 값

    public Queue_Ex() {
        this.arr = new Object[DEFAULT_CAPACITY];
        front = -1;
        rear = -1;
    }

    // TODO: 용량 확인
    boolean isFull() {
        if (front == 0 && rear == arr.length - 1) {     // front가 0이고 rear가 arr.length - 1 이면 큐에 데이터가 다 찬 것을 의미한다.
            return true;
        }
        return false;
    }

    boolean isEmpty() {
        return front == -1;     // front가 -1이면 데이터가 비어있다는 뜻이다.
    }

    // TODO: 큐 사이즈 조정
    private void resize() {
        int arr_capacity = arr.length - 1;      // 현재 큐의 용량을 확인

        if (front == 0 && rear == arr_capacity) {       // 큐가 꽉 차있는지 확인
            int new_capacity = arr.length * 2;          // 큐의 용량을 2배 늘림
            arr = Arrays.copyOf(arr, new_capacity);     // 기존의 큐에 있던 데이터를 복사
            return;
        } else if (rear < (arr_capacity / 2)) {         // 큐의 용량이 많이 비었는지 확인
            int half_capacity = arr.length / 2;         // 큐의 용량을 1/2로 축소
            arr = Arrays.copyOf(arr, Math.max(half_capacity, DEFAULT_CAPACITY));    // 축소한 크기와 기본 크기를 비교하여 더 큰 값으로 설정
            return;
        }
    }

    // TODO: 데이터 삽입(enqueue)
    public E add(E value) {
        if (isFull()) resize();     // 용량이 다 찼는지 확인 --> 찼으면 공간을 늘린다.

        if (front == -1) front = 0;     // front 위치 이동 (최초 1회만 실행)
        rear++;     // rear 위치 이동
        arr[rear] = value;      // 받아온 데이터 값을 rear 위치에 삽입
        return value;           // 저장한 데이터 반환
    }

    // TODO: 데이터 삭제(dequeue)
    public E poll() {
        if (isEmpty()) {        // 용량이 비었는지 확인
            throw new NoSuchElementException();     // 비었다면 예외 던지기
        } else {
            E value = (E)arr[front];        // 삭제할 값을 value에 저장
            arr[front] = null;          // 삭제할 값의 위치를 null로 초기화
            if (front >= rear) {        // front와 rear의 값이 같아지면 -1로 초기화
                front = -1;
                rear = -1;
            } else {
                front++;        // front 위치를 증가시켜 다음 값을 가리키게 이동
            }
            resize();       // 공간이 남을 시 사이즈 조정
            return value;   // 삭제할 값 반환
        }
    }

    // TODO: front 데이터 확인
    public E peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return (E) arr[front];      // 현재 front에 위치한 값을 반환
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }

    public static void main(String[] args) {
        Queue_Ex queue = new Queue_Ex();

        // TODO: 데이터 추가
        System.out.println(queue.add(1));
        System.out.println(queue.add(2));
        System.out.println(queue.add(3));
        System.out.println(queue.add(4));

        // TODO: front 데이터 확인
        System.out.println(queue.peek());       // 1

        // TODO: 데이터 삭제
        queue.poll();
        queue.poll();

        // TODO: 데이터 출력
        System.out.println(queue);      // [null, null, 3, 4, null, null]
    }
}
