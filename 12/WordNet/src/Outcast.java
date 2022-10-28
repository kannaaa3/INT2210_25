public class Outcast {
    private WordNet wordnet;

    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int ansId = 0;
        int maxDis = 0;

        for (int i = 0; i < nouns.length; i++) {
            int tmp = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j)
                    tmp += wordnet.distance(nouns[i], nouns[j]);
            }
            if (tmp > maxDis) {
                maxDis = tmp;
                ansId = i;
            }
        }

        return nouns[ansId];
    }

    public static void main(String[] args)  // see test client below
    {

    }
}