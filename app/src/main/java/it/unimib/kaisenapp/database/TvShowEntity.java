package it.unimib.kaisenapp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import it.unimib.kaisenapp.utils.Constants;

@Entity(tableName = Constants.TV_SHOW_TABLE_NAME)
public class TvShowEntity {

    @PrimaryKey()
    @ColumnInfo(name="id")
    private int id;
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
    @ColumnInfo(name="numEpisode")
    private int numEpisode;

    public TvShowEntity(int id, String poster_path, String category, boolean watched, boolean saved, boolean favorite, int runtime, int numEpisode) {
        this.id = id;
        this.poster_path = poster_path;
        this.category=category;
        this.saved=saved;
        this.watched=watched;
        this.favorite=favorite;
        this.runtime=runtime;
        this.numEpisode=numEpisode;
    }

    public int getNumEpisode() {
        return numEpisode;
    }

    public void setNumEpisode(int numEpisode) {
        this.numEpisode = numEpisode;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TvShowEntity that = (TvShowEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TvShowEntity{" +
                "id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", category='" + category + '\'' +
                ", watched=" + watched +
                ", saved=" + saved +
                ", favorite=" + favorite +
                ", runtime=" + runtime +
                ", numEpisode=" +numEpisode +
                '}';
    }
}
