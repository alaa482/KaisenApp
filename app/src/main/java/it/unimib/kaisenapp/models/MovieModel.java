package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {
    //Model class per i film
    private String title;
    private String poster_path;
    private String release_date;
    private int id;
    private float vote_average;
    private String overview;
    private String category;
    private int runtime;
    private String name;

    public MovieModel(String title, String poster_path, String release_date, int id, float vote_average, String overview,int runtime,String name ,String category) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.id = id;
        this.vote_average = vote_average;
        this.overview = overview;
        this.runtime=runtime;
        this.name=name;
        this.category=category;
    }


    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        overview = in.readString();
        runtime = in.readInt();
        name= in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getMovie_overview() {
        return overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeInt(id);
        parcel.writeFloat(vote_average);
        parcel.writeString(overview);
        parcel.writeInt(runtime);
        parcel.writeString(name);
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", movie_id=" + id +
                ", vote_average=" + vote_average +
                ", movie_overview='" + overview + '\'' +
                '}';
    }
}
