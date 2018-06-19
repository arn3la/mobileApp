package com.arnela.rubiconapp.Ui.main;


import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.Ui.base.BasePresenter;
import com.arnela.rubiconapp.Util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentPresenter extends BasePresenter<MainMvpView> {

    private DataManager mDataManager;
    private Disposable mDisposable;

    public MainFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    /**
     * Get movies/tv shows from external API
     */
    public void loadListData(boolean isMovie) {

        checkViewAttached();
        RxUtil.dispose(mDisposable);

        mDataManager.getListData(isMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ListWrapper<TvMovieVm>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListWrapper<TvMovieVm> movies) {

                        if (movies.Results.isEmpty()) {
                            getMvpView().showTvMovieListEmpty();
                        } else {
                            if (movies.Results.get(0) != null) {
                                getMvpView().showTvMovieList(movies.Results);
                            }
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

}
