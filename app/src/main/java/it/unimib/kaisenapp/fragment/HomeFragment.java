package it.unimib.kaisenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
;
import it.unimib.kaisenapp.ui.FilmSpec;
import it.unimib.kaisenapp.ui.SeriesSpec;
import it.unimib.kaisenapp.utils.DataWrapper;

public class HomeFragment extends Fragment  implements CategoryItemRecyclerAdapter.OnClickListener{
    //RecycleView - Adapter
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.fragment_home, container, false);
        mainCategoryRecycler = homeView.findViewById(R.id.searched_movie_recycleview);
        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(),  DataWrapper.getList(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        mainCategoryRecycler.setLayoutManager(layoutManager);



        return homeView;
    }

    @Override
    public void onClick(int id, String type) {
        if (type.equals("movie")) {
            Intent intent = new Intent(getContext(), FilmSpec.class);
            intent.putExtra("id", id);
            startActivity(intent);

        } else if (type.equals("tv_serie")) {
            Intent intent = new Intent(getContext(), SeriesSpec.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }
}
