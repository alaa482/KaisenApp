package it.unimib.kaisenapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.TvSerieModel;
import it.unimib.kaisenapp.response.TvShowSearchResponse;
import it.unimib.kaisenapp.utils.Constants;

public class TvSerieAdapter extends RecyclerView.Adapter<TvSerieAdapter.MyViewHolder> {

    private List<TvSerieModel> episodes;
    private Context context;

    public TvSerieAdapter(Context ct, List<TvSerieModel> episodes) {
        context = ct;
       this.episodes=episodes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.episode_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.episode_names.setText(episodes.get(position).getName());
        holder.episode_desctiptions.setText(episodes.get(position).getOverview());
        holder.episode_number.setText(String.valueOf(episodes.get(position).getEpisode_number()));
        String prefix="https://image.tmdb.org/t/p/w500";
        Glide.with(holder.image_episode)
                .load(Constants.PREFIX+episodes.get(position).getStill_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image_episode);

    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView episode_names, episode_desctiptions, episode_number;
        ImageView image_episode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            episode_names = itemView.findViewById(R.id.episode_name_txt);
            episode_desctiptions = itemView.findViewById(R.id.episode_overview_txt);
            episode_number = itemView.findViewById(R.id.episode_number_txt);
            image_episode = itemView.findViewById(R.id.image_episode);
        }
    }
}
