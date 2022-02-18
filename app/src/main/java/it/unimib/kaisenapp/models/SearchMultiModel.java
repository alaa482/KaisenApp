package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SearchMultiModel implements Parcelable, Serializable {

    private int id;
    private String title;
    private String original_title;
    private String poster_path;
    private String media_type;

    public SearchMultiModel(int id, String title, String original_title, String poster_path, String media_type) {
        this.id = id;
        this.title = title;
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
}
