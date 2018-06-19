package com.arnela.rubiconapp.Ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.R;
import com.arnela.rubiconapp.Ui.detail.DetailActivity;
import com.arnela.rubiconapp.Util.DialogFactory;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowFragment extends Fragment implements MainMvpView, ItemClickListener {

    private MainFragmentPresenter mMainFragmentPresenter;
    private TvMovieListAdapter mMovieAdapter;

    @BindView(R.id.rv_movies)
    RecyclerView mRVMovies;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, rootView);

        DataManager dataManager = new DataManager();

        mMovieAdapter = new TvMovieListAdapter(this);
        mMainFragmentPresenter = new MainFragmentPresenter(dataManager);

        mRVMovies.setAdapter(mMovieAdapter);
        mRVMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainFragmentPresenter.attachView(this);
        mMainFragmentPresenter.loadListData(false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMainFragmentPresenter.detachView();
    }

    @Override
    public void showTvMovieList(List<TvMovieVm> response) {

        int limit = response.size() > 10 ? 10 : response.size();
        mMovieAdapter.setSource(response.subList(0, limit));
        mMovieAdapter.notifyDataSetChanged();

    }

    @Override
    public void showTvMovieListEmpty() {

        mMovieAdapter.setSource(Collections.<TvMovieVm>emptyList());
        mMovieAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), R.string.empty_list_show, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getActivity(), getString(R.string.error_loading_list_show))
                .show();
    }

    @Override
    public void onItemClickListener(int position) {

        Intent intentDetailActivity = new Intent(getActivity(), DetailActivity.class);
        intentDetailActivity.putExtra("tv_show_id", position);

        startActivity(intentDetailActivity);

    }

}
