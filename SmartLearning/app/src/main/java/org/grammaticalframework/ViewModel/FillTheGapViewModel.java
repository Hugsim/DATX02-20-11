package org.grammaticalframework.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.grammaticalframework.Repository.FillTheGapExercise;
import org.grammaticalframework.Repository.ExerciseRepository;
import org.grammaticalframework.Grammarlex;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Bracket;
import org.grammaticalframework.pgf.Expr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FillTheGapViewModel extends AndroidViewModel {


    private static final String TAG = FillTheGapViewModel.class.getSimpleName();
    private Expr expression;
    private GF gf;

    private FillTheGapExercise ftge;

    private ExerciseRepository exerciseRepository;

    private String redactedWord;
    private String linearizedSentence;
    private ArrayList<String> inflections = new ArrayList<>();

    private LiveData<FillTheGapExercise> unsolvedExercise;

    private Grammarlex mGrammarlex;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    public FillTheGapViewModel(Application application){
        super(application);

        mGrammarlex = (Grammarlex) getApplication().getApplicationContext();
        gf = new GF(mGrammarlex);

        exerciseRepository = new ExerciseRepository(application);

        unsolvedExercise = exerciseRepository.getUnsolvedFillTheGapExercise();
    }

    public void loadWord(FillTheGapExercise ftge) {
        this.ftge = ftge;
        expression = Expr.readExpr(ftge.getAbstractSyntaxTree());
        linearizedSentence = mGrammarlex.getTargetConcr().linearize(expression);
        Object[] bs = mGrammarlex.getTargetConcr().bracketedLinearize(expression);
        findWordToRedact(bs[0]);
        setRedactedWord();
    }

    public LiveData<FillTheGapExercise> getUnsolvedExercise() {
        return unsolvedExercise;
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

    public String getSentence() {
        return linearizedSentence;
        //return gf.translateWord("apple");
    }

    public List<String> getInflections() {
        List<String> notAllInflections = new ArrayList<>();
        for(int i = 0; i < 5 && i < inflections.size(); i++){
            notAllInflections.add(inflections.get(i));
        }
        Collections.shuffle(notAllInflections);
        return notAllInflections;
    }

    public boolean checkCorrectAnswer(String answer){
        if(answer.equals(redactedWord)){
            linearizedSentence = mGrammarlex.getTargetConcr().linearize(Expr.readExpr(ftge.getAbstractSyntaxTree()));
            return true;
        } else{
            return false;
        }
    }


    //Should only be called when the previous exercise was solved
    public void getNewSentence(){
        //say that the next exercise is solved
        exerciseRepository.addSolvedFillTheGapExercise(ftge);
        //nextExercise.setValue("spare_V3");
    }


    private void findWordToRedact(Object bs){
        if(bs instanceof Bracket){
            if(((Bracket) bs).fun.equals(ftge.getFunctionToReplace())){
                redactedWord = ((Bracket) bs).children[0].toString();
                inflect(((Bracket) bs).fun);
            } else{
                if(((Bracket) bs).children != null) {
                    for(Object child : ((Bracket) bs).children) {
                        findWordToRedact(child);
                    }
                }
            }
        }
    }

    private void inflect(String verb) {
        if(!inflections.isEmpty()) {
            inflections.clear();
        }
        Expr e = Expr.readExpr(verb);
        for(Map.Entry<String,String> entry : mGrammarlex.getTargetConcr().tabularLinearize(e).entrySet()) {
            if(!entry.getValue().equals("")){
                //TODO: find out how to value if the inflection is "good" or not
                //perhaps look at
                inflections.add(entry.getValue()); //Add inflections
            }
        }
    }

    private void setRedactedWord(){
        String targetLinearization = mGrammarlex.getTargetConcr().linearize(expression);
        ArrayList<String> sentence = new ArrayList<>();
        Collections.addAll(sentence, targetLinearization.split(" "));
        //Prepend the correct inflection to inflections
        if(inflections.size() < 1)
            return;
        String temp = inflections.get(0);
        int indexInflection = inflections.indexOf(redactedWord);

        inflections.set(0, redactedWord);

        if(indexInflection > -1){
            inflections.set(indexInflection, temp);
        } else {
            inflections.add(temp);
        }

        if(linearizedSentence.contains(redactedWord)) {
            int toRemove = sentence.indexOf(redactedWord);
            String redacted = "";
            for (char c : redactedWord.toCharArray()) {
                redacted += "_";
                sentence.set(toRemove, redacted);
                linearizedSentence = android.text.TextUtils.join(" ", sentence);
            }
        }
    }


    public String getTense(){
        Expr e = Expr.readExpr( ftge.getTenseFunction());
        Expr e2 = Expr.readExpr("PhrUtt NoPConj (UttImpPol PPos (ImpVP (ComplSlash (SlashV2a choose_1_V2) (MassNP (UseN tense_N))))) NoVoc");
        return mGrammarlex.getTargetConcr().linearize(e2) + ": " + mGrammarlex.getTargetConcr().linearize(e);
    }
}
