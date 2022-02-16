package it.unimib.kaisenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.fragment.HomeFragment;
import it.unimib.kaisenapp.fragment.MyMoviesFragment;
import it.unimib.kaisenapp.fragment.ProfileFragment;
import it.unimib.kaisenapp.fragment.SearchFragment;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class MainActivity extends AppCompatActivity {
    //RecycleView - Adapter
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;

    private BottomNavigationView bottomNavigationView;

    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private List<AllCategory> allCategoryList;
    private List<String> moviesType;


    //ViewModels
    private MovieListViewModel movieListViewModel;
    private MovieDatabaseViewModel movieDatabaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(
                 View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
       /* mainCategoryRecycler = findViewById(R.id.recyclerViewHome);
        categoryItemList=new ArrayList<>();
        moviesType=new ArrayList<>();
        allCategoryList=new ArrayList<>();

       addMoviesType(moviesType);
        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++)
            categoryItemList.add(new ArrayList<>());

        mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, allCategoryList, MainActivity.this);
        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));*/


        bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationClick();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment()).commit();

       /* movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);
        movieDatabaseViewModel=new ViewModelProvider(this).get(MovieDatabaseViewModel.class);

        movieDatabaseViewModel.deleteAllTvShows();
        movieDatabaseViewModel.deleteAllMovies();

        ObserverAnyChange();
        getMoviesFromDatabase();
        if(isConnected()){
            getAllMoviesToSetupHome();

        }else
            Toast.makeText(this, "BRO HAI LA CONNESSIONE SPENTA",Toast.LENGTH_LONG).show();*/





    }
