package org.grammaticalframework.View.Fragments;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private String fragmentTag;

    public void setFragmentTag(String tag) {
        fragmentTag = tag;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }
}
