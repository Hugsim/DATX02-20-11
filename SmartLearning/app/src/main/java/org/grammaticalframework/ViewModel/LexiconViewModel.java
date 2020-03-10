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

    private ArrayList<String> translatedWords = new ArrayList<>();
    private ArrayList<LexiconWord> lexiconWords = new ArrayList<>();
    SmartLearning sl = (SmartLearning) getApplication().getApplicationContext();
    private Concr eng = sl.getSourceConcr();
    private Concr swe = sl.getTargetConcr();


    //Livedata containing the explanations for the functions searched for
    private LiveData<List<WNExplanation>> wnExplanationLiveData;
    //The functions that we are going to find in wordnet
    private List<String> functions = new ArrayList<>();

    WNExplanationRepository wnExplanationRepository;

    public LexiconViewModel(@NonNull Application application) {
        super(application);
        wnExplanationRepository = new WNExplanationRepository(application);
    }

    public ArrayList<LexiconWord> getTranslatedWords(){
        return lexiconWords;
    }

   public void wordTranslator(String word){
        if (!translatedWords.isEmpty()){
            translatedWords.clear();
        }
        if (!functions.isEmpty()){
            functions.clear();
        }
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            Expr e = Expr.readExpr(an.getLemma());
            String function = e.unApp().getFunction();
            //Log.d(TAG, an.getLemma());
            //Log.d(TAG, swe.linearize(e));
            for (String s : swe.linearizeAll(e)) {
                if (!translatedWords.contains(s)){
                    functions.add(function);
                    translatedWords.add(s);
                    lexiconWords.add(new LexiconWord(s, "", function));
                }
                Log.d(TAG, s);
            }
        }
        wnExplanationLiveData = wnExplanationRepository.getWNExplanations(functions);
    }

    public LiveData<List<WNExplanation>> getWnExplanationLiveData() {
        return wnExplanationLiveData;
    }
}