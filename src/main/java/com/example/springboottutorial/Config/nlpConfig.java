package com.example.springboottutorial.Config;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
public class nlpConfig {
    @Bean
    public StanfordCoreNLP stanfordCoreNLP() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma,ner");
        props.setProperty("ner.model", "");

        props.setProperty("ner.fine.regexner.ignorecase", "true");

        return new StanfordCoreNLP(props);
        }

}
