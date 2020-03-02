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

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    private static final String TAG = MainActivity.class.getSimpleName();
    Concr eng;
    Concr swe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.viewModel = new MainViewModel();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        SmartLearning mSmartLearning = (SmartLearning) getApplicationContext();
        eng = mSmartLearning.getSoruceConcr();
        swe = mSmartLearning.getTargetConcr();
        wordTranslator("nail");
    }

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
