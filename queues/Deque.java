/* *****************************************************************************
 *  Name: Kennedy Ho
 *  Date: 2019/11/08
 *  Description: Deque implementation
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // first node in the deque
    private Node last; // last node in the deque
    private int noOfItems; // keep track of the number of items in a deque

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        noOfItems = 0;
    }

    // A single node with double link next and previous
    private class Node {
        private Item item;
        private Node next; // link to next node
        private Node previous; // link to previous node

        // getter of item
        public Item getItem() {
            return item;
        }

        // setter of item
        public void setItem(Item item) {
            this.item = item;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return noOfItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null!");
        }
        Node oldFirst = first;
        first = new Node();
        first.setItem(item);
        // if deque is empty, make first and last pointing to the same item
        if (isEmpty()) {
            last = first;
        }
        else { // add to the front of the list
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        noOfItems++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null!");
        }
        Node oldLast = last;
        last = new Node();
        last.setItem(item);
        // if deque is empty, make first and last pointing to the same item
        if (isEmpty()) {
            first = last;
        }
        else { // add to the end of the list
            oldLast.next = last;
            last.previous = oldLast;
        }
        noOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.getItem();
        if (size() == 1) { // clear first and last if only 1 item left
            first = null;
            last = null;
        }
        else { // move first forward and unlink previous node
            first = first.next;
            first.previous = null;
        }
        noOfItems--;
        return item;
    }

    // remove and return the item from the front
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.getItem();
        if (size() == 1) { // clear first and last if only 1 item left
            first = null;
            last = null;
        }
        else { // move last backwards and unlink next node
            last = last.previous;
            last.next = null;
        }
        noOfItems--;
        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // Iterator class for deque
    private class ListIterator implements Iterator<Item> {

        private Node current = first; // let current always point to the first node

        // is there a next item?
        @Override
        public boolean hasNext() {
            return current != null;
        }

        // return current item and move current to next item
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.getItem();
            current = current.next;
            return item;
        }

        // remove an item, not supported
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Main function for unit test
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("is");
        deque.addLast("Kennedy");
        deque.addFirst("My");
        deque.addLast("name");

        for (String s : deque) {
            System.out.print(s + " ");
        }
        System.out.println();

        while (!deque.isEmpty()) {
            System.out.print(deque.removeFirst() + " ");
            System.out.print(deque.removeLast() + " ");
        }
        System.out.println();
    }
}
