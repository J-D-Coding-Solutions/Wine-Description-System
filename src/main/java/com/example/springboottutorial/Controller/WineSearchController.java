package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*; // Restoring original commented state
import com.example.springboottutorial.Controller.*; // Restoring original commented state
import edu.stanford.nlp.ling.CoreAnnotations; // Needed for NER tag access
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
// No other Stanford imports needed here
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort; // Keep original import
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // Keep original import
import org.springframework.ui.Model; // Keep original import
import org.springframework.web.bind.annotation.*;
import com.example.springboottutorial.Controller.DTO.WineSearchRequest;

// import java.time.format.SignStyle; // Restoring original commented state
import java.util.*;

@RestController // Keeping @RestController as per original code provided last time
@RequestMapping("/api/Wine-Description")
public class WineSearchController {

    private final WineRepository wineRepository;
    private final NLPController nlpController; // Keep final and constructor injection style

    @Autowired
    public WineSearchController(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
        // Keep direct instantiation as in original code
        this.nlpController = new NLPController();
    }

    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest search, Sort sort) { // Keep Sort param as in original
        long startTime = System.nanoTime();
        String userSearch = search.getCombinedInput(); // Updated getter
        System.out.println("It Works, Calculating now..." + userSearch);

        double metaweight = 1.0; // Weight for metadata
        double normweight = 0.5; // Weight for description keywords

        // Use the instance variable 'this.nlpController'
        // NLPController NLPController = new NLPController(); // This line was removed as it shadowed the instance variable

        // --- Process User Input ---
        // 1. Get user tokens (includes NER info)
        List<CoreLabel> userTokens = this.nlpController.NLP(userSearch);

        // 2. **ESSENTIAL CHANGE**: Extract User Metadata Terms directly here using NER results from userTokens
        // This block is required for the weighted similarity logic.
        List<String> UserMetaTerms = new ArrayList<>();
        Set<String> metadataEntityTypes = new HashSet<>(Arrays.asList(
                "COUNTRY", "PROVINCE", "WINERY", "VARIETY", "WINE" // Use exact NER tags from your .rules files
        ));
        String currentEntity = null;
        String currentEntityType = null;
        for (CoreLabel token : userTokens) {
            String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            if (ner != null && metadataEntityTypes.contains(ner)) {
                if (ner.equals(currentEntityType)) {
                    // Append word to the current entity if it's multi-word
                    currentEntity += " " + token.word();
                } else {
                    // Finalize the previous entity if one was being built
                    if (currentEntity != null) {
                        UserMetaTerms.add(currentEntity);
                    }
                    // Start a new entity
                    currentEntity = token.word();
                    currentEntityType = ner;
                }
            } else {
                // If the tag changes or becomes null/O, finalize the previous entity
                if (currentEntity != null) {
                    UserMetaTerms.add(currentEntity);
                }
                currentEntity = null;
                currentEntityType = null;
            }
        }
        // Add the last entity if the loop finished while building one
        if (currentEntity != null) {
            UserMetaTerms.add(currentEntity);
        }
        System.out.println("Extracted User Metadata Terms: " + UserMetaTerms); // Keep this debug output


        // 3. Get user keyword features (No changes to this call)
        List<String> userKeywordFeatures = this.nlpController.keyWordList(userTokens);

        // 4. Predict user keywords (No changes to this call)
        List<String> UserKeyWords = this.nlpController.predictKeywords(userKeywordFeatures); // Keep variable name UserKeyWords
        System.out.println("User Keywords: " + UserKeyWords); // Keep original debug output label


        // --- Compare with Wines ---
        List<wines> winelist = wineRepository.findAll(); // Keep original fetch logic

        for(wines wine: winelist){
            // System.out.println(wine.getWineName()); // Keep original commented state

            String wineDesc = wine.getWineDesc();

            // Use variable name 'tempKeyWords' as in the VERY first code block provided by user
            List<String> tempKeyWords; // Declare here

            if (wineDesc != null && !wineDesc.trim().isEmpty()) {
                // Process wine description only if not empty
                List<CoreLabel> wineTokens = this.nlpController.NLP(wineDesc); // Use this.nlpController
                List<String> wineKeywordFeatures = this.nlpController.keyWordList(wineTokens); // Use this.nlpController
                tempKeyWords = this.nlpController.predictKeywords(wineKeywordFeatures); // Assign predicted keywords to tempKeyWords
            } else {
                // If description is empty, use an empty list for keywords (necessary to avoid errors)
                System.out.println("Skipping wine due to missing/empty description: " + wine.getWineName());
                tempKeyWords = new ArrayList<>();
            }


            // Prepare Wine Metadata list (handle nulls/empty - necessary for function call)
            List<String> WineMetaTerms = new ArrayList<>();
            if (wine.getCountry() != null && !wine.getCountry().isEmpty()) WineMetaTerms.add(wine.getCountry());
            if (wine.getProvince() != null && !wine.getProvince().isEmpty()) WineMetaTerms.add(wine.getProvince());
            if (wine.getWinery() != null && !wine.getWinery().isEmpty()) WineMetaTerms.add(wine.getWinery());
            // Add other relevant terms if needed by your metadata definition for similarity
            if (wine.getVariety() != null && !wine.getVariety().isEmpty()) WineMetaTerms.add(wine.getVariety());
            if (wine.getWineName() != null && !wine.getWineName().isEmpty()) WineMetaTerms.add(wine.getWineName());

            // --- Restore original debug print statements ---
            // This block exactly matches the debug prints from the user's *first* code listing
            for(String word : tempKeyWords){ // Loop as in original debug block
                System.out.println(word); // Print each word as in original debug block

                // The following prints were inside the inner 'word' loop in the original code
//                System.out.println(">>> Debug Inputs for Wine: " + wine.getWineName());
//                System.out.println("    User Meta Terms (Arg 1): " + UserMetaTerms); // Original had no Arg # label, adding for clarity based on call
//                System.out.println("    User Combined Keywords (Arg 2): " + UserKeyWords); // Label from original debug block
//                System.out.println("    Wine Meta Terms (Arg 3): " + WineMetaTerms);        // Original label and content
//                System.out.println("    Wine Description Keywords (Arg 4): " + tempKeyWords); // Original label and content
//                System.out.println("    Metadata Weight (Arg 5): " + metaweight);
//                System.out.println("    Description Weight (Arg 6): " + normweight);
            }
            // --- End of restored original debug print block ---


            // --- **ESSENTIAL CHANGE**: Corrected Argument Order for weightedCosineSimilarity ---
            double tempSim = this.nlpController.weightedCosineSimilarity(
                    UserMetaTerms,      // metadata1 (User Meta)
                    WineMetaTerms,      // metadata2 (Wine Meta)
                    UserKeyWords,       // description1 (User Keywords)
                    tempKeyWords,       // description2 (Wine Keywords) - Use original variable name 'tempKeyWords'
                    metaweight,
                    normweight
            );
            // System.out.println("<<< Debug Raw Similarity Result for " + wine.getWineName() + ": " + tempSim); // Keep original commented state

            // Restore original handling (NO explicit NaN check)
            // The original code directly assigned, assuming setcoSim handles potential NaN if needed, or it wasn't handled.
            // Reverting to direct assignment.
            wine.setcoSim(tempSim);
            // This print was outside the NaN check in the original
            System.out.println(wine.getWineName() + " " + tempSim);


        } // End of wine loop

        // Restore original filtering logic
        winelist.removeIf(wine -> wine.getcoSim() < 0.1);

        // Restore original sorting logic
        winelist.sort(Comparator.comparingDouble(wines::getcoSim).reversed());

        // Restore original final print loop
        for(wines wine: winelist){
            System.out.println(wine.getWineName() + " " + wine.getcoSim());
        }

        // Restore original JSON generation call
        String jsonObj = this.nlpController.jsonObj(winelist); // Use this.nlpController

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // in nanoseconds

        // Restore original timing output
        System.out.println("Method execution time: " + duration + " nanoseconds");
        System.out.println("Method execution time: " + duration / 1_000_000 + " milliseconds");
        return ResponseEntity.ok(jsonObj);
    }
}