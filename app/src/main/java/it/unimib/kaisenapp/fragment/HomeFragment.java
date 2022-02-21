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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.ui.FilmSpec;
import it.unimib.kaisenapp.ui.SeriesSpec;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.utils.DataWrapper;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;

public class HomeFragment extends Fragment  implements CategoryItemRecyclerAdapter.OnClickListener,MainRecyclerAdapter.OnClickListener{
    //RecycleView - Adapter
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private int contF;
    private int contS;
    private int cont;
    private int contOF;
    private int contOS;
    private double contO;
    private MovieDatabaseViewModel movieDatabaseViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.fragment_home, container, false);
        mainCategoryRecycler = homeView.findViewById(R.id.searched_movie_recycleview);
        movieDatabaseViewModel= new ViewModelProvider(this).get(MovieDatabaseViewModel.class);
        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(),  DataWrapper.getList(), this,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        mainCategoryRecycler.setLayoutManager(layoutManager);



        return homeView;
    }
    private boolean getFavMoviesFromDatabase() {
        movieDatabaseViewModel.getAllWatchedMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {

                if (movieEntities != null) {
                    contF=movieEntities.size();
                    cont=cont+contF;
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("numSf").setValue(cont);

                    for (MovieEntity mm: movieEntities) {
                        contOF=mm.getRuntime();
                        contO=contO+contOF;

                    }
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ore").setValue(contO);
                }

            }
        });
        return true;
    }
    private boolean getFavSerieFromDatabase() {
        movieDatabaseViewModel.getAllWatchedTvShows().observe(getViewLifecycleOwner(), new Observer<List<TvShowEntity>>() {
            @Override
            public void onChanged(List<TvShowEntity> movieEntities) {

                if (movieEntities != null) {
                    contS=movieEntities.size();
                    cont=cont+contS;
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("numSf").setValue(cont);

                    for (TvShowEntity mm: movieEntities) {
                        contOS=mm.getRuntime();
                        contOS=contOS*mm.getNumEpisode();
                        contO=contO+contOS;


                    }
                    FirebaseDatabase.getInstance("https://progettok-362fa-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ore").setValue(contO);
                }

            }
        });
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();



        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(),  DataWrapper.getList(), this,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        mainCategoryRecycler.setLayoutManager(layoutManager);

        getFavMoviesFromDatabase();
        getFavSerieFromDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();

        contS=0;
        contF=0;
        cont=0;
        contOS=0;
        contOF=0;
        contO=0;
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

    @Override
    public void onClick(String type) {

    }
}
