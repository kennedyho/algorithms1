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

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) { // corner case
            throw new IllegalArgumentException();
        }

        // check if any point is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        // clone the points array so the original input is not altered
        Point[] pointsCp = points.clone();
        MergeX.sort(pointsCp);
        // check for duplicate points and throw exception
        for (int i = 0; i < pointsCp.length - 1; i++) {
            if (pointsCp[i].compareTo(pointsCp[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        // temporary array list for the line segments
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        // array for storing points ordered by slopeOrder
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

            MergeX.sort(soPoints, pointsCp[p].slopeOrder()); // sort by slopeOrder
            int q = 0;
            while (q < soPoints.length - 1) {
                ArrayList<Point> tempPointList = new ArrayList<>();
                tempPointList.add(soPoints[q]); // insert element used to compare
                for (int r = q + 1; r < soPoints.length; r++) {
                    if (Double.compare(pointsCp[p].slopeTo(soPoints[q]),
                                       pointsCp[p].slopeTo(soPoints[r])) == 0) {
                        tempPointList.add(soPoints[r]);
                    }
                    else {
                        break;
                    }
                }

                if (tempPointList.size() >= 3) {
                    tempPointList.add(pointsCp[p]);
                    Point[] tempPoints = tempPointList.toArray(new Point[tempPointList.size()]);
                    MergeX.sort(tempPoints); // sort by natural order
                    // Test print code
                    /*
                    StdOut.println("Current P is " + pointsCp[p]);
                    for (int i = 0; i < tempPoints.length; i++) {
                        StdOut.println(tempPoints[i]);
                    }
                    StdOut.println();
                    */
                    // get the line segment from the min to the max
                    if (pointsCp[p].compareTo(tempPoints[0]) == 0)
                        segmentList.add(new LineSegment(tempPoints[0],
                                                        tempPoints[tempPoints.length - 1]));
                    q += tempPointList.size() - 1;
                }
                else {
                    q += tempPointList.size();
                }
            }
        }
        // convert the array list to array
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
