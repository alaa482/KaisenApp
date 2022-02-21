package it.unimib.kaisenapp.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

import it.unimib.kaisenapp.models.SearchMultiModel;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.response.EpisodeResponse;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.database.MovieDao;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.request.MovieApiClient;

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
    public LiveData<List<SearchMultiModel>> getSearchedMulti(){
        return movieApiClient.getSearchedMulti();
    }
    public LiveData<List<MovieModel>> getMoviesByGenre(){
        return  movieApiClient.getmGenresMovie();
    }
    public LiveData<List<TvShowModel>> getTvSeriesByGenre(){
        return  movieApiClient.getmGenresTv();
    }

    public void getEpisode(int tv_id, int season_number){
        movieApiClient.getEpisode(tv_id,season_number);

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
    public void search(String query, int page) {
        movieApiClient.search(query, page);
    }
    public void getMoviesByGenre(int genre, int page){
        movieApiClient.getMoviesByGenre(genre,page);
    }
    public void getTvSeriesByGenre(int genre, int page){
        movieApiClient.getTvSeriesByGenre(genre,page);
    }
}
