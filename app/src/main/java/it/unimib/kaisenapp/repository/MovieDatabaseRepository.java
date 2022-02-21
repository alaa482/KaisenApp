package it.unimib.kaisenapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.database.TvShowDao;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.request.MovieApiClient;

public class MovieDatabaseRepository {
    private static MovieDatabaseRepository instance;
    private MovieDao movieDao;


    private MovieDatabaseRepository(MovieDao movieDao) {
        this.movieDao=movieDao;
    }



    public static MovieDatabaseRepository getInstance(MovieDao movieDao){
        if(instance == null)

            instance=new MovieDatabaseRepository(movieDao);

        return instance;
    }



    public MutableLiveData<List<MovieEntity>> getAllMovies(){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllMovies());
        return liveData;
    }
    public void updateMovie(MovieEntity movieEntity){
        movieDao.updateMovies(movieEntity);
    }




    public MutableLiveData<List<MovieEntity>> getAllMoviesByCategory(String category){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllMoviesByCategory(category));
        return liveData;
    }

    public void addMovie(MovieEntity movieEntity){
        movieDao.insertMovie(movieEntity);
    }


    public void addAllMovies(List<MovieEntity> movieEntity){
        movieDao.insertAllMovies(movieEntity);
    }


    public MutableLiveData<List<MovieEntity>> getAllSavedMovies(){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllSavedMovies());
        return liveData;
    }
    public MutableLiveData<List<MovieEntity>> getAllWatchedMovies(){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllWatchedMovies());
        return liveData;
    }
    public MutableLiveData<List<MovieEntity>> getAllFavoriteMovies(){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllFavoriteMovies());
        return liveData;
    }
    public MutableLiveData<Integer> getRuntimeMovies(){
        MutableLiveData<Integer> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getRuntimeMovies());
        return liveData;
    }

    public void deleteAllMovies(){
        movieDao.deleteAllMovies();
    }


}
