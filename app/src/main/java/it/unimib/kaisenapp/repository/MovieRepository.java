package it.unimib.kaisenapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import java.util.List;

import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.request.MovieApiClient;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private MovieDao movieDao;

    private MovieRepository() {
        movieApiClient= MovieApiClient.getInstance();
        movieDao=null;
    }

    public static MovieRepository getInstance(){
        if(instance == null){
            instance=new MovieRepository();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }

    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        movieApiClient.getMovies(typeOfRequest, page);
    }

    public void getMovies(TypeOfRequest typeOfRequest, int id, int page) {
        movieApiClient.getMovies(typeOfRequest, id, page);
    }

}
