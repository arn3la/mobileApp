package com.arnela.rubiconapp.Ui.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.SuggestionVm;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ItemClickListener;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.R;
import com.arnela.rubiconapp.Ui.detail.DetailActivity;
import com.arnela.rubiconapp.Util.DialogFactory;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TextWatcher, ItemClickListener, MainActivityMvpView {

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.searchBar)
    MaterialSearchBar mSearchBar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    // The pager adapter, which provides the pages to the view pager widget.
    private PageAdapterHandler mPagerAdapter;
    private DataManager dataManager;
    private MainActivityPresenter mPresenter;
    private CustomSuggestionsAdapter mCustomSuggestionsAdapter;
    private List<SuggestionVm> mSourceTopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mCustomSuggestionsAdapter = new CustomSuggestionsAdapter(inflater, this);
        mSearchBar.setCustomSuggestionAdapter(mCustomSuggestionsAdapter);

        dataManager = new DataManager();
        mPresenter = new MainActivityPresenter(dataManager);
        mPresenter.attachView(this);

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
    }

    // This method will be called from fragment when data in list view change
    public void updateData(List<SuggestionVm> dataSet) {

        mSourceTopList = dataSet;
        mCustomSuggestionsAdapter.setSuggestions(dataSet);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s.length() >= 3) {
            if (mPager.getCurrentItem() == 0) {
                mPresenter.loadListSearch(mSearchBar.getText(), true);
            } else {
                mPresenter.loadListSearch(mSearchBar.getText(), false);
            }
        } else {
            mCustomSuggestionsAdapter.setSuggestions(mSourceTopList.subList(0, 10));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void ItemClickListener(int programId) { // TV shows or Movie ID

        Intent intentDetailActivity = new Intent(MainActivity.this, DetailActivity.class);

        // Depending on what screen we are, we need to create appropriate key for opening new activity
        String screenKey = mPager.getCurrentItem() == 0 ? "movie_id" : "tv_show_id";
        intentDetailActivity.putExtra(screenKey, programId);

        startActivity(intentDetailActivity);
    }

    @Override
    public void showSearchResult(ListWrapper<TvMovieVm> movies) {

        List<SuggestionVm> helperList = new ArrayList<>();

        // update data for suggestions in search
        for (TvMovieVm item : movies.Results) {
            helperList.add(new SuggestionVm(item.BackdropPath, item.Title, item.Id));
        }

        mCustomSuggestionsAdapter.setSuggestions(helperList);
    }

    @Override
    public void showSearchResultEmpty() {
        mCustomSuggestionsAdapter.clearSuggestions();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading))
                .show();
    }

}
