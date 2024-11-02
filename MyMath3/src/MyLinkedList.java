import java.util.ArrayList;
import java.util.Iterator;

/**
 * Linked list implementation of the MyList interface.
 * @author Zakiy Manigo Uni:ztm2106
 * @version 1.0 October 4, 2023
 */
public class MyLinkedList<E> implements MyList<E> {
    private Node head, tail;
    private int size;

    /**
     * Constructs an empty list.
     */
    public MyLinkedList() {
        head = tail = null;
        size = 0;
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
        Node p = head;
        for (int i = 0; i < index; i++, p = p.next);
        E oldElement = p.element;
        p.element = element;
        return oldElement;
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
        Node p = head;
        for (int i = 0; i < index; i++, p = p.next);
        return p.element;
    }

    /**
     * Appends the specified element to the end of this list.
     * @param element  element to be appended to this list
     * @return true
     */
    public boolean add(E element) {
        Node n = new Node(element);
        if (head == null) {
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
        return true;
    }

    /**
     * Removes all of the elements from this list.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    public Iterator<E> iterator() {
        return new ListItr();
    }

    private class ListItr implements Iterator<E> {
        private Node current;

        ListItr() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E element = current.element;
            current = current.next;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        Node next;
        E element;

        public Node(E element) {
            this.element = element;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[");
        Node current = head;
        if(current == null) {
            return "[]";
        }
        while (current.next != null) {
            string.append(current.element).append(", ");
            current = current.next;
        }
        string.append(current.element).append("]");
        return string.toString();
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size);
        }
        Node newNode = new Node(element);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }

            newNode.next = currentNode.next;
            currentNode.next = newNode;
        }

        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", list size: " + size());
        }

        E startvalue;
        if (index == 0) {
            startvalue = head.element;
            head = head.next;
        } else {
            Node currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }

            startvalue = currentNode.next.element;
            currentNode.next = currentNode.next.next;
        }

        size--;
        return startvalue;
    }

    @Override
    public int indexOf(E element) {
        Node currentNode = head;
        for (int i = 0; i < size(); i++) {
            if (currentNode.element.equals(element)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }


    @Override
    public int[] indexesOf(E element) {
        ArrayList<Integer> indexes = new ArrayList<>();
        Node currentNode = head;
        for (int i = 0; i < size(); i++) {
            if (currentNode.element.equals(element)) {
                indexes.add(i);
            }
            currentNode = currentNode.next;
        }

        int[] indexArray = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            indexArray[i] = indexes.get(i);
        }

        return indexArray;
    }

    @Override
    public void reverse() {
        Node currentNode = head;
        Node previousNode = null;
        Node nextNode;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        head = previousNode;
    }


}
