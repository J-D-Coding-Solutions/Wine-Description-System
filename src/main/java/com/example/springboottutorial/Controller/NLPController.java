package com.example.springboottutorial.Controller;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.List;
import java.util.Properties;
import java.util.Hashtable;
import java.util.LinkedList;

public class NLPController {

    public List<CoreLabel> NLP(String text) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma,ner");

        props.setProperty("ner.fine.regexner.mapping", "src/main/resources/Wines.rules");
        props.setProperty("ner.fine.regexner.ignorecase", "true");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // make an example document
        CoreDocument doc = new CoreDocument(text);
        // annotate the document
        pipeline.annotate(doc);

        return doc.tokens();
    }

    public class values
    {
        int val1;
        int val2;
        values(int v1, int v2)
        {
            this.val1=v1;
            this.val2=v2;
        }

        public void Update_VAl(int v1, int v2)
        {
            this.val1=v1;
            this.val2=v2;
        }
    }//end of class values


    public double cosineSimilarity(List<CoreLabel> text1, List<CoreLabel> text2) {


        //Consine Sim stuffs
        Hashtable<String, values> freq_vector = new Hashtable<String, NLPController.values>();
        LinkedList<String> Distinct_words = new LinkedList<String>();
        double sim_score=0.0000000;


        for (CoreLabel token : text1) {
            if (!token.ner().equals("O")) {
                if (freq_vector.containsKey(token.word())) {
                    values vals1 = freq_vector.get(token.word());
                    int freq1 = vals1.val1 + 1;
                    int freq2 = vals1.val2;
                    vals1.Update_VAl(freq1, freq2);
                    freq_vector.put(token.word(), vals1);
                } else {
                    values vals1 = new values(1, 0);
                    freq_vector.put(token.word(), vals1);
                    Distinct_words.add(token.word());
                }
            }
        }

        for (CoreLabel token : text2) {
            if (!token.ner().equals("O")) {
                if (freq_vector.containsKey(token.word())) {
                    values vals1 = freq_vector.get(token.word());
                    int freq1 = vals1.val1;
                    int freq2 = vals1.val2 + 1;
                    vals1.Update_VAl(freq1, freq2);
                    freq_vector.put(token.word(), vals1);
                } else {
                    values vals1 = new values(1, 0);
                    freq_vector.put(token.word(), vals1);
                    Distinct_words.add(token.word());
                }
            }
        }

        //calculate the cosine similarity score.
        double VectAB = 0.0000000;
        double VectA_Sq = 0.0000000;
        double VectB_Sq = 0.0000000;

        for(int i=0;i<Distinct_words.size();i++)
        {
            values vals12 = freq_vector.get(Distinct_words.get(i));

            double freq1 = vals12.val1;
            double freq2 = vals12.val2;

            VectAB=VectAB+(freq1*freq2);

            VectA_Sq = VectA_Sq + freq1*freq1;
            VectB_Sq = VectB_Sq + freq2*freq2;
        }

        sim_score = ((VectAB)/(Math.sqrt(VectA_Sq)*Math.sqrt(VectB_Sq)));

        return sim_score;
    }


}

 /* Properties props = new Properties();
        props.setProperty("annotators", "tokenize");

        props.setProperty("tokenize.language", "English");
        props.setProperty("tokenize.options", "americanize=false");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument doc1 = new CoreDocument(text1);
        CoreDocument doc2 = new CoreDocument(text2);

        pipeline.annotate(doc1);
        pipeline.annotate(doc2);*/ // Goes in Consine stuffs