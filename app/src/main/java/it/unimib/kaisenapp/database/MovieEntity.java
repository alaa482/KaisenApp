package it.unimib.kaisenapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import it.unimib.kaisenapp.utils.Constants;

@Entity(tableName = Constants.MOVIE_TABLE_NAME)
public class MovieEntity {

    @PrimaryKey()
    @ColumnInfo(name="movie_id")
    private int movie_id;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name="category")
    private String category;
    @ColumnInfo(name="watched")
    private boolean watched;
    @ColumnInfo(name="saved")
    private boolean saved;
    @ColumnInfo(name="favorite")
    private boolean favorite;


    public MovieEntity(int movie_id, String poster_path, String category, boolean watched, boolean saved, boolean favorite) {
        this.movie_id = movie_id;
        this.poster_path = poster_path;
        this.category=category;
        this.saved=saved;
        this.watched=watched;
        this.favorite=favorite;
    }



    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
