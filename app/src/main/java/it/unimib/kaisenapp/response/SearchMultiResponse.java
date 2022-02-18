package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.SearchMultiModel;

public class SearchMultiResponse {
    @SerializedName("total_results") //tag json
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<SearchMultiModel> searchMultiModelList;

    public int getTotal_count(){
        return total_count;
    }

    public List<SearchMultiModel> getAll(){
        return searchMultiModelList;
    }


    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + searchMultiModelList +
                '}';
    }
}
