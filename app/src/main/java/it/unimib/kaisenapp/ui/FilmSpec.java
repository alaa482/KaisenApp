package it.unimib.kaisenapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.Film;

public class FilmSpec extends AppCompatActivity {

    private ImageButton backBTN;
    private TextView plot;
    private  TextView title;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_spec);

        backBTN = (ImageButton)findViewById(R.id.back_button);
        title= (TextView) findViewById(R.id.film_title);
        plot = (TextView) findViewById(R.id.plot);
        poster =(ImageView) findViewById(R.id.poster);

        Film film = new Film("caserta","cavolo che bel film","casa");

        title.setText(film.getTitle());
        plot.setText(film.getPlot());


    }

}