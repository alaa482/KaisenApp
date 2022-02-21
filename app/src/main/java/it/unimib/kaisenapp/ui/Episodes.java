package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import it.unimib.kaisenapp.R;

import it.unimib.kaisenapp.adapter.TvSerieAdapter;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class Episodes extends AppCompatActivity {
    private  MovieListViewModel movieListViewModel;

    RecyclerView recyclerView;
    private ImageButton backBTNUI;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series);
        int id = getIntent().getIntExtra("id",44797);
        int number = getIntent().getIntExtra("number",0);
        backBTNUI = (ImageButton) findViewById(R.id.arrow_back);
        title=(TextView) findViewById(R.id.title);
        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel= new ViewModelProvider(this).get(MovieListViewModel.class);
        title.setText(getIntent().getStringExtra("title"));
        backBTNUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                finish();

            }
        });
        movieListViewModel.getEpisode().observe(this, new Observer<List<TvSerieModel>>() {
            @Override
            public void onChanged(List<TvSerieModel> tvSerieModels) {
                TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(Episodes.this,tvSerieModels);
                recyclerView.setAdapter(tvSerieAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(Episodes.this));
            }
        });
        movieListViewModel.getEpisode(id,number);

        /*

*/
    }

}