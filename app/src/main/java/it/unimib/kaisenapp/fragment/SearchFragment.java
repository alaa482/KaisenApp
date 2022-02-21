package it.unimib.kaisenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.ui.SearchedMovies;
import it.unimib.kaisenapp.ui.SearchGenre;
import it.unimib.kaisenapp.models.SearchMultiModel;
import it.unimib.kaisenapp.utils.Constants;
import it.unimib.kaisenapp.viewmodels.MovieListViewModel;

public class SearchFragment extends Fragment {
    private MovieListViewModel movieListViewModel;
    private View view;
    private EditText text;
    private ImageButton button;
    private ArrayList<TextView> genres;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_search, container, false);
        movieListViewModel=new ViewModelProvider(this).get(MovieListViewModel.class);
        text=view.findViewById(R.id.text);
        button=view.findViewById(R.id.button);
        genres=new ArrayList<>();
        setId();
        observer();

        searchByGenre();
        return view;
    }

    private void search(){
        button.setOnClickListener(view -> {
            if(isValid(text.getText().toString())) {
                movieListViewModel.search(text.getText().toString(), Constants.PAGE);
            }

        });
    }
    private boolean isValid(String text){

        return text!=null && text.length()>0;
    }

    private void observer(){
        movieListViewModel.getSearchedMulti().observe(getViewLifecycleOwner(), searchMultiModels -> {

            if(searchMultiModels !=null&&text.getText().length()>0){
                Intent intent=new Intent(getActivity(), SearchedMovies.class);
                intent.putExtra("list", (ArrayList<SearchMultiModel>) searchMultiModels);
                startActivity(intent);

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        search();
    }

    @Override
    public void onPause() {
        super.onPause();
        text.setText("");


    }

    private void setId(){
        genres.add(view.findViewById(R.id.animazione));
        genres.add(view.findViewById(R.id.avventura));
        genres.add(view.findViewById(R.id.azione));
        genres.add(view.findViewById(R.id.crime));
        genres.add(view.findViewById(R.id.commedia));
        genres.add(view.findViewById(R.id.documentario));
        genres.add(view.findViewById(R.id.dramma));
        genres.add(view.findViewById(R.id.famiglia));
        genres.add(view.findViewById(R.id.fantascienza));
        genres.add(view.findViewById(R.id.musica));
        genres.add(view.findViewById(R.id.fantasy));
        genres.add(view.findViewById(R.id.guerra));
        genres.add(view.findViewById(R.id.horror));
        genres.add(view.findViewById(R.id.mistero));
        genres.add(view.findViewById(R.id.romance));
        genres.add(view.findViewById(R.id.storia));
        genres.add(view.findViewById(R.id.thriller));
        genres.add(view.findViewById(R.id.western));

    }

    private void searchByGenre(){
        for(TextView t: genres){
            t.setOnClickListener(view -> {
                TextView textView=(TextView)view;
                Intent intent=new Intent(getActivity(), SearchGenre.class);
                intent.putExtra("genre", textView.getText().toString());
                startActivity(intent);

            });
        }
    }

}
