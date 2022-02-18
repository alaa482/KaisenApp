package it.unimib.kaisenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.SearchedMovies;
import it.unimib.kaisenapp.models.SearchMultiModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class SearchFragment extends Fragment {
    private MovieListViewModel movieListViewModel;
    private View view;
    private EditText text;
    private ImageButton button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_search, container, false);
        movieListViewModel=new ViewModelProvider(this).get(MovieListViewModel.class);

        text=view.findViewById(R.id.text);
        button=view.findViewById(R.id.button);
        search();
        observer();
        return view;
    }
    private boolean isValid(String text){
        return text!=null && text.length()>0;
    }

    private void search(){
        if(isValid(text.getText().toString())) {
            button.setOnClickListener(view -> {
                Log.v("Tag", text.getText().toString());
                movieListViewModel.search(text.getText().toString(), Constants.PAGE);
            });
        }

    }

    private void observer(){
        movieListViewModel.getSearchedMulti().observe(SearchFragment.this, searchMultiModels -> {
            if(searchMultiModels !=null){
                    Log.v("Tag", searchMultiModels.toString());




            }
        });
    }


}
