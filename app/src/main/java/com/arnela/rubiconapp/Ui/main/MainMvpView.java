package com.arnela.rubiconapp.Ui.main;


import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.Ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showTvMovieList(ListWrapper<TvMovieVm> movies);

    void showTvMovieListEmpty();

    void showError();

}
