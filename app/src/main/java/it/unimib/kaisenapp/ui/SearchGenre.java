package it.unimib.kaisenapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import it.unimib.kaisenapp.utils.AppExecutor;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.GenresItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.GenresMainRecyclerAdapter;
import it.unimib.kaisenapp.fragment.SearchFragment;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class SearchGenre extends AppCompatActivity implements GenresItemRecyclerAdapter.OnClickListener, GenresMainRecyclerAdapter.OnClickListener {
    private GenresMainRecyclerAdapter mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private ArrayList<AllCategory> allCategoryList;
    private ArrayList<CategoryItem> movies;
    private GenresMainRecyclerAdapter adapter2;
    private MovieDatabaseViewModel movieDatabaseViewModel;
    private String title;
    private ImageButton backButton;
    private Fragment selectedFragment;
    private TextView textView;

    private ImageView img;
    private String genre;
    private Map<String,Integer> genres;
    private MovieListViewModel movieListViewModel;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genres = new HashMap<>();

        fill();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_search_genre);
        recyclerView= findViewById(R.id.recycle1);

        allCategoryList = new ArrayList<>();
        movies = new ArrayList<>();


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        genre = getIntent().getStringExtra("genre");



        textView = (TextView) findViewById(R.id.textView);
        textView.setText(genre);
        backButton = (ImageButton) findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchGenre.this, SearchFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            }
        });
    }

    private void fill() {
        genres.put("azione",28);
        genres.put("avventura",12);
        genres.put("animazione",16);
        genres.put("commedia",35);
        genres.put("crime",80);
        genres.put("documentario",99);
        genres.put("dramma",18);
        genres.put("famiglia",10751);
        genres.put("fantasy",14);
        genres.put("storia",36);
        genres.put("horror",27);
        genres.put("musica",10402);
        genres.put("mistero",9648);
        genres.put("romance",10749);
        genres.put("fantascienza",878);
        genres.put("thriller",53);
        genres.put("guerra",10752);
        genres.put("western",37);
    }

    private void getMovie() {
        movieListViewModel.getMoviesByGenre().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                ArrayList<CategoryItem> tmp = new ArrayList<>();
                allCategoryList.clear();
                if (movieModels != null) {

                    for (MovieModel mm : movieModels) {
                        movies.add(new CategoryItem(mm.getId(), mm.getPoster_path(), "movie", ""));
                    }
                    double size = movies.size();
                    for (int j = 0; j < Math.ceil(size / 3); j++) {

                        if (movies.size() < 3) {
                            for (int i = 0; i < movies.size(); i++) {
                                tmp.add(movies.get(i));

                            }
                            allCategoryList.add(new AllCategory("", tmp));

                            movies.removeAll(tmp);
                            tmp.clear();
                        } else {

                            for (int i = 0; i < 3; i++) {
                                tmp.add(movies.get(i));

                            }



                        }
                        allCategoryList.add(new AllCategory("", tmp));

                        movies.removeAll(tmp);
                        tmp.clear();

                    }



                }
            }
        });
    }
    public void getSerie(){
        movieListViewModel.getTvSeriesByGenre().observe(this, new Observer<List<TvShowModel>>() {
            @Override
            public void onChanged(List<TvShowModel> movieModels) {
                ArrayList<CategoryItem> tmp = new ArrayList<>();

                if(movieModels!=null){


                    for (TvShowModel mm: movieModels) {
                        movies.add(new CategoryItem(mm.getId(),mm.getPoster_path(),"tv",""));
                    }
                    double size = movies.size();
                    for (int j = 0; j < Math.ceil(size / 3); j++) {

                        if (movies.size() < 3) {
                            for (int i = 0; i < movies.size(); i++) {
                                tmp.add(movies.get(i));

                            }
                            allCategoryList.add(new AllCategory("", tmp));

                            movies.removeAll(tmp);
                            tmp.clear();
                        } else {

                            for (int i = 0; i < 3; i++) {
                                tmp.add(movies.get(i));

                            }

                        }
                        allCategoryList.add(new AllCategory("", tmp));

                        movies.removeAll(tmp);
                        tmp.clear();

                    }

                    adapter2 = new GenresMainRecyclerAdapter(SearchGenre.this, allCategoryList, SearchGenre.this, SearchGenre.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchGenre.this);
                    recyclerView.setAdapter(adapter2);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


        getSerie();
        getMovie();


        AppExecutor.getInstance().networkIO().schedule(() ->
                movieListViewModel.getMoviesByGenre(genres.get(genre.toLowerCase()), Constants.PAGE) , 0,  TimeUnit.MILLISECONDS);

        AppExecutor.getInstance().networkIO().schedule(() ->
                movieListViewModel.getTvSeriesByGenre(genres.get(genre.toLowerCase()), Constants.PAGE), 100,  TimeUnit.MILLISECONDS);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(genre);




    }

    @Override
    public void onPause() {
        super.onPause();


        allCategoryList.clear();
        movies.clear();
    }

    @Override
    public void onClick(int position, String type) {

        if(type.equals("movie")){
            Intent intent = new Intent(this, FilmSpec.class);
            intent.putExtra("id", position);
            startActivity(intent);

        }else  if(type.equals("tv")){
            Intent intent = new Intent(this, SeriesSpec.class);
            intent.putExtra("id", position);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(String type) {

    }
}
