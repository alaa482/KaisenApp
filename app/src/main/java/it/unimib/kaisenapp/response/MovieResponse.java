package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import it.unimib.kaisenapp.models.MovieModel;

//Classe per la richiesta di una singola richiesta
public class MovieResponse {

    // 1 cercare i film/serieTv
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{"+ movie +'}';
    }
}
