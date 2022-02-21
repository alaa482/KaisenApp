package it.unimib.kaisenapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

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
    @ColumnInfo(name="runtime")
    private int runtime;

    public MovieEntity(int movie_id, String poster_path, String category, boolean watched, boolean saved, boolean favorite, int runtime) {
        this.movie_id = movie_id;
        this.poster_path = poster_path;
        this.category=category;
        this.saved=saved;
        this.watched=watched;
        this.favorite=favorite;
        this.runtime=runtime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return movie_id == that.movie_id;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "movie_id=" + movie_id +
                ", poster_path='" + poster_path + '\'' +
                ", category='" + category + '\'' +
                ", watched=" + watched +
                ", saved=" + saved +
                ", favorite=" + favorite +
                ", runtime=" + runtime +
                '}';
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie_id);
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
    public String getWatched() {
        if(watched)
            return "WATCHED";
        else
            return "false";
    }
    public String getSaved() {
        if(saved)
            return "true";
        else
            return "false";
    }
    public String getFavorite() {
       if(favorite)
           return "FAVORITES";
       else
           return "false";
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
