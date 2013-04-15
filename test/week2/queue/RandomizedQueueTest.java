package week2.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: ajelimalai
 * @created: 5/03/13
 */
public class RandomizedQueueTest {

    public static final String FIRST_ELEMENT = "First Element";
    public static final String SECOND_ELEMENT = "Second element";
    private RandomizedQueue<String> randomizedQueue;


    @Before
    public void setUp() {
        randomizedQueue = new RandomizedQueue<String>();
    }


    @Test
    public void enqueue() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        assertEquals("Element was not added", 1, randomizedQueue.size());
    }


    @Test
    public void enqeueOneElementDequeSameElement() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        assertEquals("Wrong returned element", FIRST_ELEMENT, randomizedQueue.sample());
        assertEquals("Wrong returned element", FIRST_ELEMENT, randomizedQueue.dequeue());
    }


    @Test(expected = NullPointerException.class)
    public void addFirstThrowNullPointerException() {
        randomizedQueue.enqueue(null);
    }


    @Test(expected = NoSuchElementException.class)
    public void removeFirstThrowExceptionWhenNoElements() {
        randomizedQueue.dequeue();
    }


    @Test(expected = NoSuchElementException.class)
    public void removeLastThrowExceptionWhenNoElements() {
        randomizedQueue.sample();
    }


    @Test(expected = UnsupportedOperationException.class)
    public void removeFromIteratorThrowsException() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        randomizedQueue.iterator().remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void nextWhenNoElementsThrowException() {
        randomizedQueue.iterator().next();
    }


    @Test
    public void hasNextReturnTrueIfElementsExists() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        assertTrue("There are elements, but returned false", randomizedQueue.iterator().hasNext());
    }


    @Test
    public void hasNextReturnFalseIfNoElements() {
        assertFalse("There no elements, but returned true", randomizedQueue.iterator().hasNext());
    }


    @Test
    public void hasNext_ReturnsFalseAfterAllNext() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        randomizedQueue.enqueue(SECOND_ELEMENT);
        final Iterator<String> iterator = randomizedQueue.iterator();
        iterator.next();
        iterator.next();
        assertFalse("There no elements, but returned true", iterator.hasNext());

    }


    @Test
    public void iteratorContainsElements() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        randomizedQueue.enqueue(SECOND_ELEMENT);
        final ArrayList<String> values = new ArrayList<String>() {

            {
                add(FIRST_ELEMENT);
                add(SECOND_ELEMENT);
            }
        };
        final Iterator<String> iterator = randomizedQueue.iterator();
        if (iterator.hasNext()) {
            values.contains(iterator.next());
        }
    }


    @Test
    public void addAndRemoveSomeElements() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        randomizedQueue.enqueue(SECOND_ELEMENT);
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        assertTrue("Queue is not empty: ", randomizedQueue.isEmpty());
    }


    @Test
    public void addMoreThanTenElements() {
        for (int i = 0; i < 12; i++) {
            randomizedQueue.enqueue("Element " + i);
        }
        assertEquals("Wrong size", 12, randomizedQueue.size());
    }


    @Test
    public void addMoreAndDequeThanTenElements() {
        for (int i = 0; i < 45; i++) {
            randomizedQueue.enqueue("Element " + i);
        }
        deque(45);
        assertEquals("Wrong size", 0, randomizedQueue.size());
    }


    @Test
    public void addMoreAndSampleThanTenElements() {
        for (int i = 0; i < 45; i++) {
            randomizedQueue.enqueue("Element " + i);
        }
        for (int i = 0; i < 45; i++) {
            randomizedQueue.sample();
        }
        assertEquals("Wrong size", 45, randomizedQueue.size());
    }


    @Test
    public void addRemoveAndAddingAgain() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        randomizedQueue.enqueue(SECOND_ELEMENT);
        randomizedQueue.dequeue();
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(FIRST_ELEMENT);
        randomizedQueue.sample();
        randomizedQueue.dequeue();
        assertEquals("Wrong size", 0, randomizedQueue.size());
    }


    @Test
    public void paralelIterators() {
        randomizedQueue.enqueue(FIRST_ELEMENT);
        final Iterator<String> iterator = randomizedQueue.iterator();
        final Iterator<String> secondIterator = randomizedQueue.iterator();
        final String firstElement = iterator.next();
        assertTrue("There should be more elements", secondIterator.hasNext());
        final String secondElement = secondIterator.next();
    }


    @Test
    public void enqueDequeForResizing() {
        enque(50);
        deque(25);
        enque(85);
        assertEquals("Wrong size", 110, 110);
    }


    private void deque(final int aCount) {
        for (int i = 0; i < aCount; i++) {
            randomizedQueue.dequeue();
        }
    }


    private void enque(final int aCount) {
        for (int i = 0; i < aCount; i++) {
            randomizedQueue.enqueue(FIRST_ELEMENT);
        }
    }
}
