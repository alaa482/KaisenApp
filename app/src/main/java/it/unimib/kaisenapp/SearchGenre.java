package it.unimib.kaisenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.kaisenapp.adapter.GenresItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.SearchedMovieRecycleAdapter;
import it.unimib.kaisenapp.fragment.SearchFragment;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.ui.FilmSpec;
import it.unimib.kaisenapp.ui.SeriesSpec;

public class SearchGenre extends AppCompatActivity implements GenresItemRecyclerAdapter.OnClickListener {
    private RecyclerView recyclerView;
    private SearchedMovieRecycleAdapter searchedMovieRecycleAdapter;
    private List<CategoryItem> categoryItemList;
    private ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backButton = (ImageButton) findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchGenre.this, SearchFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
            }
        });
    }

    @Override
    public void onClick(int position, String type) {
        if(type.equals("movie")){
            Intent intent = new Intent(this, FilmSpec.class);
            //intent.putExtra("id", id);
            startActivity(intent);

        }else  if(type.equals("tv")){
            Intent intent = new Intent(this, SeriesSpec.class);
            //intent.putExtra("id", id);
            startActivity(intent);
        }
    }
}
