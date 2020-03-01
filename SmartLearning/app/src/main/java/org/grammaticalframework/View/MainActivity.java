package org.grammaticalframework.View;
import org.grammaticalframework.R;
import org.grammaticalframework.SmartLearning;
import org.grammaticalframework.ViewModel.MainViewModel;
import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.MorphoAnalysis;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    private static final String TAG = MainActivity.class.getSimpleName();
    Concr eng;
    Concr swe;
    public static SmartLearning mSmartLearning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.viewModel = new MainViewModel();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        mSmartLearning = (SmartLearning) getApplicationContext();
        eng = mSmartLearning.getSoruceConcr();
        swe = mSmartLearning.getTargetConcr();
        wordTranslator("run");
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }

    public void wordTranslator(String word){
        ArrayList translations = new ArrayList();
        for (MorphoAnalysis an : eng.lookupMorpho(word)) {
            Expr e = Expr.readExpr(an.getLemma());
            for (String s : swe.linearizeAll(e)) {
                if(!translations.contains(s)){
                       translations.add(s);
                       Log.d(TAG, s);
                }
            }
        }
    }
}