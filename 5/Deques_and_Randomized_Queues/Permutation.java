import edu.princeton.cs.algs4.StdRandom;

import java.util.Scanner;

public class Permutation {
    public static void main(String[] args) {
        int     k  = Integer.parseInt(args[0]);
        Scanner sc = new Scanner(System.in);

        String line = sc.nextLine();
        String[] myStr = line.split("\\s+");

        StdRandom.shuffle(myStr);
        for(int i = 0; i < k; i++) System.out.println(myStr[i]);

        sc.close();

    }
}
