package com.arnela.rubiconapp.Data;


import com.arnela.rubiconapp.Data.ApiPoints.MovieApiPoints;
import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;


import io.reactivex.Observable;

public class DataManager {

    public Observable<ListWrapper<TvMovieVm>> getListData(boolean isMovie) {
        MovieApiPoints client = ApiUtils.getMovieClient();
        if (isMovie) return client.GetMovies();
        else return client.GetShows();
    }

    public Observable<TvMovieVm> getDetailData(int movieId, boolean IsScreenMovies) {
        MovieApiPoints client = ApiUtils.getMovieClient();
        if (IsScreenMovies) return client.GetDetailMovie(String.valueOf(movieId));
        else return client.GetDetailTvShow(String.valueOf(movieId));
    }

    public Observable<ListWrapper<TvMovieVm>> getListSearch(String query, boolean isMovie) {
        MovieApiPoints client = ApiUtils.getMovieClient();
        if (isMovie) return client.GetMoviesSearch(query);
        else return client.GetShowsSearch(query);
    }

}
