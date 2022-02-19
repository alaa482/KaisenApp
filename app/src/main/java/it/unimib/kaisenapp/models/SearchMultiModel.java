package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SearchMultiModel implements Parcelable {

    private int id;
    private String title;
    private String original_title;
    private String original_name;
    private String poster_path;
    private String media_type;

    public SearchMultiModel(int id, String title, String original_title, String original_name,String poster_path, String media_type) {
        this.id = id;
        this.title = title;
        this.original_name=original_name;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.media_type = media_type;
    }


    protected SearchMultiModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        original_title = in.readString();
        poster_path = in.readString();
        media_type = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(media_type);
    }

    @Override
    public String toString() {
        return "SearchMultiModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", original_title='" + original_title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", media_type='" + media_type + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getOriginal_name() {
        return original_name;
    }
}
