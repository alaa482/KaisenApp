package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;

//Classe per la richiesta dei details del film
public class MovieDetailsResponse {
    @SerializedName("genres")
    @Expose()
    private List<GenresModel> genres;

    @SerializedName("production_companies")
    @Expose()
    private List<ProductionCompaniesModel> productionCompanies;

    @SerializedName("original_title")
    @Expose()
    private String originalTitle;

    @SerializedName("poster_path")
    @Expose()
    private String image_path;

    @SerializedName("vote_average")
    @Expose()
    private float voteAvarege;

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("overview")
    @Expose()
    private String Plot;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    public List<GenresModel> getGenres() {
        return genres;
    }

    public List<ProductionCompaniesModel> getProductionCompanies() {
        return productionCompanies;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getImage_path() {
        return image_path;
    }

    public float getVoteAvarege() {
        return voteAvarege;
    }

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return Plot;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public String toString() {
        return "MovieDetailsResponse{" +
                "genres=" + genres +
                ", productionCompanies=" + productionCompanies +
                ", originalTitle='" + originalTitle + '\'' +
                ", image_path='" + image_path + '\'' +
                ", voteAvarege=" + voteAvarege +
                ", title='" + title + '\'' +
                ", Plot='" + Plot + '\'' +
                '}';
    }
}
