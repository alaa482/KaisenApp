package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.TvSerieModel;

public class EpisodeResponse {

    
    @SerializedName("episodes")
    @Expose
    private List<TvSerieModel> tvSeries;


    public List<TvSerieModel> getTvSeries() {
        return tvSeries;
    }

    @Override
    public String toString() {
        return "TvSerieResponse{" +
                ", tvSeries=" + tvSeries +
                '}';
    }

}