package org.grammaticalframework.ViewModel;

public class LexiconWord {
    private String lemma;
    private String word;
    private String explanation;

    public LexiconWord(String lemma, String word, String explanation) {
        this.lemma = lemma;
        this.word = word;
        this.explanation = explanation;
    }

    public String getLemma() {return lemma; }

    public String getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }
}
