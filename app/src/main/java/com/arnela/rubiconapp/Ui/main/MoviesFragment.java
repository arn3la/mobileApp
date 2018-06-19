package com.arnela.rubiconapp.Ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.SuggestionVm;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.R;
import com.arnela.rubiconapp.Ui.detail.DetailActivity;
import com.arnela.rubiconapp.Util.DialogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesFragment extends Fragment implements MainMvpView, ItemClickListener {

    private MainFragmentPresenter mMainFragmentPresenter;
    private TvMovieListAdapter mMovieAdapter;
    private List<SuggestionVm> mListSource = new ArrayList<>();

    @BindView(R.id.rv_movies)
    RecyclerView mRVMovies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, rootView);

        DataManager dataManager = new DataManager();

        mMovieAdapter = new TvMovieListAdapter(this);
        mMainFragmentPresenter = new MainFragmentPresenter(dataManager);

        mRVMovies.setAdapter(mMovieAdapter);
        mRVMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainFragmentPresenter.attachView(this);

        mMainFragmentPresenter.loadListData(true);

        // Inflate the layout for this fragment
        return rootView;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMainFragmentPresenter.detachView();
    }

    @Override
    public void showTvMovieList(ListWrapper<TvMovieVm> response) {

        mMovieAdapter.setSource(response.Results.subList(0, 10));
        mMovieAdapter.notifyDataSetChanged();

        // update data for suggestions in search
        for (TvMovieVm item : response.Results) {
            mListSource.add(new SuggestionVm(item.BackdropPath, item.Title, item.Id));
        }

        // update suggestions on main activity
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateData(mListSource);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            ((MainActivity) getActivity()).updateData(mListSource);
        }
    }

    @Override
    public void showTvMovieListEmpty() {

        mMovieAdapter.setSource(Collections.<TvMovieVm>emptyList());
        mMovieAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), R.string.empty_list_movies, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getActivity(), getString(R.string.error_loading_list_movies))
                .show();
    }

    @Override
    public void ItemClickListener(int movieId) {

        Intent intentDetailActivity = new Intent(getActivity(), DetailActivity.class);
        intentDetailActivity.putExtra("movie_id", movieId);

        startActivity(intentDetailActivity);
    }

}
