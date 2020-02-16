package org.grammaticalframework.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.grammaticalframework.R;

import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment {

    private int pageIndex;

    public static NavigationFragment newInstance(){
        return new NavigationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pageIndex = this.getArguments().getInt("index", 0);
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }


}
