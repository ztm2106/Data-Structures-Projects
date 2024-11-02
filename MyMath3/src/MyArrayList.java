import java.util.ArrayList;
import java.util.Iterator;

/**
 * Resizable-array implementation of the MyList interface.
 * @author Zakiy Manigo Uni:ztm2106
 * @version 1.0 October 4, 2023
 */
public class MyArrayList<E> implements MyList<E> {
    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    Object[] elementData; // non-private to simplify nested class access

    /**
     * Constructs an empty list with the specified initial capacity.
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public MyArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this list contains no elements.
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Appends the specified element to the end of this list.
     * @param element  element to be appended to this list
     * @return true
     */
    public boolean add(E element) {
        if (size + 1 > elementData.length) {
            Object[] newData = new Object[size * 2 + 1];
            for (int i = 0; i < size; i++) {
                newData[i] = elementData[i];
            }
            elementData = newData;
        }
        elementData[size++] = element;
        return true;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index  index of the element to return
     * @return       the element at the specified position in this list
     * @throws       IndexOutOfBoundsException - if the index is out of range
     *               (index < 0 || index >= size())
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        return (E)elementData[index];
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * @param index    index of the element to return
     * @param element  element to be stored at the specified position
     * @return  the element at the specified position in this list
     * @throws  IndexOutOfBoundsException - if the index is out of range
     *          (index < 0 || index >= size())
     */
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", list size: " + size);
        }
        E oldValue = (E)elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        // clear to let GC do its work
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * Returns an iterator over the elements in this list (in proper
     * sequence).
     *
     * The returned list iterator is fail-fast -- modification of the elements
     * is not permitted during iteration.
     */
    public Iterator<E> iterator() {
        return new ListItr();
    }

    private class ListItr implements Iterator<E> {
        private int current;

        ListItr() {
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            return (E)elementData[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[");
        for (int i = 0; i < size(); i++) {
            string.append(get(i));
            if (i < size()-1) {
                string.append(", ");
            }
        }
        string.append("]");
        return string.toString();
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size());
        }
        size++;
        E oldValue = set(index, element);

        if (index <= size) {
            for (int i = index + 1; i < size; i++) {
                oldValue = set(i, oldValue);
            }
        }


    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size());
        }
        else {
            E startValue = get(size()-1);
            for (int i = size() - 2; i >= index; i--){
                startValue = set(i, startValue);
            }

            size--;
            return startValue;
        }
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size(); i++) {
            if (element.equals(get(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int[] indexesOf(E element) {
        MyArrayList<Integer> indexes = new MyArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(element)) {
                indexes.add(i);
            }
        }
        int[] indexArray = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            indexArray[i] = indexes.get(i);
        }

        return indexArray;
    }


    @Override
    public void reverse() {
        int size = size();
        int mid = size / 2;

        for (int i = 0; i < mid; i++) {
            // Swap elements at positions i and size - i - 1
            E temp = get(i);
            set(i, get(size - i - 1));
            set(size - i - 1, temp);
        }
    }

}
