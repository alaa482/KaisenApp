package it.unimib.kaisenapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

import it.unimib.kaisenapp.utils.AppExecutor;
import it.unimib.kaisenapp.database.Database;
import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.database.TvShowDao;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.repository.MovieDatabaseRepository;
import it.unimib.kaisenapp.repository.TvSerieDatabaseRepository;

public class MovieDatabaseViewModel extends AndroidViewModel {
    private MovieDatabaseRepository movieDatabaseRepository;
    private TvSerieDatabaseRepository tvShowDatabaseRepository;

    public MovieDatabaseViewModel(@NonNull Application application) {
        super(application);
        TvShowDao tvShowDao= Database.getInstance(super.getApplication()).tvShowDao();
        tvShowDatabaseRepository=TvSerieDatabaseRepository.getInstance(tvShowDao);
        MovieDao movieDao= Database.getInstance(super.getApplication()).movieDao();
        movieDatabaseRepository= MovieDatabaseRepository.getInstance(movieDao);


    }

    public LiveData<List<TvShowEntity>> getAllTvShows(){
        return tvShowDatabaseRepository.getAllSeries();
    }
    public LiveData<List<MovieEntity>> getAllMovies(){
        return movieDatabaseRepository.getAllMovies();
    }

    public LiveData<List<MovieEntity>> getAllMoviesByCategory(String category){
        return movieDatabaseRepository.getAllMoviesByCategory(category);
    }
    public LiveData<List<TvShowEntity>> getAllTvShowsByCategory(String category){
        return tvShowDatabaseRepository.getAllTvShowsByCategory(category);
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
        return tvShowDatabaseRepository.getAllSavedTvShows();
    }
    public LiveData<List<TvShowEntity>> getAllFavoriteTvShows(){
        return tvShowDatabaseRepository.getAllFavoriteTvShows();
    }
    public LiveData<List<TvShowEntity>> getAllWatchedTvShows(){
        return tvShowDatabaseRepository.getAllWatchedTvShows();
    }
    public LiveData<Integer> getRuntimeMovies(){
        return movieDatabaseRepository.getRuntimeMovies();
    }
    public LiveData<Integer> getRuntimeShows(){
        return tvShowDatabaseRepository.getRuntimeTvShows();
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
                tvShowDatabaseRepository.addTvShow(tvShowEntity);
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
                tvShowDatabaseRepository.updateSerie(tvShowEntity);
            }
        });
    }

    public void addAllMovies(List<MovieEntity> movieEntityList){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                movieDatabaseRepository.addAllMovies(movieEntityList);
            }
        });

    }
    public void addAllTvShows(List<TvShowEntity> tvShowsEntityList){
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                tvShowDatabaseRepository.addAllTvShows(tvShowsEntityList);
            }
        });

    }

    public void deleteAllMovies(){
        movieDatabaseRepository.deleteAllMovies();
    }
    public void deleteAllTvShows(){
        tvShowDatabaseRepository.deleteAllTvShows();
    }

}
