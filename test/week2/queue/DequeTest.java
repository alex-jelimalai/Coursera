package week2.queue;

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
public class DequeTest {

    public static final String FIRST_ELEMENT = "First Element";
    public static final String SECOND_ELEMENT = "Second Element";
    public static final String THIRD_ELEMENT = "Third Element";
    private Deque<String> deque;


    @Before
    public void setUp() {
        deque = new Deque<String>();
    }


    @Test
    public void isEmptyTrue() {
        assertTrue("Deque is not empty", deque.isEmpty());
    }


    @Test
    public void isEmptyFalse() {
        deque.addFirst(FIRST_ELEMENT);
        assertFalse("Deque is empty", deque.isEmpty());
    }


    @Test
    public void addFirstRemoveSameElement() {
        deque.addFirst(FIRST_ELEMENT);
        assertEquals("Not Same element was removed", FIRST_ELEMENT, deque.removeFirst());
    }


    @Test
    public void addFirst2ElementRemoveFirstInReversedOrder() {
        deque.addFirst(FIRST_ELEMENT);
        deque.addFirst(SECOND_ELEMENT);
        assertEquals("No expected order removed", SECOND_ELEMENT, deque.removeFirst());
        assertEquals("No expected order removed", FIRST_ELEMENT, deque.removeFirst());
    }


    @Test(expected = NullPointerException.class)
    public void addFirstThrowNullPointerException() {
        deque.addFirst(null);
    }


    @Test(expected = NullPointerException.class)
    public void addLastThrowNullPointerException() {
        deque.addLast(null);
    }


    @Test(expected = NoSuchElementException.class)
    public void removeFirstThrowExceptionWhenNoElements() {
        deque.removeFirst();
    }


    @Test(expected = NoSuchElementException.class)
    public void removeLastThrowExceptionWhenNoElements() {
        deque.removeLast();
    }


    @Test(expected = UnsupportedOperationException.class)
    public void removeFromIteratorThrowsException() {
        deque.addFirst(FIRST_ELEMENT);
        deque.iterator().remove();
    }


    @Test(expected = NoSuchElementException.class)
    public void nextWhenNoElementsThrowException() {
        deque.iterator().next();
    }


    @Test
    public void addLastRemoveLastSameElement() {
        deque.addLast(FIRST_ELEMENT);
        assertEquals("No same element was removed", FIRST_ELEMENT, deque.removeLast());
    }


    @Test
    public void addLastRemoveLastReversedOrder() {
        deque.addLast(FIRST_ELEMENT);
        deque.addLast(SECOND_ELEMENT);
        assertEquals("No expected order of removing", SECOND_ELEMENT, deque.removeLast());
        assertEquals("No expected order of removing", FIRST_ELEMENT, deque.removeLast());
    }


    @Test
    public void addFirstRemoveLast() {
        deque.addFirst(FIRST_ELEMENT);
        deque.addFirst(SECOND_ELEMENT);
        deque.addFirst(THIRD_ELEMENT);
        assertEquals("No expected order of removing", FIRST_ELEMENT, deque.removeLast());
        assertEquals("No expected order of removing", SECOND_ELEMENT, deque.removeLast());
        assertEquals("No expected order of removing", THIRD_ELEMENT, deque.removeLast());
        assertTrue("The queue is not empty", deque.isEmpty());
    }


    @Test
    public void sizeWhenEmpty() {
        assertEquals("The size is correct", 0, deque.size());
    }


    @Test
    public void sizeWhenTwoElements() {
        deque.addLast(SECOND_ELEMENT);
        deque.addFirst(FIRST_ELEMENT);
        assertEquals("The size is correct", 2, deque.size());
    }


    @Test
    public void sizeAfterSomeManipulation() {
        deque.addLast(SECOND_ELEMENT);
        deque.addFirst(FIRST_ELEMENT);
        deque.addFirst(SECOND_ELEMENT);
        deque.removeFirst();
        deque.removeLast();
        assertEquals("The size is correct", 1, deque.size());
    }


    @Test
    public void iteratorReturnsContainsAllElements() {
        deque.addFirst(FIRST_ELEMENT);
        deque.addLast(SECOND_ELEMENT);
        deque.addLast(THIRD_ELEMENT);
        final String[] strings = { FIRST_ELEMENT, SECOND_ELEMENT, THIRD_ELEMENT };
        final Iterator<String> iterator = deque.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals("Wrong element", strings[i++], iterator.next());
        }
        assertEquals("The No. of elements in iterator is not the same as was added", strings.length, i);
    }


    @Test
    public void isEmptyAfterSomeCombinationOfMethods() {
        deque.addFirst(FIRST_ELEMENT);
        deque.addFirst(SECOND_ELEMENT);
        deque.addLast(THIRD_ELEMENT);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        assertTrue("Queue is not empty", deque.isEmpty());
    }

}
