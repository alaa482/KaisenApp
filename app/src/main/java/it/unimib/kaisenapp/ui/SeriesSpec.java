package it.unimib.kaisenapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.SeasonsReciclerAdapter;
import it.unimib.kaisenapp.adapter.SimilarReciclerAdapter;
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
import it.unimib.kaisenapp.request.Service;
import it.unimib.kaisenapp.response.SerieDetailsResponse;
import it.unimib.kaisenapp.response.SimilarResponse;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.utils.MovieApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesSpec extends AppCompatActivity implements SimilarReciclerAdapter.OnClickListener, SeasonsReciclerAdapter.OnClickListener {
    private ImageButton backBTNUI;
    private TextView plotUI;
    private TextView titleUI;
    private ImageView posterUI;
    private TextView originalTitleUI;
    private RatingBar ratingBar;
    private TextView genresUI;
    private List<GenresModel> genresList;
    private List<ProductionCompaniesModel> productionCompaniesList;
    private String originalTitle;
    private String imagePath;
    private float avarageVote;
    private String title;
    private String plot;
    private List<MovieModel> recommendations;
    private ImageButton bookmarkedUI ;
    private ImageButton favoriteUI;
    private ImageButton starUI;
    private TextView durataUI;
    private String durata;
    private Boolean bookmarked=false;
    private Boolean favorite=false;
    private Boolean star=false;
    private int icon;

    String prefix="https://image.tmdb.org/t/p/w500/";
    MovieApi movieApi = Service.getMovieApi();
    RecyclerView recyclerView;
    RecyclerView recyclerViewSeasons;
    SimilarReciclerAdapter recommendationsRecyclerAdapter;
    SeasonsReciclerAdapter seasonsReciclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_spec);

        backBTNUI = (ImageButton) findViewById(R.id.back_button);
        titleUI = (TextView) findViewById(R.id.film_title);
        plotUI = (TextView) findViewById(R.id.plot);
        posterUI = (ImageView) findViewById(R.id.poster);
        originalTitleUI=(TextView)findViewById(R.id.OriginalTitle);
        genresUI = (TextView)findViewById(R.id.Genres);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        recyclerView=(RecyclerView)findViewById(R.id.similar);
        recyclerViewSeasons=(RecyclerView)findViewById(R.id.seasons) ;
        bookmarkedUI=(ImageButton)findViewById(R.id.bookmarked);
        favoriteUI=(ImageButton)findViewById(R.id.favorite);
        starUI=(ImageButton)findViewById(R.id.star);
        durataUI=(TextView)findViewById(R.id.durata);

        bookmarkedUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if (bookmarked) {
                    Log.v("test","false");
                    bookmarked = false;
                    icon = R.drawable.bookmark_border;
                }
                else {
                    Log.v("test","true");
                    bookmarked = true;
                    icon = R.drawable.bookmark;
                }
                bookmarkedUI.setImageResource(icon);

            }
        });
        favoriteUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (favorite) {
                    Log.v("test","false");
                    favorite = false;
                    icon = R.drawable.not_favorite;
                }
                else {
                    Log.v("test","true");
                    favorite = true;
                    icon = R.drawable.favorite;
                }
                favoriteUI.setImageResource(icon);
            }
        });
        starUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (star) {
                    Log.v("test","false");
                    star = false;
                    icon = R.drawable.not_star;
                }
                else {
                    Log.v("test","true");
                    star = true;
                    icon = R.drawable.star;
                }
                starUI.setImageResource(icon);
            }
        });
        backBTNUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                finish();

            }
        });
        int id = getIntent().getIntExtra("id",99966);
        GetRetrofitResponse(id);
        GetRetrofitResponseRecommendations(id);


    }


    private void GetRetrofitResponse(int id) {


        Call<SerieDetailsResponse> responseCall = movieApi
                .getSerieDetail(
                        id,
                        Credentials.API_KEY,
                        Credentials.LANGUAGE

                );

        responseCall.enqueue(new Callback<SerieDetailsResponse>() {
            @Override
            public void onResponse(Call<SerieDetailsResponse> call, Response<SerieDetailsResponse> response) {

                if (response.code() == 200) {

                    genresList = new ArrayList<>(((SerieDetailsResponse) response.body()).getGenres());
                    originalTitle= response.body().getOriginalName();
                    imagePath = response.body().getImage_path();
                    avarageVote = response.body().getVoteAvarege();
                    title = response.body().getName();
                    plot = response.body().getPlot();
                    durata=String.valueOf(response.body().getEpisode_run_time().get(0));
                    seasonsReciclerAdapter=new SeasonsReciclerAdapter(SeriesSpec.this,response.body().getSeasons(), SeriesSpec.this);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(SeriesSpec.this, RecyclerView.HORIZONTAL, false);
                    recyclerViewSeasons.setLayoutManager(layout);
                    recyclerViewSeasons.setAdapter(seasonsReciclerAdapter);
                }

                ratingBar.setRating(avarageVote/2);
                titleUI.setText(title);
                originalTitleUI.setText(originalTitle);
                plotUI.setText(plot);
                durataUI.setText(durata+" min");

                Glide.with(posterUI)
                        .load(prefix+imagePath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(posterUI);
                String aus ="";

                for (int i = 0 ; i< genresList.size();i++){
                   aus+=String.valueOf(genresList.get(i));
                   if (i==genresList.size()-1){
                       aus=aus.substring(0,aus.length()-3);

                   }
                }
                genresUI.setText(aus);

            }

            @Override
            public void onFailure(Call<SerieDetailsResponse> call, Throwable t) {
                Log.v("test",t.toString());
            }
        });


    }
    private void GetRetrofitResponseRecommendations(int id) {

        Call<SimilarResponse> responseCall = movieApi
                .getTvSimilar(
                        id,
                        Credentials.API_KEY,
                        Credentials.LANGUAGE

                );

        responseCall.enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(Call<SimilarResponse> call, Response<SimilarResponse> response) {

                if (response.code() == 200) {
                    recommendationsRecyclerAdapter=new SimilarReciclerAdapter(SeriesSpec.this,response.body().getMovies(), SeriesSpec.this);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(SeriesSpec.this, RecyclerView.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layout);
                    recyclerView.setAdapter(recommendationsRecyclerAdapter);

                }

            }

            @Override
            public void onFailure(Call<SimilarResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(int id) {

                Intent intent = new Intent(SeriesSpec.this, SeriesSpec.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();



        }


    @Override
    public void onClick(int id, String s) {


    }
}