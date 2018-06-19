package com.arnela.rubiconapp.Data.DataModels;


import android.os.Parcel;
import android.os.Parcelable;

public class SuggestionVm implements Parcelable {

    public SuggestionVm(String backdropPath, String title, int id) {
        BackdropPath = backdropPath;
        Title = title;
        Id = id;
    }

    private String BackdropPath;
    private String Title;
    private int Id;

    protected SuggestionVm(Parcel in) {
        BackdropPath = in.readString();
        Title = in.readString();
        Id = in.readInt();
    }

    public static final Creator<SuggestionVm> CREATOR = new Creator<SuggestionVm>() {
        @Override
        public SuggestionVm createFromParcel(Parcel in) {
            return new SuggestionVm(in);
        }

        @Override
        public SuggestionVm[] newArray(int size) {
            return new SuggestionVm[size];
        }
    };

    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Title);
        dest.writeString(this.BackdropPath);
    }

}
