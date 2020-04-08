package org.grammaticalframework.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.grammaticalframework.Repository.ExerciseRepository;
import org.grammaticalframework.Repository.SynonymExercise;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynonymExerciseViewModel extends AndroidViewModel {

    private static final String TAG = SynonymExerciseViewModel.class.getSimpleName();

    private SmartLearning mSmartLearning;
    private GF gf;
    private SynonymExercise synonymExercise;

    private String linearizedSynonym;
    private List<String> linearizedAlternatives;

    private ExerciseRepository exerciseRepository;

    private LiveData<SynonymExercise> unsolvedExercise;


    public SynonymExerciseViewModel(Application application){
        super(application);
        mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        gf = new GF(mSmartLearning);

        exerciseRepository = new ExerciseRepository(application);
        unsolvedExercise = exerciseRepository.getunsolvedSynonymExercise();
        linearizedAlternatives = new ArrayList<>();

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
        e = Expr.readExpr(se.getAnswerFunction());
        linearizedAlternatives.add(mSmartLearning.getTargetConcr().linearize(e));
    }

    public String getWord() {
        return linearizedSynonym;
    }

    public List<String> getAlternatives() {
        Collections.shuffle(linearizedAlternatives);
        return linearizedAlternatives;
    }
    
    public boolean checkCorrectAnswer(String answer) {
        Expr e = Expr.readExpr(synonymExercise.getAnswerFunction());
        String correct = mSmartLearning.getTargetConcr().linearize(e);
        return correct.equals(answer);
    }

    public void getNewExercise() {
        exerciseRepository.addSolvedSynonymExercise(synonymExercise);
    }

    public LiveData<SynonymExercise> getUnsolvedExercise() {
        return unsolvedExercise;
    }
}