/*
    private void getAllMoviesToSetupHome() {
        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.MOST_POPULAR_MOVIES, Constants.PAGE), 0,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.SIMILAR_TO_MOVIES, 634649, Constants.PAGE), 300,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.NOW_PLAYING_MOVIES, Constants.PAGE), 600,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.UPCOMING_MOVIES, Constants.PAGE), 900, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getMovies(TypeOfRequest.TOP_RATED_MOVIES,Constants.PAGE), 1200, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.MOST_POPULAR_TV_SHOWS,Constants.PAGE), 1500, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.TOP_RATED_TV_SHOWS,Constants.PAGE), 1800, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.ON_THE_AIR_TV_SHOWS,Constants.PAGE), 2100, TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() -> getTvShows(TypeOfRequest.ON_THE_AIR_TODAY_TV_SHOWS,Constants.PAGE), 2400, TimeUnit.MILLISECONDS);


    }

    private List<CategoryItem> filter(List<MovieEntity> list, String category){
        List<CategoryItem> movies=new ArrayList<>();
        if(list!=null && list.size()>0){
            for(MovieEntity movieEntity: list){
                   if(movieEntity.getCategory().equals(category))
                    movies.add(new CategoryItem(movieEntity.getMovie_id(),movieEntity.getPoster_path()));
            }

        }
        return movies;
    }
    private void getMoviesFromDatabase(){
        movieDatabaseViewModel.getAllMoviesByCategory(Constants.MOST_POPULAR_MOVIES).observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                List<CategoryItem> list=new ArrayList<>();

                if(movieEntities!=null && movieEntities.size()>0){
                    for(MovieEntity m: movieEntities){
                        Log.v("Tag", m.getCategory());
                    }

                    list=filter(movieEntities, Constants.MOST_POPULAR_MOVIES);
                    for(CategoryItem c: list)
                        Log.v("Tag", c.toString());


                    if(allCategoryList!=null)
                        allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.MOST_POPULAR_MOVIES, null))).getCategoryItemList().addAll(list);


                    mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, allCategoryList, MainActivity.this);
                    mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

                }
            }
        });
    }



    private void ObserverAnyChange(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                List<CategoryItem> list=new ArrayList<>();
                TypeOfRequest lastRequest=null;
                if(movieModels!=null){
                    if(movieModels.size()>0)
                        lastRequest=TypeOfRequest.valueOf(movieModels.get(0).getCategory());

                    for(MovieModel movieModel: movieModels){
                        MovieEntity m=new MovieEntity(movieModel.getId(), movieModel.getPoster_path(), movieModel.getCategory(),false,false,false);
                        list.add(new CategoryItem(movieModel.getId(), movieModel.getPoster_path()));

                        movieDatabaseViewModel.addMovie(m);

                    }

                    if(allCategoryList!=null) {
                        if (lastRequest == TypeOfRequest.MOST_POPULAR_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.MOST_POPULAR_MOVIES, null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.UPCOMING_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.UPCOMING_MOVIES, null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.TOP_RATED_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.TOP_RATED_MOVIES, null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.NOW_PLAYING_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.NOW_PLAYING_MOVIES, null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.SIMILAR_TO_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.SIMILAR_TO_MOVIES, null))).getCategoryItemList().addAll(list);
                    }

                }
            }
        });

        movieListViewModel.getTvShows().observe(this, new Observer<List<TvShowModel>>() {
            @Override
            public void onChanged(List<TvShowModel> tvShowModels) {
                List<CategoryItem> list=new ArrayList<>();
                TypeOfRequest lastRequest=null;

                if(tvShowModels!=null) {
                    if (tvShowModels.size() > 0)
                        lastRequest = TypeOfRequest.valueOf(tvShowModels.get(0).getCategory());


                    for (TvShowModel tvShowModel : tvShowModels) {
                        TvShowEntity t=new TvShowEntity(tvShowModel.getId(),tvShowModel.getPoster_path(),tvShowModel.getCategory(),false,false,false);
                        list.add(new CategoryItem(tvShowModel.getId(), tvShowModel.getPoster_path()));
                        movieDatabaseViewModel.addTvShow(t);
                    }


                    if (allCategoryList != null) {
                        if (lastRequest == TypeOfRequest.MOST_POPULAR_TV_SHOWS)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.MOST_POPULAR_TV_SHOWS, null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.TOP_RATED_TV_SHOWS)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.TOP_RATED_TV_SHOWS, null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.ON_THE_AIR_TV_SHOWS)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.ON_THE_AIR_TV_SHOWS, null))).getCategoryItemList().addAll(list);


                        if (lastRequest == TypeOfRequest.ON_THE_AIR_TODAY_TV_SHOWS)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.ON_THE_AIR_TODAY_TV_SHOWS, null))).getCategoryItemList().addAll(list);

                    }
                }
            }
        });


        mainRecyclerAdapter.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mainCategoryRecycler.setLayoutManager(layoutManager);
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

    }

    private void addMoviesType(List<String> moviesType) {
        moviesType.add(Constants.MOST_POPULAR_MOVIES);
        moviesType.add(Constants.UPCOMING_MOVIES);
        moviesType.add(Constants.NOW_PLAYING_MOVIES);
        moviesType.add(Constants.SIMILAR_TO_MOVIES);
        moviesType.add(Constants.TOP_RATED_MOVIES);
        moviesType.add(Constants.MOST_POPULAR_TV_SHOWS);
        moviesType.add(Constants.TOP_RATED_TV_SHOWS);
        moviesType.add(Constants.ON_THE_AIR_TV_SHOWS);
        moviesType.add(Constants.ON_THE_AIR_TODAY_TV_SHOWS);
    }

    private void getMovies(TypeOfRequest typeOfRequest, int page){
        movieListViewModel.getMovies(typeOfRequest, page);
    }
    private void getMovies(TypeOfRequest typeOfRequest,int id, int page){
        movieListViewModel.getMovies(typeOfRequest,id, page);
    }
    private void getTvShows(TypeOfRequest typeOfRequest, int page){
        movieListViewModel.getTvShows(typeOfRequest, page);
    }



    @Override
    public void onClick(int id) {
        Toast.makeText(this, "ID: "+id, Toast.LENGTH_SHORT).show();
        //new Intent(this, ActivityTest.class).startActivity(intent);

    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)? true: false;

    }

*/
    private void bottomNavigationClick(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                Fragment selectedFragment =null;
                switch(item.getItemId()){

                    case R.id.home:
                        selectedFragment= new HomeFragment();
                        break;
                    case R.id.search:
                        selectedFragment=new SearchFragment();
                        break;
                    case R.id.mylist:
                        selectedFragment=new MyMoviesFragment();
                        break;
                    case R.id.account:
                        //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        selectedFragment = new ProfileFragment();
                        break;
                    default:
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            }
        });
    }

/*  @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }*/

    /*private void setupSearchView(){
        final SearchView searchView=null;//findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //movieListViewModel.searchMovie(query, 1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }*/





}