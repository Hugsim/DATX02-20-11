package org.grammaticalframework;

import android.util.Log;

import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FillTheGapClass {
    private String sentence;
    private String redactedWord;
    private Expr expr;
    private Object[] bs;

    public List<String> getInflections() {
        return inflections;
    }

    private List<String> inflections = new ArrayList<>();

    public List<String> getTargetSentence() {
        return targetSentence;
    }

    private List<String> targetSentence = new ArrayList<>();
    Concr target;
    Concr source;


    public FillTheGapClass(String sentence, Concr source, Concr target){
        this.sentence = sentence;
        this.target = target;
        this.source = source;
        sentenceToExpr(sentence);
        bracketLinearize(source, target);
        setRedactedWord();
    }

    private void sentenceToExpr(String sentence){
        expr = Expr.readExpr(sentence);
    }

    public boolean checkCorrectAnswer(String answer){
        if(answer.equals(redactedWord)){
            return true;
        } else{
            return false;
        }
    }

    private void bracketLinearize(Concr source, Concr target){
        bs = target.bracketedLinearize(expr);
        scanBracket(bs[0]);
    }

    private void scanBracket(Object bs) {
        Log.d(TAG, "Bracketedscanned");
        if(bs instanceof Bracket){
            Log.d(TAG, ":DDDD");
            if(((Bracket) bs).cat.equals("V")|| ((Bracket) bs).cat.equals("V2")|| ((Bracket) bs).cat.equals("V3")){
                Log.d(TAG, "___________________________________________");
                String verb = ((Bracket) bs).fun;
                inflect(verb);
                return; //After the first verb has been found, stop
            }
            if (((Bracket) bs).children != null){
                for(Object child : ((Bracket) bs).children){
                    scanBracket(child);
                }
            }
        }else if(bs instanceof String){
            //Log.d(TAG, (String) bs);
        }
    }

    private void inflect(String verb) {
        Log.d(TAG, verb);
        Expr e = Expr.readExpr(verb);
        for(Map.Entry<String,String> entry  : target.tabularLinearize(e).entrySet()) {
            if(entry.getKey().contains("Act")){
                inflections.add(entry.getValue()); //Add inflections
            }
        }

    }

    private void setRedactedWord(){
        String targetLinearization = target.linearize(expr);
        for(String word : targetLinearization.split(" ")){
            targetSentence.add(word);
        }
        //Prepend the correct inflection to inflections
        for(String word : inflections) {
            if(targetLinearization.contains(word)){
                String temp = inflections.get(0);
                int indexInflection = inflections.indexOf(word);
                inflections.set(0, word);
                inflections.set(indexInflection, temp);
                redactedWord = word;
            }
        }
        if(targetSentence.contains(redactedWord)) {
            int toRemove = targetSentence.indexOf(redactedWord);
            String redacted = "";
            for (char c : redactedWord.toCharArray()) {
                redacted += "_";
                targetSentence.set(toRemove, redacted);
            }
        }
    }

}
