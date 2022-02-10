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
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
import it.unimib.kaisenapp.response.MovieDetailsResponse;
import it.unimib.kaisenapp.response.MovieSearchResponse;
import it.unimib.kaisenapp.utils.Credentials;
import retrofit2.Call;
import retrofit2.Response;

/*
classe che fa da ponte tra retrofit e livedata
 */
public class MovieApiClient {
    private MutableLiveData<List<MovieModel>> mMovies;
    private MutableLiveData<List<GenresModel>> mGenres;
    private MutableLiveData<List<ProductionCompaniesModel>> mProductionCompanies;
    private static MovieApiClient instance;

    //global request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
        mGenres=new MutableLiveData<>();
        mProductionCompanies=new MutableLiveData<>();
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

    public LiveData<List<GenresModel>> getGenres(){return  mGenres;}
    public LiveData<List<ProductionCompaniesModel>> getProductionCompanies(){return  mProductionCompanies;}


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
            retrieveMoviesRunnable=new RetrieveMoviesRunnable(typeOfRequest, page,0);
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
    public void searchMovieDetails(TypeOfRequest typeOfRequest, int id) {
        if(retrieveMoviesRunnable==null){
            retrieveMoviesRunnable=new RetrieveMoviesRunnable(typeOfRequest, 0,id);
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
        private int id;
        private int page;
        private boolean cancelRequest;
        private TypeOfRequest typeOfRequest;

        public RetrieveMoviesRunnable(String query, int page) {
            this.query = query;
            this.page = page;
            cancelRequest=false;
            typeOfRequest=null;
        }
        public RetrieveMoviesRunnable(TypeOfRequest typeOfRequest, int page, int id){
            this.page = page;
            this.typeOfRequest=typeOfRequest;
            cancelRequest=false;
            this.id=id;
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

                    switch (typeOfRequest){

                        case MOST_POPULAR: {
                            response=getMostPopularMovies(page).execute();
                            break;
                        }

                    }


                }else
                    response=getMovies(query, page).execute();

                if (cancelRequest)
                    return;

                if(response.code() == 200) {

                    switch(typeOfRequest){
                        case MOST_POPULAR:{

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
                            break;
                        }

                    }

                }else { //caso non c'è connessione
                    Log.v("Body", "Error " );
                    String error = response.errorBody().string();

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

        private  Call<MovieDetailsResponse> getDetailsMovie(int id){

            return Service.getMovieApi().getMovieDetail(
                    id,
                    Credentials.API_KEY,
                    Credentials.LANGUAGE

            );
        }





    }




}




