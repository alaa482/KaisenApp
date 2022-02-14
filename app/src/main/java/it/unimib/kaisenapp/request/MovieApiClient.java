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
import retrofit2.Call;
import retrofit2.Response;

//Classe che fa da ponte tra retrofit e livedata

public class MovieApiClient{

    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;     //global request

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


   /* public void searchMoviesApi(String query, int page){
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
    }*/

    public void getMovies(TypeOfRequest typeOfRequest, int page) {
        //if(retrieveMoviesRunnable==null)
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

        @Override
        public void run() {
            //get response body
            Response response=null;
            try {
                if(typeOfRequest!=null){
                    switch (typeOfRequest){
                        case MOST_POPULAR_MOVIES: response=getMostPopularMovies(page).execute();
                        break;

                        case UPCOMING_MOVIES: response=getUpcomingMovies(page).execute();
                        break;

                        case TOP_RATED_MOVIES:  response=getTopRated(page).execute();
                        break;

                        case NOW_PLAYING_MOVIES:  response=getNowPlaying(page).execute();
                        break;

                        case SIMILAR_TO_MOVIES:  response=getSimilarMovies(id,page).execute();
                        break;

                    }

                }else
                    response=getMovies(query, page).execute();


                if (cancelRequest)
                    return;

                if(response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                    for (MovieModel m: list) {
                        m.setCategory(typeOfRequest.toString());

                    }
                   /* synchronized (this){
                        Log.v("Tag", "CATEGORIA:" + list.get(0).getCategory()+" - "+list.get(0).getTitle());
                        Log.v("Tag", "___________________________________________________________");
                    }*/


                    if(page == 1) {
                        synchronized (this) {
                            //Invio i dati al live date
                            //PostValue: utilizzato per il background thread | setValue: non utilizzato per il background thread
                            mMovies.postValue(list); // il background thread invia l'oggetto al main thread ->observer chiama la onchange
                        }
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else { //caso non c'è connessione
                    Log.v("Tag", "Error " + response.errorBody().string());
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
        private Call<MovieSearchResponse> getUpcomingMovies(int page){
            return Service.getMovieApi().getUpcomingMovies(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<MovieSearchResponse> getTopRated(int page){
            return Service.getMovieApi().getTopRated(
                    Credentials.API_KEY,
                    Credentials.LANGUAGE,
                    page
            );
        }
        private Call<MovieSearchResponse> getNowPlaying(int page){
            return Service.getMovieApi().getNowPlaying(
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





    }




}




