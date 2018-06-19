package com.arnela.rubiconapp.Ui.main;


import android.support.v4.app.FragmentManager;

import com.arnela.rubiconapp.Data.DataManager;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.Ui.base.BasePresenter;
import com.arnela.rubiconapp.Util.RxUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenter extends BasePresenter<MainActivityMvpView> {

    private DataManager mDataManager;
    private Disposable mDisposable;

    public MainActivityPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainActivityMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public PageAdapterHandler getFragmentManager(FragmentManager fm) {
        return new PageAdapterHandler(fm);
    }

    public ZoomOutPageTransformer getAnimationForFragments() {
        return new ZoomOutPageTransformer();
    }

    /**
     * Get searched movies/tv shows from external API for suggestion
     */
    public void loadListSearch(String searchTitle, boolean isMovie) {

        mDataManager.getListSearch(searchTitle, isMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ListWrapper<TvMovieVm>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListWrapper<TvMovieVm> result) {

                        if (result.Results.isEmpty()) {
                            getMvpView().showSearchResultEmpty();
                        } else {
                            if (result.Results.get(0) != null) {
                                getMvpView().showSearchResult(result.Results);
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
                    public void onNext(ListWrapper<TvMovieVm> result) {

                        if (result.Results.isEmpty()) {
                            getMvpView().showSearchResultEmpty();
                        } else {
                            if (result.Results.get(0) != null) {
                                int limit = result.Results.size() > 10 ? 10 : result.Results.size();
                                getMvpView().showSearchResult(result.Results.subList(0, limit));
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
