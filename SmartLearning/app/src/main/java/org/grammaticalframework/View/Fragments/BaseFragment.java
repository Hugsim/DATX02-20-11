package org.grammaticalframework.View.Fragments;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private String fragmentTag;

    public BaseFragment(String tag) {
        fragmentTag = tag;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }
}
