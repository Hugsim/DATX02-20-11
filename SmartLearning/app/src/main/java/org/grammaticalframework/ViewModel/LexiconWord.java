package org.grammaticalframework.ViewModel;

import java.io.Serializable;
import java.util.List;

public class LexiconWord implements Serializable {
    private String lemma;
    private String word;
    private String explanation;
    private String function;
    private String tag;
    private String synonymCode;
    private String synonymWords;
    //The status of the linearization, i.e. if it has been checked or is guessed
    private String status = null;
    //The language code for the word
    private String langcode = null;

    public LexiconWord(String lemma, String word, String explanation, String tag, String function, String synonymCode, String synonymWords) {
        this.lemma = lemma;
        this.word = word;
        this.explanation = explanation;
        this.function = function;
        this.tag = tag;
        this.synonymCode = synonymCode;
        this.synonymWords = synonymWords;
    }

    public String getLemma() {return lemma; }

    public String getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getFunction() { return function; }

    public String getTag(){
        return tag;
    }

    public String getSynonymCode(){
        return synonymCode;
    }

    public void setSynonymCode(String synonyms){
        this.synonymCode = synonyms;
    }
    
    public void setSynonymWords(String synonymWords){this.synonymWords = synonymWords;}

    public String getSynonymWords() {return synonymWords;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }
}
