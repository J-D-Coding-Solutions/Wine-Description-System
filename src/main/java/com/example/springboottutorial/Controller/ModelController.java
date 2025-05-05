package com.example.springboottutorial.Controller;


import com.example.springboottutorial.Model.arff;
import com.example.springboottutorial.Repository.arffRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelController {

    private final arffRepository arffRepository;

    public ModelController(arffRepository arffRepository) {
        this.arffRepository = arffRepository;
    }

    private File tempFile;

    public void createArff() {
        //Prepare text for training
        try {
            // Prepare output file. This is the file that Weka uses to train a model

           tempFile = new File(System.getProperty("java.io.tmpdir"), "wine_descriptors.arff");
           FileWriter writer = new FileWriter(tempFile);


            writer.write("@relation wine_descriptors\n"); //The Name of the Model I think

            writer.write("@attribute word string\n"); //Attrubutes of the data in the model
            writer.write("@attribute pos {Adjective, Noun, Verb, Other}\n"); //Attribute
            writer.write("@attribute wordLength Numeric\n");
            writer.write("@attribute containsDigit {True, False}\n");
            writer.write("@attribute prevWordPOS {Adjective, Noun, Verb, Other, Empty}\n");
            writer.write("@attribute nextWordPOS {Adjective, Noun, Verb, Other, Empty}\n");
            writer.write("@attribute isContextuallyDescriptive {True, False}\n");
            writer.write("@attribute Suffix {berry, ic, y, ous, ness, -like, al, ish, False}\n");

            writer.write("@attribute keyWord {True, False}\n"); //This is the attribute the model is trying to guess
            writer.write("@attribute wordWeight Numeric \n");

            writer.write("@data\n");


            List<arff> arffs = arffRepository.findAll();
            for (arff arff : arffs) {
                writer.write("\"" + arff.getWord() + "\"," + arff.getPos() + "," + arff.getWordLength() + "," + arff.getContainsDigit() + "," + arff.getPrevWordPOS() + "," + arff.getNextWordPOS() + "," + arff.getIsContextuallyDescriptive() + "," + arff.getSuffix() + "," + arff.getKeyWord() + "," + arff.getWordWeight() + "\n");  //Label
            }
            writer.close();
            System.out.println("ARFF file generated. You can now label data in Weka.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


    public void trainModel() {
        try {
            //Creating and Training a New Model in code (Can be a new Class)
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(tempFile.getAbsolutePath());//Loads the Arff file, throws expection if file not found
            Instances data = source.getDataSet(); //gets the Data from under the @Data tag

            Remove remove = new Remove();
            remove.setAttributeIndices("1");  // Weka uses 1-based indexing!
            remove.setInputFormat(data);
            System.out.println(data.attribute(0).name());

            Instances filteredData = Filter.useFilter(data, remove);


            remove.setAttributeIndices("9");
            System.out.println(data.attribute(9).name());// Weka uses 1-based indexing!
            remove.setInputFormat(filteredData);


            filteredData = Filter.useFilter(filteredData, remove);

            filteredData.setClassIndex(filteredData.numAttributes() - 1);// Sets the class attribute as the last attribute

            System.out.println(filteredData.attribute(0).name());
            System.out.println(filteredData.attribute(filteredData.numAttributes() - 1).name());

            Classifier classifier = new J48();  // Decision tree, This throws and error since its protected class.
            classifier.buildClassifier(filteredData);

            weka.core.SerializationHelper.write("Models/wine_model.model", classifier);
           // boolean tempDelete = tempFile.delete();
            //System.out.println("Temp file deleted: " + tempDelete);
            System.out.println("Model trained and saved!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            //Creating and Training a New Model in code (Can be a new Class)
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(tempFile.getAbsolutePath());//Loads the Arff file, throws expection if file not found
            Instances data = source.getDataSet(); //gets the Data from under the @Data tag

            Remove remove = new Remove();
            remove.setAttributeIndices("1");  // Weka uses 1-based indexing!
            remove.setInputFormat(data);
            System.out.println(data.attribute(0).name());

            Instances filteredData = Filter.useFilter(data, remove);


            remove.setAttributeIndices("8");
            System.out.println(data.attribute(8).name());// Weka uses 1-based indexing!
            remove.setInputFormat(filteredData);


            filteredData = Filter.useFilter(filteredData, remove);

            filteredData.setClassIndex(filteredData.numAttributes() - 1);// Sets the class attribute as the last attribute

            System.out.println(filteredData.attribute(0).name());
            System.out.println(filteredData.attribute(filteredData.numAttributes() - 1).name());

            Classifier classifier = new RandomForest();  // Decision tree, This throws and error since its protected class.
            classifier.buildClassifier(filteredData);

            weka.core.SerializationHelper.write("Models/weight_model.model", classifier);
            // boolean tempDelete = tempFile.delete();
            //System.out.println("Temp file deleted: " + tempDelete);
            System.out.println("Model trained and saved!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

       System.out.println("Deleted temp file:" + tempFile.delete());

    }


    public List<String> predictKeywordType(List<String> keyWords){
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
            Classifier classifier = (Classifier) SerializationHelper.read("Models/wine_model.model");


            //Creates an instance for each keyWord
            for (String keyWord : keyWords) {
                List<String> data = Arrays.asList(keyWord.split(","));
                DenseInstance inst = new DenseInstance(attributes.size()); // 6 attributes

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

//                System.out.println("KeyWord: " + dataset.get(i).stringValue((dataset.get(i).attribute(0))));
//                System.out.println("Predicted keyWord class: "  + predictionLabel);


                    predictKeyWords.add(predictionLabel);

            }

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return predictKeyWords;
    }


    public List<Double> predictWeights(List<String> keyWords){
        List<Double> predictWeights = new ArrayList<>();
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


        attributes.add(new Attribute("wordWeight")); // Class attribute

        Instances dataset = new Instances("TestInstances", attributes, 0);
        try {
            Classifier classifier = (Classifier) SerializationHelper.read("Models/weight_model.model");


            //Creates an instance for each keyWord
            for (String keyWord : keyWords) {
                List<String> data = Arrays.asList(keyWord.split(","));
                DenseInstance inst = new DenseInstance(attributes.size()); // 6 attributes

                inst.setValue(attributes.get(0), data.get(0));
                inst.setValue(attributes.get(1), data.get(1)); // pos
                inst.setValue(attributes.get(2), Integer.parseInt(data.get(2))); // wordLength
                inst.setValue(attributes.get(3), data.get(3));           // wordLength
                inst.setValue(attributes.get(4), data.get(4));;
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

                predictWeights.add(predictionIndex);
            }

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return predictWeights;
    }

    public void addArff(List<String> userKeyWords, List<String> predictedKeyWord,  List<Double> predictedWeights){
        for(int i = 0; i < userKeyWords.size(); i++){
            String[] parts = userKeyWords.get(i).split(",");

// Extract each variable from the parts array
            String extractedLemma = parts[0].replace("\"", "");  // Remove the quotes around the lemma
            String extractedPos = parts[1];
            int extractedWordLength = Integer.parseInt(parts[2]);
            String extractedContainsDigit = parts[3];
            String extractedPrevWordPOS = parts[4];
            String extractedNextPos = parts[5];
            String extractedIsContextuallyDescriptive = parts[6];
            String extractedSuffix = parts[7];

            arff arff = new arff(extractedLemma, extractedPos, extractedWordLength, extractedContainsDigit, extractedPrevWordPOS, extractedNextPos, extractedIsContextuallyDescriptive, extractedSuffix, predictedKeyWord.get(i), predictedWeights.get(i));
            arffRepository.save(arff);
        }
    }


}
