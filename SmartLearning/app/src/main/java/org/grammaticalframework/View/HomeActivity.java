package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.HomeViewModel;
import androidx.annotation.NonNull;


public class HomeActivity extends BaseActivity {
    private Intent intentExercise;
    private Intent intentLexicon;
    private Intent intentLanguage;

    private BottomNavigationView bottomNavigation;
    private HomeViewModel homeViewModel;

    public HomeViewModel getViewModel(){
        return homeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeViewModel = new HomeViewModel();

        intentExercise = new Intent(this,ExerciseActivity.class);
        intentLexicon = new Intent(this, LexiconActivity.class);
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

}
