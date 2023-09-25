package Stack_Queue;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 스택(Stack)
 * 후입선출의 구조를 갖고있다.
 * 뒤로 가기, 실행 취소(redo/undo), 스택 메모리와 같이 직전의 데이터를 빠르게 갖고와야할 경우 많이 사용된다.
 */

public class Stack_Ex<E> {
    
    private static final int DEFAULT_CAPACITY = 6;      // 기본 할당 용량
    private Object[] arr;           // 데이터를 담을 배열
    private int top;                // 스택의 가장 마지막 원소를 가리키는 인덱스 필드
    
    public Stack_Ex() {
        this.arr = new Object[DEFAULT_CAPACITY];
        this.top = -1;
    }
    
    
    // TODO: 스택의 원소 확인
    public boolean isFull() {
        return top == arr.length - 1;
    }
    
    public boolean isEmpty() {
        return top == -1;       // top이 -1이면 아무것도 없는 배열을 의미함.
    }

    // TODO: 스택 사이즈 조정
    private void resize() {
        int arr_capacity = arr.length - 1;      // 현재 스택의 용량을 확인

        if (top == arr_capacity) {          // 현재 스택의 용량이 꽉 찼는지 확인
            int new_capacity = arr.length * 2;          // 용량을 2배로 늘림
            arr = Arrays.copyOf(arr, new_capacity);     // 기존의 스택에 있던 데이터 복사
            return;
        } else if (top < (arr_capacity / 2)) {      // 현재 스택의 용량이 많이 비었는지 확인
            int half_capacity = arr.length / 2;     // 용량을 1/2로 축소
            
            arr = Arrays.copyOf(arr, Math.max(half_capacity, DEFAULT_CAPACITY));    // 기본 용량과 비교하여 스택을 복사하기
            return;
        }
    }

    // TODO: 데이터 삽입
    public E push(E value) {
        if (isFull()) resize();     // 스택의 용량이 다 찼는지 확인하고 공간이 부족하면 늘린다.

        top++;      // 데이터를 추가하기 때문에 top의 위치를 올려준다.

        arr[top] = value;       // 스택의 새로운 top 위치에 새로운 값을 넣어준다.

        return value;       // 추가된 데이터를 반환해준다.
    }

    // TODO: 데이터 삭제
    public E pop() {
        if (isEmpty()) throw new EmptyStackException();     // 스택이 비어있는지 확인하고 비어있다면 예외 던지기

        E value = (E) arr[top];         // 현재 스택의 top에 위치한 데이터를 value에 담기

        arr[top] = null;        // 데이터를 뺀 공간은 null로 초기화

        top--;          // 데이터를 pop했으니 스택의 top 위치를 감소 --> 사이즈 감소

        resize();       // 빈공간이 많이 있는지 확인

        return value;   // pop한 데이터 반환
    }
    
    // TODO: top 데이터 확인
    public E peek() {
        if (isEmpty()) throw new EmptyStackException();     // 스택이 비어있는지 확인하고 비어있다면 예외 던지기

        return (E) arr[top];        // 스택의 top에 위치한 데이터 반환 --> 삭제되는 것이 아님
    }

    // TODO: 데이터 탐색
    public int search(E value) {
        for (int i = top; i >= 0; i--) {
            if (arr[i].equals(value)) return top - i + 1;
        }

        return -1;      // 찾는 값이 없으면 -1을 반환
    }

    // TODO: 데이터 출력


    @Override
    public String toString() {
        return Arrays.toString(arr);
    }

    public static void main(String[] args) {
        Stack_Ex<Integer> stack = new Stack_Ex<>();

        // TODO: 데이터 삽입
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        // TODO: top의 값을 확인
        System.out.println(stack.peek());       // 5
        
        // TODO: 데이터 탐색
        System.out.println(stack.search(3));        // 3

        // TODO: 데이터 삭제
        stack.pop();
        stack.pop();
        
        // TODO: 스택 출력
        System.out.println(stack);       // [1, 2, 3, null, null, null]
        
    }
}
