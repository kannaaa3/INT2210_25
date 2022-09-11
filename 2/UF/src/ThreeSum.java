import edu.princeton.cs.algs4.*;

import java.util.Arrays;

public class ThreeSum {

  public static void main(String[] args) {
    In in = new In("4Kinds.txt");
    int[] a = in.readAllInts();

    int n = 0;

    for (int i = 0; i < a.length; i++) {
      for (int j = i + 1; j < a.length; j++) {
        for (int k = j + 1; k < a.length; k++) {
          if (a[i] + a[j] + a[k] == 0)
            System.out.println(a[i] + " " + a[j] + " " + a[k]);
        }
      }
    }
  }
}
