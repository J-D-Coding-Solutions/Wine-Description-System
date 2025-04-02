package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import com.example.springboottutorial.Controller.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.springboottutorial.Controller.DTO.WineSearchRequest;

import java.util.*;

@RestController
@RequestMapping("/api/Wine-Description")
public class WineSearchController {

    private final WineRepository wineRepository;

    public WineSearchController(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    /*class SortByValue implements Comparator<wines> {
        @Override
        public double compare(wines a, wines b){
            return Integer.compare(a.getcoSim(), b.getcoSim());
        }
    }*/


    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest search) {
        long startTime = System.nanoTime();
        String userSearch = search.getCombinedInput(); // Updated getter
        System.out.println("It Works, Calculating now..." + userSearch);

        NLPController NLPController = new NLPController();
        List<CoreLabel> userKeyWord = NLPController.NLP(UserSearch);

        List<wines> winelist = wineRepository.findAll();
        for(wines wine: winelist){
           List<CoreLabel> tempKeyword = NLPController.NLP(wine.getWineDesc());
           double tempSim = NLPController.cosineSimilarity(userKeyWord, tempKeyword);
           if(!Double.isNaN(tempSim)){
               wine.setcoSim(tempSim);
           }
        }
        for(wines wine: winelist){
           if(!(wine.getcoSim() == 0.0)){
               System.out.println(wine.getWineName() + " " + wine.getcoSim());
           }
        }


        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // in nanoseconds

        System.out.println("Method execution time: " + duration + " nanoseconds");
        System.out.println("Method execution time: " + duration / 1_000_000 + " milliseconds");
        return ResponseEntity.ok("It Works!!! " + UserSearch);
    }
}
