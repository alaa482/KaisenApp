package it.unimib.kaisenapp.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.database.Database;
import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.repository.MovieDatabaseRepository;

public class MovieDatabaseViewModel extends AndroidViewModel {
    private MovieDatabaseRepository movieDatabaseRepository;
    //private LiveData<List<MovieEntity>> listMovies;

    public MovieDatabaseViewModel(@NonNull Application application) {
        super(application);
        MovieDao movieDao= Database.getInstance(super.getApplication()).movieDao();
        movieDatabaseRepository= MovieDatabaseRepository.getInstance(movieDao);
    }

    public LiveData<List<MovieEntity>> readAlldata(){
        return movieDatabaseRepository.readAlldata();
    }

    public void addMovie(MovieEntity movieEntity){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.addMovie(movieEntity);
            }
        });

    }


}
