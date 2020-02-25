package org.grammaticalframework.View;

import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.View.Fragments.MainExerciseFragment;
import org.grammaticalframework.View.Fragments.MainHomeFragment;
import org.grammaticalframework.View.Fragments.MainLexiconFragment;

public class FragmentFactory {
    public static BaseFragment createMainExerciseFragment(){
        return new MainExerciseFragment();
    }

    public static BaseFragment createMainLexiconFragment(){
        return new MainLexiconFragment();
    }

    public static BaseFragment createMainHomeFragment(){
        return new MainHomeFragment();
    }

}
