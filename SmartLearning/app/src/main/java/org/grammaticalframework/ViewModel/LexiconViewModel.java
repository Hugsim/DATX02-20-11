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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords = new ArrayList<>();;
    private List<LexiconWord> lexiconWords = new ArrayList<>();;
    SmartLearning sl = (SmartLearning) getApplication().getApplicationContext();
    private Concr eng = sl.getSourceConcr();
    private Concr swe = sl.getTargetConcr();
    private static final String TAG = LexiconViewModel.class.getSimpleName();

    public LexiconViewModel(@NonNull Application application) {
        super(application);
    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

    public void wordTranslator(String word){
        PGF gr = sl.getGrammar();
        if (!translatedWords.isEmpty()){
            translatedWords.clear();
        }
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            if(swe.hasLinearization(an.getLemma())){
                Expr e = Expr.readExpr(an.getLemma());
                String html = gr.getFunctionType(an.getLemma()).getCategory(); // Ordklass i html
                String wordClass = Html.fromHtml(html).toString(); // Tar bort html
                for (String s : swe.linearizeAll(e)) {
                    if (!translatedWords.contains(wordClass + " " + s)){
                        translatedWords.add(wordClass + " " + s);
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