package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.SimilarReciclerAdapter;
import it.unimib.kaisenapp.database.MovieEntity;
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.request.MovieApi;
import it.unimib.kaisenapp.request.Service;
import it.unimib.kaisenapp.response.MovieDetailsResponse;
import it.unimib.kaisenapp.response.SimilarResponse;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.viewmodels.MovieDatabaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmSpec extends AppCompatActivity implements SimilarReciclerAdapter.OnClickListener {
    private ImageButton backBTNUI;
    private TextView plotUI;
    private TextView titleUI;
    private ImageView posterUI;
    private TextView originalTitleUI;
    private RatingBar ratingBar;
    private TextView genresUI;
    private List<GenresModel> genresList;
    private String originalTitle;
    private String imagePath;
    private float avarageVote;
    private String title;
    private String plot;
    private ImageButton bookmarkedUI ;
    private ImageButton favoriteUI;
    private ImageButton starUI;
    private TextView durataUI;
    private String durata;
    private Boolean bookmarked=false;
    private Boolean favorite=false;
    private Boolean star=false;
    private int icon;
    private MovieDatabaseViewModel movieDatabaseViewModel;
    private  MovieEntity m;

    String prefix="https://image.tmdb.org/t/p/w500/";
    MovieApi movieApi = Service.getMovieApi();
    RecyclerView recyclerView;
    SimilarReciclerAdapter recommendationsRecyclerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_spec);
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
        bookmarkedUI=(ImageButton)findViewById(R.id.bookmarked);
        favoriteUI=(ImageButton)findViewById(R.id.favorite);
        starUI=(ImageButton)findViewById(R.id.star);
        durataUI=(TextView)findViewById(R.id.durata);
        movieDatabaseViewModel= new ViewModelProvider(this).get(MovieDatabaseViewModel.class);



        bookmarkedUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                movieDatabaseViewModel.getAllMovies().observe(FilmSpec.this, new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(List<MovieEntity> movieEntities) {
                        for (MovieEntity mm : movieEntities) {
                            if (mm.equals(m)) {
                                if (bookmarked) {

                                    mm.setWatched(!bookmarked);
                                    m.setWatched(!bookmarked);
                                    movieDatabaseViewModel.updateMovie(mm);

                                    icon = R.drawable.watched_border;
                                    bookmarked=!bookmarked;


                                }
                                else {
                                    mm.setWatched(!bookmarked);
                                    m.setWatched(!bookmarked);
                                    movieDatabaseViewModel.updateMovie(mm);
                                    icon = R.drawable.watched;
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


                                              movieDatabaseViewModel.getAllMovies().observe(FilmSpec.this, new Observer<List<MovieEntity>>() {
                                                  @Override
                                                  public void onChanged(List<MovieEntity> movieEntities) {
                                                      for (MovieEntity mm : movieEntities) {
                                                          if (mm.equals(m)) {
                                                              if (favorite) {

                                                                  mm.setSaved(!favorite);
                                                                  m.setSaved(!favorite);
                                                                  movieDatabaseViewModel.updateMovie(mm);

                                                                  icon = R.drawable.saved_border;
                                                                  favorite=!favorite;


                                                              }
                                                              else {
                                                                  mm.setSaved(!favorite);
                                                                  m.setSaved(!favorite);
                                                                  movieDatabaseViewModel.updateMovie(mm);
                                                                  icon = R.drawable.saved;
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


                                          movieDatabaseViewModel.getAllMovies().observe(FilmSpec.this, new Observer<List<MovieEntity>>() {
                                              @Override
                                              public void onChanged(List<MovieEntity> movieEntities) {
                                                  for (MovieEntity mm : movieEntities) {
                                                      if (mm.equals(m)) {
                                                          if (star) {

                                                              mm.setFavorite(!star);
                                                              m.setFavorite(!star);
                                                              movieDatabaseViewModel.updateMovie(mm);

                                                              icon = R.drawable.favourite_border;
                                                              star = !star;


                                                          } else {
                                                              mm.setFavorite(!star);
                                                              m.setFavorite(!star);
                                                              movieDatabaseViewModel.updateMovie(mm);
                                                              icon = R.drawable.favourite;
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
        int id = getIntent().getIntExtra("id",634649);
        GetRetrofitResponse(id);
        GetRetrofitResponseRecommendations(id);


    }


    private void GetRetrofitResponse(int id) {


        Call<MovieDetailsResponse> responseCall = movieApi
                .getMovieDetail(
                        id,
                        Credentials.API_KEY,
                        Credentials.LANGUAGE

                );

        responseCall.enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.code() == 200) {
                    genresList = new ArrayList<>(((MovieDetailsResponse) response.body()).getGenres());
                    originalTitle= response.body().getOriginalTitle();
                    imagePath = response.body().getImage_path();
                    avarageVote = response.body().getVoteAvarege();
                    title = response.body().getTitle();
                    plot = response.body().getPlot();
                    durata=String.valueOf(response.body().getRuntime());
                }

                ratingBar.setRating(avarageVote/2);
                titleUI.setText(title);
                originalTitleUI.setText(originalTitle);
                plotUI.setText(plot);
                if(durata==null)
                    durata = "0";
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
                m = new MovieEntity(id,imagePath,null,bookmarked,star,favorite,Integer.parseInt(durata));




                movieDatabaseViewModel.getAllMovies().observe(FilmSpec.this, new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(List<MovieEntity> movieEntityList) {
                        if(movieEntityList!=null){

                            for (MovieEntity mm: movieEntityList) {



                                if(mm.equals(m)){

                                    favorite = mm.isSaved();
                                    bookmarked = mm.isWatched();
                                    star = mm.isFavorite();
                                    if (!mm.isFavorite()) {

                                        icon = R.drawable.favourite_border;
                                    }
                                    else {

                                        icon = R.drawable.favourite;
                                    }
                                    starUI.setImageResource(icon);

                                    if (!mm.isSaved()) {

                                        icon = R.drawable.saved_border;
                                    }
                                    else {

                                        icon = R.drawable.saved;
                                    }

                                    favoriteUI.setImageResource(icon);

                                    if (!mm.isWatched()) {

                                        icon = R.drawable.watched_border;
                                    }
                                    else {

                                        icon = R.drawable.watched;
                                    }
                                    bookmarkedUI.setImageResource(icon);


                                }

                            }


                        }
                    }
                });
                movieDatabaseViewModel.addMovie(m);



            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

            }
        });


    }
    private void GetRetrofitResponseRecommendations(int id) {

        Call<SimilarResponse> responseCall = movieApi
                .getMovieRecommendation(
                        id,
                        Credentials.API_KEY,
                        Credentials.LANGUAGE

                );

        responseCall.enqueue(new Callback<SimilarResponse>() {
            @Override
            public void onResponse(Call<SimilarResponse> call, Response<SimilarResponse> response) {

                if (response.code() == 200) {
                    recommendationsRecyclerAdapter=new SimilarReciclerAdapter(FilmSpec.this,response.body().getMovies(),FilmSpec.this);
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(FilmSpec.this, RecyclerView.HORIZONTAL, false);
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
        Intent intent = new Intent(FilmSpec.this,FilmSpec.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }



}