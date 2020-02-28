package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.grammaticalframework.R;
import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.ViewModel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HomeActivity extends BaseActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final BaseFragment mainExerciseFragment = FragmentFactory.createMainExerciseFragment();
    private final BaseFragment mainLexiconFragment = FragmentFactory.createMainLexiconFragment();
    private final BaseFragment mainHomeFragment = FragmentFactory.createMainHomeFragment();

    private BottomNavigationView bottomNavigation;
    private HomeViewModel homeViewModel;

    public HomeViewModel getViewModel(){
        return homeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeViewModel = new HomeViewModel(); //TODO: Change this to ViewModelProvider???
        overridePendingTransition(0, 0);
        addAllFragments(R.id.container,mainHomeFragment);

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
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    private void addAllFragments(int id,BaseFragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.commit();
    }
}
