package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowModel implements Parcelable {

    private String name;
    private String poster_path;
    private int id;
    private String overview;
    private String category;

    public TvShowModel(String name, String poster_path, int id, String overview, String category) {
        this.name = name;
        this.poster_path = poster_path;
        this.id = id;
        this.overview = overview;
        this.category = category;
    }

    protected TvShowModel(Parcel in) {
        name = in.readString();
        poster_path = in.readString();
        id = in.readInt();
        overview = in.readString();
        category = in.readString();
    }

    public static final Creator<TvShowModel> CREATOR = new Creator<TvShowModel>() {
        @Override
        public TvShowModel createFromParcel(Parcel in) {
            return new TvShowModel(in);
        }

        @Override
        public TvShowModel[] newArray(int size) {
            return new TvShowModel[size];
        }
    };

    public String getCategory() {
        return category;
    }


    public String getName() {
        return name;
    }



    public String getPoster_path() {
        return poster_path;
    }


    public int getId() {
        return id;
    }


    public String getOverview() {
        return overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(poster_path);
        parcel.writeInt(id);
        parcel.writeString(overview);
        parcel.writeString(category);
    }

    @Override
    public String toString() {
        return "TvShowModel{" +
                "name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", id=" + id +
                ", overview='" + overview + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public void setCategory(String category) {
        this.category=category;
    }
}
