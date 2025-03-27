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

import java.util.Properties;

@RestController
@RequestMapping("/api/Wine-Description")
public class WineSearchController {

    @PostMapping
    public ResponseEntity<String> SearchWine(@RequestBody WineSearchRequest Search) {
        String UserSearch = Search.getDescription();

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma,ner");

        props.setProperty("ner.fine.regexner.mapping", "src/main/resources/Wines.rules");
        props.setProperty("ner.fine.regexner.ignorecase", "true");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // make an example document
        CoreDocument doc = new CoreDocument(UserSearch);
        // annotate the document
        pipeline.annotate(doc);
        // view results
        System.out.println("---");
        System.out.println("entities found");
        for (CoreEntityMention em : doc.entityMentions())
            System.out.println("\tdetected entity: \t"+em.text()+"\t"+em.entityType());
        System.out.println("---");
        System.out.println("tokens and ner tags");

        for (CoreLabel token : doc.tokens()) {
            if (!token.ner().equals("O")) {
                String tokenAndNERTags = "(" + token.word() + ", " + token.ner() + ")";
                System.out.println(tokenAndNERTags);

            }
        }
        return ResponseEntity.ok("It Works!!! " + UserSearch);
    }
}
