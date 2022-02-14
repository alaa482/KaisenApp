package it.unimib.kaisenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.fragment.HomeFragment;
import it.unimib.kaisenapp.fragment.MyMoviesFragment;
import it.unimib.kaisenapp.fragment.ProfileFragment;
import it.unimib.kaisenapp.fragment.SearchFragment;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.MovieEntity;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;
;
public class MainActivity extends AppCompatActivity implements CategoryItemRecyclerAdapter.OnClickListener {
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private BottomNavigationView bottomNavigationView;

    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private List<AllCategory> allCategoryList;

    private MovieListViewModel movieListViewModel;
    private List<String> moviesType; //categorie dei film: al cimena, pià votati ...



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        categoryItemList=new ArrayList<>();
        moviesType=new ArrayList<>();
        allCategoryList=new ArrayList<>();
        addMoviesType(moviesType);
        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++)
            categoryItemList.add(new ArrayList<>());

        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));

        bottomNavigationView=findViewById(R.id.bottomBar);
        bottomNavigationClick();
        //setupSearchView();
        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserverAnyChange();

        if(isConnected()){
            getMovies(TypeOfRequest.TOP_RATED_MOVIES, Constants.PAGE);
            getMovies(TypeOfRequest.UPCOMING_MOVIES, Constants.PAGE);
            getMovies(TypeOfRequest.NOW_PLAYING_MOVIES, Constants.PAGE);
            getMovies(TypeOfRequest.SIMILAR_TO_MOVIES, 634649, Constants.PAGE);
            getMovies(TypeOfRequest.MOST_POPULAR_MOVIES, Constants.PAGE);
        }else
            Toast.makeText(this, "BRO HAI LA CONNESSIONE SPENTA",Toast.LENGTH_LONG).show();


        insertDataToDatabase();


        mainCategoryRecycler = findViewById(R.id.recyclerViewHome);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainCategoryRecycler.setLayoutManager(layoutManager);

    }

    private void insertDataToDatabase(){
        MovieEntity m = new MovieEntity(0,11, "immagine");
        movieListViewModel.insertMovie(m);
        Toast.makeText(this, "Aggiunto",Toast.LENGTH_LONG).show();

    }


    private void addMoviesType(List<String> moviesType) {
        moviesType.add(Constants.MOST_POPULAR_MOVIES);
        moviesType.add(Constants.UPCOMING_MOVIES);
        moviesType.add(Constants.NOW_PLAYING_MOVIES);
        moviesType.add(Constants.SIMILAR_TO_MOVIES);
        moviesType.add(Constants.TOP_RATED_MOVIES);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

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
    private void bottomNavigationClick(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                Fragment selectedFragment =null;
                switch(item.getItemId()){

                    /*case R.id.home:
                        selectedFragment=new HomeFragment();
                        break;*/
                    case R.id.search:
                        selectedFragment=new SearchFragment();
                        break;
                    case R.id.mylist:
                        selectedFragment=new MyMoviesFragment();
                        break;
                    case R.id.account:
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

    //Observering any data change (live data fa da observer)
    private void ObserverAnyChange(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                List<CategoryItem> list=new ArrayList<>();
                TypeOfRequest lastRequest=null;

                if(movieModels!=null){
                    synchronized (this){
                        if(movieModels.size()>0){
                            lastRequest=TypeOfRequest.valueOf(movieModels.get(0).getCategory());
                            Log.v("Tag", "REQ : "+ lastRequest+ " - "+movieModels.get(0).getTitle());
                        }
                    }

                    for(MovieModel movieModel: movieModels){
                        list.add(new CategoryItem(movieModel.getId(), movieModel.getPoster_path()));
                       // Log.v("Tag", "TITOLO: "+ movieModel.getTitle()+" "+movieModel.getId());

                    }

                    if(allCategoryList!=null) {
                        if(lastRequest == TypeOfRequest.MOST_POPULAR_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(new String(Constants.MOST_POPULAR_MOVIES), null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.UPCOMING_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(new String(Constants.UPCOMING_MOVIES), null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.TOP_RATED_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(new String(Constants.TOP_RATED_MOVIES), null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.NOW_PLAYING_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(new String(Constants.NOW_PLAYING_MOVIES), null))).getCategoryItemList().addAll(list);

                        if (lastRequest == TypeOfRequest.SIMILAR_TO_MOVIES)
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(new String(Constants.SIMILAR_TO_MOVIES), null))).getCategoryItemList().addAll(list);
                    }


                    mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, allCategoryList, MainActivity.this);
                    mainCategoryRecycler.setAdapter(mainRecyclerAdapter);


                }

            }
        });
    }

    private synchronized void getMovies(TypeOfRequest typeOfRequest, int page){
        movieListViewModel.getMovies(typeOfRequest, page);
    }
    private synchronized void getMovies(TypeOfRequest typeOfRequest,int id, int page){
        movieListViewModel.getMovies(typeOfRequest,id, page);
    }


    // 4 chiamata a searchMovie
   /*private void searchMovieApi(String query, int page){
        movieListViewModel.searchMovie(query, page);
    }*/
    @Override
    public void onClick(int id) {
        Toast.makeText(this, "ID: "+id, Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(this, ActivityTest.class);
        startActivity(intent);*/

    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)? true: false;

    }







}