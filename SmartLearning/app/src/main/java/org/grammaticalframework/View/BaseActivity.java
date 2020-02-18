package org.grammaticalframework.View;

import org.grammaticalframework.ViewModel.BaseViewModel;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    abstract public BaseViewModel getViewModel();
}
