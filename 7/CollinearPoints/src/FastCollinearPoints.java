import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

    private LineSegment[] allSeqsArr;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] inputPoints) {
        if (inputPoints == null) throw new IllegalArgumentException();
        int     n      = inputPoints.length;
        Point[] points = new Point[n];

        List<LineSegment> allSegsList = new ArrayList<>();
        Double[][]        slope       = new Double[n][n];

        // Initial check
        for (int i = 0; i < n; i++) {
            if (inputPoints[i] == null) throw new IllegalArgumentException();
            points[i] = inputPoints[i];
        }

        Arrays.sort(points);
        for (int i = 0; i < n; i++) {
            if (i < n - 1 && points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }


        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                slope[i][j] = points[i].slopeTo(points[j]);

        for (int i = 0; i < n; i++) {
            // all slope to the current point
            List<Pair<Double, Integer>> allSlopeTo = new ArrayList<>();

            for (int j = i + 1; j < n; j++)
                if (slope[i][j] != Double.NEGATIVE_INFINITY)
                    allSlopeTo.add(new Pair<>(slope[i][j], j));

            Collections.sort(allSlopeTo);
            allSlopeTo.add(new Pair<>(Double.NEGATIVE_INFINITY, i)); // extra redundent slope for easier implementation

            int l = 0;
            while (l < allSlopeTo.size() - 1) {
                Double thisSlope = allSlopeTo.get(l).first;
                int    r         = l;
                while (allSlopeTo.get(r + 1).first.equals(thisSlope)) r++;
                if (r - l >= 2) { // four points or more
                    // Disable all subsegment
                    for (int fi = l; fi < r; fi++) {
                        for (int la = fi + 1; la <= r; la++) {
                            int pla = allSlopeTo.get(la).second;
                            int pfi = allSlopeTo.get(fi).second;
                            slope[pfi][pla] = Double.NEGATIVE_INFINITY;
                        }
                    }
                    // add the segment
                    allSegsList.add(new LineSegment(points[i], // Notice i here. It's not l
                                                    points[allSlopeTo.get(r).second]));
                }
                l = r + 1;
            }

            allSeqsArr = allSegsList.toArray(new LineSegment[0]);
        }
    }


    public int numberOfSegments() {
        return allSeqsArr.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        return Arrays.copyOf(allSeqsArr, allSeqsArr.length);
    } // the line segments

    private static class Pair<T extends Comparable<T>, U extends Comparable<U>> implements Comparable<Pair<T, U>> {
        private final T first;
        private final U second;

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
}
