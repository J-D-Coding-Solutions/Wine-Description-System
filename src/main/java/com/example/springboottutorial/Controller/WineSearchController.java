package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.wines;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.example.springboottutorial.Controller.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springboottutorial.Controller.DTO.WineSearchRequest;

import java.time.format.SignStyle;
import java.util.*;

@RestController

@RequestMapping("/api/Wine-Description")
public class WineSearchController {



    private final WineRepository wineRepository;
    private final arffRepository arffRepository;
    private final NLPController nlpService;


    public WineSearchController(WineRepository wineRepository, arffRepository arffRepository, NLPController nlpService) {
        this.wineRepository = wineRepository;
        this.arffRepository = arffRepository;
        this.nlpService = nlpService;
    }

    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest search, Sort sort) {
        long startTime = System.nanoTime();
        String userSearch = search.getCombinedInput(); // Updated getter
        System.out.println("It Works, Calculating now..." + userSearch);

        ModelController modelController = new ModelController(arffRepository);

        //Debug thing
        List<String> userCleanedWords = nlpService.keyWordList(nlpService.NLP(userSearch));
        System.out.println(userCleanedWords);
        modelController.addArff(userCleanedWords, modelController.predictKeywordType(userCleanedWords), modelController.predictWeights(userCleanedWords));


        List<NLPController.predictValues> userKeyWords = nlpService.predictKeywords(userCleanedWords);


        List<wines> winelist = wineRepository.findAll();

        for(wines wine: winelist){
            System.out.println(wine.getWineName());
            List<String> tempKeyWords = nlpService.keyWordList(nlpService.NLP(wine.getWineDesc() + " " + wine.getCountry() + " "));
            List<NLPController.predictValues> tempPrediction = nlpService.predictKeywords(tempKeyWords);
            for(NLPController.predictValues word : tempPrediction){
                System.out.println(word.keyWord);
            }
            double tempSim = nlpService.cosineSimilarity(userKeyWords, tempPrediction);
            if(!Double.isNaN(tempSim)) {
                System.out.println(wine.getWineName() + " " + tempSim);
                wine.setcoSim(tempSim);
            }
        }
        winelist.removeIf(wine -> wine.getcoSim() < 0.1);

        winelist.sort(Comparator.comparingDouble(wines::getcoSim).reversed());

        for(wines wine: winelist){
            System.out.println(wine.getWineName() + " " + wine.getcoSim());
        }

        String jsonObj = nlpService.jsonObj(winelist);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // in nanoseconds

        System.out.println("Method execution time: " + duration + " nanoseconds");
        System.out.println("Method execution time: " + duration / 1_000_000 + " milliseconds");
        System.out.println(jsonObj);
        return ResponseEntity.ok(jsonObj);
    }
}