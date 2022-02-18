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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter2;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter2;
import it.unimib.kaisenapp.database.MovieEntity;

import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.ui.FilmSpec;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class MyMoviesFragment extends Fragment implements CategoryItemRecyclerAdapter.OnClickListener{

    private MainRecyclerAdapter mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private List<AllCategory> allCategoryList;
    private List<String> moviesType;

    private MainRecyclerAdapter adapter2;

    private MovieDatabaseViewModel movieDatabaseViewModel;
    RecyclerView recyclerView;
    List<MovieEntity> movieEntitiesApp;





    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.activity_fav_list, container, false);

        recyclerView=(RecyclerView) homeView.findViewById(R.id.recyclerViewHome);
        categoryItemList=new ArrayList<>();
        allCategoryList=new ArrayList<>();
        moviesType=new ArrayList<>();
        movieEntitiesApp = new ArrayList<>();
        movieDatabaseViewModel= new ViewModelProvider(this).get(MovieDatabaseViewModel.class);
        addMoviesType(moviesType);
        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++) {
            categoryItemList.add(new ArrayList<>());
        }
        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));

        adapter2=new MainRecyclerAdapter(getContext(), allCategoryList, MyMoviesFragment.this);
        adapter2.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return homeView;
    }

    private void getMoviesFromDatabase() {
          movieDatabaseViewModel.getAllMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {

                if (movieEntities != null) {
                    CategoryItem c;
                    for (MovieEntity mm : movieEntities) {
                        c = new CategoryItem(mm.getMovie_id(), mm.getPoster_path(), "Movie");
                            if (mm.isFavorite()) {

                                    allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.FAVORITES, null))).getCategoryItemList().add(c);



                            }
                            if (mm.isWatched()) {

                                    allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.WATCHED, null))).getCategoryItemList().add(c);

                            }
                            if (mm.isSaved()) {

                                    allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.PLAN_TO_WATCH, null))).getCategoryItemList().add(c);

                            }
                        }
                        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), allCategoryList, MyMoviesFragment.this);
                        recyclerView.setAdapter(mainRecyclerAdapter);
                        movieEntitiesApp = movieEntities;
                    }

            }
        });





                }


    @Override
    public void onResume() {
        super.onResume();

        getMoviesFromDatabase();
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

        }
    }
}