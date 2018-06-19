package com.arnela.rubiconapp.Ui.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.SuggestionVm;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.R;
import com.arnela.rubiconapp.Ui.detail.DetailActivity;
import com.arnela.rubiconapp.Util.DialogFactory;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TextWatcher, ItemClickListener, MainActivityMvpView {

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.searchBar)
    MaterialSearchBar mSearchBar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    Timer timer;

    // The pager adapter, which provides the pages to the view pager widget.
    private PageAdapterHandler mPagerAdapter;
    private MainActivityPresenter mPresenter;
    private CustomSuggestionsAdapter mCustomSuggestionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mCustomSuggestionsAdapter = new CustomSuggestionsAdapter(inflater, this);
        mSearchBar.setCustomSuggestionAdapter(mCustomSuggestionsAdapter);

        DataManager dataManager = new DataManager();
        mPresenter = new MainActivityPresenter(dataManager);
        mPresenter.attachView(this);
        mPresenter.loadListData(mPager.getCurrentItem() == 0);

        mSearchBar.addTextChangeListener(this);

        mPagerAdapter = mPresenter.getFragmentManager(getSupportFragmentManager());

        setupViewPager();
        tabLayout.setupWithViewPager(mPager);

    }

    public void setupViewPager() {
        mPagerAdapter.addFragment(new MoviesFragment(), getResources().getString(R.string.movies));
        mPagerAdapter.addFragment(new TvShowFragment(), getResources().getString(R.string.tv_shows));
        mPager.setPageTransformer(false, mPresenter.getAnimationForFragments());
        mPager.setAdapter(mPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0: // movie fragment
                        mPresenter.loadListData(true);
                        break;
                    case 1: // tv show fragment
                        mPresenter.loadListData(false);
                        break;
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void afterTextChanged(final Editable s) {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (s.length() < 3) {
                    mPresenter.loadListData(mPager.getCurrentItem() == 0);
                } else {
                    mPresenter.loadListSearch(mSearchBar.getText(), mPager.getCurrentItem() == 0);
                }
            }
        }, 200);
    }

    @Override
    public void onItemClickListener(int programId) { // TV shows or Movie ID

        Intent intentDetailActivity = new Intent(MainActivity.this, DetailActivity.class);

        // Depending on what screen we are, we need to create appropriate key for opening new activity
        String screenKey = mPager.getCurrentItem() == 0 ? "movie_id" : "tv_show_id";
        intentDetailActivity.putExtra(screenKey, programId);

        startActivity(intentDetailActivity);
    }

    @Override
    public void showSearchResult(List<TvMovieVm> movies) {

        List<SuggestionVm> helperList = new ArrayList<>();

        // update data for suggestions in search
        for (TvMovieVm item : movies) {
            helperList.add(new SuggestionVm(item.BackdropPath, item.Title, item.Id));
        }

        mCustomSuggestionsAdapter.setSuggestions(helperList);
    }

    @Override
    public void showSearchResultEmpty() {
        mCustomSuggestionsAdapter.setSuggestions(new ArrayList<SuggestionVm>());
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading))
                .show();
    }

}
