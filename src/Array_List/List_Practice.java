package Array_List;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class List_Practice {
    public static void main(String[] args) {

        // TODO: ArrayList  -- 내부적으로는 배열을 사용하여 데이터를 저장하기 때문에 데이터 접근 속도가 빠르다.
        System.out.println("=============ArrayList=================");
        ArrayList<String> list1 = new ArrayList();
        
        // TODO: 값 저장
        list1.add("Kim");       // 처음 데이터를 저장하면 index 0부터 저장한다.
        list1.add(0, "Jun");       // 리스트는 배열과 다르게 중간에 데이터 삽입이 쉽다.
        list1.add(2, "Tom");

        // TODO: 값 변경
        list1.set(0, "Kang");

        // TODO: 값 삭제
        list1.remove(1);        // 중간에 데이터를 삭제하는 것도 간편하게 사용할 수 있다.
//        list1.clear();        // clear()는 리스트에 있는 모든 데이터를 삭제한다.

        // TODO: 값 출력
        for (int i = 0; i < list1.size(); i++) {    // 리스트는 size() 메서드를 통해 리스트의 길이를 알 수 있다.
            System.out.println(list1.get(i));
        }

        for (String str : list1) {
            System.out.println(str);
        }

        // TODO: 값 조회
        System.out.println(list1.contains("Kim"));      // 입력받은 값이 있는지 확인한다. 있으면 true 없으면 false
        System.out.println(list1.contains("Kang"));
        System.out.println(list1.indexOf("Kim"));       // 입력받은 값의 위치를 찾아준다. 없으면 -1
        System.out.println(list1.indexOf("Tom"));

        // TODO: LinkedList -- 노드를 사용하여 데이터를 다루며 저장, 추가, 삭제가 빈번하게 일어나는 경우에 유용하다.
        System.out.println("=============LinkedList=================");
        LinkedList<String> list2 = new LinkedList();    // 연결 리스트는 데이터가 추가될 때마다 노드들이 생성되어
                                                        // 동적으로 추가되는 방식이기 때문에 초기 값을 미리 지정하는 기능을 제공하지 않는다.

        // TODO: 값 저장
        list2.addFirst("ABC");
        list2.addLast("123");
        list2.add(1, "mid");
        list2.add("1A1");
        list2.add("B2B");
        
        // TODO: 값 변경
        list2.set(1, "aaa");

        // TODO: 값 삭제
        list2.removeFirst();
        list2.removeLast();
        list2.remove(1);
//        list2.clear();        // 리스트에 저장된 값을 모두 삭제한다.

        // TODO: 값 출력
        for (String str : list2) {
            System.out.println(str);
        }

        // TODO: 값 조회
        System.out.println(list2.contains("123"));
        System.out.println(list2.indexOf("mid"));



    }
}
