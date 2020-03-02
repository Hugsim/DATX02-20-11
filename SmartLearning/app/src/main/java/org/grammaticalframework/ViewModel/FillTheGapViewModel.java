package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.ExprApplication;
import org.grammaticalframework.pgf.MorphoAnalysis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.IntStream;

public class FillTheGapViewModel extends AndroidViewModel {


    private static final String TAG = FillTheGapViewModel.class.getSimpleName();
    Concr eng;
    Concr swe;

    private ArrayList<String> reductedWords = new ArrayList<>();
    private ArrayList<String> sentence = new ArrayList<>();
    private ArrayList<String> inflections = new ArrayList<>();

    public FillTheGapViewModel(Application application){
        super(application);

        Log.d(TAG, "INSIDE OF FILLTHEGAPVIEWMODEL");

        SmartLearning mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        eng = mSmartLearning.getSoruceConcr();
        swe = mSmartLearning.getTargetConcr();

        Expr e = Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (UseV eat_2_V) a_la_carte_Adv)))) NoVoc");


        Object[] bs = swe.bracketedLinearize(e);
        printChildren(bs[0], bs[0]);
        
        String swedishLinearization = swe.linearize(e);
        for(String word : swedishLinearization.split(" ")){
            sentence.add(word);
        }
        //Prepend the correct inflection to inflections
        for(String word : inflections) {
            if(swedishLinearization.contains(word)){
                String temp = inflections.get(0);
                int indexInflection = inflections.indexOf(word);
                inflections.set(0, word);
                inflections.set(indexInflection, temp);
            }
        }

        //Redact word(s)
        for(String word : reductedWords) {
            if(!sentence.contains(word))
                break;
            int toRemove = sentence.indexOf(word);
            String redacted = "";
            for(char c : word.toCharArray()) {
                redacted += "_";
            }
            sentence.set(toRemove, redacted);
        }
    }


    public ArrayList<String> getSentence() {
        return sentence;
    }

    public ArrayList<String> getInflections() {
        ArrayList<String> notAllInflections = new ArrayList<>();
        for(int i = 0; i < 5 && i < inflections.size(); i++){
            notAllInflections.add(inflections.get(i));
        }
        Collections.shuffle(notAllInflections);
        return notAllInflections;
    }

    private void printChildren(Object bs, Object fullBs) {
        if(bs instanceof Bracket){
            if(((Bracket) bs).cat.equals("V")){
                inflect((Bracket) bs, (Bracket) fullBs);
                return; //After the first verb has been found, stop
            }
            if (((Bracket) bs).children != null){
                for(Object child : ((Bracket) bs).children){
                    printChildren(child, fullBs);
                }
            }
        }else if(bs instanceof String){
            //Log.d(TAG, (String) bs);
        }
    }

    private void inflect(Bracket bracket, Bracket fullBs) {
        String word = bracket.children[0].toString();
        for(MorphoAnalysis an : swe.lookupMorpho(word)){
            Expr e = Expr.readExpr(an.getLemma());
            for(Map.Entry<String,String> entry  : swe.tabularLinearize(e).entrySet()) {
                if(entry.getKey().contains("Act")){
                    inflections.add(entry.getValue()); //Add inflections
                }
            }
        }
    }


}
