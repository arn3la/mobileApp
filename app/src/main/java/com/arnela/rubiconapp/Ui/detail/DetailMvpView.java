package com.arnela.rubiconapp.Ui.detail;


import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Ui.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showInfo(TvMovieVm movie);

    void showError();

}
