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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment {

    /*private int pageIndex;

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
        final Intent intentExercise = new Intent(getActivity(),ExerciseActivity.class);

        BottomNavigationView bottomNavigationView = getView().findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(pageIndex);
        menuItem.setChecked(true);*/

        /*bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                intent = intentExercise;
                                break;
                            case R.id.action_item2:
                                intent = intentLexicon;
                                break;
                            case R.id.action_item3:
                                intent = intentLanguage;
                                break;
                            default:
                                intent = intentExercise;
                                break;
                        }

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition(0, 0);
                        return true;
                    }
                });

    }*/


}
