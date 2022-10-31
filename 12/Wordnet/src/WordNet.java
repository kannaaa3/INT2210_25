import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private final Map<Integer, String>       synsetMap;
    private final Map<String, List<Integer>> nounMap;

    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        synsetMap = new HashMap<>();
        nounMap = new HashMap<>();

        String[] synsetsLine = new In(synsets).readAllLines();
        Digraph  G           = new Digraph(synsetsLine.length);

        // Do this first
        In hypernymsIn = new In(hypernyms);
        while (hypernymsIn.hasNextLine()) {
            String[] words = hypernymsIn.readLine().split(",");
            for (int i = 1; i < words.length; i++)
                G.addEdge(Integer.parseInt(words[0]), Integer.parseInt(words[i]));
        }

        // Check whether G is cyclic or not
        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle() || notRootedDAG(G)) throw new IllegalArgumentException();

        for (String s : synsetsLine) {
            String[] fields = s.split(",");
            int      id     = Integer.parseInt(fields[0]);
            synsetMap.put(id, fields[1]);

            String[] wordList = fields[1].split(" ");
            for (String word : wordList) {
                List<Integer> newList = new ArrayList<>();
                if (nounMap.containsKey(word))
                    newList = nounMap.get(word);
                newList.add(id);
                nounMap.put(word, newList);
            }
        }

        sap = new SAP(G);
    }

    private boolean notRootedDAG(Digraph g) {
        int root = 0;
        for (int i = 0; i < g.V(); i++) {
            int cnt = 0;
            for (int x : g.adj(i)) cnt++;
            if (cnt == 0) root++;
        }
        if (root > 1) return true;
        return false;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {return nounMap.keySet();}

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Word can't be null!");
        return nounMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        return sap.length(nounMap.get(nounA), nounMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        int ancestor = sap.ancestor(nounMap.get(nounA), nounMap.get(nounB));
        return synsetMap.get(ancestor);
    }
}
