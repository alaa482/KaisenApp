package it.unimib.kaisenapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.TypeOfRequest;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.utils.MovieApi;
import retrofit2.Call;
import retrofit2.Response;

/*
classe che fa da ponte tra retrofit e livedata
 */
public class MovieApiClient {
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    //global request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
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


    public void searchMoviesApi(String query, int page){
        if(retrieveMoviesRunnable==null){
            retrieveMoviesRunnable=new RetrieveMoviesRunnable(query, page);
        }
        //retrieveMoviesRunnable=new RetrieveMoviesRunnable(query, page);

        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //cancello la chiamata retrofit
                Log.v("Body", "slow");
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    public void searchMostPopularMovies(TypeOfRequest typeOfRequest, int page) {
        if(retrieveMoviesRunnable==null){
            retrieveMoviesRunnable=new RetrieveMoviesRunnable(typeOfRequest, page);
        }
        //retrieveMoviesRunnable=new RetrieveMoviesRunnable(query, page);

        final Future myHandler = AppExecutor.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    //recupero i dati dalle RestApi con la classe runnable

    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int page;
        private boolean cancelRequest;
        private TypeOfRequest typeOfRequest;

        public RetrieveMoviesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            cancelRequest=false;
            typeOfRequest=null;
        }
        public RetrieveMoviesRunnable(TypeOfRequest typeOfRequest, int page){
            this.page = page;
            this.typeOfRequest=typeOfRequest;
            cancelRequest=false;
        }
        private void cancelRequest(){
            cancelRequest=true;
        }

        @Override
        public void run() {
            //get response body
            Response response=null;
            try {
                if(typeOfRequest!=null){
                    if(typeOfRequest==TypeOfRequest.MOST_POPULAR){
                        response=getMostPopularMovies(page).execute();
                    }
                }else
                    response=getMovies(query, page).execute();

                if (cancelRequest)
                    return;

                if(response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(page == 1) {
                        //inviare dati al live date
                        //PostValue: utilizzato per il background thread
                        //setValue: non utilizzato per il background thread
                        mMovies.postValue(list);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else { //caso non c'è connessione
                    String error = response.errorBody().string();
                    Log.v("Body", "Error " + error);
                }
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





    }




}




