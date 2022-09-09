import pkg.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter the number of objects: ");

        int n    = myScanner.nextInt();
        var myUF = new UnionFind(n);

        System.out.println("Let's connect 'em all together!");

        while (myScanner.hasNextLine()) {
            int queryType;
            int u, v;

            queryType = myScanner.nextInt();
            u = myScanner.nextInt();
            v = myScanner.nextInt();

            if (queryType == 0)
                myUF.ifConnected(u, v);
            else myUF.union(u, v);
        }


    }

}
