package org.grammaticalframework.View;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.ViewModel.MainViewModel;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.ExprApplication;
import org.grammaticalframework.pgf.MorphoAnalysis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    private static final String TAG = MainActivity.class.getSimpleName();
    Concr eng;
    Concr swe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new MainViewModel();
        Intent intent = new Intent(this, HomeActivity.class);

        SmartLearning mSmartLearning = (SmartLearning) getApplicationContext();
        eng = mSmartLearning.getSoruceConcr();
        swe = mSmartLearning.getTargetConcr();

        Expr e = Expr.readExpr("PhrUtt NoPConj (UttS (UseCl (TTAnt TPast ASimul) PPos (PredVP (UsePron we_Pron) (AdvVP (UseV eat_2_V) a_la_carte_Adv)))) NoVoc");
        Object[] bs = eng.bracketedLinearize(e);
        printChildren(bs[0]);

        //wordTranslator("nail");

        startActivity(intent);
        finish();
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

    @Override
    public MainViewModel getViewModel() {
        return viewModel;
    }

    public void wordTranslator(String word){
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            Expr e = Expr.readExpr(an.getLemma());
            //Log.d(TAG, an.getLemma());
            //Log.d(TAG, swe.linearize(e));
            for (String s : swe.linearizeAll(e)) {
                    Log.d(TAG, s);
            }
        }
    }
}
