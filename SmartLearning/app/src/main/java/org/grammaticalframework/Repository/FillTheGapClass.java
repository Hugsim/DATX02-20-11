package org.grammaticalframework;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.List;

public class FillTheGapClass {
    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    private String sentence;

    public String getRedactedWord() {
        return redactedWord;
    }

    public void setRedactedWord(String redactedWord) {
        this.redactedWord = redactedWord;
    }

    private String redactedWord;

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    private Expr expr;

    public Object[] getBs() {
        return bs;
    }

    public void setBs(Object[] bs) {
        this.bs = bs;
    }

    private Object[] bs;

    public List<String> getInflections() {
        return inflections;
    }

    public void setInflections(List<String> inflections) {
        this.inflections = inflections;
    }

    private List<String> inflections = new ArrayList<>();

    public List<String> getTargetSentence() {
        return targetSentence;
    }

    public void setTargetSentence(List<String> targetSentence) {
        this.targetSentence = targetSentence;
    }

    private List<String> targetSentence = new ArrayList<>();



    public FillTheGapClass(String sentence){
        this.sentence = sentence;
    }

}
