import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;

import java.util.*;


public class WordNet {
    public final SAP                        sap;
    public       Map<Integer, String>       synsets;
    public final Map<String, List<Integer>> nounMap;

    // constructor takes the name of the two input files
    public WordNet(String synsetsInp, String hypernymsInp) {
        if (synsetsInp == null || hypernymsInp == null) throw new IllegalArgumentException();

        // Synsets Constructing
        synsets = new HashMap<>();
        nounMap = new HashMap<>();

        String[] synsetsLine = synsetsInp.split("\n");
        for (int i = 0; i < synsetsLine.length; i++) {
            String[] line = synsetsLine[i].split(",");

//            Integer id = Integer.parseInt(line.get(0));
            List<String> words = Arrays.asList(line[1].split(" "));
            synsets.put(i, line[1]);

            for (String subwords : words) {
                if (nounMap.containsKey(subwords)) {
                    nounMap.get(subwords).add(i);
                } else {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    nounMap.put(subwords, tmp);
                }
            }
        }

        Digraph G = new Digraph(synsetsLine.length);

        // Hypernyms constructing
        String[] hypernymsLine = hypernymsInp.split("\n");
        for (int i = 0; i < hypernymsLine.length; i++) {
            String[] line = hypernymsLine[i].split(",");

            for (int j = 1; j < line.length; j++)
                G.addEdge(i, Integer.parseInt(line[j]));
        }

        // Check if G is Cycle
        if (!(new Topological(G)).hasOrder()) throw new IllegalArgumentException();
        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return nounMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Not a Wordnet noun!");

        List<Integer> setA = nounMap.get(nounA);
        List<Integer> setB = nounMap.get(nounB);

        return sap.length(setA, setB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        List<Integer> setA     = nounMap.get(nounA);
        List<Integer> setB     = nounMap.get(nounB);
        int           ancestor = sap.ancestor(setA, setB);

        return synsets.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        String  synsets   = Files.readString(Paths.get("synsets.txt"));
//        String  hypernyms = Files.readString(Paths.get("hypernyms.txt"));
//        WordNet mynet     = new WordNet(synsets, hypernyms);
//        System.out.println(mynet.nounSets);
//        System.out.println(mynet.distance("10", "8"));
//        System.out.println("SAP: ");
//        for (Integer key : mynet.synsets.keySet()) {
//            System.out.println("Key: " +key + " ");
//            System.out.println(mynet.synsets.get(key));
//        }
//
//        System.out.println(mynet.nounSets.get("11"));

//        System.out.println(mynet.distance("11", "1"));

    }
}