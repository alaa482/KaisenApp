package it.unimib.kaisenapp.request;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.response.EpisodeResponse;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import it.unimib.kaisenapp.response.TvShowSearchResponse;
import it.unimib.kaisenapp.utils.Credentials;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

//Classe che fa da ponte tra retrofit e livedata

public class MovieApiClient{

    private MutableLiveData<List<MovieModel>> mMovies;
    private MutableLiveData<List<TvShowModel>> mTvShows;
    private MutableLiveData<List<TvSerieModel>> mTvSerieEpisode;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;     //global request
    private RetrieveEpisodesRunnable retrieveEpisodesRunnable;

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
        mTvShows=new MutableLiveData<>();
    }

    public static MovieApiClient getInstance(){
        if(instance==null){
            instance=new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }

    public LiveData<List<TvShowModel>> getTvShows(){
        return mTvShows;
    }

    public LiveData<List<TvSerieModel>> getEpisode(){
        return mTvSerieEpisode;
    }

    public void getEpisode(int tv_id, int season_number, int episode_number) {
        retrieveEpisodesRunnable=new RetrieveEpisodesRunnable(tv_id, season_number, episode_number);
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveEpisodesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        retrieveMoviesRunnable=new RetrieveMoviesRunnable(typeOfRequest, page);
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }
    public void getMovies(TypeOfRequest typeOfRequest, int id, int page) {
       // if(retrieveMoviesRunnable==null)
            retrieveMoviesRunnable=new RetrieveMoviesRunnable(typeOfRequest, id, page);

        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }
    public void getTvShows(TypeOfRequest typeOfRequest, int page) {
        retrieveMoviesRunnable=new RetrieveMoviesRunnable(typeOfRequest, page);

        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int id;
        private int page;
        private boolean cancelRequest;
        private TypeOfRequest typeOfRequest;

        public RetrieveMoviesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            id=-1;
            cancelRequest=false;
            typeOfRequest=null;
        }
        public RetrieveMoviesRunnable(TypeOfRequest typeOfRequest, int page){
            this.page = page;
            this.typeOfRequest=typeOfRequest;
            cancelRequest=false;
        }
        public RetrieveMoviesRunnable(TypeOfRequest typeOfRequest, int id, int page){
            this.id=id;
            this.page = page;
            this.typeOfRequest=typeOfRequest;
            cancelRequest=false;
        }

        private void cancelRequest(){
            cancelRequest=true;
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void run() {
            boolean flag=false;
            Response response=null;
            try {
                if(typeOfRequest!=null){
                    switch (typeOfRequest){
                        case MOST_POPULAR_MOVIES: response=getMostPopularMovies(page).execute();
                            flag=true;
                            break;

                        case UPCOMING_MOVIES: response=getUpcomingMovies(page).execute();
                            flag=true;
                            break;

                        case TOP_RATED_MOVIES:  response=getTopRatedMovies(page).execute();
                            flag=true;
                            break;

                        case NOW_PLAYING_MOVIES:  response=getNowPlayingMovies(page).execute();
                            flag=true;
                            break;

                        case SIMILAR_TO_MOVIES:  response=getSimilarMovies(id,page).execute();
                            flag=true;
                            break;

                        case MOST_POPULAR_TV_SHOWS: response=getMostPopularTvShows(page).execute();
                        break;

                        case TOP_RATED_TV_SHOWS: response=getTopRatedTvShows(page).execute();
                            break;

                        case ON_THE_AIR_TV_SHOWS: response=getOnTheAirTvShows(page).execute();
                            break;

                        case ON_THE_AIR_TODAY_TV_SHOWS: response=getOnTheAirTodayTvShows(page).execute();
                            break;


                    }
                }


                if (cancelRequest)
                    return;

                if(response.code() == 200) {
                    if(flag) {
                        List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                        for (MovieModel m : list)
                            m.setCategory(typeOfRequest.toString());
                        if (page == 1) {
                            mMovies.postValue(list);
                        } else {
                            List<MovieModel> currentMovies = mMovies.getValue();
                            currentMovies.addAll(list);
                            mMovies.postValue(currentMovies);
                        }
                    }else{
                        List<TvShowModel> l = new ArrayList<>(((TvShowSearchResponse)response.body()).getTvShows());
                        for (TvShowModel m: l)
                            m.setCategory(typeOfRequest.toString());
                        if(page == 1) {
                            mTvShows.postValue(l);
                        }else {
                            List<TvShowModel> currentTvShows = mTvShows.getValue();
                            currentTvShows.addAll(l);
                            mTvShows.postValue(currentTvShows);
                        }
                    }

                }else
                    Log.v("Tag", "Response error: " + response.errorBody().string());

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int page){
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    page
            );
        }
        private Call<MovieSearchResponse> getMostPopularMovies(int page){
            return Service.getMovieApi().getPopularMovies(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<MovieSearchResponse> getUpcomingMovies(int page){
            return Service.getMovieApi().getUpcomingMovies(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<MovieSearchResponse> getTopRatedMovies(int page){
            return Service.getMovieApi().getTopRatedMovies(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<MovieSearchResponse> getNowPlayingMovies(int page){
            return Service.getMovieApi().getNowPlayingMovies(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    Credentials.REGION,
                    page
            );
        }
        private Call<MovieSearchResponse> getSimilarMovies(int id, int page){
            return Service.getMovieApi().getSimilarMovies(
                    id,
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    Credentials.REGION,
                    page
            );
        }
        private Call<TvShowSearchResponse> getOnTheAirTodayTvShows(int page){
            return Service.getMovieApi().getOnTheAirTodayTvShows(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<TvShowSearchResponse> getOnTheAirTvShows(int page){
            return Service.getMovieApi().getOnTheAirTvShows(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<TvShowSearchResponse> getMostPopularTvShows(int page){
            return Service.getMovieApi().getPopularTvShows(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<TvShowSearchResponse> getTopRatedTvShows(int page) {
            return Service.getMovieApi().getTopRatedTvShows(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }



    }

    private class RetrieveEpisodesRunnable implements Runnable{
        private int tv_id;
        private int season_number;
        private int episode_number;
        private boolean cancelRequest;

        public RetrieveEpisodesRunnable(int tv_id, int season_number, int episode_number) {
            this.tv_id = tv_id;
            this.season_number = season_number;
            this.episode_number = episode_number;
            cancelRequest=false;
        }
        private void cancelRequest(){
            cancelRequest=true;
        }

        @Override
        public void run() {

            try {
                Response response= getAllEpisode(tv_id,season_number, episode_number).execute();

                if (cancelRequest)
                    return;

                if(response.code() == 200) {
                    List<TvSerieModel> list = new ArrayList<>(((EpisodeResponse) response.body()).getTvSeries());
                    mTvSerieEpisode.postValue(list);
                }else
                    Log.v("Tag", "Response error: " + response.errorBody().string());

            } catch (IOException e) {
                e.printStackTrace();

            }

        }

        private Call<EpisodeResponse> getAllEpisode(int tv_id, int season_number, int episode_number){
            return Service.getMovieApi().getAllEpisode(
                    tv_id,
                    season_number,
                    episode_number,
                    Credentials.API_KEY,
                    Credentials.LANGUAGE
            );
        }
    }

}




