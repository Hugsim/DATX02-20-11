package org.grammaticalframework.ViewModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.grammaticalframework.R;

import java.util.List;

public class LexiconDetailsAdapter extends RecyclerView.Adapter<LexiconDetailsAdapter.WordItemViewHolder> {
    private List<String> inflections;
    private static final String TAG = LexiconDetailsAdapter.class.getSimpleName();

    public LexiconDetailsAdapter(List<String> inflections) {
        this.inflections = inflections;
    }

    @Override
    public LexiconDetailsAdapter.WordItemViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        final View wordItemView = inflater.inflate(R.layout.lexicondetails_recycleritem, parent, false);
        LexiconDetailsAdapter.WordItemViewHolder viewHolder = new LexiconDetailsAdapter.WordItemViewHolder(wordItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LexiconDetailsAdapter.WordItemViewHolder viewHolder, int position) {
        String word = inflections.get(position);

        viewHolder.wordTextView.setText(word);
    }

    @Override
    public int getItemCount() {
        return inflections.size();
    }

    public class WordItemViewHolder extends RecyclerView.ViewHolder {
        public TextView wordTextView;
        public WordItemViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.item);
        }
    }
}
