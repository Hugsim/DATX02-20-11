package org.grammaticalframework.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.grammaticalframework.R;
import org.grammaticalframework.ViewModel.LexiconWord;
import org.grammaticalframework.ViewModel.LexiconWordAdapter;

public class LexiconDetailsFragment extends BaseFragment {

    private ImageView backButton;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_lexicon_details, container, false);

        navController = Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment));
        backButton = fragmentView.findViewById(R.id.lexicon_details_back);

        backButton.setOnClickListener((v) -> {
            navController.navigate(R.id.action_lexiconDetailsFragment_to_lexiconFragment);
            /*AppCompatActivity activity = (AppCompatActivity) fragmentView.getContext();
            Fragment lexiconFragment = getFragmentManager().findFragmentByTag("lexiconFragment");
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, lexiconFragment).addToBackStack(null).commit();*/
        });

        return fragmentView;

    }
}
