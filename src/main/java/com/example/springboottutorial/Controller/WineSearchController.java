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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api/Wine-Description")
public class WineSearchController {

    private final WineRepository wineRepository;

    public WineSearchController(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    public static class wineList{
        String name;
        double coSim;

    }


    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest Search) {
        long startTime = System.nanoTime();
        String UserSearch = Search.getDescription();

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
