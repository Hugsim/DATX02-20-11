package org.grammaticalframework.View;

import android.os.Bundle;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.LanguageViewModel;

public class LanguageActivity extends BaseActivity {
    private LanguageViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        viewModel = new LanguageViewModel();
    }

    @Override
    public BaseViewModel getViewModel() {
        return viewModel;
    }
}
