package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SearchMultiModel implements Parcelable {

    private int id;
    private String title; //movie
    private String name; //tv
    private String original_name;
    private String poster_path;
    private String media_type;


    public SearchMultiModel(int id, String title, String name, String original_name, String poster_path, String media_type) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.original_name = original_name;
        this.poster_path = poster_path;
        this.media_type = media_type;
    }

    protected SearchMultiModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        name = in.readString();
        original_name = in.readString();
        poster_path = in.readString();
        media_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(name);
        dest.writeString(original_name);
        dest.writeString(poster_path);
        dest.writeString(media_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchMultiModel> CREATOR = new Creator<SearchMultiModel>() {
        @Override
        public SearchMultiModel createFromParcel(Parcel in) {
            return new SearchMultiModel(in);
        }

        @Override
        public SearchMultiModel[] newArray(int size) {
            return new SearchMultiModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getMedia_type() {
        return media_type;
    }

}
