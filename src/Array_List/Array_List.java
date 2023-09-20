package Array_List;

import java.util.Arrays;

public class Array_List<E> implements Array_List_Method<E> {

    private static final int DEFAULT_CAPACITY = 5;
    private static final Object[] EMPTY_ELEMENTDATA = {};

    private int size;
    Object[] elementData;

    public Array_List() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public Array_List(int capacity) {
        if (capacity > 0) {
            this.elementData = new Object[capacity];
        } else if (capacity == 0) {
            this.elementData = new Object[DEFAULT_CAPACITY];
        } else if (capacity < 0) {
            throw new RuntimeException(new IllegalAccessException("리스트 용량이 잘못되었습니다."));
        }

        this.size = 0;
    }

    private void resize() {
        int element_capacity = elementData.length;

        if (element_capacity == size) {
            int new_capacity = element_capacity * 2;

            elementData = Arrays.copyOf(elementData, new_capacity);
            return;
        }

        if ((element_capacity / 2) > size) {
            int half_capacity = element_capacity / 2;

            elementData = Arrays.copyOf(elementData, Math.max(half_capacity, DEFAULT_CAPACITY));
            return;
        }

        if (Arrays.equals(elementData, EMPTY_ELEMENTDATA)) {
            elementData = new Object[DEFAULT_CAPACITY];
            return;
        }
    }

    @Override
    public boolean add(Object value) {
        resize();

        elementData[size] = value;
        size++;
        return true;
    }

    @Override
    public void add(int index, Object value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(value);
        } else {
            resize();

            for (int i = size; i > index; i--) {
                elementData[i] = elementData[i - 1];
            }

            elementData[index] = value;
            size++;
        }

    }

    @Override
    public boolean remove(Object value) {
        int idx = indexOf(value);

        if (idx == -1) return false;

        remove(idx);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        E element = (E) elementData[index];

        elementData[index] = null;

        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
            elementData[i + 1] = null;
        }

        size--;

        resize();

        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) elementData[index];
    }

    @Override
    public void set(int index, Object value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        elementData[index] = value;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0 ? true : false;
    }

    @Override
    public int indexOf(Object value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elementData[i].equals(value)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        if (value == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i].equals(value)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(elementData);
    }

    class ListIterator implements Array_List_Iterator<E> {

        private int nextIndex = 0;

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E) elementData[nextIndex++];
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size();
        }

        @Override
        @SuppressWarnings("unchecked")
        public E previous() {
            return (E) elementData[--nextIndex];
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public void add(Object element) {
            Array_List.this.add(nextIndex, element);
        }

        @Override
        public void remove() {
            Array_List.this.remove(nextIndex - 1);
            nextIndex--;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Array_List<?> cloneList = (Array_List<?>) super.clone();

            cloneList.elementData = new Object[size];

            cloneList.elementData = Arrays.copyOf(elementData, size);

            return cloneList;
        }

        public ListIterator listIterator() {
            return new ListIterator();
        }
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @SuppressWarnings("checked")
    public <T> T[] toArray(T[] arr) {
        if (arr.length < size) {
            return (T[]) Arrays.copyOf(elementData, size);
        } else {
            System.arraycopy(elementData, 0, arr, 0, size);

            if (arr.length > size) arr[size] = null;

            return arr;
        }
    }

    public static void main(String[] args) {

    }
}
