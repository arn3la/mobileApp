package com.arnela.rubiconapp.Data.Helper;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListWrapper<T> {

    @SerializedName("results")
    @Expose
    public List<T> Results;

    @SerializedName("page")
    @Expose
    public int Page;

    @SerializedName("total_pages")
    @Expose
    public int TotalPages;

    @SerializedName("total_results")
    @Expose
    public int TotalResults;

}
