package com.arnela.rubiconapp.Ui.main;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.arnela.rubiconapp.Data.DataModels.SuggestionVm;
import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.Data.Helper.RoutesConstants;
import com.arnela.rubiconapp.R;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CustomSuggestionsAdapter extends SuggestionsAdapter<SuggestionVm, SuggestionHolder> {

    private ItemClickListener mListener;

    public CustomSuggestionsAdapter(LayoutInflater inflater, ItemClickListener listener) {
        super(inflater);
        mListener = listener;
    }

    @Override
    public int getSingleViewHeight() {
        return 80;
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_movies, parent, false);
        return new SuggestionHolder(view, mListener);
    }

    @Override
    public void onBindSuggestionHolder(SuggestionVm suggestion, SuggestionHolder holder, int position) {

        holder.image.setVisibility(View.VISIBLE);
        holder.title.setVisibility(View.VISIBLE);

        holder.ItemId = suggestion.getId();
        holder.title.setText(suggestion.getTitle());

        Picasso.get().load(RoutesConstants.BASE_URL_IMG + suggestion.getBackdropPath())
                .placeholder(R.drawable.img_placeholder)
                .transform(new CropCircleTransformation())
                .into(holder.image);

    }

    /**
     * <b>Override to customize functionality</b>
     * <p>Returns a filter that can be used to constrain data with a filtering
     * pattern.</p>
     * <p>
     * <p>This method is usually implemented by {RecyclerView.Adapter}
     * classes.</p>
     *
     * @return a filter used to constrain data
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String term = constraint.toString();
                if (term.isEmpty() || term.length() < 3) {
                    suggestions = suggestions_clone;
                } else {
                    suggestions = new ArrayList<>();
                    for (SuggestionVm item : suggestions_clone)
                        if (item.getTitle().toLowerCase().contains(term.toLowerCase()))
                            suggestions.add(item);
                }
                results.values = suggestions;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestions = (ArrayList<SuggestionVm>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
