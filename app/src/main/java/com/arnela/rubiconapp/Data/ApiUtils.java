package com.arnela.rubiconapp.Data;


import com.arnela.rubiconapp.Data.ApiPoints.MovieApiPoints;
import com.arnela.rubiconapp.Data.Helper.RoutesConstants;

public class ApiUtils {

    public static MovieApiPoints getMovieClient() {
        return RetrofitClient.getClient(RoutesConstants.BASE_URL).create(MovieApiPoints.class);
    }

}
