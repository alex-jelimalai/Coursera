package week1.percolation;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import week1.percolation.Percolation;

public class PercolationTest {

    private Percolation percolation;


    @Before
    public void setUp() {
        percolation = new Percolation(4);
    }


    @Test
    public void open_oneSite() {
        percolation.open(1, 1);
        Assert.assertTrue("The site is not open", percolation.isOpen(1, 1));
    }


    @Test
    public void isFullReturnTrue() {
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(2, 3);
        percolation.open(1, 3);
        Assert.assertTrue("The site is not full open", percolation.isFull(3, 4));

    }


    @Test
    public void isFullReturnFalse() {
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(2, 3);
        percolation.open(1, 3);
        Assert.assertFalse("The is full method does not work", percolation.isFull(4, 1));

    }


    @Test
    public void isPercolate() {
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(3, 4);
        percolation.open(2, 4);
        percolation.open(2, 3);
        percolation.open(1, 3);
        percolation.open(4, 3);
        Assert.assertTrue("The system does not percolate", percolation.percolates());

    }

}
