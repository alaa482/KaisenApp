package it.unimib.kaisenapp.response;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.TvSerieModel;

public class EpisodeResponse {

    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose
    private List<TvSerieModel> tvSeries;

    public int getTotal_count() {
        return total_count;
    }

    public List<TvSerieModel> getTvSeries() {
        return tvSeries;
    }

    @Override
    public String toString() {
        return "TvSerieResponse{" +
                "total_count=" + total_count +
                ", tvSeries=" + tvSeries +
                '}';
    }
}