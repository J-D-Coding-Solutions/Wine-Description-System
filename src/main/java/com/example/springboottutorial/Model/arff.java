package com.example.springboottutorial.Model;

/**
 * arff.java
 * This class represents the structure of the ARFF (Attribute-Relation File Format) file.
 */

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;

@Entity
@Table( name = "aarf")
public class arff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aarf_id;

    private String word;
    private String pos;
    private Integer wordLength;
    private String containsDigit;
    private String prevWordPOS;
    private String nextWordPOS;
    private String isContextuallyDescriptive;
    private String Suffix;
    private String keyWord;
    private Double wordWeight;


    public arff(){}

    public arff (String word, String pos, Integer wordLength,
                  String containsDigit, String prevwordPOS, String nextWordPOS,
                  String isContextuallyDescriptive, String suffix, String keyWord, Double wordWeight){
        this.word = word;
        this.pos = pos;
        this.wordLength = wordLength;
        this.containsDigit = containsDigit;
        this.prevWordPOS = prevwordPOS;
        this.nextWordPOS = nextWordPOS;
        this.isContextuallyDescriptive = isContextuallyDescriptive;
        this.Suffix = suffix;
        this.keyWord = keyWord;
        this.wordWeight = wordWeight;

    }

    public Long getAarf_id() {return aarf_id;}
    public String getWord() {return word;}
    public String getPos() {return pos;}
    public Integer getWordLength() {return wordLength;}
    public String getContainsDigit() {return containsDigit;}
    public String getPrevWordPOS() {return prevWordPOS;}
    public String getNextWordPOS() {return nextWordPOS;}
    public String getIsContextuallyDescriptive() {return isContextuallyDescriptive;}
    public String getSuffix() {return Suffix;}
    public String getKeyWord() {return keyWord;}
    public Double getWordWeight() {return wordWeight;}

    public void setAarf_id(Long aarf_id) {this.aarf_id = aarf_id;}
    public void setWord(String word) {this.word = word;}
    public void setPos(String pos) {this.pos = pos;}
    public void setWordLength(Integer wordLength) {this.wordLength = wordLength;}
    public void setContainsDigit(String containsDigit) {this.containsDigit = containsDigit;}
    public void setPrevWordPOS(String prevWordPOS) {this.prevWordPOS = prevWordPOS;}
    public void setNextWordPOS(String nextWordPOS) {this.nextWordPOS = nextWordPOS;}
    public void setIsContextuallyDescriptive(String isContextuallyDescriptive) {this.isContextuallyDescriptive = isContextuallyDescriptive;}
    public void setSuffix(String Suffix) {this.Suffix = Suffix;}
    public void setKeyWord(String keyWord) {this.keyWord = keyWord;}
    public void setWordWeight(Double wordWeight) {this.wordWeight = wordWeight;}
}
