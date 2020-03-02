package org.grammaticalframework.ViewModel;

public class LexiconWord {
    private String word;
    private String explanation;

    public LexiconWord(String word, String explanation) {
        this.word = word;
        this.explanation = explanation;
    }

    public String getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }
}
