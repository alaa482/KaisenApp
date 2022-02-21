package it.unimib.kaisenapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import it.unimib.kaisenapp.models.SearchMultiModel;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.repository.MovieRepository;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository=MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<TvShowModel>> getTvShows(){
        return movieRepository.getTvShows();
    }
    public LiveData<List<SearchMultiModel>> getSearchedMulti(){
        return movieRepository.getSearchedMulti();
    }
    public LiveData<List<MovieModel>> getMoviesByGenre(){
        return movieRepository.getMoviesByGenre();
    }
    public LiveData<List<TvShowModel>> getTvSeriesByGenre(){
        return movieRepository.getTvSeriesByGenre();
    }
    public LiveData<List<TvSerieModel>> getEpisode(){
        return movieRepository.getEpisode();
    }

    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        movieRepository.getMovies(typeOfRequest, page);
    }
    public void search(String query, int page) {
        movieRepository.search(query, page);
    }
    public void getTvShows(TypeOfRequest typeOfRequest, int page) {
        movieRepository.getTvShows(typeOfRequest, page);
    }
    public void getEpisode(int tv_id, int season_number) {
        movieRepository.getEpisode(tv_id, season_number);
    }
    public void getMoviesByGenre(int genre, int page){
        movieRepository.getMoviesByGenre(genre, page);
    }
    public void getTvSeriesByGenre(int genre, int page){
        movieRepository.getTvSeriesByGenre(genre, page);
    }
    
}
