package week3.colliniar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Fast {

    private static final String ARROW = " -> ";
    private static final Map<Double, List<Point>> usedSlopes = new HashMap<Double,List<Point>>();
    private static final List<Colliniar> colliniarPoints = new ArrayList<Colliniar>();
    private static final List<Point> slopedPoints = new ArrayList<Point>();


    public static void main(String[] arg) {
        final List<Point> points = getPoints(arg[0]);
        initDraw();
        drawPoints(points);
        displayColliniarPoints(getColliniarPoints(points));
        StdDraw.show(0);
    }


    private static void drawPoints(final List<Point> aPoints) {
        for (final Point point : aPoints) {
            point.draw();
        }
    }


    protected static List<Colliniar> getColliniarPoints(List<Point> aPoints) {
        validateNotNull(aPoints);
        clearCollections();
        slopedPoints.addAll(aPoints);
        for (final Point point : aPoints) {
            collectColliniarsFor(point);
        }

        return colliniarPoints;
    }


    private static void collectColliniarsFor(final Point point) {
        double baseSlope = Double.NEGATIVE_INFINITY;
        Collections.sort(slopedPoints, point.SLOPE_ORDER);
        List<Point> colliniarList = new ArrayList<Point>();
        for (final Point slopedPoint : slopedPoints) {
            final double actualSlope = point.slopeTo(slopedPoint);
            if (point.equals(slopedPoint) || containedByUsedSlopes(slopedPoint, actualSlope)) {
                continue;
            }
            if (actualSlope != baseSlope) {
                addColliniarPoints(baseSlope, point, colliniarList);
            }
            colliniarList.add(slopedPoint);
            baseSlope = actualSlope;
        }
        addColliniarPoints(baseSlope, point, colliniarList);
    }


    private static void addPointToUsedSlopes(final List<Point> aPoints, final double aBaseSlope) {
        if(isSlopeUnused(aBaseSlope)){
            usedSlopes.put(aBaseSlope, new ArrayList<Point>());
        }
        usedSlopes.get(aBaseSlope).addAll(aPoints);
    }


    private static boolean containedByUsedSlopes(final Point aSlopedPoint, final double aActualSlope) {
        return isSlopeUsed(aActualSlope) && usedSlopes.get(aActualSlope).contains(aSlopedPoint);
    }


    private static void addColliniarPoints(double aBaseSlope, Point point, List<Point> aColliniarList) {
        addPointToUsedSlopes(aColliniarList, aBaseSlope);
        addNewColliniarPoint(colliniarPoints, point, aColliniarList);
        aColliniarList.clear();
    }


    private static boolean isSlopeUsed(final double aActualSlope) {
        return usedSlopes.containsKey(aActualSlope);
    }


    private static boolean isSlopeUnused(final double aBaseSlope) {
        return !isSlopeUsed(aBaseSlope);
    }


    private static void clearCollections() {
        slopedPoints.clear();
        colliniarPoints.clear();
        usedSlopes.clear();
    }


    private static void addNewColliniarPoint(final List<Colliniar> aCollinear, final Point point, final List<Point> aColliniarList) {
        if (aColliniarList.size() > 2) {
            aColliniarList.add(point);
            Collections.sort(aColliniarList);
            aCollinear.add(new Colliniar(new ArrayList<Point>(aColliniarList)));
        }
    }


    private static void validateNotNull(final List<Point> aPoints) {
        if (aPoints == null) {
            throw new NullPointerException();
        }
    }


    private static void displayColliniarPoints(List<Colliniar> points) {
        for (final Colliniar pointsColliniar : points) {
            stdOut(pointsColliniar.getPoints());
        }
    }


    private static void stdOut(List<Point> points) {
        StringBuilder builder = new StringBuilder();
        for (final Point point : points) {
            builder.append(point);
            builder.append(ARROW);
        }
        builder.replace(builder.length() - ARROW.length(), builder.length(), "");
        StdOut.println(builder);
        draw(points);
    }


    private static void initDraw() {
        StdDraw.setXscale(0, 50000);
        StdDraw.setYscale(0, 50000);
        StdDraw.show(0);
    }


    private static void draw(final List<Point> points) {
        if (points.isEmpty()) {
            return;
        }
        points.get(0).drawTo(points.get(points.size() - 1));
    }


    private static List<Point> getPoints(String file) {
        In in = new In(file);
        int N = in.readInt();
        final List<Point> points = new ArrayList<Point>();
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points.add(new Point(x, y));
        }
        return points;
    }


    protected static class Colliniar {

        private List<Point> points;


        public List<Point> getPoints() {
            return points;
        }


        protected Colliniar(final List<Point> aPoints) {
            points = aPoints;
        }


        @Override
        public boolean equals(final Object aOther) {
            if (this == aOther) {
                return true;
            }
            if (aOther == null || getClass() != aOther.getClass()) {
                return false;
            }

            final Colliniar that = (Colliniar)aOther;

            return points.equals(that.points);

        }


        @Override
        public int hashCode() {
            return points.hashCode();
        }
    }


}
