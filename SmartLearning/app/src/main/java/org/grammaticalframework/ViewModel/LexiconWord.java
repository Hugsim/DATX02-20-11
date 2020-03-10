package org.grammaticalframework.ViewModel;

public class LexiconWord {
    private String lemma;
    private String word;
    private String explanation;
    private String tag;

    public LexiconWord(String lemma, String word, String explanation, String tag) {
        this.lemma = lemma;
        this.word = word;
        this.explanation = explanation;
        this.tag = tag;
    }

    public String getLemma() {return lemma; }

    public String getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTag(){
        return tag;
    }

}
