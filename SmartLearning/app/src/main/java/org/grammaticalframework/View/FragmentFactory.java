package org.grammaticalframework.View;

import org.grammaticalframework.View.Fragments.BaseFragment;
import org.grammaticalframework.View.Fragments.Exercise_nav_graph_Fragment;
import org.grammaticalframework.View.Fragments.GrammarFragment;
import org.grammaticalframework.View.Fragments.MainHomeFragment;
import org.grammaticalframework.View.Fragments.MainLexiconFragment;
import org.grammaticalframework.View.Fragments.VocabularyFragment;

public class FragmentFactory {
    public static BaseFragment createMainExerciseFragment(){ return new Exercise_nav_graph_Fragment(); }

    public static BaseFragment createMainLexiconFragment(){
        return new MainLexiconFragment();
    }

    public static BaseFragment createMainHomeFragment(){
        return new MainHomeFragment();
    }

    public static BaseFragment createGrammarFragment(){ return new GrammarFragment(); }

    public static BaseFragment createVocabularyFragment(){return new VocabularyFragment();}
}
