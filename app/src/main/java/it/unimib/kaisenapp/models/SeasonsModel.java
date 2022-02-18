package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SeasonsModel implements Parcelable {
    private String name;
    private int id;
    private String poster_path;
    private int season_number;

    protected SeasonsModel(Parcel in) {
        name = in.readString();
        id = in.readInt();
        poster_path = in.readString();
        season_number = in.readInt();
    }

    public static final Creator<SeasonsModel> CREATOR = new Creator<SeasonsModel>() {
        @Override
        public SeasonsModel createFromParcel(Parcel in) {
            return new SeasonsModel(in);
        }

        @Override
        public SeasonsModel[] newArray(int size) {
            return new SeasonsModel[size];
        }
    };

    public int getSeason_number() {
        return season_number;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public String toString() {
        return "SeasonsModel{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(poster_path);
        dest.writeInt(season_number);
    }
}
