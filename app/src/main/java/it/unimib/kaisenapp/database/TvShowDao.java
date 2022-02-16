package it.unimib.kaisenapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TvShowDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    void insertTvShow(TvShowEntity tvShowEntity);

    @Insert
    void insertAllTvShows(List<TvShowEntity> tvShowEntities);

    @Query("SELECT * FROM tv_show")
    List<TvShowEntity> getAllTvShows();

    @Query("SELECT * FROM tv_show WHERE category= :category")
    List<TvShowEntity> getAllTvShowsByCategory(String category);

    @Query("SELECT * FROM tv_show WHERE saved = 1")
    List<TvShowEntity> getAllSavedTvShows();

    @Query("SELECT * FROM tv_show WHERE watched = 1")
    List<TvShowEntity> getAllWatchedTvShows();

    @Query("SELECT * FROM tv_show WHERE favorite = 1")
    List<TvShowEntity> getAllFavoriteTvShows();


    @Delete
    void deleteTvShow(TvShowEntity tvShowEntity);

    @Query("DELETE FROM tv_show")
    void deleteAllTvShows();
}
