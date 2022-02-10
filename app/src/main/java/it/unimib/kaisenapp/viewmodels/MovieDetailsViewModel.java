package it.unimib.kaisenapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import java.util.List;

import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
import it.unimib.kaisenapp.repository.MovieRepository;

public class MovieDetailsViewModel extends ViewModel{
    private MovieRepository movieRepository;

    public MovieDetailsViewModel() {

        movieRepository=MovieRepository.getInstance();
    }

    public LiveData<List<GenresModel>> getGenres(){

        return movieRepository.getGenres();
    }
    public LiveData<List<ProductionCompaniesModel>> getProductionCompanies(){

        return movieRepository.getProductionCompanies();
    }

    public void searchMovieDetails(TypeOfRequest typeOfRequest, int id){
        movieRepository.searchMovieDetails(typeOfRequest,id);
    }
}
