package com.arnela.rubiconapp.Ui.main;


import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Ui.base.MvpView;

import java.util.List;

public interface MainActivityMvpView extends MvpView {

    void showSearchResult(List<TvMovieVm> movies);

    void showSearchResultEmpty();

    void showError();

}
