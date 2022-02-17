package it.unimib.kaisenapp.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.request.MovieApiClient;

//PATTERN SINGLETON
public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;


    private MovieRepository() {
        movieApiClient= MovieApiClient.getInstance();
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

    public LiveData<List<TvShowModel>> getTvShows(){
        return movieApiClient.getTvShows();
    }

    public LiveData<List<TvSerieModel>> getEpisode(){
        return movieApiClient.getEpisode();
    }



    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        movieApiClient.getMovies(typeOfRequest, page);
    }

    public void getMovies(TypeOfRequest typeOfRequest, int id, int page) {
        movieApiClient.getMovies(typeOfRequest, id, page);
    }
    public void getTvShows(TypeOfRequest typeOfRequest, int page){
        movieApiClient.getTvShows(typeOfRequest,page);
    }

    public void getEpisode(int tv_id, int season_number){
        movieApiClient.getEpisode(tv_id,season_number);

    }


}