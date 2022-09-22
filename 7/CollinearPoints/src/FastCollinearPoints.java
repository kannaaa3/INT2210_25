import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private int           numSegs;
    private LineSegment[] allSeqsArr;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        int n = points.length;

        numSegs = 0;
        List<LineSegment> allSegsList = new ArrayList<>();
        Double[][]        slope       = new Double[n][n];

        // Initial check
        Arrays.sort(points);
        for (int i = 0; i < n; i++) {
            if (points[i] == null || (i < n - 1 && points[i].compareTo(points[i + 1]) == 0))
                throw new IllegalArgumentException();
            System.out.println(points[i]);
        }


        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                slope[i][j] = points[i].slopeTo(points[j]);

        for (int i = 0; i < n; i++) {
            // all slope to the current point
            List<Pair<Double, Integer>> allSlopeTo = new ArrayList<>();

            for (int j = i + 1; j < n; j++)
                if (slope[i][j] != Double.POSITIVE_INFINITY)
                    allSlopeTo.add(new Pair(slope[i][j], j));

            Collections.sort(allSlopeTo);
            allSlopeTo.add(new Pair(Double.POSITIVE_INFINITY, i)); // add redundent slope

            //DB
//            if(i == 0)
//            for (Pair<Double,Integer> xs : allSlopeTo) {
//                System.out.print(xs.first + ": " + xs.second);
//                System.out.println();
//            }

            int l = 0;
            while (l < allSlopeTo.size() - 1) {
                Double thisSlope = allSlopeTo.get(l).first;
                int    r         = l;
                while (allSlopeTo.get(r + 1).first.equals(thisSlope)) r++;
//                if(i == 0) System.out.println(l + " -> " + r);
                if (r - l >= 2) {
                    // Disable all subsegment
                    for (int la = r; la > l; la--)
                        for (int fi = l; fi < la; fi++) {
                            int pla = allSlopeTo.get(la).second;
                            int pfi = allSlopeTo.get(fi).second;
                            slope[pfi][pla] = Double.POSITIVE_INFINITY;
                        }
                    // add the segment
                    allSegsList.add(new LineSegment(points[allSlopeTo.get(i).second], // Notice i here. It's not l
                                                    points[allSlopeTo.get(r).second]));
                }
                l = r + 1;
            }

            numSegs = allSegsList.size();
            allSeqsArr = new LineSegment[numSegs];
            for (int j = 0; j < numSegs; j++) {
                allSeqsArr[j] = allSegsList.get(j);
            }

        }
    }


    public int numberOfSegments() {
        return numSegs;
    }       // the number of line segments

    public LineSegment[] segments() {
        return allSeqsArr;
    } // the line segments

    public static class Pair<T extends Comparable<T>, U extends Comparable<U>> implements Comparable<Pair<T, U>> {
        public final T first;
        public final U second;

        public Pair(T fi, U se) {
            this.first = fi;
            this.second = se;
        }

        @Override
        public int compareTo(Pair<T, U> thatPair) {
            if (!first.equals(thatPair.first)) return first.compareTo(thatPair.first);
            return second.compareTo(thatPair.second);
        }
    }

    public static void main(String[] args) {
//        Pair<Double, Integer>[] tPair = new Pair[3];
//        tPair[0] = new Pair(0.3, 4);
//        tPair[1] = new Pair(0.1, 3);
//        tPair[2] = new Pair(0.3, 1);
//
//        Arrays.sort(tPair);
//
//        for (int i = 0; i < tPair.length; i++) {
//            System.out.println(tPair[i].first + ", " + tPair[i].second);
//        }


//        Point[] points = {new Point(0, 0), new Point(4, 8), new Point(4, 4), new Point(6, 6),
//                          new Point(1, 2), new Point(2, 4), new Point(3, 6), new Point(2, 2)};

//        FastCollinearPoints fcp = new FastCollinearPoints(points);
//        System.out.println("numSegs " + fcp.numSegs);
//        for (int i = 0; i < fcp.numSegs; i++) {
//            System.out.println(fcp.allSeqsArr[i].p + "->"
//                                       + fcp.allSeqsArr[i].q);
//        }

    }
}
