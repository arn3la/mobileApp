package com.arnela.rubiconapp.Data.DataModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TvMovieVm {

    @SerializedName(value = "NO NAME", alternate = {"title", "name"})
    public String Title;

    @SerializedName("backdrop_path")
    @Expose
    public String BackdropPath;

    @SerializedName("id")
    @Expose
    public int Id;

    @SerializedName("vote_average")
    @Expose
    public float VoteAverage;

    @SerializedName("overview")
    @Expose
    public String Overview;

}
