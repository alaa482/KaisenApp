package it.unimib.kaisenapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.SearchMultiModel;
import it.unimib.kaisenapp.utils.DataWrapper;

public class SearchedMovies extends AppCompatActivity implements CategoryItemRecyclerAdapter.OnClickListener {
    private RecyclerView recyclerView;
    private CategoryItemRecyclerAdapter categoryItemRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searched_movies);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        List<SearchMultiModel> movies= (List<SearchMultiModel>) getIntent().getSerializableExtra("searchedMovies");
        Log.v("Tag", movies+"");
        recyclerView = findViewById(R.id.searched_movie_recycleview);
        categoryItemRecyclerAdapter = new CategoryItemRecyclerAdapter(this,  new ArrayList<>(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);





    }

    @Override
    public void onClick(int id, String type) {

    }
}
