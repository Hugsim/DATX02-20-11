package org.grammaticalframework.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.grammaticalframework.Repository.ExerciseRepository;
import org.grammaticalframework.Repository.SynonymExercise;
import org.grammaticalframework.Grammarlex;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynonymExerciseViewModel extends AndroidViewModel {

    private static final String TAG = SynonymExerciseViewModel.class.getSimpleName();

    private Grammarlex mGrammarlex;
    private GF gf;
    private SynonymExercise synonymExercise;

    private String linearizedSynonym;
    private List<String> linearizedAlternatives;

    private ExerciseRepository exerciseRepository;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    private LiveData<SynonymExercise> unsolvedExercise;


    public SynonymExerciseViewModel(Application application){
        super(application);
        mGrammarlex = (Grammarlex) getApplication().getApplicationContext();
        gf = new GF(mGrammarlex);

        exerciseRepository = new ExerciseRepository(application);
        unsolvedExercise = exerciseRepository.getunsolvedSynonymExercise();
        linearizedAlternatives = new ArrayList<>();

    }

    public void loadWord(SynonymExercise se) {
        this.synonymExercise = se;
        Expr e = Expr.readExpr(se.getSynonymFunction());
        linearizedSynonym = mGrammarlex.getTargetConcr().linearize(e);
        linearizedAlternatives.clear();
        for (String s : se.getAlternativeFunctions()) {
            e = Expr.readExpr(s);
            linearizedAlternatives.add(mGrammarlex.getTargetConcr().linearize(e));
        }
        e = Expr.readExpr(se.getAnswerFunction());
        linearizedAlternatives.add(mGrammarlex.getTargetConcr().linearize(e));
    }

    public String getWord() {
        return linearizedSynonym;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public List<String> getAlternatives() {
        Collections.shuffle(linearizedAlternatives);
        return linearizedAlternatives;
    }
    
    public boolean checkCorrectAnswer(String answer) {
        Expr e = Expr.readExpr(synonymExercise.getAnswerFunction());
        String correct = mGrammarlex.getTargetConcr().linearize(e);
        return correct.equals(answer);
    }

    public void getNewExercise() {
        exerciseRepository.addSolvedSynonymExercise(synonymExercise);
    }

    public LiveData<SynonymExercise> getUnsolvedExercise() {
        return unsolvedExercise;
    }
}
