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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.SeasonsReciclerAdapter;
import it.unimib.kaisenapp.adapter.SimilarReciclerAdapter;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.database.TvShowEntity;
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.MovieModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
import it.unimib.kaisenapp.request.MovieApi;
import it.unimib.kaisenapp.request.Service;
import it.unimib.kaisenapp.response.SerieDetailsResponse;
import it.unimib.kaisenapp.response.SimilarResponse;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;
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
    int id;
    private MovieDatabaseViewModel movieDatabaseViewModel;
    private TvShowEntity m;

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
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

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
        movieDatabaseViewModel= new ViewModelProvider(this).get(MovieDatabaseViewModel.class);


        bookmarkedUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                movieDatabaseViewModel.getAllTvShows().observe(SeriesSpec.this, new Observer<List<TvShowEntity>>() {
                    @Override
                    public void onChanged(List<TvShowEntity> movieEntities) {
                        for (TvShowEntity mm : movieEntities) {
                            if (mm.equals(m)) {
                                if (bookmarked) {

                                    mm.setWatched(!bookmarked);
                                    m.setWatched(!bookmarked);
                                    movieDatabaseViewModel.updateSerie(mm);
                                    Log.v("test", String.valueOf(mm.isWatched()));

                                    icon = R.drawable.bookmark_border;
                                    bookmarked=!bookmarked;


                                }
                                else {
                                    mm.setWatched(!bookmarked);
                                    m.setWatched(!bookmarked);
                                    movieDatabaseViewModel.updateSerie(mm);
                                    Log.v("test", String.valueOf(mm.isWatched()));
                                    icon = R.drawable.bookmark;
                                    bookmarked=!bookmarked;
                                }
                                bookmarkedUI.setImageResource(icon);
                            }

                        }
                    }
                });


            }
        });
        favoriteUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                movieDatabaseViewModel.getAllTvShows().observe(SeriesSpec.this, new Observer<List<TvShowEntity>>() {
                    @Override
                    public void onChanged(List<TvShowEntity> movieEntities) {
                        for (TvShowEntity mm : movieEntities) {
                            if (mm.equals(m)) {
                                if (favorite) {

                                    mm.setFavorite(!favorite);
                                    m.setFavorite(!favorite);
                                    movieDatabaseViewModel.updateSerie(mm);
                                    Log.v("test", String.valueOf(mm.isFavorite()));

                                    icon = R.drawable.not_favorite;
                                    favorite=!favorite;


                                }
                                else {
                                    mm.setFavorite(!favorite);
                                    m.setFavorite(!favorite);
                                    movieDatabaseViewModel.updateSerie(mm);
                                    Log.v("test", String.valueOf(mm.isFavorite()));
                                    icon = R.drawable.favorite;
                                    favorite=!favorite;
                                }
                                favoriteUI.setImageResource(icon);
                            }

                        }
                    }
                });
            }
        });
        starUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.v("test",movieDatabaseViewModel.toString());
                movieDatabaseViewModel.getAllTvShows().observe(SeriesSpec.this, new Observer<List<TvShowEntity>>() {
                    @Override
                    public void onChanged(List<TvShowEntity> movieEntities) {
                        for (TvShowEntity mm : movieEntities) {
                            if (mm.equals(m)) {
                                if (star) {

                                    mm.setSaved(!star);
                                    m.setSaved(!star);
                                    movieDatabaseViewModel.updateSerie(mm);
                                    Log.v("test", String.valueOf(mm.isSaved()));

                                    icon = R.drawable.not_star;
                                    star = !star;


                                } else {
                                    mm.setSaved(!star);
                                    m.setSaved(!star);
                                    movieDatabaseViewModel.updateSerie(mm);
                                    Log.v("test", String.valueOf(mm.isSaved()));
                                    icon = R.drawable.star;
                                    star = !star;
                                }
                                starUI.setImageResource(icon);
                            }

                        }
                    }

                });


            }
        });
        backBTNUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                finish();

            }
        });
        id= getIntent().getIntExtra("id",99966);
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
                m = new TvShowEntity(id,imagePath,null,bookmarked,star,favorite);



                Log.v("test",movieDatabaseViewModel.getAllMovies().toString());
                /*movieDatabaseViewModel.getAllTvShows().observe(SeriesSpec.this, new Observer<List<TvShowEntity>>() {
                    @Override
                    public void onChanged(List<TvShowEntity> movieEntityList) {
                        if(movieEntityList!=null){

                            for (TvShowEntity mm: movieEntityList) {



                                if(mm.equals(m)){
                                    Log.v("test",mm.toString()+" mm");
                                    favorite = mm.isFavorite();
                                    bookmarked = mm.isWatched();
                                    star = mm.isSaved();
                                    if (!mm.isSaved()) {

                                        icon = R.drawable.not_star;
                                    }
                                    else {

                                        icon = R.drawable.star;
                                    }
                                    starUI.setImageResource(icon);

                                    if (!mm.isFavorite()) {

                                        icon = R.drawable.not_favorite;
                                    }
                                    else {

                                        icon = R.drawable.favorite;
                                    }

                                    favoriteUI.setImageResource(icon);

                                    if (!mm.isWatched()) {

                                        icon = R.drawable.bookmark_border;
                                    }
                                    else {

                                        icon = R.drawable.bookmark;
                                    }
                                    bookmarkedUI.setImageResource(icon);


                                }

                            }


                        }
                    }
                });
                movieDatabaseViewModel.addTvShow(m);


                 */
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
                intent.putExtra("id",id );
                startActivity(intent);
                finish();



        }


    @Override
    public void onClick(int id, int s) {
        // intent all'unica pagina che quel terrone non ha ancora finito
        Intent intent = new Intent(SeriesSpec.this, Episodes.class);
        intent.putExtra("id", this.id);
        intent.putExtra("number", s);
        intent.putExtra("title", title);
        startActivity(intent);

    }
}