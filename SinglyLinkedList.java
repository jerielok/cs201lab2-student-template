
import java.util.*;

public class SinglyLinkedList<E extends Comparable<E>> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {

        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    public SinglyLinkedList() {

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);

        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (isEmpty()) {
            tail = null;
        }
        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    // write your codes here
    public void swap() {
        if (size <= 1) {
            return;
        }

        // Store nodes in their CURRENT linked-list order
        List<Node<E>> original = new ArrayList<>();

        Node<E> current = head;

        while (current != null) {
            original.add(current);
            current = current.getNext();
        }

        // make sorted list 
        List<Node<E>> sorted = new ArrayList<>(original);

        sorted.sort((a, b)
                -> a.getElement().compareTo(b.getElement())
        );

        // map largest with smallest and second largest with second smallest...
        Map<Node<E>, Node<E>> swapMap = new HashMap<>();

        int n = sorted.size();

        for (int i = 0; i < n / 2; i++) {
            Node<E> low = sorted.get(i);
            Node<E> high = sorted.get(n - 1 - i);

            swapMap.put(low, high);
            swapMap.put(high, low);
        }

        // odd number of nodes, middle-ranked node stays where it is
        if (n % 2 == 1) {
            Node<E> middle = sorted.get(n / 2);
            swapMap.put(middle, middle);
        }

        // using the swapMap, figure out new ordering of nodes
        List<Node<E>> newOrder = new ArrayList<>();

        for (Node<E> node : original) {
            newOrder.add(swapMap.get(node));
        }

        // using newOrder list to connect all the nodes in order
        for (int i = 0; i < n - 1; i++) {
            newOrder.get(i).setNext(newOrder.get(i + 1));
        }

        newOrder.get(n - 1).setNext(null);

        // update head and tail
        head = newOrder.get(0);
        tail = newOrder.get(n - 1);
    }
}
