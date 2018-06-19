package com.arnela.rubiconapp.Ui.main;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.R;

import butterknife.BindView;

public class SuggestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.txt_title_item)
    TextView title;
    @BindView(R.id.img_movie_pic)
    ImageView image;
    private ItemClickListener mListener;
    public int ItemId;

    public SuggestionHolder(View itemView, ItemClickListener listener) {
        super(itemView);

        title = itemView.findViewById(R.id.txt_title_item);
        image = itemView.findViewById(R.id.img_movie_pic);

        itemView.setOnClickListener(this);

        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClickListener(ItemId);
    }

}
