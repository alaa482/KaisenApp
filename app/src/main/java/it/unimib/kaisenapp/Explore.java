package it.unimib.kaisenapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.GenresItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.GenresMainRecyclerAdapter;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.fragment.MyMoviesFragment;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;

public class Explore extends AppCompatActivity {
    private GenresMainRecyclerAdapter mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private List<AllCategory> allCategoryList;
    private List<String> moviesType;
    private GenresMainRecyclerAdapter adapter2;
    private MovieDatabaseViewModel movieDatabaseViewModel;
    private String title;
    RecyclerView recyclerView;
    List<MovieEntity> movieEntitiesApp;
    List<TvShowEntity> serieEntitiesApp;
    public void onCreate( @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_genre);
        title=getIntent().getStringExtra("title");
        Log.v("msggg",title);

        /*categoryItemList=new ArrayList<>();
        allCategoryList=new ArrayList<>();
        moviesType=new ArrayList<>();
        movieEntitiesApp = new ArrayList<>();
        movieDatabaseViewModel= new ViewModelProvider(this).get(MovieDatabaseViewModel.class);*/
        //movieDatabaseViewModel.deleteAllMovies();


    }



    /*@Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++) {
            categoryItemList.add(new ArrayList<>());
            // moviesType.add("");
        }
        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));

        adapter2=new GenresMainRecyclerAdapter(getContext(), allCategoryList, Explore.this);
        adapter2.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }*/
    /*@Override
    public void onClick(int position, String type) {

    }*/


    }

