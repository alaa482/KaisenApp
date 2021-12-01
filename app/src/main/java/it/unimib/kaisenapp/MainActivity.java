package it.unimib.kaisenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.adapter.FilmAdapter;
import it.unimib.kaisenapp.data.Film;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFilm;
    private RecyclerView recyclerViewAnime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerViewFilm=findViewById(R.id.recyclerViewHome);
        recyclerViewFilm.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewAnime=findViewById(R.id.recyclerViewHomeAnime);
        recyclerViewAnime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Film> list=new ArrayList<>();
        List<Film> anime=new ArrayList<>();
        for (int i=0; i<10; i++)
            list.add(new Film("Venom", R.drawable.venom));

        for (int i=0; i<10; i++)
            anime.add(new Film("Dragon Ball Super", R.drawable.dragon_ball));


        FilmAdapter filmAdapter = new FilmAdapter(this,list);
        FilmAdapter animeAdapter = new FilmAdapter(this,anime);
        recyclerViewAnime.setAdapter(animeAdapter);
        recyclerViewFilm.setAdapter(filmAdapter);
    }
}