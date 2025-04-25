package com.example.springboottutorial.Controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DELETE_LATER {

    String trainingData = "This tremendous 100% varietal wine hails from Oakville and was aged over three years in oak. Juicy red-cherry fruit and a compelling hint of caramel greet the palate, framed by elegant, fine tannins and a subtle minty tone in the background. Balanced and rewarding from start to finish, it has years ahead of it to develop further nuance. Enjoy 2022â€“2030." +
            " Mac Watson honors the memory of a wine once made by his mother in this tremendously delicious, balanced and complex botrytised white. Dark gold in color, it layers toasted hazelnut, pear compote and orange peel flavors, reveling in the succulence of its 122 g/L of residual sugar." +
            " This spent 20 months in 30% new French oak, and incorporates fruit from Ponzi's Aurora, Abetina and Madrona vineyards, among others. Aromatic, dense and toasty, it deftly blends aromas and flavors of toast, cigar box, blackberry, black cherry, coffee and graphite. Tannins are polished to a fine sheen, and frame a finish loaded with dark chocolate and espresso. Drink now through 2032." +
            " Slightly gritty black-fruit aromas include a sweet note of pastry along with a hint of prune. Wall-to-wall saturation ensures that all corners of one's mouth are covered. Flavors of blackberry, mocha and chocolate are highly impressive and expressive, while this settles nicely on a long finish. Drink now through 2024." +
            " Lush cedary black-fruit aromas are luxe and offer notes of marzipan and vanilla. This bruiser is massive and tannic on the palate, but still lush and friendly. Chocolate is a key flavor, while baked berry and cassis flavors are hardly wallflowers. On the finish, this is tannic and deep as a sea trench. Drink this saturated black-colored Toro through 2023." +
            " This re-named vineyard was formerly bottled as deLancellotti. You'll find striking minerality underscoring chunky black fruits. Accents of citrus and graphite comingle, with exceptional midpalate concentration. This is a wine to cellar, though it is already quite enjoyable. Drink now through 2030." +
            " The producer sources from two blocks of the vineyard for this wineâ€”one at a high elevation, which contributes bright acidity. Crunchy cranberry, pomegranate and orange peel flavors surround silky, succulent layers of texture that present as fleshy fruit. That delicately lush flavor has considerable length." +
            " Elegance, complexity and structure come together in this drop-dead gorgeous winethat ranks among Italy's greatest whites. It opens with sublime yellow spring flower, aromatic herb and orchard fruit scents. The creamy, delicious palate seamlessly combines juicy white peach, ripe pear and citrus flavors while white almond and savory mineral notes grace the lingering finish." +
            " From 18-year-old vines, this supple well-balanced effort blends flavors of mocha, cherry, vanilla and breakfast tea. Superbly integrated and delicious even at this early stage, this wine seems destined for a long and savory cellar life. Drink now through 2028." +
            " A standout even in this terrific lineup of 2015 releases from Patricia Green, the Weber opens with a burst of cola and tobacco scents and accents. It continues, subtle and detailed, with flavors of oranges, vanilla, tea and milk chocolate discreetly threaded through ripe blackberry fruit." +
            " This wine is in peak condition. The tannins and the secondary flavors dominate this ripe leather-textured wine. The fruit is all there as well: dried berries and hints of black-plum skins. It is a major wine right at the point of drinking with both the mature flavors and the fruit in the right balance." +
            " With its sophisticated mix of mineral, acid and tart fruits, this seductive effort pleases from start to finish. Supple and dense, it's got strawberry, blueberry, plum and black cherry, a touch of chocolate, and that underlying streak of mineral. All these elements are in good proportion and finish with an appealing silky texture. It's delicious already, but give it another decade for full enjoyment. Drink now through 2028." +
            " First made in 2006, this succulent luscious Chardonnay is all about minerality. It's got a rich core of butterscotch and the seemingly endless layers of subtle flavors that biodynamic farming can bring. It spends 18 months on the lees prior to bottling. Drink now through 2028." +
            " This blockbuster, powerhouse of a wine suggests blueberry pie and chocolate as it opens in the glass. On the palate, it's smooth and seductively silky, offering complex cedar, peppercorn and peppery oak seasonings amidst its dense richness. It finishes with finesse and spice." +
            " Nicely oaked blackberry, licorice, vanilla and charred aromas are smooth and sultry. This is an outstanding wine from an excellent year. Forward barrel-spice and mocha flavors adorn core blackberry and raspberry fruit, while this runs long and tastes vaguely chocolaty on the velvety finish. Enjoy this top-notch Tempranillo through 2030." +
            " Coming from a seven-acre vineyard named after the dovecote on the property, this is a magnificent wine. Powered by both fruit tannins and the 28 months of new wood aging, it is darkly rich and with great concentration. As a sign of its pedigree, there is also elegance here, a restraint which is new to this wine. That makes it a wine for long-term aging. Drink from 2022." +
            " This fresh and lively medium-bodied wine is beautifully crafted, with cherry blossom aromas and tangy acidity. Layered and seductive, it offers a crisp mix of orange peel, cherry, pomegranate and baking spice flavors that are ready for the table or the cellar." ;

    String testData = "Alluring, complex and powerful aromas of grilled meat, berries, tea, smoke, vanilla and spice cover every base. An intense palate is concentrated but still elegant. Blackberry, molasses and mocha flavors finish with chocolaty oak notes, fine tannins and overall cohesion. Drink this special Rioja from 2020 through 2035." +
            " Tarry blackberry and cheesy oak aromas are appropriate for a wine of this size and magnitude. In the mouth, this Tinta de Toro is expansive and grabby, with bullish tannins. Slightly salty earthy accents come with core blackberry flavors and notes of baking spices and chocolate, while on the finish this is long, chocolaty, delicious and not too hard or tannic. Drink through 2023." +
            " The apogee of this ambitious winery's white wine efforts, this bottling of just two barrels of Clone 5 shows focused marzipan, marcona almond and white peach notes on the nose. The palate sizzles with saltiness and a lemony acidity that cuts through its brown butter richness with finesse. The finish is luxurious and long." +
            " San Jose-based producer Adam Comartin heads 1,100 feet up into the mountains to source fruit for this tremendous wine, which offers wild cherry, sage, and eucalyptus scents on the nose. Ripe black plum and strawberry fruit lead the palate, followed by acid-driven waves of juniper, pine and bay leaf, finishing with a menthol-like sensation." +
            " Yields were down in 2015, but intensity is up, giving this medium-bodied, silky wine the potential to drink well through at least 2025. Hickory smoke outlines white peach before ending in a long flurry of lime zest." +
            " BergstrÃ¶m has made a Shea designate since 2003, intent on showcasing a â€œprettyâ€\u009D style of the vineyard. Here are lovely aromatics, with grape jelly, rose petals and plum in the nose, and a complex run of red fruits in the mouth. Streaks of cola, brown sugar and more come up in a generous finish.";


    prepareData prepareData = new prepareData();
    //prepareData.createArff(testData); //Very Scary!

    List<CoreLabel> tokens = prepareData.NlPProcessing(testData);
    String pos = "";


//      trainModel trainModel = new trainModel();
//       trainModel.trainModel();

//        useModel useModel = new useModel();
//
//
//        for (CoreLabel token : tokens) {
//            String temp = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//            temp  = temp.startsWith("J") ? "Adjective" : temp.startsWith("N") ? "Noun" : temp.startsWith("V") ? "Verb" : "Other";//This is seperating into POS, only one sthat we want
//            Instances data = useModel.createAttribute(temp);
//            System.out.print(token.word() + " " + temp + " ");
//            useModel.predictData(data);//Possible inputs are Adjective, Noun, Verb, Other
//            If results = True then List of Key
//        }


    Classifier classifier = (Classifier) SerializationHelper.read("wine_model.model");

    // Load the new unseen data
    ConverterUtils.DataSource source = new ConverterUtils.DataSource("wine_descriptors_test.arff");
    Instances newData = source.getDataSet();

    Remove remove = new Remove();
        remove.setAttributeIndices("1");  // Weka uses 1-based indexing!
        remove.setInputFormat(newData);
    // Set the class index (important!)

    Instances filteredData = Filter.useFilter(newData, remove);

        filteredData.setClassIndex(filteredData.numAttributes() - 1); // usually last column

    // Evaluate predictions
        for (int i = 0; i < filteredData.numInstances(); i++) {
        double prediction = classifier.classifyInstance(filteredData.instance(i));
        String predictedClass = filteredData.classAttribute().value((int) prediction);
        System.out.println(newData.instance(i).stringValue(0));//prints word
        System.out.println("Instance " + i + " actual class: " + filteredData.classAttribute().value((int) filteredData.instance(i).classValue()));//prints what the answer is
        System.out.println("Instance " + i + " predicted class: " + predictedClass);//What it predicted

        System.out.println("Instance " + i + " Actual Class: " + filteredData.instance(i).stringValue(0) );//the corrected POS

    }


    public List<CoreLabel> NlPProcessing(String text)

    {
        List<CoreLabel> tokens = new ArrayList<>();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                tokens.add(token);
            }
        }
        return tokens;
    }

    public void createArff(String text){
        //Prepare text for training
        try {
            // Prepare output file. This is the file that Weka uses to train a model
            FileWriter writer = new FileWriter("wine_descriptors_test.arff");

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

            writer.write("@data\n");

// Loop through tokens to get adjectives
            List<CoreLabel> tokens =  NlPProcessing(text);
            System.out.println("Tokens: " + tokens.size());
            String prevWordPOS = "Empty";
            String nextPos = "Empty";
            int i = 0;
            String[] suffixes = {"berry", "ic", "y", "ous", "ness", "-like", "al", "ish"};
            for (CoreLabel token :tokens) {
                String word = token.word();
                String lemma = token.lemma();
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String containsDigit = "False";
                String Suffix = "False";
                String isContextuallyDescriptive = "False";
                //System.out.println(word + " " + pos);

                //Rules
                pos = pos.startsWith("J") ? "Adjective" : pos.startsWith("N") ? "Noun" : pos.startsWith("V") ? "Verb" : "Other";

                for(Character c : word.toCharArray()){//Checks if word contains a numbers!
                    if(Character.isDigit(c)){
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

                if(tokens.size()-2 >= i){
                    nextPos = tokens.get(i + 1).get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    nextPos = nextPos.startsWith("J") ? "Adjective" : nextPos.startsWith("N") ? "Noun" : nextPos.startsWith("V") ? "Verb" : "Other";
                }
                else {;
                    nextPos = "Empty";
                }

                boolean contextuallyDescriptive = (prevWordPOS.equals("Adjective") && pos.equals("Noun"))  || (pos.equals("Adjective") && nextPos.equals("Adjective"));
                if(contextuallyDescriptive){
                    isContextuallyDescriptive = "True";
                }
                //TODO:Add more Attrubutes for training, MAKE SURE SAID ATTRIBUTE CAN BE FOUND DYNAMICALLY (Paste this [ "," + ATTRIBUTE + ] after last comma)
                writer.write("\"" + lemma + "\"," + pos + "," + word.length() + "," + containsDigit + "," + prevWordPOS + "," + nextPos + "," + isContextuallyDescriptive + "," + Suffix + ",\n");  //Label

                prevWordPOS = pos;
                i++;
            }

            writer.close();
            System.out.println("ARFF file generated. You can now label data in Weka.");
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void trainModel(){
        try {
            //Creating and Training a New Model in code (Can be a new Class)
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("wine_descriptors.arff");//Loads the Arff file, throws expection if file not found
            Instances data = source.getDataSet(); //gets the Data from under the @Data tag

            Remove remove = new Remove();
            remove.setAttributeIndices("1");  // Weka uses 1-based indexing!
            remove.setInputFormat(data);

            Instances filteredData = Filter.useFilter(data, remove);

            filteredData.setClassIndex(filteredData.numAttributes() - 1);// Sets the class attribute as the last attribute


            Classifier classifier = new J48();  // Decision tree, This throws and error since its protected class.
            classifier.buildClassifier(filteredData);

            weka.core.SerializationHelper.write("wine_model_DELETE.model", classifier);
            System.out.println("Model trained and saved!");
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    ArrayList<Attribute> attributes = new ArrayList<>();//Creates an array of currently blank attributes
    List<String> categories = Arrays.asList("Adjective", "Noun", "Verb", "Other");//Adds
        attributes.add(new Attribute("pos", categories));//adds to array
    List<String> isKeyWord = Arrays.asList("True", "False");//adds
        attributes.add(new Attribute("keyWord", isKeyWord));//adds to array

    Instances dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(1); //Sets the Attribute that we want to guess

    // Add instance
        for (String word : type.split(" ")) {
        DenseInstance instance = new DenseInstance(2);
        instance.setValue(attributes.get(0), word);
        dataRaw.add(instance);
    }
        return dataRaw;
}

public void predictData(Instances dataRaw){
    try {
        //Using the model to classify new data(Can be its own Class)
        Classifier wineClassfier = (Classifier) weka.core.SerializationHelper.read("wine_model.model");


        // Predict
        for (final Instance instance : dataRaw) {
            double result = wineClassfier.classifyInstance(instance);
            System.out.println(dataRaw.classAttribute().value((int) result));
        }
    }
    catch (Exception e){
        System.out.println("Error: " + e.getMessage());
    }
}


}


}
