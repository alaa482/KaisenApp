package it.unimib.kaisenapp.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
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

    public LiveData<List<GenresModel>> getGenres(){
        return movieApiClient.getGenres();
    }
    public LiveData<List<ProductionCompaniesModel>> getProductionCompanies(){
        return movieApiClient.getProductionCompanies();
    }

    //chiamate metodi della repo
    public void searchMovieApi(String query, int page){
        movieApiClient.searchMoviesApi(query, page);
    }


    public void searchMostPopularMovies(TypeOfRequest typeOfRequest, int page) {
        movieApiClient.searchMostPopularMovies(typeOfRequest, page);
    }

    public void searchMovieDetails(TypeOfRequest typeOfRequest, int id){
        movieApiClient.searchMovieDetails(typeOfRequest,id);
    }
}
