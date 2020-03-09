package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.text.Html;
import android.util.Log;

import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;
import org.grammaticalframework.pgf.PGF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords;
    private List<LexiconWord> lexiconWords;
    private SmartLearning sl;
    private Concr eng;
    private Concr swe;
    private static final String TAG = LexiconViewModel.class.getSimpleName();
    private PGF gr;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        translatedWords = new ArrayList<>();
        lexiconWords = new ArrayList<>();
        sl = (SmartLearning) getApplication().getApplicationContext();
        eng = sl.getSourceConcr();
        swe = sl.getTargetConcr();
        gr = sl.getGrammar();
    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

    public void wordTranslator(String word){
        if (!translatedWords.isEmpty()){
            translatedWords.clear();
        }
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            if(swe.hasLinearization(an.getLemma())){ // Om ordet kan lineariseras
                Expr e = Expr.readExpr(an.getLemma());
                for (String s : swe.linearizeAll(e)) {
                    if (!translatedWords.contains(an.getLemma() + " " + s)){
                        translatedWords.add(an.getLemma() + " " + s);
                    }
                }
            }
        }
        stringToLexicon();
    }

    public void stringToLexicon(){
        for (String string: translatedWords){
            lexiconWords.add(new LexiconWord(string,"explanation"));
        }
    }
}