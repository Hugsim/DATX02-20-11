package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.grammaticalframework.Language;
import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.ExerciseViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ExerciseActivity extends BaseActivity {
    private ExerciseViewModel viewModel;
    private Intent intentLexicon;
    private Intent intentLanguage;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        viewModel = new ExerciseViewModel();
        intentLexicon = new Intent(this, LexiconActivity.class);
        intentLanguage = new Intent(this, Language.class);
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_item2:
                    startActivity(intentLexicon);
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
