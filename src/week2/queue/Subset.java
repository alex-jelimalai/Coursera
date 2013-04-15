package week2.queue;

import edu.princeton.cs.introcs.StdIn;

/**
 * @author: ajelimalai
 * @created: 6/03/13
 */

public class Subset {

    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        populateQueue(randomizedQueue);
        show(k, randomizedQueue);
    }


    private static void show(final int aK, final RandomizedQueue<String> aQueue) {
        for (int i = 0; i < aK; i++) {
            System.out.println(aQueue.dequeue());
        }
    }


    private static void populateQueue(final RandomizedQueue<String> aQueue) {
        while (!StdIn.isEmpty()) {
            aQueue.enqueue(StdIn.readString());
        }
    }
}
