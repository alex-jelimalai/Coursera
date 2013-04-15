package week2.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.introcs.StdRandom;

/**
 * @author: ajelimalai
 * @created: 5/03/13
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Item[] array;


    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[10];
    }


    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }


    // return the number of items on the queue
    public int size() {
        return size;

    }


    // add the item
    public void enqueue(final Item item) {
        validateNotNull(item);
        ensureSpace();
        array[size] = item;
        size++;
    }


    private void ensureSpace() {
        if (size == array.length) {
            makeNewArray(2 * array.length);
        }
    }


    private void makeNewArray(int newLength) {
        Item[] newArray = (Item[]) new Object[newLength];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }


    // delete and return a random item
    public Item dequeue() {
        validateNotEmpty();
        final int index = getRandomIndex();
        Item item = array[index];
        array[index] = array[--size];
        array[size] = null;
        compressSpace();
        return item;
    }


    private void compressSpace() {
        if (size > 3 && size == array.length / 4) {
            makeNewArray(array.length / 2);
        }
    }


    private int getRandomIndex() {
        return StdRandom.uniform(0, size);
    }


    private void validateNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }


    // return (but do not delete) a random item
    public Item sample() {
        validateNotEmpty();
        final int index = getRandomIndex();
        return array[index];
    }


    private void validateNotNull(final Item aItem) {
        if (aItem == null) {
            throw new NullPointerException();
        }
    }


    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }


    private class RandomizedIterator implements Iterator<Item> {

        private int position = 0;
        private Item[] items;


        private RandomizedIterator() {
            items = (Item[]) new Object[size];
            System.arraycopy(array, 0, items, 0, size);
            shuffle();
        }


        private void shuffle() {
            if (items.length > 0) {
                StdRandom.shuffle(items, 0, items.length - 1);
            }
        }


        @Override
        public boolean hasNext() {
            return position < items.length;
        }


        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[position++];
        }


        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
