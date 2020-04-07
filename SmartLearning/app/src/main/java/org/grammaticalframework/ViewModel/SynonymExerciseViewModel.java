package org.grammaticalframework.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import org.grammaticalframework.Repository.FillTheGapExercise;
import org.grammaticalframework.Repository.FillTheGapExerciseRepository;
import org.grammaticalframework.Repository.SynonymExercise;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Expr;

import java.util.List;

public class SynonymExerciseViewModel extends AndroidViewModel {

    private static final String TAG = SynonymExerciseViewModel.class.getSimpleName();

    private SmartLearning mSmartLearning;
    private GF gf;
    private SynonymExercise synonymExercise;

    private String linearizedSynonym;
    private List<String> linearizedAlternatives;

    private FillTheGapExerciseRepository fillTheGapExerciseRepository;

    private LiveData<SynonymExercise> unsolvedExercise;


    public SynonymExerciseViewModel(Application application){
        super(application);

        mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        gf = new GF(mSmartLearning);

        unsolvedExercise = fillTheGapExerciseRepository.getunsolvedSynonymExercise();

    }

    public void loadWord(SynonymExercise se) {
        this.synonymExercise = se;
        Expr e = Expr.readExpr(se.getSynonymFunction());
        linearizedSynonym = mSmartLearning.getTargetConcr().linearize(e);
        linearizedAlternatives.clear();
        for (String s : se.getAlternativeFunctions()) {
            e = Expr.readExpr(s);
            linearizedAlternatives.add(mSmartLearning.getTargetConcr().linearize(e));
        }
    }

    public String getWord() {
        return linearizedSynonym;
    }

    public List<String> getAlternatives() {
        return linearizedAlternatives;
    }
    
    public boolean checkCorrectAnswer(String answer) {
        return answer.equals(synonymExercise.getAnswerFunction());
    }

    public void getNewExercise() {
        //Look at getNewSentence in ftgvm and make this work similary. Functionality is implemented mostly in the repository though
    }


}
