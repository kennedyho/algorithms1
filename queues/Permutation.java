/* *****************************************************************************
 *  Name: Kennedy Ho
 *  Date: 2019/11/10
 *  Description: Permutation
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int argument = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();

        if (argument < 0) {
            throw new IllegalArgumentException();
        }

        while (!StdIn.isEmpty()) {
            randQueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < argument; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }
}
