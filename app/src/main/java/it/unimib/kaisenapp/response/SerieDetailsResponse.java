package it.unimib.kaisenapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
import it.unimib.kaisenapp.models.SeasonsModel;

//Classe per la richiesta dei details del film
public class SerieDetailsResponse {
    @SerializedName("genres")
    @Expose()
    private List<GenresModel> genres;


    @SerializedName("original_name")
    @Expose()
    private String originalName;

    @SerializedName("poster_path")
    @Expose()
    private String image_path;

    @SerializedName("vote_average")
    @Expose()
    private float voteAvarege;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("overview")
    @Expose()
    private String Plot;

    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> episode_run_time;

    @SerializedName("number_of_episodes")
    @Expose
    private int number_of_episodes;

    @SerializedName("seasons")
    @Expose
    private List<SeasonsModel> seasons;

    public List<SeasonsModel> getSeasons() {
        return seasons;
    }

    public List<GenresModel> getGenres() {
        return genres;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getImage_path() {
        return image_path;
    }

    public float getVoteAvarege() {
        return voteAvarege;
    }

    public String getName() {
        return name;
    }

    public String getPlot() {
        return Plot;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }
    public int getnumber_of_episodes() {
        return number_of_episodes;
    }

    @Override
    public String toString() {
        return "SerieDetailsResponse{" +
                "genres=" + genres +
                ", originalName='" + originalName + '\'' +
                ", image_path='" + image_path + '\'' +
                ", voteAvarege=" + voteAvarege +
                ", name='" + name + '\'' +
                ", Plot='" + Plot + '\'' +
                ", episode_run_time=" + episode_run_time +
                '}';
    }
}
