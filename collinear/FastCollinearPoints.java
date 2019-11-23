/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] pointsCp = points.clone();
        Arrays.sort(pointsCp);
        for (int i = 0; i < pointsCp.length - 1; i++) {
            if (pointsCp[i].compareTo(pointsCp[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        Point[] soPoints = new Point[pointsCp.length - 1];
        for (int p = 0; p < pointsCp.length; p++) {

            // Cloning an array for slopeOrder arrangement excluding point P
            int skipCount = 0;
            for (int i = 0; i < soPoints.length; i++) {
                if (i == p) {
                    skipCount = 1;
                }
                soPoints[i] = pointsCp[i + skipCount];
            }

            MergeX.sort(soPoints, pointsCp[p].slopeOrder());
            int currentIdx = 0;
            int counter = 0;
            for (int q = 0; q < soPoints.length; q++) {
                if (Double.compare(pointsCp[p].slopeTo(soPoints[currentIdx]),
                                   pointsCp[p].slopeTo(soPoints[q])) == 0) {
                    counter++;
                }
                else {
                    if (counter >= 3) {
                        Point[] tempPoints = new Point[counter + 1];
                        for (int i = 0; i < tempPoints.length; i++) {
                            tempPoints[i] = soPoints[currentIdx + i];
                        }
                        MergeX.sort(tempPoints);
                        if (pointsCp[p].compareTo(tempPoints[0]) == 0)
                            segmentList.add(new LineSegment(tempPoints[0],
                                                            tempPoints[tempPoints.length - 1]));
                    }
                    counter = 0;
                    currentIdx = q;
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
