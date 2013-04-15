package week3.colliniar;

import java.util.Comparator;

import edu.princeton.cs.introcs.StdDraw;

public class Point implements Comparable<Point> {

    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();

    private int x;
    private int y;


    // construct the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;

    }


    // draw this point
    public void draw() {
         StdDraw.point(x, y);
    }


    // draw the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }


    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    // is this point lexicographically smaller than that point?
    public int compareTo(Point that) {
        if (y != that.y) {
            return y - that.y;
        }
        return x - that.x;
    }


    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (isSameCoordinate(y, that.y)) {
            if (isSameCoordinate(x, that.x)) {
                return Double.NEGATIVE_INFINITY;
            }
            return 0d;
        }
        if (isSameCoordinate(x, that.x)) {
            return Double.POSITIVE_INFINITY;
        }
        return (double)(that.y - y) / (that.x - x);
    }


    private boolean isSameCoordinate(final int aFirstCoordinate, final int aSecondCoordinate) {
        return aFirstCoordinate == aSecondCoordinate;
    }


    private class SlopeComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            return Double.valueOf(slopeTo(o1)).compareTo(slopeTo(o2));
        }

    }
}
