package Array_List;

public interface Array_List_Iterator<T> {

    T next();
    boolean hasNext();
    T previous();
    boolean hasPrevious();

    void add(Object element);
    void remove();
}
