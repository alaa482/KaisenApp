package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.MovieModel;


public class SimilarResponse {
    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    @Override
    public String toString() {
        return "RecommendationsResponse{" +
                "movies=" + movies +
                '}';
    }

    public List<MovieModel> getMovies(){
        return movies;
    }
}
