package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.wines;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
// import edu.stanford.nlp.pipeline.Annotation; // Not used in original relevant methods
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
// import edu.stanford.nlp.util.CoreMap; // Not used in original relevant methods

import weka.classifiers.Classifier;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;


import java.util.*;
import java.util.function.Function; // Needed for weightedCosineSimilarity cleaning

public class NLPController {

    public List<CoreLabel> NLP(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma,ner");
        props.setProperty("ner.model", ""); // Uses only RegexNER

        //props.setProperty("ner.fine.regexner.mapping", "src/main/resources/Country.rules,src/main/resources/Wines.rules, src/main/resources/Province.rules, src/main/resources/Variety.rules, src/main/resources/Winery.rules");
        props.setProperty("ner.fine.regexner.ignorecase", "true");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument doc = new CoreDocument(text);
        pipeline.annotate(doc);
        return doc.tokens();
    }

    // keyWordList() method - with corrected prevWordPOS update logic
    public List<String> keyWordList(List<CoreLabel> tokens){
        List<String> keyWords = new ArrayList<>();
        String prevWordPOS = "Empty"; // Initial value BEFORE the loop
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

            String simplifiedPos = pos.startsWith("J") ? "Adjective" : pos.startsWith("N") ? "Noun" : pos.startsWith("V") ? "Verb" : "Other";

            for (Character c : word.toCharArray()) {
                if (Character.isDigit(c)) {
                    containsDigit = "True";
                    break;
                }
            }

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
                nextPos = "Empty";
            }

            boolean contextuallyDescriptive = (prevWordPOS.equals("Adjective") && simplifiedPos.equals("Noun")) || (simplifiedPos.equals("Adjective") && nextPos.equals("Adjective"));
            if (contextuallyDescriptive) {
                isContextuallyDescriptive = "True";
            }

            if (!word.equals(",")) {
                keyWords.add("\"" + lemma + "\"," + simplifiedPos + "," + word.length() + "," + containsDigit + "," + prevWordPOS + "," + nextPos + "," + isContextuallyDescriptive + "," + Suffix + ",?\n");
            }

            prevWordPOS = simplifiedPos;

