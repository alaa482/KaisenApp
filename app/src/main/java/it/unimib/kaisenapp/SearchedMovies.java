package it.unimib.kaisenapp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import it.unimib.kaisenapp.adapter.SearchedMovieRecycleAdapter;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.SearchMultiModel;

public class SearchedMovies extends AppCompatActivity implements SearchedMovieRecycleAdapter.OnClickListener {
    private RecyclerView recyclerView;
    private SearchedMovieRecycleAdapter searchedMovieRecycleAdapter;
    private List<CategoryItem> categoryItemList;

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

        recyclerView = findViewById(R.id.recycle);
        searchedMovieRecycleAdapter = new SearchedMovieRecycleAdapter(this,  categoryItemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(searchedMovieRecycleAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(int id, String type) {
        Toast.makeText(this, "ID: "+id+" "+type, Toast.LENGTH_SHORT).show();
        if(type.equalsIgnoreCase("movie")){

        }else{

        }

    }
}
