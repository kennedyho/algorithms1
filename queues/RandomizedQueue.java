/* *****************************************************************************
 *  Name: Kennedy Ho
 *  Date: 2019/11/08
 *  Description: Randomized Queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randQ; // the data structure for queue
    private int num;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randQ = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return num == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return num;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null!");
        }

        // if queue is full, double the array size
        if (num == randQ.length) {
            resize(randQ.length * 2);
        }
        randQ[num++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        // return a item at a random index and replace with the last item in queue
        int dqIndex = StdRandom.uniform(num);
        Item item = randQ[dqIndex];
        randQ[dqIndex] = randQ[--num];
        randQ[num] = null;
        // if the number of items is a quarter
        if (num > 0 && num == randQ.length / 4) {
            resize(randQ.length / 2); // reduce the size by half
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return randQ[StdRandom.uniform(num)];
    }

    // resize the array when it's full and also when it's a quarter full
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < num; i++) {
            copy[i] = randQ[i];
        }
        randQ = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private int current = num; // current number of items
        private final Item[] randomized = copyAndShuffle(); // a copy of randQ which is shuffled

        // is there a next item?
        public boolean hasNext() {
            return current != 0;
        }

        // return the next item and go to the next index
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return randomized[--current];
        }

        // Unsupported remove() method
        public void remove() {
            throw new UnsupportedOperationException();
        }

        // copy the current randomized queue to a new array and shuffle it uniformly
        private Item[] copyAndShuffle() {
            Item[] array = (Item[]) new Object[num];
            for (int i = 0; i < num; i++) {
                array[i] = randQ[i];
            }
            StdRandom.shuffle(array);
            return array;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rdq = new RandomizedQueue<>();
        rdq.enqueue("Kennedy");
        rdq.enqueue("is");
        rdq.enqueue("name");
        rdq.enqueue("My");

        for (String s : rdq) {
            System.out.print(s + " ");
        }
        System.out.println();

        while (!rdq.isEmpty()) {
            System.out.print(rdq.dequeue() + " ");
        }
        System.out.println();
    }
}
