package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.TvSerieAdapter;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class Episodes extends AppCompatActivity {
    private  MovieListViewModel movieListViewModel;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series);

        int id = getIntent().getIntExtra("id",44797);
        int number = getIntent().getIntExtra("number",1);

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);

        observe();

        movieListViewModel.getEpisode(id,number);


        /*
        TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(this, s1, s2, s3, images);
        recyclerView.setAdapter(tvSerieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
*/
    }
    void observe(){
        movieListViewModel.getEpisode().observe(this, new Observer<List<TvSerieModel>>() {
            @Override
            public void onChanged(List<TvSerieModel> tvSerieModels) {
                Log.v("test", tvSerieModels.toString());
            }
        });
    }
}