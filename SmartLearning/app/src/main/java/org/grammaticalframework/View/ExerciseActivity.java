package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.ExerciseViewModel;
import androidx.annotation.NonNull;

public class ExerciseActivity extends BaseActivity {
    private ExerciseViewModel viewModel;
    private Intent intentLexicon;
    private Intent intentHome;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        viewModel = new ExerciseViewModel();
        intentLexicon = new Intent(this, LexiconActivity.class);
        intentHome = new Intent(this, HomeActivity.class);
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_item1:
                    startActivity(intentHome);
                    break;
                case R.id.action_item3:
                    startActivity(intentLexicon);
                    break;
            }
            return  true;
        }
    };

    @Override
    public BaseViewModel getViewModel() {
        return viewModel;
    }

    public void buttonClick(View view) {
        int id = view.getId();
        Toast toast;

        switch (id){
            case R.id.grammar_button:
                // Intent intent = new Intent(this, GrammarActivity.class);
                toast = Toast.makeText(this, "Go to grammar", Toast.LENGTH_SHORT);
                toast.show();
                break;
            case R.id.vocabulary_button:
                // Intent intent = new Intent(this, WordsActivity.class);
                toast = Toast.makeText(this, "Go to vocabulary", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }

        // startActivity(intent);

    }
}
