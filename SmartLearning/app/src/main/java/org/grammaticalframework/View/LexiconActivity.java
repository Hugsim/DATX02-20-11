package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.LexiconViewModel;

import androidx.annotation.NonNull;

public class LexiconActivity extends BaseActivity {
    private LexiconViewModel viewModel;
    private Intent intentExercise;
    private Intent intentHome;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon);
        viewModel = new LexiconViewModel();
        overridePendingTransition(0, 0);

        intentExercise= new Intent(this, ExerciseActivity.class);
        intentHome = new Intent(this, HomeActivity.class);
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        bottomNavigation.getMenu().getItem(2).setChecked(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_item1:
                    startActivity(intentHome);
                    break;
                case R.id.action_item2:
                    startActivity(intentExercise);
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
