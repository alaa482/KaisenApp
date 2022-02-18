package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TvSerieModel implements Parcelable {

    private String name;        // nome episodio
    private String still_path;  // img episodio
    private String overview;
    private int episode_number;
    private int id;             // id episodio

    // Constructor
    public TvSerieModel(String name, String still_path, String overview, int episode_number, int id) {
        this.name = name;
        this.still_path = still_path;
        this.overview = overview;
        this.episode_number = episode_number;
        this.id = id;
    }


    protected TvSerieModel(Parcel in) {
        name = in.readString();
        still_path = in.readString();
        overview = in.readString();
        episode_number = in.readInt();
        id = in.readInt();
    }

    public static final Creator<TvSerieModel> CREATOR = new Creator<TvSerieModel>() {
        @Override
        public TvSerieModel createFromParcel(Parcel in) {
            return new TvSerieModel(in);
        }

        @Override
        public TvSerieModel[] newArray(int size) {
            return new TvSerieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStill_path() {
        return still_path;
    }

    public String getOverview() {
        return overview;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TvSerieModel{" +
                "name='" + name + '\'' +
                ", still_path='" + still_path + '\'' +
                ", overview='" + overview + '\'' +
                ", episode_number=" + episode_number +
                ", id=" + id +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(still_path);
        dest.writeString(overview);
        dest.writeInt(episode_number);
        dest.writeInt(id);
    }
}