package it.unimib.kaisenapp.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter2;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter2;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class MyMoviesFragment extends Fragment{
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter2 mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private List<AllCategory> allCategoryList;
    private List<String> moviesType;
    private List<String> image;
    private CategoryItemRecyclerAdapter2 adapter;
    private MainRecyclerAdapter2 adapter2;
    private BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;



    private MovieListViewModel movieListViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.activity_fav_list, container, false);

        recyclerView=(RecyclerView) homeView.findViewById(R.id.recyclerViewHome);
        categoryItemList=new ArrayList<>();
        allCategoryList=new ArrayList<>();
        moviesType=new ArrayList<>();
        image=new ArrayList<>();

        addMoviesType(moviesType);
         Log.v("msg",moviesType.get(0));
            for (int j = 0; j < Constants.NUMBERS_OF_FAV_IN_A_RECYCLEVIEW; j++)
                image.add("");

        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++)
            categoryItemList.add(new ArrayList<>());
            categoryItemList.get(0).add(new CategoryItem(0,"",""));
        mainRecyclerAdapter = new MainRecyclerAdapter2(getContext(), allCategoryList);
        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));


        //adapter=new CategoryItemRecyclerAdapter2(getContext(),image);
        adapter2=new MainRecyclerAdapter2(getContext(), allCategoryList);
        //RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        //recyclerView.setLayoutManager(layout);
        adapter2.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter2);
        //recyclerView.setAdapter(adapter);


        return homeView;
    }

    private void addMoviesType(List<String> moviesType) {
        moviesType.add(Constants.FAVORITES);
        moviesType.add(Constants.WATCHED);
        moviesType.add(Constants.PLAN_TO_WATCH);
    }


}