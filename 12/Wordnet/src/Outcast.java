public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int ansId  = 0;
        int maxDis = 0;

        for (int i = 0; i < nouns.length; i++) {
            int sum = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j)
                    sum += wordnet.distance(nouns[i], nouns[j]);
            }
            if (sum > maxDis) {
                maxDis = sum;
                ansId = i;
            }
        }
        return nouns[ansId];
    }
}