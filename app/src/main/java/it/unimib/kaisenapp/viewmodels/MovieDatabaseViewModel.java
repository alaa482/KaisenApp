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
import it.unimib.kaisenapp.database.TvShowDao;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.repository.MovieDatabaseRepository;

public class MovieDatabaseViewModel extends AndroidViewModel {
    private MovieDatabaseRepository movieDatabaseRepository;


    public MovieDatabaseViewModel(@NonNull Application application) {
        super(application);
        MovieDao movieDao= Database.getInstance(super.getApplication()).movieDao();
        movieDatabaseRepository= MovieDatabaseRepository.getInstance(movieDao);
    }

    public LiveData<List<TvShowEntity>> getAllTvShows(){
        return movieDatabaseRepository.getAllSeries();
    }
    public LiveData<List<MovieEntity>> getAllMovies(){
        return movieDatabaseRepository.getAllMovies();
    }

    public LiveData<List<MovieEntity>> getAllMoviesByCategory(String category){
        return movieDatabaseRepository.getAllMoviesByCategory(category);
    }
    public LiveData<List<TvShowEntity>> getAllTvShowsByCategory(String category){
        return movieDatabaseRepository.getAllTvShowsByCategory(category);
    }

    public LiveData<List<MovieEntity>> getAllWatchedMovies(){
        return movieDatabaseRepository.getAllWatchedMovies();
    }
    public LiveData<List<MovieEntity>> getAllFavoriteMovies(){
        return movieDatabaseRepository.getAllFavoriteMovies();
    }
    public LiveData<List<MovieEntity>> getAllSavedMovies(){
        return movieDatabaseRepository.getAllSavedMovies();
    }

    public LiveData<List<TvShowEntity>> getAllSavedTvShows(){
        return movieDatabaseRepository.getAllSavedTvShows();
    }
    public LiveData<List<TvShowEntity>> getAllFavoriteTvShows(){
        return movieDatabaseRepository.getAllFavoriteTvShows();
    }
    public LiveData<List<TvShowEntity>> getAllWatchedTvShows(){
        return movieDatabaseRepository.getAllWatchedTvShows();
    }


    public void addMovie(MovieEntity movieEntity){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.addMovie(movieEntity);
            }
        });

    }
    public void addTvShow(TvShowEntity tvShowEntity){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.addTvShow(tvShowEntity);
            }
        });

    }
    public void updateMovie(MovieEntity movieEntity){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.updateMovie(movieEntity);
            }
        });
    }
    public void updateSerie(TvShowEntity tvShowEntity){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.updateSerie(tvShowEntity);
            }
        });
    }

    public void addAllMovies(List<MovieEntity> movieEntityList){
        movieDatabaseRepository.addAllMovies(movieEntityList);
        /*final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {

            }
        });*/

    }
    public void addAllTvShows(List<TvShowEntity> tvShowsEntityList){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.addAllTvShows(tvShowsEntityList);
            }
        });

    }

    public void deleteAllMovies(){
        movieDatabaseRepository.deleteAllMovies();
    }
    public void deleteAllTvShows(){
        movieDatabaseRepository.deleteAllTvShows();
    }

}
