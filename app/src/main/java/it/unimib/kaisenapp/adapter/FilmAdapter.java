package it.unimib.kaisenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.kaisenapp.data.Film;
import it.unimib.kaisenapp.R;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder>{

    Context context;
    List<Film> mFilm;

    public FilmAdapter(Context context, List<Film> mFilm) {
        this.context = context;
        this.mFilm = mFilm;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_film_serietv,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textViewTitolo.setText(mFilm.get(i).getTitolo());
        myViewHolder.imageView.setImageResource(mFilm.get(i).getAnteprima());
    }

    @Override
    public int getItemCount() {
        return mFilm.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitolo;
        private ImageView imageView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textViewTitolo=itemView.findViewById(R.id.item_film_titolo);
            imageView=itemView.findViewById(R.id.item_film_img);

        }
    }



}
