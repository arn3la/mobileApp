package com.arnela.rubiconapp.Ui.main;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.Data.Helper.RoutesConstants;
import com.arnela.rubiconapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TvMovieListAdapter extends RecyclerView.Adapter<TvMovieListAdapter.TvMovieViewHolder> {

    private List<TvMovieVm> mMovieList;
    private ItemClickListener mListener;

    public TvMovieListAdapter(ItemClickListener listener) {
        mMovieList = new ArrayList<>();
        mListener = listener;
    }

    public void setSource(List<TvMovieVm> movies) {
        mMovieList = movies;
    }

    public List<TvMovieVm> getSource() {
        return mMovieList;
    }


    @NonNull
    @Override
    public TvMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movies, parent, false);

        return new TvMovieViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final TvMovieViewHolder holder, int position) {

        TvMovieVm movie = mMovieList.get(position);

        holder.mTxtTitle.setText(movie.Title);
        holder.mTxtGrade.setText(String.valueOf(movie.VoteAverage));
        holder.MovieId = movie.Id;

        Picasso.get().load(RoutesConstants.BASE_URL_IMG + movie.BackdropPath)
                .placeholder(R.drawable.img_placeholder)
                .transform(new CropCircleTransformation())
                .into(holder.mImgMovie);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class TvMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_title_item)
        TextView mTxtTitle;
        @BindView(R.id.txt_grade)
        TextView mTxtGrade;
        @BindView(R.id.img_movie_pic)
        ImageView mImgMovie;
        private ItemClickListener mListener;
        public int MovieId;

        public TvMovieViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClickListener(MovieId);
        }
    }

}
