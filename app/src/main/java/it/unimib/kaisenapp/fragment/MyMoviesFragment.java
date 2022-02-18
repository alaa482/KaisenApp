package it.unimib.kaisenapp.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter2;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter2;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class MyMoviesFragment extends Fragment implements CategoryItemRecyclerAdapter.OnClickListener{
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter2 mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private List<AllCategory> allCategoryList;
    private List<String> moviesType;
    private List<String> image;
    private CategoryItemRecyclerAdapter2 adapter;
    private MainRecyclerAdapter adapter2;
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

        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++) {
            categoryItemList.add(new ArrayList<>());
           // moviesType.add("");
        }

        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++)
            categoryItemList.get(i).add(new CategoryItem(i,"",""));



        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));

        getMoviesFromDatabase();
        adapter2=new MainRecyclerAdapter(getContext(), allCategoryList, MyMoviesFragment.this);
        adapter2.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter2);



        return homeView;
    }

    private void getMoviesFromDatabase() {



                List<CategoryItem> list=new ArrayList<>();
    for(int i = 0; i<Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW;i++){
        list.add(new CategoryItem(i,"",""));
    }


                    if(allCategoryList!=null) {
                        allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.FAVORITES, null))).getCategoryItemList().addAll(list);
                        allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.WATCHED, null))).getCategoryItemList().addAll(list);
                        allCategoryList.get(allCategoryList.indexOf(new AllCategory(Constants.PLAN_TO_WATCH, null))).getCategoryItemList().addAll(list);
                    }

                    mainRecyclerAdapter = new MainRecyclerAdapter2(getContext(), allCategoryList);
        recyclerView.setAdapter(mainRecyclerAdapter);

                }




    private void addMoviesType(List<String> moviesType) {
        moviesType.add(Constants.FAVORITES);
        moviesType.add(Constants.WATCHED);
        moviesType.add(Constants.PLAN_TO_WATCH);
    }


    @Override
    public void onClick(int position, String type) {

    }
}