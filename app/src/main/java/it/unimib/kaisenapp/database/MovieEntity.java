package it.unimib.kaisenapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import it.unimib.kaisenapp.Constants;

@Entity(tableName = Constants.MOVIE_TABLE_NAME)
public class MovieEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="movie_id")
    private int movie_id;
    @ColumnInfo(name = "poster_path")
    private String poster_path;

    public MovieEntity(int id, int movie_id, String poster_path) {
        this.id = id;
        this.movie_id = movie_id;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
