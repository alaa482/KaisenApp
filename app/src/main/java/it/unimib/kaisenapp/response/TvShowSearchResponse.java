package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.TvShowModel;

public class TvShowSearchResponse {

    @SerializedName("total_results") //tag json
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<TvShowModel> show;

    public int getTotal_count(){
        return total_count;
    }

    public List<TvShowModel> getTvShows(){
        return show;
    }


    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", show=" + show +
                '}';
    }

}
