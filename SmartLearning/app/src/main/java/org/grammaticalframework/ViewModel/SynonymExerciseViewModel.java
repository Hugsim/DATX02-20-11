package org.grammaticalframework.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import org.grammaticalframework.Repository.FillTheGapExercise;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.gf.GF;
import org.grammaticalframework.pgf.Expr;

public class SynonymExerciseViewModel extends AndroidViewModel {

    private static final String TAG = SynonymExerciseViewModel.class.getSimpleName();

    private SmartLearning mSmartLearning;
    private GF gf;
    private SynonymExercise ftge;



    public SynonymExerciseViewModel(Application application){
        super(application);

        mSmartLearning = (SmartLearning) getApplication().getApplicationContext();
        gf = new GF(mSmartLearning);





    }

    public void loadWord(FillTheGapExercise ftge) {
        this.ftge = ftge;
        expression = Expr.readExpr(ftge.getAbstractSyntaxTree());
        linearizedSentence = mSmartLearning.getTargetConcr().linearize(expression);
        Object[] bs = mSmartLearning.getTargetConcr().bracketedLinearize(expression);
        findWordToRedact(bs[0]);
        setRedactedWord();
    }
}
