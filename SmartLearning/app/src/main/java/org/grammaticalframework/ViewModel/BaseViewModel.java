package org.grammaticalframework.ViewModel;

import org.grammaticalframework.Grammarlex;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {

    private final static Grammarlex model = new Grammarlex();

    public static Grammarlex getModel() {
        return model;
    }
}
