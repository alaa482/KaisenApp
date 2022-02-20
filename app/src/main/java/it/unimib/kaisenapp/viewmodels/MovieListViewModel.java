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

//class view model
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


    public LiveData<List<TvSerieModel>> getEpisode(){
        return movieRepository.getEpisode();
    }

    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        movieRepository.getMovies(typeOfRequest, page);
    }

    public void getMovies(TypeOfRequest typeOfRequest, int id, int page) {
        movieRepository.getMovies(typeOfRequest, id, page);
    }

    public void getTvShows(TypeOfRequest typeOfRequest, int page) {
        movieRepository.getTvShows(typeOfRequest, page);
    }

    public void getEpisode(int tv_id, int season_number) {
        movieRepository.getEpisode(tv_id, season_number);
    }
    
}
