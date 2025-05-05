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
        this.nlpController = new NLPController();
    }

    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest search, Sort sort) { // Keep Sort param as in original
        long startTime = System.nanoTime();
        String userSearch = search.getCombinedInput(); // Updated getter
        System.out.println("It Works, Calculating now..." + userSearch);

        double metaweight = 1.0; // Weight for metadata
        double normweight = 0.5; // Weight for description keywords



        List<CoreLabel> userTokens = this.nlpController.NLP(userSearch);


        List<String> UserMetaTerms = new ArrayList<>();
        Set<String> metadataEntityTypes = new HashSet<>(Arrays.asList(
                "COUNTRY", "PROVINCE", "WINERY", "VARIETY", "WINE"
        ));
        String currentEntity = null;
        String currentEntityType = null;
        for (CoreLabel token : userTokens) {
            String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            if (ner != null && metadataEntityTypes.contains(ner)) {
                if (ner.equals(currentEntityType)) {
                    currentEntity += " " + token.word();
                } else {
                    if (currentEntity != null) {
                        UserMetaTerms.add(currentEntity);
                    }
                    currentEntity = token.word();
                    currentEntityType = ner;
                }
            } else {
                if (currentEntity != null) {
                    UserMetaTerms.add(currentEntity);
                }
                currentEntity = null;
                currentEntityType = null;
            }
        }
        if (currentEntity != null) {
            UserMetaTerms.add(currentEntity);
        }
        System.out.println("Extracted User Metadata Terms: " + UserMetaTerms); // Keep this debug output


        List<String> userKeywordFeatures = this.nlpController.keyWordList(userTokens);

        List<String> UserKeyWords = this.nlpController.predictKeywords(userKeywordFeatures); // Keep variable name UserKeyWords
        System.out.println("User Keywords: " + UserKeyWords); // Keep original debug output label


        List<wines> winelist = wineRepository.findAll(); // Keep original fetch logic

        for(wines wine: winelist){

            String wineDesc = wine.getWineDesc();

            List<String> tempKeyWords;

            if (wineDesc != null && !wineDesc.trim().isEmpty()) {
                List<CoreLabel> wineTokens = this.nlpController.NLP(wineDesc);
                List<String> wineKeywordFeatures = this.nlpController.keyWordList(wineTokens);
                tempKeyWords = this.nlpController.predictKeywords(wineKeywordFeatures);
            } else {
                System.out.println("Skipping wine due to missing/empty description: " + wine.getWineName());
                tempKeyWords = new ArrayList<>();
            }


            // Prepare Wine Metadata list (handle nulls/empty - necessary for function call)
            List<String> WineMetaTerms = new ArrayList<>();
            if (wine.getCountry() != null && !wine.getCountry().isEmpty()) WineMetaTerms.add(wine.getCountry());
            if (wine.getProvince() != null && !wine.getProvince().isEmpty()) WineMetaTerms.add(wine.getProvince());
            if (wine.getWinery() != null && !wine.getWinery().isEmpty()) WineMetaTerms.add(wine.getWinery());
            if (wine.getVariety() != null && !wine.getVariety().isEmpty()) WineMetaTerms.add(wine.getVariety());
            if (wine.getWineName() != null && !wine.getWineName().isEmpty()) WineMetaTerms.add(wine.getWineName());


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


            double tempSim = this.nlpController.weightedCosineSimilarity(
                    UserMetaTerms,
                    WineMetaTerms,
                    UserKeyWords,
                    tempKeyWords,
                    metaweight,
                    normweight
            );


            wine.setcoSim(tempSim);
            System.out.println(wine.getWineName() + " " + tempSim);


        } // End of wine loop

        winelist.removeIf(wine -> wine.getcoSim() < 0.15);

        winelist.sort(Comparator.comparingDouble(wines::getcoSim).reversed());

        for(wines wine: winelist){
            System.out.println(wine.getWineName() + " " + wine.getcoSim());
        }

        String jsonObj = this.nlpController.jsonObj(winelist); // Use this.nlpController

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        // Restore original timing output
        System.out.println("Method execution time: " + duration + " nanoseconds");
        System.out.println("Method execution time: " + duration / 1_000_000 + " milliseconds");
        return ResponseEntity.ok(jsonObj);
    }
}