            i++;
        }
        return keyWords;
    }


    public List<String> predictKeywords(List<String> keyWords){
        List<String> predictKeyWords = new ArrayList<>();
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

        //attributes.add(new Attribute("wordWeight")); // Numeric attribute

        Instances dataset = new Instances("TestInstances", attributes, 0);
        try {

            Classifier classifier = (Classifier) SerializationHelper.read("src/main/resources/wine_model.model");

            for (String keyWord : keyWords) {
                List<String> data = Arrays.asList(keyWord.split(","));
                DenseInstance inst = new DenseInstance(9);


                inst.setDataset(dataset);

                inst.setValue(attributes.get(0), data.get(0));            // word (String) - Index 0
                inst.setValue(attributes.get(1), data.get(1));            // pos (Nominal) - Index 1
                inst.setValue(attributes.get(2), Integer.parseInt(data.get(2))); // wordLength (Numeric) - Index 2 - Original used Integer.parseInt
                inst.setValue(attributes.get(3), data.get(3));            // containsDigit (Nominal) - Index 3
                inst.setValue(attributes.get(4), data.get(4));            // prevWordPOS (Nominal) - Index 4
                inst.setValue(attributes.get(5), data.get(5));            // nextWordPOS (Nominal) - Index 5
                inst.setValue(attributes.get(6), data.get(6));            // isContextuallyDescriptive (Nominal) - Index 6
                inst.setValue(attributes.get(7), data.get(7));            // Suffix (Nominal) - Index 7
                //inst.setValue(attributes.get(8), data.get(8));            //WordWeight


                dataset.add(inst);
            }

            dataset.setClassIndex(dataset.numAttributes() - 1);

            Remove remove = new Remove();
            remove.setAttributeIndices("1");
            remove.setInputFormat(dataset);
            Instances filteredData = Filter.useFilter(dataset, remove);

            //Sets the Class attribute, The one we are trying to predict.
            // Class index needs to be set on the *filtered* data
            if (filteredData.numAttributes() > 0) { // Check attributes exist before setting index
                filteredData.setClassIndex(filteredData.numAttributes() - 1);
            } else {
                System.out.println("Error: No attributes remaining after filtering in predictKeywords. Cannot classify.");
                return predictKeyWords;
            }


            for(int i =0; i<filteredData.numInstances(); i++){
                double predictionIndex = classifier.classifyInstance(filteredData.instance(i)); // Use instance(i) from filtered data

                String predictionLabel = filteredData.classAttribute().value((int) predictionIndex);

//                System.out.println("KeyWord: " + dataset.get(i).stringValue((dataset.get(i).attribute(0))));
//                System.out.println("Predicted keyWord class: "  + predictionLabel);

                if(predictionLabel.equals("True")){
                    predictKeyWords.add(dataset.instance(i).stringValue(attributes.get(0))); // Use instance(i) and original attribute def
                }
            }

        }catch (Exception e){
            System.out.println("Error in predictKeywords: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for better debug info
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

    // weightedCosineSimilarity() method - MODIFIED for correct weighting
    public double weightedCosineSimilarity(
            List<String> metadata1, List<String> metadata2, // Will be used to identify metadata terms
            List<String> description1, List<String> description2,
            double metadataWeight, double descriptionWeight)
    {
        // Combine all terms from both documents (metadata + description)
        List<String> allTerms1 = new ArrayList<>();
        if (metadata1 != null) allTerms1.addAll(metadata1);
        if (description1 != null) allTerms1.addAll(description1);

        List<String> allTerms2 = new ArrayList<>();
        if (metadata2 != null) allTerms2.addAll(metadata2);
        if (description2 != null) allTerms2.addAll(description2);

        Set<String> metadataTermSet = new HashSet<>();
        Function<String, String> clean = s -> (s == null) ? "" : s.toLowerCase().replace("\"", ""); // Helper to clean

        if (metadata1 != null) {
            for (String meta : metadata1) {
                metadataTermSet.add(clean.apply(meta));
            }
        }
        if (metadata2 != null) {
            for (String meta : metadata2) {
                metadataTermSet.add(clean.apply(meta));
            }
        }
        metadataTermSet.remove(""); // Remove empty string if present

        System.out.println("--- Identifying Metadata Terms ---");
        System.out.println("Metadata Set for Weighting: " + metadataTermSet);


        // Build frequency vector using the original Hashtable approach
        Hashtable<String, values> freq_vector = new Hashtable<>();
        LinkedList<String> Distinct_words = new LinkedList<>();

        // Process terms from Document 1 (metadata1 + description1)
        for (String word : allTerms1) {
            if (word == null || word.isEmpty()) continue;
            String cleanedWord = clean.apply(word); // Clean the word for map keys
            if (cleanedWord.isEmpty()) continue;

            if (freq_vector.containsKey(cleanedWord)) {
                values vals1 = freq_vector.get(cleanedWord);
                int freq1 = vals1.val1 + 1;
                int freq2 = vals1.val2;
                vals1.Update_VAl(freq1, freq2);
            } else {
                values vals1 = new values(1, 0);
                freq_vector.put(cleanedWord, vals1);
                Distinct_words.add(cleanedWord);
            }
        }

        // Process terms from Document 2 (metadata2 + description2)
        for (String word : allTerms2) {
            if (word == null || word.isEmpty()) continue;
            String cleanedWord = clean.apply(word);
            if (cleanedWord.isEmpty()) continue;

            if (freq_vector.containsKey(cleanedWord)) {
                values vals1 = freq_vector.get(cleanedWord);
                int freq1 = vals1.val1;
                int freq2 = vals1.val2 + 1;
                vals1.Update_VAl(freq1, freq2);
            } else {
                values vals2 = new values(0, 1);
                freq_vector.put(cleanedWord, vals2);
                Distinct_words.add(cleanedWord);
            }
        }

        double VectAB = 0.0000000; // Dot product
        double VectA_Sq = 0.0000000; // Magnitude Squared Doc 1
        double VectB_Sq = 0.0000000; // Magnitude Squared Doc 2

        System.out.println("--- Calculating Weighted Scores ---");
        // Iterate through all distinct words found
        for (String distinctWord : Distinct_words) {
            values wordfreq = freq_vector.get(distinctWord);
            double freq1 = wordfreq.val1;
            double freq2 = wordfreq.val2;

            // Determine the weight for this term
            double weight;
            if (metadataTermSet.contains(distinctWord)) {
                weight = metadataWeight;
                System.out.println("  Weighting (Meta): '" + distinctWord + "' with " + metadataWeight);
            } else {
                weight = descriptionWeight;
                System.out.println("  Weighting (Desc): '" + distinctWord + "' with " + descriptionWeight);
            }
// *** NEW DEBUG BLOCK START ***
            // Check if the term exists in BOTH inputs (is a true match contributing to VectAB)
//            if (freq1 > 0 && freq2 > 0) {
//                String termType = metadataTermSet.contains(distinctWord) ? "Metadata" : "Description";
//                double contributionToDotProduct = weight * freq1 * freq2; // Calculate this term's contribution
//
//                System.out.printf("  -> Match Found: '%s' | Type: %s | User Freq: %.0f | Wine Freq: %.0f | Weight: %.1f | Dot Product Contrib: %.2f%n",
//                        distinctWord,
//                        termType,
//                        freq1,
//                        freq2,
//                        weight,
//                        contributionToDotProduct);
//            }
            // *** NEW DEBUG BLOCK END ***
            // Apply weight to components: w*f1*f2, w*f1^2, w*f2^2
            VectAB += weight * freq1 * freq2;
            VectA_Sq += weight * freq1 * freq1;
            VectB_Sq += weight * freq2 * freq2;


        }

        System.out.println("Final Dot Product (Weighted VectAB): " + VectAB);
        System.out.println("Final Mag Sq Doc 1 (Weighted VectA_Sq): " + VectA_Sq);
        System.out.println("Final Mag Sq Doc 2 (Weighted VectB_Sq): " + VectB_Sq);

        double sim_score = 0.0;
        if (VectA_Sq > 0 && VectB_Sq > 0) {
            sim_score = VectAB / (Math.sqrt(VectA_Sq) * Math.sqrt(VectB_Sq));
        } else {
            System.out.println("One or both vector magnitudes are zero. Similarity is 0.");
        }

        sim_score = Math.max(0.0, Math.min(1.0, sim_score));

        System.out.printf("<<< NLPController: Final Weighted Score for this comparison: %.6f%n", sim_score);
        return sim_score;
    }

    public String jsonObj(List<wines> winelist){
        String jsonObj = "[";

        for (wines wine : winelist) {
            jsonObj = jsonObj + "{" + "\"winename\":\"" + wine.getWineName() + "\", \"winery\":\"" + wine.getWinery() + "\", \"country\":\"" + wine.getCountry() + "\", \"province\": \"" + wine.getProvince() + "\"},";
        }

        if (jsonObj.length() > 1) {
            jsonObj = jsonObj.substring(0, jsonObj.length() - 1);
        }

        jsonObj = jsonObj + "]";
        return jsonObj;
    }
}