package org.grammaticalframework.View;
import org.grammaticalframework.ViewModel.MainViewModel;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new MainViewModel();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public MainViewModel getViewModel() {
        return viewModel;
    }
}
