package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.utils.AppExecutor;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.utils.DataWrapper;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class SplashScreen extends AppCompatActivity {

    private MovieListViewModel movieListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);
        DataWrapper.setList(new ArrayList<>());
        addMoviesType();
        ObserverAnyChange();
        if(isConnected())
            getAllMoviesToSetupHome();
        else{
            DataWrapper.clear();
            Toast.makeText(this, Constants.CONNECTION,Toast.LENGTH_LONG).show();
        }


        Handler handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(SplashScreen.this, LoginUser.class);
            startActivity(intent);
            finish();
        }, 3000);



    }

    private void addMoviesType() {
        DataWrapper.addTitle(Constants.MOST_POPULAR_MOVIES);
        DataWrapper.addTitle(Constants.UPCOMING_MOVIES);
        DataWrapper.addTitle(Constants.NOW_PLAYING_MOVIES);
        DataWrapper.addTitle(Constants.TOP_RATED_MOVIES);
        DataWrapper.addTitle(Constants.MOST_POPULAR_TV_SHOWS);
        DataWrapper.addTitle(Constants.TOP_RATED_TV_SHOWS);
        DataWrapper.addTitle(Constants.ON_THE_AIR_TV_SHOWS);
        DataWrapper.addTitle(Constants.ON_THE_AIR_TODAY_TV_SHOWS);
    }
    private void getAllMoviesToSetupHome() {
        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.MOST_POPULAR_MOVIES, Constants.PAGE), 0,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.TOP_RATED_MOVIES,Constants.PAGE), 300,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.NOW_PLAYING_MOVIES, Constants.PAGE), 600,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.UPCOMING_MOVIES, Constants.PAGE), 900, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.MOST_POPULAR_TV_SHOWS,Constants.PAGE), 1100, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.TOP_RATED_TV_SHOWS,Constants.PAGE), 1400, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.ON_THE_AIR_TV_SHOWS,Constants.PAGE), 1700, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.ON_THE_AIR_TODAY_TV_SHOWS,Constants.PAGE), 2000, TimeUnit.MILLISECONDS);
    }
    private void ObserverAnyChange(){
        movieListViewModel.getMovies().observe(this, movieModels -> {
            List<CategoryItem> list=new ArrayList<>();
            if(movieModels!=null){
                for(int i=0; i<movieModels.size(); ++i)
                    list.add(new CategoryItem(movieModels.get(i).getId(), movieModels.get(i).getPoster_path(),"movie", ""));

                if(movieModels.size()>0){
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.MOST_POPULAR_MOVIES.toString()))
                        DataWrapper.addCategoryItemList(Constants.MOST_POPULAR_MOVIES,list);
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.UPCOMING_MOVIES.toString()))
                        DataWrapper.addCategoryItemList(Constants.UPCOMING_MOVIES,list);
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.TOP_RATED_MOVIES.toString()))
                        DataWrapper.addCategoryItemList(Constants.TOP_RATED_MOVIES,list);
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.NOW_PLAYING_MOVIES.toString()))
                        DataWrapper.addCategoryItemList(Constants.NOW_PLAYING_MOVIES,list);

                }
            }
        });

        movieListViewModel.getTvShows().observe(this, tvShowModels -> {
            List<CategoryItem> list=new ArrayList<>();
            if(tvShowModels!=null) {
                for (TvShowModel tvShowModel : tvShowModels)
                    list.add(new CategoryItem(tvShowModel.getId(), tvShowModel.getPoster_path(),"tv_serie", ""));

                if(tvShowModels.size()>0){
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.MOST_POPULAR_TV_SHOWS.toString()))
                        DataWrapper.addCategoryItemList(Constants.MOST_POPULAR_TV_SHOWS,list);
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.TOP_RATED_TV_SHOWS.toString()))
                        DataWrapper.addCategoryItemList(Constants.TOP_RATED_TV_SHOWS,list);
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.ON_THE_AIR_TV_SHOWS.toString()))
                        DataWrapper.addCategoryItemList(Constants.ON_THE_AIR_TV_SHOWS,list);
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.ON_THE_AIR_TODAY_TV_SHOWS.toString()))
                        DataWrapper.addCategoryItemList(Constants.ON_THE_AIR_TODAY_TV_SHOWS,list);

                }
            }
        });
    }
    private void getMovies(TypeOfRequest typeOfRequest, int page){
        movieListViewModel.getMovies(typeOfRequest, page);
    }
    private void getTvShows(TypeOfRequest typeOfRequest, int page){
        movieListViewModel.getTvShows(typeOfRequest, page);
    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;

    }

}