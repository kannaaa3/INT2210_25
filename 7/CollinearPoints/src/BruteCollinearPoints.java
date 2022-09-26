import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {
    private int           numSegs;
    private LineSegment[] allSegsArr;

    public BruteCollinearPoints(Point[] inputPoints)    // finds all line segments containing 4 points
    {
        if (inputPoints == null) throw new IllegalArgumentException();

        int     n      = inputPoints.length;
        Point[] points = new Point[n];

        numSegs = 0;

        for (int i = 0; i < n; i++) {
            if (inputPoints[i] == null) throw new IllegalArgumentException();
            points[i] = inputPoints[i];
        }
        Arrays.sort(points);

        for (int i = 0; i < n; i++) {
            if (i < n - 1 && points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        List<LineSegment> listSeq = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Comparator<Point> cmpSlope = points[i].slopeOrder();

            for (int j = i + 1; j < n; j++)
                for (int k = j + 1; k < n; k++)
                    for (int l = k + 1; l < n; l++)
                        if (cmpSlope.compare(points[j], points[k]) == 0 &&
                                cmpSlope.compare(points[k], points[l]) == 0) {
                            listSeq.add(new LineSegment(points[i], points[l]));
                            numSegs++;
//                            System.out.println("Add: (" + points[i].x + "," + points[i].y + ") and (" + points[l].x + "," + points[l].y  + ")");
                        }
        }

        allSegsArr = listSeq.toArray(listSeq.toArray(new LineSegment[0]));
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegs;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(allSegsArr, allSegsArr.length);
    }
}
