package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import org.grammaticalframework.Repository.WNExplanation;
import org.grammaticalframework.Repository.WNExplanationRepository;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LexiconViewModel extends AndroidViewModel {
    private List<String> translatedWords = new ArrayList<>();
    private List<LexiconWord> lexiconWords = new ArrayList<>();
    SmartLearning sl = (SmartLearning) getApplication().getApplicationContext();
    private Concr eng = sl.getSourceConcr();
    private Concr swe = sl.getTargetConcr();

    public LiveData<List<WNExplanation>> wnExplanationLiveData;

    public LexiconViewModel(@NonNull Application application) {
        super(application);

        WNExplanationRepository wnExplanationRepository = new WNExplanationRepository(application);
        wnExplanationLiveData = wnExplanationRepository.getAllWordNetExplanations();

    }

    public List<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

   public void wordTranslator(String word){
        if (!translatedWords.isEmpty()){
            translatedWords.clear();
        }
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            Expr e = Expr.readExpr(an.getLemma());
            //Log.d(TAG, an.getLemma());
            //Log.d(TAG, swe.linearize(e));
            for (String s : swe.linearizeAll(e)) {
                if (!translatedWords.contains(s)){
                translatedWords.add(s);
                }
                Log.d(TAG, s);
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