package com.arnela.rubiconapp.Data.Helper;


public class RoutesConstants {

    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w500";
    public static final String BASE_URL_API_KEY = "?api_key=b9afe0c03970142d5f4e292188bde800&language=en-US";

    //list
    public static final String URL_MOVIE_GET = "3/movie/top_rated" + BASE_URL_API_KEY + " &page=1";
    public static final String URL_SHOW_GET = "3/tv/top_rated" + BASE_URL_API_KEY + "&page=1";

    //detail
    public static final String URL_MOVIE_DETAIL_GET = "3/movie/";
    public static final String URL_TV_SHOW_DETAIL_GET = "3/tv/";

    //search
    public static final String URL_MOVIE_GET_SEARCH = "3/search/movie/" + BASE_URL_API_KEY + "&page=1";
    public static final String URL_SHOW_GET_SEARCH = "3/search/tv/" + BASE_URL_API_KEY + "&page=1";

}
