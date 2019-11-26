/* *****************************************************************************
 *  Name: Kennedy
 *  Date: 2019/11/20
 *  Description: Brute force collinear points
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        // check for null elements - edge cases
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

        }

        // Clone the array to prevent altering the input
        Point[] pointsCp = points.clone();
        MergeX.sort(pointsCp); // sort the points in natural order

        // check for duplicate elements - edge cases
        for (int i = 0; i < pointsCp.length - 1; i++) {
            if (pointsCp[i].compareTo(pointsCp[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        // temporary array list to store the line segments
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        for (int p = 0; p < pointsCp.length - 3; p++) {
            for (int q = p + 1; q < pointsCp.length - 2; q++) {
                for (int r = q + 1; r < pointsCp.length - 1; r++) {
                    if (Double.compare(pointsCp[p].slopeTo(pointsCp[q]),
                                       pointsCp[p].slopeTo(pointsCp[r]))
                            != 0) {
                        continue;
                    }
                    for (int s = r + 1; s < pointsCp.length; s++) {
                        if (Double
                                .compare(pointsCp[p].slopeTo(pointsCp[q]),
                                         pointsCp[p].slopeTo(pointsCp[r]))
                                == 0
                                && Double.compare(pointsCp[p].slopeTo(pointsCp[q]),
                                                  pointsCp[p].slopeTo(pointsCp[s])) == 0) {
                            segmentList.add(new LineSegment(pointsCp[p], pointsCp[s]));
                        }
                    }
                }
            }
        }
        // convert the line segments array list to array
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
