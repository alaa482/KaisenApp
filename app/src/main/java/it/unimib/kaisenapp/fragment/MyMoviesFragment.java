package it.unimib.kaisenapp.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.ui.Explore;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.database.MovieEntity;

import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.ui.FilmSpec;
import it.unimib.kaisenapp.ui.SeriesSpec;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;

public class MyMoviesFragment extends Fragment implements CategoryItemRecyclerAdapter.OnClickListener, MainRecyclerAdapter.OnClickListener {
    private MainRecyclerAdapter mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private ArrayList<AllCategory> allCategoryList;
    private List<String> moviesType;
    private MainRecyclerAdapter adapter2;
    private int contF;
    private int contS;
    private int cont;
    private int contOF;
    private int contOS;
    private double contO;
    private MovieDatabaseViewModel movieDatabaseViewModel;
    RecyclerView recyclerView;
    List<MovieEntity> movieEntitiesApp;
    List<TvShowEntity> serieEntitiesApp;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.activity_fav_list, container, false);
        recyclerView=(RecyclerView) homeView.findViewById(R.id.recyclerViewHome);
        categoryItemList=new ArrayList<>();
        allCategoryList=new ArrayList<>();
        moviesType=new ArrayList<>();
        movieEntitiesApp = new ArrayList<>();
        movieDatabaseViewModel= new ViewModelProvider(this).get(MovieDatabaseViewModel.class);
        addMoviesType(moviesType);
        return homeView;
    }

    private void getMoviesFromDatabase() {
          movieDatabaseViewModel.getAllMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {

                if (movieEntities != null) {

                    CategoryItem c;
                    for (MovieEntity mm : movieEntities) {

                            if (mm.isFavorite()) {

                                c = new CategoryItem(mm.getMovie_id(), mm.getPoster_path(), "Movie","");
                                allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.FAVORITES, new ArrayList<>()))).getCategoryItemList().add(c);
                            }
                            if (mm.isWatched()) {
                                contF++;
                                c = new CategoryItem(mm.getMovie_id(), mm.getPoster_path(), "Movie","");
                                allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.WATCHED, new ArrayList<>()))).getCategoryItemList().add(c);


                            }
                            if (mm.isSaved()) {

                                c = new CategoryItem(mm.getMovie_id(), mm.getPoster_path(), "Movie","");
                                allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.PLAN_TO_WATCH, new ArrayList<>()))).getCategoryItemList().add(c);
                            }
                        }
                }
            }
        });
    }
    private void getSeriesFromDatabase() {
        movieDatabaseViewModel.getAllTvShows().observe(getViewLifecycleOwner(), new Observer<List<TvShowEntity>>() {
            @Override
            public void onChanged(List<TvShowEntity> movieEntities) {
                if (movieEntities != null) {

                    CategoryItem c;
                    for (TvShowEntity mm : movieEntities) {

                        if (mm.isFavorite()) {

                            c = new CategoryItem(mm.getId(), mm.getPoster_path(), "tv_serie","");
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.FAVORITES, null))).getCategoryItemList().add(c);
                        }
                        if (mm.isWatched()) {
                            c = new CategoryItem(mm.getId(), mm.getPoster_path(), "tv_serie","");
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.WATCHED, null))).getCategoryItemList().add(c);

                        }
                        if (mm.isSaved()) {

                            c = new CategoryItem(mm.getId(), mm.getPoster_path(), "tv_serie","");
                            allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.PLAN_TO_WATCH, null))).getCategoryItemList().add(c);
                        }
                    }
                    mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), allCategoryList, MyMoviesFragment.this,MyMoviesFragment.this);
                    recyclerView.setAdapter(mainRecyclerAdapter);
                    serieEntitiesApp = movieEntities;
                }
            }
        });





    }
    private boolean getFavMoviesFromDatabase() {
        movieDatabaseViewModel.getAllWatchedMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                if (movieEntities != null) {
                    contF=movieEntities.size();
                    cont=cont+contF;
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("numSf").setValue(cont);
                    for (MovieEntity mm: movieEntities) {
                        contOF=mm.getRuntime();
                        contO=contO+contOF;
                    }
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ore").setValue(contO);
                }
            }
        });
        return true;
    }
    private boolean getFavSerieFromDatabase() {
        movieDatabaseViewModel.getAllWatchedTvShows().observe(getViewLifecycleOwner(), new Observer<List<TvShowEntity>>() {
            @Override
            public void onChanged(List<TvShowEntity> movieEntities) {
                if (movieEntities != null) {
                    contS=movieEntities.size();
                    cont=cont+contS;
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("numSf").setValue(cont);
                    for (TvShowEntity mm: movieEntities) {
                        contOS=mm.getRuntime();
                        contOS=contOS*mm.getNumEpisode();
                        contO=contO+contOS;
                    }
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ore").setValue(contO);
                }
            }
        });
        return true;
    }



    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++) {
            categoryItemList.add(new ArrayList<>());
        }
        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));
        adapter2=new MainRecyclerAdapter(getContext(), allCategoryList, MyMoviesFragment.this,MyMoviesFragment.this);
        adapter2.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        getMoviesFromDatabase();
        getSeriesFromDatabase();
        getFavMoviesFromDatabase();
        getFavSerieFromDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        allCategoryList.clear();
        categoryItemList.clear();
        contS=0;
        contF=0;
        cont=0;
        contOS=0;
        contOF=0;
        contO=0;
    }

    private void addMoviesType(List<String> moviesType) {
        moviesType.add(Constants.FAVORITES);
        moviesType.add(Constants.WATCHED);
        moviesType.add(Constants.PLAN_TO_WATCH);
    }


    @Override
    public void onClick(int position, String type) {
        if(type.equals("Movie")){
            Intent intent = new Intent(getContext(), FilmSpec.class);
            intent.putExtra("id", position);
            startActivity(intent);

        }else  if(type.equals("tv_serie")){
            Intent intent = new Intent(getContext(), SeriesSpec.class);
            intent.putExtra("id", position);
            startActivity(intent);
    }
}

    @Override
    public void onClick(String type) {
        Intent intent = new Intent(getContext(), Explore.class);
        intent.putExtra("title", type);
        intent.putExtra("num",(ArrayList<CategoryItem>) allCategoryList.get(allCategoryList.indexOf(new AllCategory(type,new ArrayList<>()))).getCategoryItemList());
        startActivity(intent);
    }
}