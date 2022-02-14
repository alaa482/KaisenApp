package it.unimib.kaisenapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
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


    public MutableLiveData<List<MovieEntity>> readAlldata(){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllMovies());
        return liveData;
    }

    public void addMovie(MovieEntity movieEntity){
        movieDao.insertMovie(movieEntity);
    }


}
