package it.unimib.kaisenapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.kaisenapp.TypeOfRequest;
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

    //chiamata metodi in view model
    public void searchMovie(String query, int page){
        movieRepository.searchMovieApi(query, page);

    }

    public void searchMostPopularMovies(TypeOfRequest typeOfRequest, int page) {
        movieRepository.searchMostPopularMovies(typeOfRequest, page);
    }


}
