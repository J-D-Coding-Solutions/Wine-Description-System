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
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api/Wine-Description")
public class WineSearchController {

    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest Search) {
        long startTime = System.nanoTime();
        String UserSearch = Search.getDescription();

        NLPController NLPController = new NLPController();
        List<CoreLabel> userKeyWord = NLPController.NLP(UserSearch);

        List<List> somilierWines = new ArrayList<List>();


        List<CoreLabel> keyWords = NLPController.NLP("The wine had hints of different fruits such as fig and blackberry. It was sweet with flavors of chocolate and hints of vanilla. The flavors were layered and intense.");
        List<CoreLabel> keyWords2 = NLPController.NLP("This tremendous 100% varietal wine hails from Oakville and was aged over three years in oak. Juicy red-cherry fruit and a compelling hint of caramel greet the palate, framed by elegant, fine tannins and a subtle minty tone in the background. Balanced and rewarding from start to finish, it has years ahead of it to develop further nuance.");
        List<CoreLabel> keyWords3 = NLPController.NLP("The producer sources from two blocks of the vineyard for this wine is one at a high elevation, which contributes bright acidity. Crunchy cranberry, pomegranate and orange peel flavors surround silky, succulent layers of texture that present as fleshy fruit. That delicately lush flavor has considerable length.");
        List<CoreLabel> keyWords4 = NLPController.NLP("From 18-year-old vines, this supple well-balanced effort blends flavors of mocha, cherry, vanilla and breakfast tea. Superbly integrated and delicious even at this early stage, this wine seems destined for a long and savory cellar life.");

        somilierWines.add(keyWords);
        somilierWines.add(keyWords2);
        somilierWines.add(keyWords3);
        somilierWines.add(keyWords4);

        for(int i = 0; i < somilierWines.size(); i++) {
            System.out.println(NLPController.cosineSimilarity(userKeyWord, somilierWines.get(i)));
        }


        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // in nanoseconds

        System.out.println("Method execution time: " + duration + " nanoseconds");
        System.out.println("Method execution time: " + duration / 1_000_000 + " milliseconds");
        return ResponseEntity.ok("It Works!!! " + UserSearch);
    }
}
