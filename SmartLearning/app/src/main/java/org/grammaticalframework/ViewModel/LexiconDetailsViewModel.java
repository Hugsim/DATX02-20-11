package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;
import org.grammaticalframework.pgf.PGF;

import java.util.ArrayList;
import java.util.Map;

public class LexiconDetailsViewModel extends AndroidViewModel {
    private static final String TAG = LexiconDetailsViewModel.class.getSimpleName();
    private Concr eng;
    private Concr swe;
    private ArrayList<String> inflections;
    private SmartLearning sl;
    private PGF gr;

    public LexiconDetailsViewModel(@NonNull Application application) {
        super(application);
        sl = (SmartLearning) getApplication().getApplicationContext();
        inflections = new ArrayList<>();
        eng = sl.getSourceConcr();
        swe = sl.getTargetConcr();
        gr = sl.getGrammar();
    }

    public ArrayList<String> getInflections(){
        return inflections;
    }

    public String inflect(String word){
        Log.d(TAG, word);
        Expr e = Expr.readExpr("MkDocument (NoDefinition \"\") (Inflection" + wordClass(word) + " " + word + ") \"\"");
        return swe.linearize(e);
    }

    public String wordClass(String lemma){
        return gr.getFunctionType(lemma).getCategory();
    }
}
