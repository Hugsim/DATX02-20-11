package org.grammaticalframework.ViewModel;

import org.grammaticalframework.SmartLearning;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    private final static SmartLearning model = new SmartLearning();

    public static SmartLearning getModel() {
        return model;
    }
}
