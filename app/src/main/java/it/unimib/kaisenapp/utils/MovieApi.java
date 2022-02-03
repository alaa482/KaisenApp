package it.unimib.kaisenapp.utils;

import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //Search for movies
    //https://api.themoviedb.org/3/search/movie?api_key={my_api_key}&query=venom+2
    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //Search movie by Id
    //https://api.themoviedb.org/3/movie/550?api_key=be4219b3a8a1a1c28bd58860d75ad530&language=it-it

    @GET("movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );

    //film più popolari
    //https://api.themoviedb.org/3/movie/popular?api_key=be4219b3a8a1a1c28bd58860d75ad530&language=it-it&page=1
    @GET("movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );


}
