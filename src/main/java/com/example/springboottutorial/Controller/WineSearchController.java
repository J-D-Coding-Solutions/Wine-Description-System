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

    public WineSearchController(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }




    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest search, Sort sort) {
        long startTime = System.nanoTime();
        String userSearch = search.getCombinedInput(); // Updated getter
        System.out.println("It Works, Calculating now..." + userSearch);

        NLPController NLPController = new NLPController();

        //Debug thing
       List<String> userKeyWords = NLPController.keyWordList(NLPController.NLP(userSearch));

       userKeyWords = NLPController.predictKeywords(userKeyWords);
        System.out.println("User Keywords: " + userKeyWords);
        List<wines> winelist = wineRepository.findAll();

        for(wines wine: winelist){
            System.out.println(wine.getWineName());
           List<String> tempKeyWords = NLPController.keyWordList(NLPController.NLP(wine.getWineDesc() + " " + wine.getCountry() + " "));
           tempKeyWords = NLPController.predictKeywords(tempKeyWords);
           for(String word : tempKeyWords){
               System.out.println(word);
           }
           double tempSim = NLPController.cosineSimilarity(userKeyWords, tempKeyWords);
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

        String jsonObj = NLPController.jsonObj(winelist);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // in nanoseconds

        System.out.println("Method execution time: " + duration + " nanoseconds");
        System.out.println("Method execution time: " + duration / 1_000_000 + " milliseconds");
        return ResponseEntity.ok(jsonObj);
    }
}