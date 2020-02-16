package org.grammaticalframework.View;

import android.os.Bundle;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.HomeViewModel;
import androidx.fragment.app.Fragment;

public class HomeActivity extends BaseActivity {
    private final Fragment navigationFragment = FragmentFactory.createNavigationFragment();
    private HomeViewModel homeViewModel;

    public HomeViewModel getViewModel(){
        return homeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeViewModel = new HomeViewModel();
    }

}
