package org.grammaticalframework.ViewModel;

public class LexiconWord {
    private String lemma;
    private String word;
    private String explanation;
    private String function;
    private String tag;

    public LexiconWord(String lemma, String word, String explanation, String tag) {
        this.lemma = lemma;
        this.word = word;
        this.explanation = explanation;
        this.function = function;
        this.tag = tag;
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

}
