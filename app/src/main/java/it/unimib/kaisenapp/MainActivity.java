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

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
import java.util.List;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.fragment.HomeFragment;
import it.unimib.kaisenapp.fragment.MyMoviesFragment;
import it.unimib.kaisenapp.fragment.ProfileFragment;
import it.unimib.kaisenapp.fragment.SearchFragment;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;
;

public class MainActivity extends AppCompatActivity implements CategoryItemRecyclerAdapter.OnClickListener {
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private BottomNavigationView bottomNavigationView;
    private List<List<CategoryItem>> categoryItemList;
    private List<AllCategory> allCategoryList;
    private MovieListViewModel movieListViewModel;
    private List<String> moviesType;
    private static final int NUMBER_OF_RECYCLE_VIEW=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        categoryItemList=new ArrayList<>();
        moviesType=new ArrayList<>();
        allCategoryList=new ArrayList<>();
        addMoviesType(moviesType);
        for(List<CategoryItem> item: categoryItemList)
            item=new ArrayList<>();
        for(int i=0; i< NUMBER_OF_RECYCLE_VIEW; i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));
        
        
        bottomNavigationView=findViewById(R.id.bottomBar);
       // bottomNavigationClick();
        //setupSearchView();

        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);
        ObserverAnyChange(TypeOfRequest.MOST_POPULAR);

        searchMostPopularMovies(TypeOfRequest.MOST_POPULAR, 1);




        mainCategoryRecycler = findViewById(R.id.recyclerViewHome);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainCategoryRecycler.setLayoutManager(layoutManager);

    }

    private void addMoviesType(List<String> moviesType) {
        moviesType.add("Più pololari");
        moviesType.add("I più votati");
        moviesType.add("Prossimamente");
        moviesType.add("Al cinema");
    }

    //prendo i dati dalla barra di ricerca e chiamo le api
    private void setupSearchView(){
        final SearchView searchView=findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovie(query, 1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void bottomNavigationClick(){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                Fragment selectedFragment =null;
                switch(item.getItemId()){

                    case R.id.home:
                        selectedFragment=new HomeFragment();
                        break;
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
    private void ObserverAnyChange(TypeOfRequest typeOfRequest){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                List<CategoryItem> list=new ArrayList<>();
                if(movieModels!=null){
                    int index=0;
                    for(MovieModel movieModel: movieModels){
                        //get data
                        list.add(new CategoryItem(index, movieModel.getPoster_path()));
                        index++;
                    }
                    if(typeOfRequest==TypeOfRequest.MOST_POPULAR)
                        allCategoryList.get(allCategoryList.indexOf(new AllCategory(moviesType.get(0), null))).getCategoryItemList().addAll(list);


                    mainRecyclerAdapter = new MainRecyclerAdapter(MainActivity.this, allCategoryList, MainActivity.this);
                    mainCategoryRecycler.setAdapter(mainRecyclerAdapter);

                }

            }
        });
    }
    // 4 chiamata a searchMovie
    private void searchMovieApi(String query, int page){
        movieListViewModel.searchMovie(query, page);
    }
    private void searchMostPopularMovies(TypeOfRequest typeOfRequest, int page){
        movieListViewModel.searchMostPopularMovies(typeOfRequest, page);
    }




    @Override
    public void onClick(int position) {
        Toast.makeText(this, "nice cock", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(this, ActivityTest.class);
        startActivity(intent);*/

    }





  /*  void setWallpaperHome(){
        home_wallpaper=(ImageView) findViewById(R.id.wallpaper_id);
        Glide.with(this).load(R.drawable.wallpaper_home).into(home_wallpaper);
    }*/

    //retrofit con chiamate sul thread pricipale
  /*  private void GetRetrofitResponse() {
        MovieApi movieApi= Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Venom",
                        1
                        );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){ //caso ok
                    Log.v("Body", "Response: " +response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie: movies)
                        Log.v("Tag", "Film: "+movie.getMovie_overview());


                }else{

                    try {
                        Log.v("Tag", "ERROR "+ response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Log.d("", "ERROR");

            }
        });
    }

    private void GetRetrofitResponseAccordingToId(){
        MovieApi movieApi=Service.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                550,
                Credentials.API_KEY);
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200){
                    MovieModel movie =response.body();
                    Log.v("Tag", "Response: "+movie.getTitle());
                }else{

                    try {
                        Log.v("Tag", "ERROR: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    private void getPopularMovies(){
        List<CategoryItem> popularMovies=new ArrayList<>();
        MovieApi movieApi=Service.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getPopularMovies(
                Credentials.API_KEY,
                Credentials.LANGUAGE,
                1);
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for(int i=0; i<movies.size(); ++i)
                        popularMovies.add(new CategoryItem(i, R.drawable.wallpaper_home));

                    allCategoryList.add(new AllCategory("Most Popular", popularMovies));


                }else{

                    try {
                        Log.v("Tag", "ERROR: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

    }*/
}