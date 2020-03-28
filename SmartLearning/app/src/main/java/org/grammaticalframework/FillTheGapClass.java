package org.grammaticalframework;

import android.util.Log;

import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FillTheGapClass {
    private String sentence;
    private String redactedWord;
    private Expr expr;
    private Object[] bs;
    private GF gf;

    public List<String> getInflections() {
        return inflections;
    }

    private List<String> inflections = new ArrayList<>();

    public List<String> getTargetSentence() {
        return targetSentence;
    }

    private List<String> targetSentence = new ArrayList<>();


    public FillTheGapClass(String sentence, GF gf){
        this.sentence = sentence;
        this.gf = gf;
        sentenceToExpr(sentence);
        bracketLinearize(gf.getSourceConcr(), gf.getTargetConcr());
        setRedactedWord();
    }

    private void sentenceToExpr(String sentence){
        expr = GF.readExpr(sentence);
    }

    public boolean checkCorrectAnswer(String answer){
        return answer.equals(redactedWord);
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
        }
    }

    private void inflect(String verb) {
        Log.d(TAG, verb);
        Expr e = GF.readExpr(verb);
        for(Map.Entry<String, String> entry : gf.tabularLinearize(e)) {
            if(entry.getKey().contains("Act")){
                inflections.add(entry.getValue());
            }
        }

    }

    private void setRedactedWord(){
        String targetLinearization = gf.linearize(expr);
        targetSentence.addAll(Arrays.asList(targetLinearization.split(" ")));

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

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < redactedWord.length(); i++) {
                sb.append('_');
            }
            targetSentence.set(toRemove, sb.toString());
        }
    }

}
