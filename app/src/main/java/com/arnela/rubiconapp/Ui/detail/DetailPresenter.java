package com.arnela.rubiconapp.Ui.detail;


import android.content.Intent;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Ui.base.BasePresenter;
import com.arnela.rubiconapp.Util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailPresenter extends BasePresenter<DetailMvpView> {

    private DataManager mDataManager;
    private Disposable mDisposable;

    public DetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void loadInfoDetail(int movieId, boolean IsScreenMovies) {

        checkViewAttached();
        RxUtil.dispose(mDisposable);

        mDataManager.getDetailData(movieId, IsScreenMovies)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TvMovieVm>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TvMovieVm movie) {

                        if (movie != null) {
                            getMvpView().showInfo(movie);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // Helper method to check from what fragment user has clicked so we can be able to get id of movie / tv show
    public ScreenShow getTv(Intent intent) {

        if (intent.hasExtra("movie_id"))
            return new ScreenShow(true, intent.getIntExtra("movie_id", 0));
        if (intent.hasExtra("tv_show_id"))
            return new ScreenShow(false, intent.getIntExtra("tv_show_id", 0));

        return null;
    }

    public class ScreenShow {

        public boolean IsScreenMovies;
        public int Id;

        public ScreenShow(boolean isScreenMovies, int id) {
            IsScreenMovies = isScreenMovies;
            Id = id;
        }
    }

}
