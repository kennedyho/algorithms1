/* *****************************************************************************
 *  Name: Kennedy Ho
 *  Date: 2019/11/08
 *  Description: Randomized Queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randQ;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randQ = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null!");
        }

        if (N == randQ.length) {
            resize(randQ.length * 2);
        }
        randQ[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int dqIndex = StdRandom.uniform(N);
        Item item = randQ[dqIndex];
        randQ[dqIndex] = randQ[--N];
        randQ[N] = null;
        if (N > 0 && N == randQ.length / 4) {
            resize(randQ.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return randQ[StdRandom.uniform(N)];
    }

    // resize the array when it's full and also when it's a quarter full
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = randQ[i];
        }
        randQ = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private int current = N;
        private Item[] randomized = copyAndShuffle();

        public boolean hasNext() {
            return current != 0;
        }

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

        private Item[] copyAndShuffle() {
            Item[] array = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
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
