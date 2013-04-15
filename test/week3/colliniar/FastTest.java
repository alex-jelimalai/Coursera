package week3.colliniar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import week3.colliniar.Fast.Colliniar;

/**
 * @author: ajelimalai
 * @created: 28/03/13
 */
public class FastTest {

    @Test(expected = NullPointerException.class)
    public void getColliniarPoints_nullThrowException() {
        Fast.getColliniarPoints(null);
    }


    @Test
    public void getColliniarPoints_LessThanThreeElementsReturnEmpty() {
        final ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 3));
        assertTrue("The result contains", Fast.getColliniarPoints(points).isEmpty());
    }


    @Test
    public void getColliniarPoints_FourColliniarPointsReturnsListWithOneColliniar() {
        final ArrayList<Point> points = new ArrayList<Point>();
        addFourDiagonalPoints(points);
        final Colliniar expectedResult = new Colliniar(points);
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        assertEquals("The result contains more or less than one result", 1, colliniarPoints.size());
        assertTrue("Not expected result", colliniarPoints.contains(expectedResult));
    }


    private void addFourDiagonalPoints(final ArrayList<Point> aPoints) {
        aPoints.add(new Point(1, 1));
        aPoints.add(new Point(2, 2));
        aPoints.add(new Point(3, 3));
        aPoints.add(new Point(4, 4));
    }


    @Test
    public void FourVerticalPoints() {
        final ArrayList<Point> points = new ArrayList<Point>();
        addFourVerticalPoints(points);
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        Collections.sort(points);
        final Colliniar expectedResult = new Colliniar(points);
        assertEquals("The result contains more or less than one result", 1, colliniarPoints.size());
        assertTrue("Not expected result", colliniarPoints.contains(expectedResult));

    }


    private void addFourVerticalPoints(final ArrayList<Point> aPoints) {
        aPoints.add(new Point(5, 2));
        aPoints.add(new Point(5, 53));
        aPoints.add(new Point(5, 8));
        aPoints.add(new Point(5, 25));
    }


    @Test
    public void sixPointHorizontal() {
        final ArrayList<Point> points = new ArrayList<Point>();
        addSixHorizontalPoints(points);
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        Collections.sort(points);
        final Colliniar expectedResult = new Colliniar(points);
        assertEquals("The result contains more or less than one result", 1, colliniarPoints.size());
        assertTrue("Not expected result", colliniarPoints.contains(expectedResult));
    }


    private void addSixHorizontalPoints(final ArrayList<Point> aPoints) {
        aPoints.add(new Point(5, 5));
        aPoints.add(new Point(10, 5));
        aPoints.add(new Point(15, 5));
        aPoints.add(new Point(99, 5));
        aPoints.add(new Point(4, 5));
        aPoints.add(new Point(1, 5));
    }


    @Test
    public void twoDifferentLinesHorizontal() {
        final ArrayList<Point> line1 = new ArrayList<Point>();
        final ArrayList<Point> line2 = new ArrayList<Point>();
        final Point commonPoint = new Point(2, 3);
        addLine1Points(line1, commonPoint);
        addLine2Points(line2);
        final List<Point> points = new ArrayList<Point>();
        points.addAll(line1);
        points.addAll(line2);
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        line2.add(commonPoint);
        sortForExpectedResult(line1, line2);
        assertResults(line1, line2, colliniarPoints);
    }


    private void assertResults(final List<Point> aLine1, final List<Point> aLine2, final List<Colliniar> aColliniarPoints) {
        assertEquals("The result contains more or less than two results", 2, aColliniarPoints.size());
        assertTrue("Line 1 not found", aColliniarPoints.contains(new Colliniar(aLine1)));
        assertTrue("Line 2 not found", aColliniarPoints.contains(new Colliniar(aLine2)));
    }


    private void sortForExpectedResult(final List<Point> aLine1, final List<Point> aLine2) {
        Collections.sort(aLine1);
        Collections.sort(aLine2);
    }


    private void addLine2Points(final ArrayList<Point> aLine2) {
        aLine2.add(new Point(4, 4));
        aLine2.add(new Point(6, 5));
        aLine2.add(new Point(8, 6));
        aLine2.add(new Point(10, 7));
    }


    private void addLine1Points(final ArrayList<Point> aLine1, final Point aCommonPoint) {
        aLine1.add(new Point(6, 7));
        aLine1.add(new Point(4, 5));
        aLine1.add(aCommonPoint);
        aLine1.add(new Point(3, 4));
        aLine1.add(new Point(5, 6));
    }


    @Test
    public void twoParallelLines() {
        final List<Point> line1 = new ArrayList<Point>() {

            {
                add(new Point(2, 0));
                add(new Point(3, 1));
                add(new Point(4, 2));
                add(new Point(5, 3));
            }
        };
        final List<Point> line2 = new ArrayList<Point>() {

            {
                add(new Point(1, 0));
                add(new Point(2, 1));
                add(new Point(3, 2));
                add(new Point(4, 3));
            }
        };
        final List<Point> points = new ArrayList<Point>() {

            {
                addAll(line1);
                addAll(line2);
            }
        };
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        sortForExpectedResult(line1,line2);
        assertResults(line1, line2, colliniarPoints);
    }

    @Test
    public void twoParallelHorizontalLines() {
        final List<Point> line1 = new ArrayList<Point>() {

            {
                add(new Point(2, 5));
                add(new Point(3, 5));
                add(new Point(4, 5));
                add(new Point(5, 5));
            }
        };
        final List<Point> line2 = new ArrayList<Point>() {

            {
                add(new Point(1, 9));
                add(new Point(2, 9));
                add(new Point(3, 9));
                add(new Point(4, 9));
            }
        };
        final List<Point> points = new ArrayList<Point>() {

            {
                addAll(line1);
                addAll(line2);
            }
        };
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        sortForExpectedResult(line1,line2);
        assertResults(line1, line2, colliniarPoints);
    }

    @Test
    public void twoParallelVerticalLines() {
        final List<Point> line1 = new ArrayList<Point>() {

            {
                add(new Point(5, 50));
                add(new Point(5, 55));
                add(new Point(5, 66));
                add(new Point(5, 53));
            }
        };
        final List<Point> line2 = new ArrayList<Point>() {

            {
                add(new Point(60, 9));
                add(new Point(60, 20));
                add(new Point(60, 90));
                add(new Point(60, 59));
            }
        };
        final List<Point> points = new ArrayList<Point>() {

            {
                addAll(line1);
                addAll(line2);
            }
        };
        final List<Colliniar> colliniarPoints = Fast.getColliniarPoints(points);
        sortForExpectedResult(line1,line2);
        assertResults(line1, line2, colliniarPoints);
    }

}
