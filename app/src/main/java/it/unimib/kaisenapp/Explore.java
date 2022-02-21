package it.unimib.kaisenapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.adapter.GenresItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.GenresMainRecyclerAdapter;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;

public class Explore extends AppCompatActivity implements GenresItemRecyclerAdapter.OnClickListener, GenresMainRecyclerAdapter.OnClickListener {
    private GenresMainRecyclerAdapter mainRecyclerAdapter;
    private List<List<CategoryItem>> categoryItemList; //contiene i film di ogni recycleview
    private ArrayList<AllCategory> allCategoryList;
    private List<String> moviesType;
    private GenresMainRecyclerAdapter adapter2;
    private MovieDatabaseViewModel movieDatabaseViewModel;
    private String title;
    private ImageButton backButton;
    private Fragment selectedFragment;
    private TextView textView;
    private ImageView img;
    RecyclerView recyclerView;
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_genre);
        recyclerView= findViewById(R.id.recycle1);
        title=getIntent().getStringExtra("title");
        allCategoryList = new ArrayList<>();
        ArrayList<CategoryItem> movies = getIntent().getParcelableArrayListExtra("num");
        ArrayList<CategoryItem> tmp = new ArrayList<>();
        double size = movies.size();
        if(movies!=null) {
            for (int j = 0; j < Math.ceil(size / 3); j++) {
                Log.v("msggg", Math.ceil(size / 3)+"");
                if (movies.size() < 3) {
                    for (int i = 0; i < movies.size(); i++) {
                        tmp.add(movies.get(i));

                    }
                    allCategoryList.add(new AllCategory("", tmp));
                    Log.v("msggg",allCategoryList.toString());
                    movies.removeAll(tmp);
                    tmp.clear();
                } else {

                    for (int i = 0; i < 3; i++) {
                        tmp.add(movies.get(i));
                        Log.v("msggg",tmp.toString());
                    }
                    Log.v("msggg",tmp.toString());



                }
                allCategoryList.add(new AllCategory("", tmp));
                Log.v("msggg",allCategoryList.toString());
                movies.removeAll(tmp);
                tmp.clear();

            }
            /*for (int i = 0; i < 3; i++) {
                tmp.add(movies.get(i));
            }
            allCategoryList.add(new AllCategory("", tmp));
            allCategoryList.add(new AllCategory("", tmp));
            allCategoryList.add(new AllCategory("", tmp));
            allCategoryList.add(new AllCategory("", tmp));*/



            Log.v("msggg", allCategoryList + "");
            adapter2 = new GenresMainRecyclerAdapter(this, allCategoryList, this, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setAdapter(adapter2);
            recyclerView.setLayoutManager(layoutManager);
        }
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(title);
        backButton = (ImageButton) findViewById(R.id.imageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(int position, String type) {

    }

    @Override
    public void onClick(String type) {

    }



    /*@Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i< Constants.NUMBERS_OF_MOVIES_IN_A_RECYCLEVIEW; i++) {
            categoryItemList.add(new ArrayList<>());
            // moviesType.add("");
        }
        for(int i=0; i< moviesType.size(); i++)
            allCategoryList.add(new AllCategory(moviesType.get(i), categoryItemList.get(i)));

        adapter2=new GenresMainRecyclerAdapter(getContext(), allCategoryList, Explore.this);
        adapter2.addAllCategory(allCategoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }*/
    /*@Override
    public void onClick(int position, String type) {

    }*/


    }
