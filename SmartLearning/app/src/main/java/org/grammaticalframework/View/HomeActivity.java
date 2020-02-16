package org.grammaticalframework.View;

import android.os.Bundle;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.HomeViewModel;
import androidx.fragment.app.Fragment;

public class HomeActivity extends BaseActivity {
    private static final int index = 1;
    private final Fragment navigationFragment = FragmentFactory.createNavigationFragment();
    private HomeViewModel homeViewModel;

    public HomeViewModel getViewModel(){
        return homeViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Sends the activity index to NavigationFragment via Bundle
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        navigationFragment.setArguments(bundle);


        homeViewModel = new HomeViewModel();
    }

}
