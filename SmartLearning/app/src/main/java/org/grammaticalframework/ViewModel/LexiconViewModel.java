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


public class LexiconViewModel extends AndroidViewModel {

    final static String TAG = LexiconViewModel.class.getSimpleName();

    private List<String> translatedWords = new ArrayList<>();
    private List<LexiconWord> lexiconWords = new ArrayList<>();
    SmartLearning sl = (SmartLearning) getApplication().getApplicationContext();
    private Concr eng = sl.getSourceConcr();
    private Concr swe = sl.getTargetConcr();

    public LiveData<List<WNExplanation>> wnExplanationLiveData;

    WNExplanationRepository wnExplanationRepository;

    public LexiconViewModel(@NonNull Application application) {
        super(application);

        wnExplanationRepository = new WNExplanationRepository(application);
        wnExplanationLiveData = wnExplanationRepository.getAllWordNetExplanations();
        if (wnExplanationLiveData.getValue() != null){
            Log.d(TAG, wnExplanationLiveData.getValue().toString());
        }
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
            String explanation = wnExplanationRepository.getWNExplanation(e.unApp().getFunction());
            explanation = explanation == null ? "" : explanation;
            //Log.d(TAG, an.getLemma());
            //Log.d(TAG, swe.linearize(e));
            for (String s : swe.linearizeAll(e)) {
                if (!translatedWords.contains(s)){
                    translatedWords.add(s);
                    lexiconWords.add(new LexiconWord(s, explanation));
                }
                Log.d(TAG, s);
            }
        }
    }

}