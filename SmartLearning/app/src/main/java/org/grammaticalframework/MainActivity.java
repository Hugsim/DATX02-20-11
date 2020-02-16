package org.grammaticalframework;

import org.grammaticalframework.View.BaseActivity;

import org.grammaticalframework.View.HomeActivity;
import org.grammaticalframework.ViewModel.MainViewModel;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new MainViewModel();
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, HomeActivity.class);
        //startActivity(intent);
    }

    @Override
    public MainViewModel getViewModel() {
        return viewModel;
    }
}
