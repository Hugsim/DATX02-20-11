package org.grammaticalframework.ViewModel;

public class LexiconWord {
    private String word;
    private String explanation;
    private String function;


    public LexiconWord(String word, String explanation, String function) {
        this.word = word;
        this.explanation = explanation;
        this.function = function;
    }

    public String getWord() {
        return word;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getFunction() { return function; }
}
