package Array_List;

public interface Array_List_Method<T> {

    boolean add(T value);
    void add(int index, T value);

    boolean remove(Object value);
    T remove(int index);

    T get(int index);
    void set(int index, T value);

    boolean contains(Object value);
    int indexOf(Object value);
    int lastIndexOf(Object o);

    int size();
    boolean isEmpty();

    public void clear();
}
