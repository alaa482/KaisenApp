package it.unimib.kaisenapp.request;

import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.response.EpisodeResponse;
import it.unimib.kaisenapp.response.MovieDetailsResponse;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import it.unimib.kaisenapp.response.SearchMultiResponse;
import it.unimib.kaisenapp.response.SerieDetailsResponse;
import it.unimib.kaisenapp.response.SimilarResponse;
import it.unimib.kaisenapp.response.TvShowSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //MOVIES
    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    @GET("movie/popular")
    Call<MovieSearchResponse> getPopularMovies(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/upcoming")
    Call<MovieSearchResponse> getUpcomingMovies(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/top_rated")
    Call<MovieSearchResponse> getTopRatedMovies(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/now_playing")
    Call<MovieSearchResponse> getNowPlayingMovies(
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

    //TV SERIES
    @GET("tv/top_rated")
    Call<TvShowSearchResponse> getTopRatedTvShows(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/popular")
    Call<TvShowSearchResponse> getPopularTvShows(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/on_the_air")
    Call<TvShowSearchResponse> getOnTheAirTvShows(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/airing_today")
    Call<TvShowSearchResponse> getOnTheAirTodayTvShows(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("tv/{tv_id}?")
    Call<SerieDetailsResponse> getSerieDetail(
            @Path("tv_id") int tv_id,
            @Query("api_key") String key,
            @Query("language") String language

    );

    @GET("tv/{tv_id}/similar?")
    Call<SimilarResponse> getTvSimilar(
            @Path("tv_id") int tv_id,
            @Query("api_key") String key,
            @Query("language") String language
    );

    @GET("search/multi")
    Call<SearchMultiResponse> search(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Query("region") String region
    );



    @GET("movie/{movie_id}?")
    Call<MovieDetailsResponse> getMovieDetail(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("language") String language

    );

    @GET("movie/{movie_id}/similar?")
    Call<SimilarResponse> getMovieRecommendation(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key,
            @Query("language") String language
    );

    @GET("tv/{tv_id}/season/{season_number}?")
    Call<EpisodeResponse> getAllEpisode(
            @Path("tv_id") int tv_id,
            @Path("season_number") int season_number,
            @Query("api_key") String key,
            @Query("language") String language
    );

    @GET("discover/movie")
    Call<MovieSearchResponse> getMoviesByGenre(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("with_genres") int genres,
            @Query("page") int page
    );

    @GET("discover/tv")
    Call<TvShowSearchResponse> getTvSeriesByGenre(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("with_genres") int genres,
            @Query("page") int page
    );


}
