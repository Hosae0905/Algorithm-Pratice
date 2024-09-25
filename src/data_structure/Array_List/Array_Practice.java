package data_structure.Array_List;

import java.util.Arrays;
import java.util.Comparator;

public class Array_Practice {
    public static void main(String[] args) {

        // TODO: 1차원 배열
        // 배열의 선언
        int[] arr = new int[5];     // 배열 객체를 생성하여 선언
        int[] arr1 = {40, 20, 10, 30, 50};  // 1차원 배열 선언과 초기화

        // 배열의 초기화
        for (int i = 0; i < arr.length; i++) {      // 객체로 생성한 배열을 for문을 통해서 초기화
            arr[i] = i + 1;
        }

        // 배열 출력
        System.out.println(Arrays.toString(arr));

        // 배열 정렬
        Arrays.sort(arr);       // 자기 자신 배열을 정렬
        Arrays.sort(arr, 0, 2);     // 배열의 일부만 정렬

        // 배열 비교
        Arrays.equals(arr, arr1);

        // TODO: 2차원 배열
        // 2차원 배열 선언
        int[][] twoArr = new int[3][4];     // 객체를 생성하여 선언
        int[][] twoArr1 = {     // 2차원 배열 선언과 초기화
                {10, 20, 30, 40},       // [0][0] ~ [0][3]
                {20, 30, 40, 50},       // [1][0] ~ [1][3]
                {30, 40, 50, 60}        // [2][0] ~ [2][3]
        };

        // 2차원 배열 초기화
        for (int i = 0; i < twoArr.length; i++) {
            for (int j = 0; j < twoArr[i].length; j++) {
                twoArr[i][j] = i + j;
            }
        }

        // 2차원 배열 출력
        System.out.println(Arrays.deepToString(twoArr));

        // 2차원 배열 비교
        Arrays.deepEquals(twoArr, twoArr1);

        // TODO: 가변 배열
        int[][] changeArr = {   // 2차원 배열을 테이블 형태로 하지 않아도 사용할 수 있다.
                {10, 20, 30, 40},
                {20, 30},
                {30, 40, 50},
                {40, 50}
        };

        // TODO: 객체 배열
        // 객체 배열 선언
        newObject[] arrayObj = new newObject[3];    // 객체 배열을 생성하여 선언
        newObject[] arrayObj1 = {                   // 객체 배열 선언과 초기화
                new newObject(1, "Tae"),
                new newObject(2, "Choi"),
                new newObject(3, "Yang")
        };

        // 객체 배열 초기화
        arrayObj[0] = new newObject(4, "kim");
        arrayObj[1] = new newObject(2, "Lee");
        arrayObj[2] = new newObject(7, "Jang");

        // 객체 배열 출력
        System.out.println(arrayObj[0].name);

        // 객체 배열 복사
        arrayObj1 = arrayObj.clone();       // 주소가 복사되기 때문에 같은 값을 가리키게 되어 하나의 값을 변경하면 같이 변경된다.
        System.out.println(arrayObj1[0].name);

        arrayObj1[0].name = "James";
        System.out.println(arrayObj1[0].name);
        System.out.println(arrayObj[0].name);

        newObject[] arrayObj2 = new newObject[3];
        for (int i = 0; i < arrayObj.length; i++) {     // for문을 이용해서 객체 배열의 값을 복사하여 사용하면 같이 변경되는 것을 막을 수 있다.
            arrayObj2[i] = new newObject(arrayObj[i].id, arrayObj[i].name);
        }

        arrayObj2[0].name = "Tom";
        System.out.println(arrayObj[0].name);
        System.out.println(arrayObj2[0].name);

        // 객체 배열 정렬
        Arrays.sort(arrayObj);      // Comparable 사용

        for (newObject objectArr : arrayObj) {
            System.out.println(objectArr.id);
        }

        Arrays.sort(arrayObj, new Comparator<newObject>() {     // Comparator 사용
            @Override
            public int compare(newObject o1, newObject o2) {
                return o1.name.compareTo(o2.name);      // 문자열 비교
            }
        });

        for (newObject objectArr : arrayObj) {
            System.out.println(objectArr.name);
        }

        newObject[] arrayObj3 = {
                new newObject(1, "Tae"),
                new newObject(2, "Choi"),
                new newObject(3, "Yang"),
                new newObject(3, "Kang"),
                new newObject(3, "Tang"),
        };

        Arrays.sort(arrayObj3, Comparator.comparing(newObject::getName));
        for (newObject newArr : arrayObj3) {
            System.out.println(newArr.name);
        }

        System.out.println('\n');

        Arrays.sort(arrayObj3, Comparator.comparing(newObject::getId).thenComparing(newObject::getName));
        for (newObject newArr : arrayObj3) {
            System.out.println(newArr.name + " " + newArr.id);
        }
    }
}

class newObject implements Comparable<newObject>{
    int id;
    String name;

    public newObject(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public int compareTo(newObject arr1) {
        if (this.id < arr1.id) {
            return -1;
        } else if (this.id == arr1.id) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
