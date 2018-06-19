package com.arnela.rubiconapp.Ui.main;


import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.Ui.base.MvpView;

public interface MainActivityMvpView extends MvpView {

    void showSearchResult(ListWrapper<TvMovieVm> movies);

    void showSearchResultEmpty();

    void showError();

}
