package org.grammaticalframework.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    public void onStart(){
        super.onStart();
        BottomNavigationView bottomNavigationView = getView().findViewById(R.id.navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(pageIndex);
        menuItem.setChecked(true);

    }


}
