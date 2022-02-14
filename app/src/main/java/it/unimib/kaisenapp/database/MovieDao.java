package it.unimib.kaisenapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.unimib.kaisenapp.Constants;

@Dao
public interface MovieDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE) //se c'è il già film, ignoralo
    void insertMovie(MovieEntity movieEntity);

    @Insert
    void insertAllMovies(MovieEntity... movieEntities);

    @Query("SELECT * FROM movie")
    List<MovieEntity> getAllMovies();


    @Delete
    void deleteMovie(MovieEntity movieEntity);

    @Query("DELETE FROM movie")
    void deleteAllMovies();

}
