import edu.princeton.cs.algs4.Digraph;

import java.io.IOException;
import java.util.ArrayList;

public class SAP {
    public final Digraph Graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.Graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!validVertex(v) || !validVertex(w)) throw new IllegalArgumentException();
        return new SAPTwoVertices(
                new ArrayList<>() {{
                    add(v);
                }}, new ArrayList<>() {{
            add(w);
        }}, Graph
        ).getDistance();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!validVertex(v) || !validVertex(w)) throw new IllegalArgumentException();
        return new SAPTwoVertices(new ArrayList<>() {{
            add(v);
        }}, new ArrayList<>() {{
            add(w);
        }}, Graph).getAncestor();
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validSet(v) || !validSet(w)) throw new IllegalArgumentException();
        return new SAPTwoVertices(v, w, Graph).getDistance();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validSet(v) || !validSet(w)) throw new IllegalArgumentException();
        return new SAPTwoVertices(v, w, Graph).getAncestor();
    }

    private boolean validVertex(int u) {
        return (u < 0 || u >= Graph.V()) ? false : true;
    }

    private boolean validSet(Iterable<Integer> v) {
        if (v == null) return false;
        for (Integer x : v) {
            if (x == null) return false;
            if (!validVertex(x)) return false;
        }
        return true;
    }

    // do unit testing of this class
    public static void main(String[] args) throws IOException {
//        String  synsets   = Files.readString(Paths.get("synsets.txt"));
//        String  hypernyms = Files.readString(Paths.get("hypernyms.txt"));
//        WordNet mynet     = new WordNet(synsets, hypernyms);
//        System.out.println(mynet.nounMap);
//        System.out.println(mynet.distance("10", "8"));
//        System.out.println("SAP: ");
    }
}
