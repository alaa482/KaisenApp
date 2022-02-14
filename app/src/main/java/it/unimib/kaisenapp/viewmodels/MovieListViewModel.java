package it.unimib.kaisenapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.database.MovieRoomDatabase;
import it.unimib.kaisenapp.models.MovieDao;
import it.unimib.kaisenapp.models.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.repository.MovieRepository;

//class view model
public class MovieListViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<MovieEntity>> listMovies;



    /* extends ViewModel
    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }
     */
    public MovieListViewModel(Application application) {
        super(application);
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

    public void getData(){
        MovieDao m=MovieRoomDatabase.getDatabase(super.getApplication()).movieDao();
        movieRepository=MovieRepository.getInstance();
        listMovies=movieRepository.getAllData();
    }

    /**
     * Inserimento del film nel database da parte di un thread in background
     * @param movieEntity
     */
    public void insertMovie(MovieEntity movieEntity){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieRepository.insertMovie(movieEntity);
            }
        });

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);

    }

   /* public void searchMovie(String query, int page){
        movieRepository.searchMovieApi(query, page);

    }*/


}
