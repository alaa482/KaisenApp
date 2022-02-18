package it.unimib.kaisenapp.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import it.unimib.kaisenapp.AppExecutor;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;;
import it.unimib.kaisenapp.models.TvShowModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.utils.DataWrapper;
import it.unimib.kaisenapp.utils.TypeOfRequest;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class HomeFragment extends Fragment  implements CategoryItemRecyclerAdapter.OnClickListener{
    //RecycleView - Adapter
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.fragment_home, container, false);
        for(int i=0;i<DataWrapper.getList().size();i++)
         System.out.println(DataWrapper.getList().get(i).getCategoryTitle());

        mainCategoryRecycler = homeView.findViewById(R.id.recyclerViewHome);
        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(),  DataWrapper.getList(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        mainCategoryRecycler.setLayoutManager(layoutManager);



        return homeView;
    }

    @Override
    public void onClick(int id, String type) {
        Toast.makeText(getActivity(), "ID: "+id, Toast.LENGTH_SHORT).show();
    }
}
