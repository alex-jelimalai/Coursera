package week2.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author: ajelimalai
 * @created: 5/03/13
 */
public class Deque<Item> implements Iterable<Item> {

    private Node firstNode = null;
    private int size = 0;


    // construct an empty deque
    public Deque() {

    }


    // is the deque empty?
    public boolean isEmpty() {
        return firstNode == null;
    }


    // return the number of items on the deque
    public int size() {
        return size;
    }


    // insert the item at the front
    public void addFirst(final Item item) {
        validateNotNull(item);
        size++;
        Node node = firstNode;
        firstNode = new Node(item);
        firstNode.next = node;
        if (node != null) {
            firstNode.previous = node.previous;
            node.previous = firstNode;
        } else {
            firstNode.previous = firstNode;
        }
    }


    private void validateNotNull(final Item aItem) {
        if (aItem == null) {
            throw new NullPointerException("Null items are not allowed in queue");
        }
    }


    // insert the item at the end
    public void addLast(final Item item) {
        validateNotNull(item);
        Node node = new Node(item);
        if (isEmpty()) {
            firstNode = node;
            firstNode.previous = firstNode;
        } else {
            Node lastNode = firstNode.previous;
            lastNode.next = node;
            node.previous = lastNode;
            firstNode.previous = node;
        }
        size++;
    }


    // delete and return the item at the front
    public Item removeFirst() {
        validateExistElements();
        Item item = firstNode.item;
        Node lastNode = firstNode.previous;
        firstNode = firstNode.next;
        if (firstNode != null) {
            firstNode.previous = lastNode;
        }
        size--;
        return item;
    }


    private void validateExistElements() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements in the queue");
        }
    }


    // delete and return the item at the end
    public Item removeLast() {
        validateExistElements();
        final Node oldLastElement = firstNode.previous;
        stepBack(oldLastElement);
        size--;
        return oldLastElement.item;
    }


    private void stepBack(final Node aOldLastElement) {
        Node newLastNode = aOldLastElement.previous;
        if (isNotLastNode()) {
            newLastNode.next = null;
            firstNode.previous = newLastNode;
        } else {
            firstNode = null;
        }
    }


    private boolean isNotLastNode() {
        return firstNode != firstNode.previous;
    }


    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    private class Node {

        private Item item;
        private Node next;
        private Node previous;


        private Node() {

        }


        private Node(final Item aItem) {
            this.item = aItem;
        }

    }


    private class DequeIterator implements Iterator<Item> {

        private Node node = firstNode;
        private boolean started;


        @Override
        public boolean hasNext() {
            if (!started) {
                return firstNode != null;
            } else {
                return node.next != null;
            }
        }


        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (!started) {
                started = true;
                return node.item;
            }
            node = node.next;
            return node.item;
        }


        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
