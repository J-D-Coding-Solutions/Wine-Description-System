package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.wines;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import weka.classifiers.Classifier;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;


import java.awt.*;
import java.util.*;
import java.util.List;

public class NLPController {



    public List<CoreLabel> NLP(String text) {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma,ner");
        props.setProperty("ner.model", "");

        props.setProperty("ner.fine.regexner.mapping", "src/main/resources/Country.rules,src/main/resources/Wines.rules, src/main/resources/Province.rules, src/main/resources/Variety.rules, src/main/resources/Winery.rules");

        props.setProperty("ner.fine.regexner.ignorecase", "true");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // make an example document
        CoreDocument doc = new CoreDocument(text);
        // annotate the document
        pipeline.annotate(doc);
        return doc.tokens();
    }


    public List<String> keyWordList(List<CoreLabel> tokens){
        List<String> keyWords = new ArrayList<>();
        String prevWordPOS = "Empty";
        String nextPos = "Empty";
        int i = 0;
        String[] suffixes = {"berry", "ic", "y", "ous", "ness", "-like", "al", "ish"};
        for(CoreLabel token : tokens) {
            String word = token.word();
            String lemma = token.lemma();
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            String containsDigit = "False";
            String Suffix = "False";
            String isContextuallyDescriptive = "False";
            //System.out.println(word + " " + pos);

            //Rules
            pos = pos.startsWith("J") ? "Adjective" : pos.startsWith("N") ? "Noun" : pos.startsWith("V") ? "Verb" : "Other";

            for (Character c : word.toCharArray()) {//Checks if word contains a numbers!
                if (Character.isDigit(c)) {
                    containsDigit = "True";
                    break;
                }
            }
            //Suffixes Checker, add more suffixes if needed above
            for (String suffix : suffixes) {
                if (word.toLowerCase().endsWith(suffix)) {
                    Suffix = suffix;
                    break;
                }
            }

            if (tokens.size() - 2 >= i) {
                nextPos = tokens.get(i + 1).get(CoreAnnotations.PartOfSpeechAnnotation.class);
                nextPos = nextPos.startsWith("J") ? "Adjective" : nextPos.startsWith("N") ? "Noun" : nextPos.startsWith("V") ? "Verb" : "Other";
            } else {
                ;
                nextPos = "Empty";
            }

            boolean contextuallyDescriptive = (prevWordPOS.equals("Adjective") && pos.equals("Noun")) || (pos.equals("Adjective") && nextPos.equals("Adjective"));
            if (contextuallyDescriptive) {
                isContextuallyDescriptive = "True";
            }
            if (!word.equals(",")) {
                keyWords.add("\"" + lemma + "\"," + pos + "," + word.length() + "," + containsDigit + "," + prevWordPOS + "," + nextPos + "," + isContextuallyDescriptive + "," + Suffix + ",?\n");
            }
        }

        return keyWords;
    }

    public List<String> predictKeywords(List<String> keyWords){
        List<String> predictKeyWords = new ArrayList<>();
        //Build the Attribute List for the classifier.
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("word", (ArrayList<String>) null)); // String attribute

        ArrayList<String> posValues = new ArrayList<>(Arrays.asList("Adjective", "Noun", "Verb", "Other"));
        attributes.add(new Attribute("pos", posValues)); // Nominal attribute

        attributes.add(new Attribute("wordLength")); // Numeric attribute

        ArrayList<String> containsDigit = new ArrayList<>(Arrays.asList("True", "False"));
        attributes.add(new Attribute("containsDigit", containsDigit));

        ArrayList<String> prevWordPOS = new ArrayList<>(Arrays.asList("Adjective", "Noun", "Verb", "Other", "Empty"));
        attributes.add(new Attribute("prevWordPOS", prevWordPOS));

        ArrayList<String> nextWordPOS = new ArrayList<>(Arrays.asList("Adjective", "Noun", "Verb", "Other", "Empty"));
        attributes.add(new Attribute("nextWordPOS", nextWordPOS));

        ArrayList<String> isContextuallyDescriptive = new ArrayList<>(Arrays.asList("True", "False"));
        attributes.add(new Attribute("isContextuallyDescriptive", isContextuallyDescriptive));

        ArrayList<String> Suffix = new ArrayList<>(Arrays.asList("berry", "ic", "y", "ous", "ness", "-like", "al", "ish", "False"));
        attributes.add(new Attribute("Suffix", Suffix));

