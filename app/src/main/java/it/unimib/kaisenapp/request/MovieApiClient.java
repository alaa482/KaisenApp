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
import it.unimib.kaisenapp.utils.AppExecutor;
import it.unimib.kaisenapp.models.SearchMultiModel;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.response.EpisodeResponse;
import it.unimib.kaisenapp.response.SearchMultiResponse;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import it.unimib.kaisenapp.response.TvShowSearchResponse;
import it.unimib.kaisenapp.utils.Credentials;
import retrofit2.Call;
import retrofit2.Response;

//Classe che fa da ponte tra retrofit e livedata

public class MovieApiClient{
    private static MovieApiClient instance;

    private final MutableLiveData<List<MovieModel>> mMovies;
    private final MutableLiveData<List<TvShowModel>> mTvShows;
    private final MutableLiveData<List<TvSerieModel>> mTvSerieEpisode;
    private final MutableLiveData<List<SearchMultiModel>> mSearchMulti;
    private final MutableLiveData<List<MovieModel>> mGenresMovie;
    private final MutableLiveData<List<TvShowModel>> mGenresTv;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    private RetrieveEpisodesRunnable retrieveEpisodesRunnable;
    private RetrieveSearchMultiRunnable retrieveSearchMultiRunnable;
    private RetrieveMoviesGenreRunnable retrieveMoviesGenreRunnable;
    private RetrieveTvSerieGenreRunnable retrieveTvSerieGenreRunnable;

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
        mTvShows=new MutableLiveData<>();
        mTvSerieEpisode=new MutableLiveData<>();
        mSearchMulti=new MutableLiveData<>();
        mGenresMovie = new MutableLiveData<>();
        mGenresTv = new MutableLiveData<>();
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

    public MutableLiveData<List<MovieModel>> getmGenresMovie() {
        return mGenresMovie;
    }

    public MutableLiveData<List<TvShowModel>> getmGenresTv() {
        return mGenresTv;
    }

    public LiveData<List<TvShowModel>> getTvShows(){
        return mTvShows;
    }

    public LiveData<List<TvSerieModel>> getEpisode(){
        return mTvSerieEpisode;
    }

    public void getEpisode(int tv_id, int season_number) {
        retrieveEpisodesRunnable=new RetrieveEpisodesRunnable(tv_id, season_number);
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveEpisodesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    public LiveData<List<SearchMultiModel>> getSearchedMulti(){
        return mSearchMulti;
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
    public void search(String query, int page){
        retrieveSearchMultiRunnable=new RetrieveSearchMultiRunnable(query, page);
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveSearchMultiRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    public void getMoviesByGenre(int genre, int page){
        retrieveMoviesGenreRunnable=new RetrieveMoviesGenreRunnable(genre,page);
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveMoviesGenreRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);

    }
    public void getTvSeriesByGenre(int genre,int page){
        retrieveTvSerieGenreRunnable=new RetrieveTvSerieGenreRunnable(genre,page);
        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveTvSerieGenreRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable{
        private int id;
        private int page;
        private boolean cancelRequest;
        private TypeOfRequest typeOfRequest;

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
        private boolean cancelRequest;

        public RetrieveEpisodesRunnable(int tv_id, int season_number) {
            this.tv_id = tv_id;
            this.season_number = season_number;

            cancelRequest=false;
        }
        private void cancelRequest(){
            cancelRequest=true;
        }

        @Override
        public void run() {

            try {
                Response response= getAllEpisode(tv_id,season_number).execute();

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

        private Call<EpisodeResponse> getAllEpisode(int tv_id, int season_number){
            return Service.getMovieApi().getAllEpisode(
                    tv_id,
                    season_number,
                    Credentials.API_KEY,
                    Credentials.LANGUAGE
            );
        }
    }

    private class RetrieveSearchMultiRunnable implements Runnable{
        private boolean cancelRequest;
        private String query;
        private int page;

        public RetrieveSearchMultiRunnable(String query, int page) {
            cancelRequest = false;
            this.query = query;
            this.page = page;
        }

        private void cancelRequest(){
            cancelRequest=true;
        }

        @Override
        public void run() {
            try {

                Response response=search(query,page).execute();

                if(cancelRequest)
                    return;

                if(response.code() == 200){
                    List<SearchMultiModel> searchedList = new ArrayList<>(((SearchMultiResponse) response.body()).getAll());
                    mSearchMulti.postValue(searchedList);


                }else
                    Log.v("Tag", "Response error: " + response.errorBody().string());

            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        Call<SearchMultiResponse> search(String query, int page){
            return Service.getMovieApi().search(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    query,
                    page,
                    Credentials.REGION
            );
        }
    }

    private class RetrieveMoviesGenreRunnable implements Runnable{
        private int genre;
        private boolean cancelRequest;
        private int page;

        public RetrieveMoviesGenreRunnable(int genre,int page) {
            this.genre = genre;
            cancelRequest=false;
            this.page = page;
        }
        private void cancelRequest(){
            cancelRequest=true;
        }


        @Override
        public void run() {
            try {
                Response response=getMoviesByGenre(genre,page).execute();

                if(cancelRequest)
                    return;

                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    mGenresMovie.postValue(list);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Call<MovieSearchResponse> getMoviesByGenre(int genre,int page){
            return Service.getMovieApi().getMoviesByGenre(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    genre,
                    page
            );
        }
    }
    private class RetrieveTvSerieGenreRunnable implements Runnable{
        private int genre;
        private boolean cancelRequest;
        private int page;

        public RetrieveTvSerieGenreRunnable(int genre,int page) {
            this.genre = genre;
            cancelRequest=false;
            this.page = page;
        }
        private void cancelRequest(){
            cancelRequest=true;
        }


        @Override
        public void run() {

            try {
                Response response=getTvSeriesByGenre(genre,page).execute();
                if(cancelRequest)
                    return;

                if(response.code() == 200){
                    List<TvShowModel> l = new ArrayList<>(((TvShowSearchResponse)response.body()).getTvShows());

                    mGenresTv.postValue(l);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        Call<TvShowSearchResponse> getTvSeriesByGenre(int genre,int page){
            return Service.getMovieApi().getTvSeriesByGenre(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    genre,
                    page
            );
        }
    }

}




