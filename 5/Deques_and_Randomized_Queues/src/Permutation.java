import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) { 
        int k = Integer.parseInt(args[0]);
        int cnt = 0;

        RandomizedQueue<String> randQ = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String inp = StdIn.readString();
            randQ.enqueue(inp);
        }

        for (String s : randQ) {
            if(cnt == k) break;
            System.out.println(s);
            cnt ++;
        }
    }
}
