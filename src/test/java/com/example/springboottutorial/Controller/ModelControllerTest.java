package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.arff;
import com.example.springboottutorial.Repository.arffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModelControllerTest {

    private arffRepository arffRepository;
    private ModelController modelController;

    @BeforeEach
    void setUp() {
        arffRepository = mock(arffRepository.class);
        modelController = new ModelController(arffRepository);
    }

    @Test
    void testCreateArff() throws Exception {
        arff sample = new arff("fruity", "Adjective", 6, "False", "Noun", "Empty", "True", "y", "True", 0.8);
        when(arffRepository.findAll()).thenReturn(List.of(sample));

        modelController.createArff();

        File tmpFile = new File(System.getProperty("java.io.tmpdir"), "wine_descriptors.arff");
        assertTrue(tmpFile.exists());
        String content = Files.readString(tmpFile.toPath());

        assertTrue(content.contains("@relation wine_descriptors"));
        assertTrue(content.contains("fruity"));
        assertTrue(content.contains("Adjective"));
        assertTrue(content.contains("True")); // keyword
        tmpFile.delete();
    }

    @Test
    void testTrainModelKeywordException() throws Exception {

            // Create a ModelController and set a temp file that doesn't exist
            ModelController modelController = new ModelController(arffRepository);

            // Use reflection to set the private tempFile field (since it's not accessible)
            File badFile = new File("nonexistent.arff");  // File that doesn't exist
            java.lang.reflect.Field field = ModelController.class.getDeclaredField("tempFile");
            field.setAccessible(true);
            field.set(modelController, badFile);

            // Run the method
            modelController.trainModel();

            // No exception should propagate; check logs manually or use logger spy if needed
            assertTrue(true); // if we reach here, test passed
        }

    @Test
    void testTrainModelWeightException() throws Exception {
        ModelController modelController = new ModelController(arffRepository);

        // Create a temp file that exists but has invalid ARFF content
        File invalidArff = File.createTempFile("invalid", ".arff");
        java.nio.file.Files.writeString(invalidArff.toPath(), "invalid content");

        // Inject the temp file
        java.lang.reflect.Field field = ModelController.class.getDeclaredField("tempFile");
        field.setAccessible(true);
        field.set(modelController, invalidArff);

        // Run trainModel
        modelController.trainModel();

        // Clean up
        invalidArff.delete();

        // If no exception propagated, test is successful
        assertTrue(true);
    }


    @Test
    void testAddArff() {
        List<String> userKeyWords = List.of("\"fruity\",Adjective,6,False,Noun,Empty,True,y");
        List<String> predictedKeyWords = List.of("True");
        List<Double> predictedWeights = List.of(0.75);

        modelController.addArff(userKeyWords, predictedKeyWords, predictedWeights);

        ArgumentCaptor<arff> captor = ArgumentCaptor.forClass(arff.class);
        verify(arffRepository, times(1)).save(captor.capture());

        arff saved = captor.getValue();
        assertEquals("fruity", saved.getWord());
        assertEquals("Adjective", saved.getPos());
        assertEquals(6, saved.getWordLength());
        assertEquals("True", saved.getKeyWord());
        assertEquals(0.75, saved.getWordWeight());

        System.out.println("AddArff Test Successful!");
        System.out.println("Word: " + saved.getWord());
        System.out.println("Pos: " + saved.getPos());
        System.out.println("Word Length: " + saved.getWordLength());
        System.out.println("Key Word: " + saved.getKeyWord());
        System.out.println("Word Weight: " + saved.getWordWeight());
    }
}