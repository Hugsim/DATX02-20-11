package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.BaseViewModel;
import org.grammaticalframework.ViewModel.LexiconViewModel;

public class LexiconActivity extends BaseActivity {
    private LexiconViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon);
        viewModel = new LexiconViewModel();
    }

    @Override
    public BaseViewModel getViewModel() {
        return viewModel;
    }
}
