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
import it.unimib.kaisenapp.request.MovieApiClient;

public class MovieDatabaseRepository {
    private static MovieDatabaseRepository instance;
    private MovieDao movieDao;
    private TvShowDao tvShowDao;

    private MovieDatabaseRepository(MovieDao movieDao) {
        this.movieDao=movieDao;
    }

    private MovieDatabaseRepository(TvShowDao tvShowDao) {
        this.tvShowDao=tvShowDao;
    }

    public static MovieDatabaseRepository getInstance(MovieDao movieDao){
        if(instance == null)
            instance=new MovieDatabaseRepository(movieDao);

        return instance;
    }
    public static MovieDatabaseRepository getInstance(TvShowDao tvShowDao){
        if(instance == null)
            instance=new MovieDatabaseRepository(tvShowDao);

        return instance;
    }


    public MutableLiveData<List<MovieEntity>> getAllMovies(){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllMovies());
        return liveData;
    }
    public MutableLiveData<List<TvShowEntity>> getAllTvShows(){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllTvShows());
        return liveData;
    }

    public MutableLiveData<List<MovieEntity>> getAllMoviesByCategory(String category){
        MutableLiveData<List<MovieEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(movieDao.getAllMoviesByCategory(category));
        return liveData;
    }
    public MutableLiveData<List<TvShowEntity>> getAllTvShowsByCategory(String category){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllTvShowsByCategory(category));
        return liveData;
    }

    public void addMovie(MovieEntity movieEntity){
        movieDao.insertMovie(movieEntity);
    }

    public void addTvShow(TvShowEntity tvShowEntity){
        tvShowDao.insertTvShow(tvShowEntity);
    }

    public void addAllMovies(List<MovieEntity> movieEntity){
        movieDao.insertAllMovies(movieEntity);
    }
    public void addAllTvShows(List<TvShowEntity> tvShowEntities){
        tvShowDao.insertAllTvShows(tvShowEntities);
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
    public MutableLiveData<List<TvShowEntity>> getAllFavoriteTvShows(){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllFavoriteTvShows());
        return liveData;
    }
    public MutableLiveData<List<TvShowEntity>> getAllSavedTvShows(){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllSavedTvShows());
        return liveData;
    }
    public MutableLiveData<List<TvShowEntity>> getAllWatchedTvShows(){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllWatchedTvShows());
        return liveData;
    }

    public void deleteAllMovies(){
        movieDao.deleteAllMovies();
    }
    public void deleteAllTvShows(){
        movieDao.deleteAllMovies();
    }


}
