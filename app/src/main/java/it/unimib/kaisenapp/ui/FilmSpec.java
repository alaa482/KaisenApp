package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;
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
import it.unimib.kaisenapp.models.GenresModel;
import it.unimib.kaisenapp.models.ProductionCompaniesModel;
import it.unimib.kaisenapp.request.Service;
import it.unimib.kaisenapp.response.MovieDetailsResponse;
import it.unimib.kaisenapp.utils.Credentials;
import it.unimib.kaisenapp.utils.MovieApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmSpec extends AppCompatActivity {
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
    String prefix="https://image.tmdb.org/t/p/w500/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_spec);

        backBTNUI = (ImageButton) findViewById(R.id.back_button);
        titleUI = (TextView) findViewById(R.id.film_title);
        plotUI = (TextView) findViewById(R.id.plot);
        posterUI = (ImageView) findViewById(R.id.poster);
        originalTitleUI=(TextView)findViewById(R.id.OriginalTitle);
        genresUI = (TextView)findViewById(R.id.Genres);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        backBTNUI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                finish();

            }
        });
        int id = getIntent().getIntExtra("id",634649);
        GetRetrofitResponse(id);

    }

    private void GetRetrofitResponse(int id) {
        MovieApi movieApi = Service.getMovieApi();

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
                    productionCompaniesList= new ArrayList<>(((MovieDetailsResponse) response.body()).getProductionCompanies());
                    originalTitle= response.body().getOriginalTitle();
                    imagePath = response.body().getImage_path();
                    avarageVote = response.body().getVoteAvarege();
                    title = response.body().getTitle();
                    plot = response.body().getPlot();
                }
                else{

                }
                ratingBar.setRating(avarageVote/2);
                titleUI.setText(title);
                originalTitleUI.setText(originalTitle);
                plotUI.setText(plot);

                Glide.with(posterUI)
                        .load(prefix+imagePath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(posterUI);
                String aus ="";
                Log.v("test", genresList.toString());
                for (int i = 0 ; i< genresList.size();i++){
                   aus+=String.valueOf(genresList.get(i));
                   if (i==genresList.size()-1){
                       aus=aus.substring(0,aus.length()-3);

                   }
                }
                genresUI.setText(aus);

            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

            }
        });
    }
}