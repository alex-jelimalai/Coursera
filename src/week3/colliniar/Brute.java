package week3.colliniar;

import java.util.Arrays;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class Brute {
	private static final String ARROW = " -> ";

	public static void main(String[] arg) {
		Point[] points = getPoints(arg[0]);
		initDraw();
		Arrays.sort(points);
        drawPoints(points);
		displayColliniarPoints(points);
		StdDraw.show(0);

	}

    private static void drawPoints(final Point[] aPoints) {
        for (final Point point : aPoints) {
            point.draw();
        }
    }

	private static void displayColliniarPoints(Point[] points) {
		for (int index1 = 0; index1 < points.length - 3; index1++) {
			for (int index2 = index1+1; index2 < points.length - 2; index2++) {
				for (int index3 = index2+1; index3 < points.length - 1; index3++) {
					for (int index4 = index3+1; index4 < points.length; index4++) {
						Point[] pointsToCheck = new Point[4];
						pointsToCheck[0] = points[index1];
						pointsToCheck[1] = points[index2];
						pointsToCheck[2] = points[index3];
						pointsToCheck[3] = points[index4];
						if (isPointsColliniar(pointsToCheck)) {
							stdOut(pointsToCheck);
                            draw(pointsToCheck);
						}
					}
				}
			}
		}
	}

	private static void stdOut(Point[] points) {
		StringBuilder builder = new StringBuilder();
		for (final Point point : points) {
			builder.append(point);
			builder.append(ARROW);
		}
		builder.replace(builder.length() - ARROW.length(), builder.length(), "");
		StdOut.println(builder);
	}

	private static boolean isPointsColliniar(Point[] points) {
		double slope = 0.0;
		boolean firstSlopeFound = false;
		boolean isColliniar = true;
		Point previousPoint = null;
		for (final Point point : points) {
			if (previousPoint == null) {
				previousPoint = point;
				continue;
			} else if (!firstSlopeFound) {
				slope = previousPoint.slopeTo(point);
				previousPoint = point;
				firstSlopeFound = true;
				continue;
			}
			final double actualSlope = previousPoint.slopeTo(point);
			if (slope != actualSlope) {
				isColliniar = false;
				break;
			}
			slope = actualSlope;
			previousPoint = point;
		}
		return isColliniar;
	}

	private static void initDraw() {
		StdDraw.setXscale(0, 50000);
		StdDraw.setYscale(0, 50000);
		StdDraw.show(0);
	}

	private static void draw(Point[] points) {
		points[0].drawTo(points[points.length - 1]);
	}

	private static Point[] getPoints(String file) {
		In in = new In(file);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point point = new Point(x, y);
			points[i]=point;
		}
		return points;
	}

}
