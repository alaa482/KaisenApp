package it.unimib.kaisenapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.kaisenapp.R;

public class TvSerieAdapter extends RecyclerView.Adapter<TvSerieAdapter.MyViewHolder> {

    String data1[], data2[], data3[];
    int images[];
    Context context;

    public TvSerieAdapter(Context ct, String s1[], String s2[], String s3[], int img[]) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        images = img;
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
        holder.episode_names.setText(data1[position]);
        holder.episode_desctiptions.setText(data2[position]);
        holder.episode_time.setText(data3[position]);
        holder.image_episode.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView episode_names, episode_desctiptions, episode_time;
        ImageView image_episode;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            episode_names = itemView.findViewById(R.id.episode_name_txt);
            episode_desctiptions = itemView.findViewById(R.id.episode_overview_txt);
            episode_time = itemView.findViewById(R.id.episode_time_txt);
            image_episode = itemView.findViewById(R.id.image_episode);
        }
    }
}
