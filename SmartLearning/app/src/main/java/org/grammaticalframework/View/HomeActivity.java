package org.grammaticalframework.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.grammaticalframework.R;
import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.ViewModel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;


public class HomeActivity extends BaseActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final BaseFragment mainExerciseFragment = FragmentFactory.createMainExerciseFragment();
    private final BaseFragment mainLexiconFragment = FragmentFactory.createMainLexiconFragment();
    private final BaseFragment mainHomeFragment = FragmentFactory.createMainHomeFragment();
    private final BaseFragment grammarFragment = FragmentFactory.createGrammarFragment();
    private final BaseFragment vocabularyFragment = FragmentFactory.createVocabularyFragment();

    private BottomNavigationView bottomNavigation;
    private HomeViewModel homeViewModel;
    FragmentSwapper fs = new FragmentSwapper(fragmentManager);

    public HomeViewModel getViewModel() {
        return homeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeViewModel = new HomeViewModel();
        overridePendingTransition(0, 0);
        addAllFragments(R.id.container, mainHomeFragment);

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        bottomNavigation.getMenu().getItem(0).setChecked(true);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_item1:
                    changeToFragment(mainHomeFragment);
                    break;
                case R.id.action_item2:
                    changeToFragment(mainExerciseFragment);
                    break;
                case R.id.action_item3:
                    changeToFragment(mainLexiconFragment);
                    break;
            }
            return  true;
        }
    };

    private void changeToFragment(BaseFragment fragment){
        fs.changeToFragment(fragment);
    }

    private void addAllFragments(int id,BaseFragment fragment){
        fs.initFragments(id, fragment);
    }

    public void onBackPressed()
    {
        fs.onBackPressed();
    }

    public void goToVocabularyView(View view){
        fs.changeToFragment(vocabularyFragment);
    }
    public void goToGrammarView(View view){
        changeToFragment(grammarFragment);
    }
}
