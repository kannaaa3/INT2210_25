import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final boolean solvable;
    private Node         goalNode;
    private Stack<Board> pathToGoal;

    private static class Node {
        private Board board;
        private int   priority;
        private int   moves;

        private Node traceNode;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Node> theSol  = new MinPQ<>(cmpPriority());
        MinPQ<Node> twinSol = new MinPQ<>(cmpPriority());

        Node initNode = new Node();
        initNode.board = initial;
        initNode.moves = 0;
        initNode.priority = initNode.board.manhattan();
        theSol.insert(initNode);

        Node twinNode = new Node();
        twinNode.board = initial.twin();
        twinNode.moves = 0;
        twinNode.priority = twinNode.board.manhattan();
        twinSol.insert(twinNode);


        while (true) {
            if (popQueue(theSol)) {
                solvable = true;
                break;
            }
            if (popQueue(twinSol)) {
                solvable = false;
                break;
            }
        }

        if (solvable) {
            Node tmp = goalNode;
            pathToGoal = new Stack<>();
            do {
                pathToGoal.push(tmp.board);
                tmp = tmp.traceNode;
            } while (tmp != null);
        }
    }

    private boolean popQueue(MinPQ<Node> pq) {
        Node head = pq.delMin();
        if (head.board.isGoal()) {
            goalNode = head;
            return true;
        }
        for (Board neighbor : head.board.neighbors()) {
            if (head.traceNode != null && neighbor.equals(head.traceNode.board))
                continue;

            Node nxt = new Node();
            nxt.board = neighbor;
            nxt.moves = head.moves + 1;
            nxt.priority = neighbor.manhattan() + nxt.moves;
            nxt.traceNode = head;

            pq.insert(nxt);
        }
        return false;
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? goalNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solvable ? pathToGoal : null;
    }

    private Comparator<Node> cmpPriority() {
        return Comparator.comparingInt(b -> b.priority);
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In      in    = new In(args[0]);
        int     n     = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}