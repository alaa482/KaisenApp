package it.unimib.kaisenapp.utils;

import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
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
    @GET("movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );

    //Popular movies
    @GET("movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );
    //Upcoming
    @GET("movie/upcoming")
    Call<MovieSearchResponse> getUpcomingMovies(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("movie/top_rated")
    Call<MovieSearchResponse> getTopRated(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("movie/now_playing")
    Call<MovieSearchResponse> getNowPlaying(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("region") String region,
            @Query("page") int page
    );
    @GET("movie/{movie_id}/recommendations?")
    Call<MovieSearchResponse> getSimilarMovies(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("region") String region,
            @Query("page") int page
    );



}
