package com.arnela.rubiconapp.Ui.main;


import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showTvMovieList(List<TvMovieVm> movies);

    void showTvMovieListEmpty();

    void showError();

}
