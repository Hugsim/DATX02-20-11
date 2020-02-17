package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.grammaticalframework.Language;
import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.LexiconViewModel;

import androidx.annotation.NonNull;

public class LexiconActivity extends BaseActivity {
    private LexiconViewModel viewModel;
    private Intent intentExercise;
    private Intent intentLanguage;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon);
        viewModel = new LexiconViewModel();

        intentExercise= new Intent(this, ExerciseActivity.class);
        intentLanguage = new Intent(this, LanguageActivity.class);
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_item1:
                    startActivity(intentExercise);
                    break;
                case R.id.action_item3:
                    startActivity(intentLanguage);
                    break;
            }
            return  true;
        }
    };


    @Override
    public BaseViewModel getViewModel() {
        return viewModel;
    }
}
