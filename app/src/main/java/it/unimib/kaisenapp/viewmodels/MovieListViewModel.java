package it.unimib.kaisenapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.database.Database;
import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.repository.MovieRepository;

//class view model
public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<MovieEntity>> listMovies;

    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        movieRepository.getMovies(typeOfRequest, page);
    }

    public void getMovies(TypeOfRequest typeOfRequest, int id, int page) {
        movieRepository.getMovies(typeOfRequest, id, page);
    }

    /*public void getData(){
        MovieDao m= Database.getInstance(super.getApplication()).movieDao();
        movieRepository=MovieRepository.getInstance();
        listMovies=movieRepository.getAllData();
    }*/




}
