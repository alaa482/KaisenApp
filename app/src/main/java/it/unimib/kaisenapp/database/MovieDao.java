package it.unimib.kaisenapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.unimib.kaisenapp.models.TvSerieModel;

@Dao
public interface MovieDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    void insertMovie(MovieEntity movieEntity);

    @Insert
    void insertAllMovies(List<MovieEntity> movieEntities);

    @Query("SELECT * FROM movie")
    List<MovieEntity> getAllMovies();

    @Query("SELECT * FROM movie WHERE category= :category")
    List<MovieEntity> getAllMoviesByCategory(String category);

    @Query("SELECT * FROM movie WHERE saved = 1")
    List<MovieEntity> getAllSavedMovies();

    @Query("SELECT * FROM movie WHERE watched = 1")
    List<MovieEntity> getAllWatchedMovies();

    @Query("SELECT * FROM movie WHERE favorite = 1")
    List<MovieEntity> getAllFavoriteMovies();

    @Query("SELECT runtime FROM movie WHERE movie_id = movie_id")
    Integer getRuntimeMovies();

    @Delete
    void deleteMovie(MovieEntity movieEntity);

    @Query("DELETE FROM movie")
    void deleteAllMovies();

    @Update
    void updateMovies(MovieEntity movieEntity);



}
