package org.grammaticalframework.View;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.ViewModel.MainViewModel;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    private static final String TAG = MainActivity.class.getSimpleName();
    Concr eng;
    Concr swe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new MainViewModel();
        Intent intent = new Intent(this, HomeActivity.class);

        SmartLearning mSmartLearning = (SmartLearning) getApplicationContext();
        eng = mSmartLearning.getSoruceConcr();
        swe = mSmartLearning.getTargetConcr();
        wordTranslator("nail");

        startActivity(intent);
        finish();
    }

    @Override
    public MainViewModel getViewModel() {
        return viewModel;
    }

    public void wordTranslator(String word){
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            Expr e = Expr.readExpr(an.getLemma());
            //Log.d(TAG, an.getLemma());
            //Log.d(TAG, swe.linearize(e));
            for (String s : swe.linearizeAll(e)) {
                    Log.d(TAG, s);
            }
        }
    }
}
