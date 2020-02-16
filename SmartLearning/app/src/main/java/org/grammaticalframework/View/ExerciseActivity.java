package org.grammaticalframework.View;

import android.os.Bundle;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.ExerciseViewModel;

import androidx.fragment.app.Fragment;

public class ExerciseActivity extends BaseActivity {
    private ExerciseViewModel viewModel;
    public static final int index = 0;
    private final Fragment navigationFragment = FragmentFactory.createNavigationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        viewModel = new ExerciseViewModel();

        //Sends the activity index to NavigationFragment via Bundle
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        navigationFragment.setArguments(bundle);
    }

    @Override
    public BaseViewModel getViewModel() {
        return viewModel;
    }
}
