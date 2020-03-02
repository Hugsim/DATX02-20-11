package org.grammaticalframework.View;

import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.View.Fragments.LexiconDetailsFragment;
import org.grammaticalframework.View.Fragments.MainHomeFragment;
import org.grammaticalframework.View.Fragments.MainLexiconFragment;

public class FragmentFactory {
    public static BaseFragment createMainLexiconFragment(){
        return new MainLexiconFragment();
    }
    public static BaseFragment createMainHomeFragment(){
        return new MainHomeFragment();
    }

    public static BaseFragment createLexiconDetailsFragment() {return new LexiconDetailsFragment(); }
}
