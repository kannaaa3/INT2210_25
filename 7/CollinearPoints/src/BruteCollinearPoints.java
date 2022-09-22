import javax.swing.text.Segment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {
    private int           ansNum;
    private LineSegment[] arSeq;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        int               n       = points.length;
        List<LineSegment> listSeq = new ArrayList<>();

        ansNum = 0;

        Arrays.sort(points);

        for (int i = 0; i < n; i++) {
            if (points[i] == null || (i < n - 1 && points[i].compareTo(points[i + 1]) == 0))
                throw new IllegalArgumentException();
        }

        for (int i = 0; i < n; i++) {
            Comparator<Point> cmpSlope = points[i].slopeOrder();

            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (cmpSlope.compare(points[j], points[k]) == 0 &&
                                cmpSlope.compare(points[k], points[l]) == 0) {
                            listSeq.add(new LineSegment(points[i], points[l]));
                            ansNum++;
//                            System.out.println("Add: (" + points[i].x + "," + points[i].y + ") and (" + points[l].x + "," + points[l].y  + ")");
                        }
                    }

                }

            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return ansNum;
    }

    public LineSegment[] segments()                // the line segments
    {
        return arSeq;
    }

    public static void main(String[] args) {
        Point[]              points = {new Point(0, 0), new Point(3, 3), new Point(1, 1), new Point(2, 2), new Point(3, 2)};
        BruteCollinearPoints dumk   = new BruteCollinearPoints(points);
        System.out.println(dumk.ansNum);
    }
}