        ArrayList<String> classValues = new ArrayList<>(Arrays.asList("True", "False"));
        attributes.add(new Attribute("keyWord", classValues)); // Class attribute

        Instances dataset = new Instances("TestInstances", attributes, 0);
        try {
            Classifier classifier = (Classifier) SerializationHelper.read("src/main/resources/wine_model.model");


            //Creates an instance for each keyWord
            for (String keyWord : keyWords) {
                List<String> data = Arrays.asList(keyWord.split(","));
                DenseInstance inst = new DenseInstance(9); // 6 attributes

                inst.setValue(attributes.get(0), data.get(0));
                inst.setValue(attributes.get(1), data.get(1)); // pos
                inst.setValue(attributes.get(2), Integer.parseInt(data.get(2))); // wordLength
                inst.setValue(attributes.get(3), data.get(3));           // wordLength
                inst.setValue(attributes.get(4), data.get(4));
                inst.setValue(attributes.get(5), data.get(5));
                inst.setValue(attributes.get(6), data.get(6));
                inst.setValue(attributes.get(7), data.get(7));

                dataset.add(inst);
            }
            dataset.setClassIndex(dataset.numAttributes() - 1);
            //Removes the Name attribute from the dataset.
            Remove remove = new Remove();
            remove.setAttributeIndices("1");  // Weka uses 1-based indexing!
            remove.setInputFormat(dataset);
            Instances filteredData = Filter.useFilter(dataset, remove);
            //Sets the Class attribute, The one we are trying to predict.
            filteredData.setClassIndex(filteredData.numAttributes() - 1);

            for(int i =0; i<filteredData.numInstances(); i++){
                double predictionIndex = classifier.classifyInstance(filteredData.get(i)); // predict first instance
                String predictionLabel = dataset.classAttribute().value((int) predictionIndex);

                if(predictionLabel.equals("True")){
                    predictKeyWords.add(dataset.get(i).stringValue((dataset.get(i).attribute(0))));
                }
            }

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return predictKeyWords;
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

    public double cosineSimilarity(List<String> text1, List<String> text2) {


        //Consine Sim stuffs
        Hashtable<String, values> freq_vector = new Hashtable<String, NLPController.values>();
        LinkedList<String> Distinct_words = new LinkedList<String>();
        double sim_score=0.0000000;


        for (String word : text1) {
                if (freq_vector.containsKey(word)) {
                    values vals1 = freq_vector.get(word);
                    int freq1 = vals1.val1 + 1;
                    int freq2 = vals1.val2;
                    vals1.Update_VAl(freq1, freq2);
                    freq_vector.put(word, vals1);
                } else {
                    values vals1 = new values(1, 0);
                    freq_vector.put(word, vals1);
                    Distinct_words.add(word);
                }

        }

        for (String word : text2) {
                if (freq_vector.containsKey(word)) {
                    values vals1 = freq_vector.get(word);
                    int freq1 = vals1.val1;
                    int freq2 = vals1.val2 + 1;
                    vals1.Update_VAl(freq1, freq2);
                    freq_vector.put(word, vals1);
                } else {
                    values vals2 = new values(0, 1);
                    freq_vector.put(word, vals2);
                    Distinct_words.add(word);
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

            System.out.println(Distinct_words.get(i) + ":" +  freq1 + " " + freq2);

            VectAB=VectAB+(freq1*freq2);

            VectA_Sq = VectA_Sq + freq1*freq1;
            VectB_Sq = VectB_Sq + freq2*freq2;
        }

        sim_score = ((VectAB)/(Math.sqrt(VectA_Sq)*Math.sqrt(VectB_Sq)));

        return sim_score;
    }

    public String jsonObj(List<wines> winelist){
        String jsonObj = "[";

        for (wines wine : winelist) {
            System.out.println(wine.getWineName() + " " + wine.getCountry());
            jsonObj = jsonObj + "{" + "\"winename\":\"" + wine.getWineName() + "\", \"winery\":\"" + wine.getWinery() + "\", \"country\":\"" + wine.getCountry() + "\", \"province\": \"" + wine.getProvince() + "\"},";
        }

        if (jsonObj.length() > 1) {
            jsonObj = jsonObj.substring(0, jsonObj.length() - 1);
        }

        jsonObj = jsonObj + "]";
        return jsonObj;

    }
}