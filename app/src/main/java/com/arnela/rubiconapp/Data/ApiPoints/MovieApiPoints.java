package com.arnela.rubiconapp.Data.ApiPoints;


import com.arnela.rubiconapp.Data.DataModels.TvMovieVm;
import com.arnela.rubiconapp.Data.Helper.ListWrapper;
import com.arnela.rubiconapp.Data.Helper.RoutesConstants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiPoints {

    @GET(RoutesConstants.URL_MOVIE_GET)
    Observable<ListWrapper<TvMovieVm>> GetMovies();

    @GET(RoutesConstants.URL_SHOW_GET)
    Observable<ListWrapper<TvMovieVm>> GetShows();

    @GET(RoutesConstants.URL_MOVIE_DETAIL_GET + "{movie_id}" + RoutesConstants.BASE_URL_API_KEY)
    Observable<TvMovieVm> GetDetailMovie(@Path("movie_id") String movieId);

    @GET(RoutesConstants.URL_TV_SHOW_DETAIL_GET + "{tv_id}" + RoutesConstants.BASE_URL_API_KEY)
    Observable<TvMovieVm> GetDetailTvShow(@Path("tv_id") String tviD);

    @GET(RoutesConstants.URL_MOVIE_GET_SEARCH)
    Observable<ListWrapper<TvMovieVm>> GetMoviesSearch(@Query("query") String Name);

    @GET(RoutesConstants.URL_SHOW_GET_SEARCH)
    Observable<ListWrapper<TvMovieVm>> GetShowsSearch(@Query("query") String Title);

}
