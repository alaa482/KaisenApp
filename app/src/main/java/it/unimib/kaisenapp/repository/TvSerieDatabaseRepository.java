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

public class TvSerieDatabaseRepository {
    private static TvSerieDatabaseRepository instance;

    private TvShowDao tvShowDao;



    private TvSerieDatabaseRepository(TvShowDao tvShowDao) {
        this.tvShowDao=tvShowDao;
    }


    public static TvSerieDatabaseRepository getInstance(TvShowDao tvShowDao){
        if(instance == null)
            instance=new TvSerieDatabaseRepository(tvShowDao);

        return instance;
    }





    public void updateSerie(TvShowEntity tvShowEntity){
        tvShowDao.updateTvSerie(tvShowEntity);
    }

    public MutableLiveData<List<TvShowEntity>> getAllSeries(){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllTvShows());
        return liveData;
    }



    public MutableLiveData<List<TvShowEntity>> getAllTvShowsByCategory(String category){
        MutableLiveData<List<TvShowEntity>> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getAllTvShowsByCategory(category));
        return liveData;
    }


    public void addTvShow(TvShowEntity tvShowEntity){
        tvShowDao.insertTvShow(tvShowEntity);
    }


    public void addAllTvShows(List<TvShowEntity> tvShowEntities){
        tvShowDao.insertAllTvShows(tvShowEntities);
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
    public MutableLiveData<Integer> getRuntimeTvShows(){
        MutableLiveData<Integer> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getRuntimeTvShows());
        return liveData;
    }
    public MutableLiveData<Integer> getNumEpisodeTvShows(){
        MutableLiveData<Integer> liveData= new MutableLiveData<>();
        liveData.postValue(tvShowDao.getnumEpisodeTvShows());
        return liveData;
    }


    public void deleteAllTvShows(){
        tvShowDao.getAllTvShows();
    }


}
