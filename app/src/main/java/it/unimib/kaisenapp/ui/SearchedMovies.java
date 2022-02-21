package it.unimib.kaisenapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.SearchedMovieRecycleAdapter;
import it.unimib.kaisenapp.fragment.SearchFragment;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.SearchMultiModel;

public class SearchedMovies extends AppCompatActivity implements SearchedMovieRecycleAdapter.OnClickListener {
    private RecyclerView recyclerView;
    private SearchedMovieRecycleAdapter searchedMovieRecycleAdapter;
    private List<CategoryItem> categoryItemList;
    private ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searched_movies);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ArrayList<SearchMultiModel> movies=  getIntent().getParcelableArrayListExtra("list");
        categoryItemList=new ArrayList<>();
        for(SearchMultiModel s: movies){
            if(s.getPoster_path()!=null){
                if(s.getMedia_type().equalsIgnoreCase("tv"))
                    categoryItemList.add(new CategoryItem(s.getId(), s.getPoster_path(),s.getMedia_type(), s.getName()));
                else
                    categoryItemList.add(new CategoryItem(s.getId(), s.getPoster_path(),s.getMedia_type(), s.getTitle()));
            }
        }


        recyclerView = findViewById(R.id.recycle1);
        searchedMovieRecycleAdapter = new SearchedMovieRecycleAdapter(this,  categoryItemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(searchedMovieRecycleAdapter);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        backButton = (ImageButton) findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchedMovies.this, SearchFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(int id, String type) {
        if(type.equals("movie")){
            Intent intent = new Intent(this, FilmSpec.class);
            intent.putExtra("id", id);
            startActivity(intent);

        }else  if(type.equals("tv")){
            Intent intent = new Intent(this, SeriesSpec.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

    }
}
