package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.wines;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import weka.core.SerializationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NLPControllerTest {

    @Autowired
    private StanfordCoreNLP pipeline; // Mocking StanfordCoreNLP class


    private NLPController nlpController; // Inject mock into the controller

    @BeforeEach
    public void setup() {
        nlpController = new NLPController(pipeline);
    }

    @Test
    public void testNLP() {
        String inputText = "This is a delicious berry-flavored wine.";
        List<CoreLabel> tokens = nlpController.NLP(inputText);

        assertFalse(tokens.isEmpty());
        assertEquals("This", tokens.get(0).word());
        assertNotNull(tokens.get(0).lemma());
        System.out.println("NLP Test Successful!");
        System.out.println("Word: " + tokens.get(0).word());
        System.out.println("Lemma: " + tokens.get(0).lemma());
        System.out.println("Part of Speech: " + tokens.get(0).get(CoreAnnotations.PartOfSpeechAnnotation.class));
    }

    @Test
    public void testKeyWordList() {
        // Mock a list of CoreLabel tokens
        CoreLabel token1 = new CoreLabel();
        token1.setWord("dark");
        token1.setLemma("dark");
        token1.set(CoreAnnotations.PartOfSpeechAnnotation.class, "JJ"); // Adjective

        CoreLabel token2 = new CoreLabel();
        token2.setWord("berries");
        token2.setLemma("berry");
        token2.set(CoreAnnotations.PartOfSpeechAnnotation.class, "NN"); // Noun

        List<CoreLabel> tokens = new ArrayList<>();
        tokens.add(token1);
        tokens.add(token2);

        // Call the method under test
        List<String> result = nlpController.keyWordList(tokens);

        // Assert the results are as expected
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertTrue(result.get(0).contains("dark"));
        assertTrue(result.get(1).contains("berry"));
        System.out.println("KeyWordList Test Successful!");
        System.out.println("Result 1: " + result.get(0));
        System.out.println("Result 2: " + result.get(1));
        System.out.println("Number of Results: " + result.size());
    }

    @Test
    public void testPredictKeywords() {
        // Prepare the input
        List<String> keyWords = List.of("dark,Adjective,4,False,Empty,Noun,False,False",
                "berries,Noun,7,False,Adjective,Empty,True,berry");

        // Mock the classifier and prediction behavior
        // Here you should mock Weka model or its behavior for predictKeywords

        // Call the method under test
        List<NLPController.predictValues> result = nlpController.predictKeywords(keyWords);

        // Assert that predictions are returned
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("dark", result.get(0).keyWord);
        assertEquals(0.5, result.get(0).weight, 1); // Assuming weight returned is 0.5
        System.out.println("PredictKeywords Test Successful!");
        System.out.println("Word: " + result.get(0).keyWord);
        System.out.println("Is Keyword?: True");
        System.out.println("Weight: " + result.get(0).weight);
    }


    @Test
    public void testPredictKeywords_ExceptionHandling() throws Exception {
        List<String> keyWords = List.of("delicious,Adjective,9,False,Empty,Adjective,True,ic,?");

        // Mocking the SerializationHelper.read method to throw an exception
        mockStatic(SerializationHelper.class);
        when(SerializationHelper.read(anyString())).thenThrow(new RuntimeException("Model not found!: "));


        // Call the method and expect a runtime exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            nlpController.predictKeywords(keyWords);
        });
        System.out.println("PredictKeywords Exception Test Successful!");
        System.out.println("Model not Found: " + exception.getMessage());
        // Ensure the exception message matches the expected message
        assertEquals("Model not found!: ", exception.getMessage());
        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("Model not found!: ", exception.getCause().getMessage());
    }


    @Test
    public void testCosineSimilarity() {
        // Prepare mock data for cosine similarity
        List<NLPController.predictValues> text1 = new ArrayList<>();
        text1.add(new NLPController.predictValues("dark", 0.7));
        text1.add(new NLPController.predictValues("berries", 0.8));

        List<NLPController.predictValues> text2 = new ArrayList<>();
        text2.add(new NLPController.predictValues("dark", 0.7));
        text2.add(new NLPController.predictValues("fruit", 0.9));

        // Call the cosineSimilarity method
        double similarity = nlpController.cosineSimilarity(text1, text2);

        // Assert that the similarity score is within a reasonable range
        assertTrue(similarity >= 0.0 && similarity <= 1.0);
        System.out.println("Cosine Sim Test Successful!");
        System.out.println("Cosine Sim: " + similarity);

    }

    @Test
    public void testJsonObj() {
        // Create a list of wines for testing the jsonObj method
        wines wine1 = new wines();
        wine1.setWineName("Test Wine");
        wine1.setWinery("Test Winery");
        wine1.setCountry("Test Country");
        wine1.setProvince("Test Province");

        List<wines> winelist = new ArrayList<>();
        winelist.add(wine1);

        // Call the jsonObj method
        String result = nlpController.jsonObj(winelist);

        // Validate the JSON string
        assertNotNull(result);
        assertTrue(result.contains("Test Wine"));
        assertTrue(result.contains("Test Winery"));
        assertTrue(result.contains("Test Country"));
        assertTrue(result.contains("Test Province"));
        System.out.println("JsonObj Test Successful!");
        System.out.println("JSON Object: " + result);
    }
}
