package it.unimib.kaisenapp.utils;


import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.models.SearchMultiModel;

public final class SearchMovieWrapper {
    private static List<SearchMultiModel> movies;

    public SearchMovieWrapper(){
        movies=new ArrayList<>();
    }

    private SearchMovieWrapper(List<SearchMultiModel> movies){
        SearchMovieWrapper.movies=movies;
    }

    public static List<SearchMultiModel> getMovies(){
        return movies;
    }
    public static void setMovies(List<SearchMultiModel> movies){
        SearchMovieWrapper.movies=movies;
    }




}
