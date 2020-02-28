package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;

import java.util.Map;

public class FillTheGapViewModel extends AndroidViewModel {


    private static final String TAG = FillTheGapViewModel.class.getSimpleName();
    Concr eng;
    Concr swe;

    public FillTheGapViewModel(Application application){
        super(application);

        Log.d(TAG, "INSIDE OF FILLTHEGAPVIEWMODEL");

        SmartLearning mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        eng = mSmartLearning.getSoruceConcr();
        swe = mSmartLearning.getTargetConcr();

        Expr e = Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (UseV eat_2_V) a_la_carte_Adv)))) NoVoc");
        Object[] bs = eng.bracketedLinearize(e);
        printChildren(bs[0]);


    }

    private void printChildren(Object bs) {
        if(bs instanceof Bracket){
            if(((Bracket) bs).cat.equals("V"))
                inflect((String)((Bracket) bs).children[0]);
            if (((Bracket) bs).children != null){
                //Log.d(TAG, "CATEGORY:::::::::::: " + ((Bracket) bs).cat);
                for(Object child : ((Bracket) bs).children){
                    printChildren(child);
                }
            }
        }else if(bs instanceof String){
            //Log.d(TAG, (String) bs);
        }
    }

    private void inflect(String word) {
        for(MorphoAnalysis an : eng.lookupMorpho(word)){
            Expr e = Expr.readExpr(an.getLemma());
            for(Map.Entry<String,String> entry  : swe.tabularLinearize(e).entrySet()) {
                if(entry.getKey().contains("Pass"))
                    Log.d(TAG, entry.getKey() + ": " + entry.getValue());
            }
        }
    }


}
