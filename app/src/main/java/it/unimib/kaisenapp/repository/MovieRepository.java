package it.unimib.kaisenapp.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.models.MovieDao;
import it.unimib.kaisenapp.models.MovieEntity;
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


    public LiveData<List<MovieEntity>> getAllData(){
        return movieDao.getAllMovies();
    }
    public void insertMovie(MovieEntity movieEntity){
        movieDao.insertMovie(movieEntity);
    }

    public MovieDao getMovieDao() {
        return movieDao;
    }
    /*public void searchMovieApi(String query, int page){
        movieApiClient.searchMoviesApi(query, page);
    }*/
}
