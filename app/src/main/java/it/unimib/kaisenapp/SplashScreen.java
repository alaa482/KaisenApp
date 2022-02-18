package it.unimib.kaisenapp;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.fragment.HomeFragment;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.utils.DataWrapper;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class SplashScreen extends AppCompatActivity {


    private MovieListViewModel movieListViewModel;
    private DataWrapper dataWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);
        dataWrapper=new DataWrapper();
        ObserverAnyChange();
        if(isConnected())
            getAllMoviesToSetupHome();
        else
            Toast.makeText(this, Constants.CONNECTION,Toast.LENGTH_LONG).show();

        Handler handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);



    }
    private void getAllMoviesToSetupHome() {
        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.MOST_POPULAR_MOVIES, Constants.PAGE), 0,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.TOP_RATED_MOVIES,Constants.PAGE), 200,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.NOW_PLAYING_MOVIES, Constants.PAGE), 400,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.UPCOMING_MOVIES, Constants.PAGE), 600, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.MOST_POPULAR_TV_SHOWS,Constants.PAGE), 800, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.TOP_RATED_TV_SHOWS,Constants.PAGE), 1000, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.ON_THE_AIR_TV_SHOWS,Constants.PAGE), 1200, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.ON_THE_AIR_TODAY_TV_SHOWS,Constants.PAGE), 1400, TimeUnit.MILLISECONDS);
    }
    private void ObserverAnyChange(){
        movieListViewModel.getMovies().observe(this, movieModels -> {
            List<CategoryItem> list=new ArrayList<>();
            if(movieModels!=null){
                for(int i=0; i<movieModels.size(); ++i)
                    list.add(new CategoryItem(movieModels.get(i).getId(), movieModels.get(i).getPoster_path(),"movie"));

                if(movieModels.size()>0){
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.MOST_POPULAR_MOVIES.toString()))
                        dataWrapper.addCategoryItemList(Constants.MOST_POPULAR_MOVIES,list);
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.UPCOMING_MOVIES.toString()))
                        dataWrapper.addCategoryItemList(Constants.UPCOMING_MOVIES,list);
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.TOP_RATED_MOVIES.toString()))
                        dataWrapper.addCategoryItemList(Constants.TOP_RATED_MOVIES,list);
                    if (movieModels.get(0).getCategory().equals(TypeOfRequest.NOW_PLAYING_MOVIES.toString()))
                        dataWrapper.addCategoryItemList(Constants.NOW_PLAYING_MOVIES,list);

                }
            }
        });

        movieListViewModel.getTvShows().observe(this, tvShowModels -> {
            List<CategoryItem> list=new ArrayList<>();
            if(tvShowModels!=null) {
                for (TvShowModel tvShowModel : tvShowModels)
                    list.add(new CategoryItem(tvShowModel.getId(), tvShowModel.getPoster_path(),"tv_serie"));

                if(tvShowModels.size()>0){
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.MOST_POPULAR_TV_SHOWS.toString()))
                        dataWrapper.addCategoryItemList(Constants.MOST_POPULAR_TV_SHOWS,list);
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.TOP_RATED_TV_SHOWS.toString()))
                        dataWrapper.addCategoryItemList(Constants.TOP_RATED_TV_SHOWS,list);
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.ON_THE_AIR_TV_SHOWS.toString()))
                        dataWrapper.addCategoryItemList(Constants.ON_THE_AIR_TV_SHOWS,list);
                    if (tvShowModels.get(0).getCategory().equals(TypeOfRequest.ON_THE_AIR_TODAY_TV_SHOWS.toString()))
                        dataWrapper.addCategoryItemList(Constants.ON_THE_AIR_TODAY_TV_SHOWS,list);

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