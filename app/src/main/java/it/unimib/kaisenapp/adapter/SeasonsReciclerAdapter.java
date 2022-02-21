package it.unimib.kaisenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.SeasonsModel;
import it.unimib.kaisenapp.utils.Constants;

public class SeasonsReciclerAdapter extends RecyclerView.Adapter<SeasonsReciclerAdapter.ItemViewHolder> {

    private Context context;
    private List<SeasonsModel> seasons;
    private OnClickListener onClickListener;
    public SeasonsReciclerAdapter(Context context, List<SeasonsModel> seasons, OnClickListener onClickListener) {
        this.context = context;
        this.seasons = seasons;
        this.onClickListener=onClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false), onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String prefix="https://image.tmdb.org/t/p/w500";
        Glide.with(holder.itemImage)
                .load(Constants.PREFIX+seasons.get(position).getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        if(seasons != null)
            return seasons.size();
        return 0;
    }

    public  class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView itemImage;
        OnClickListener onClickListener;
        public ItemViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image1);
            this.onClickListener=onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(seasons.get(getAdapterPosition()).getId(),seasons.get(getAdapterPosition()).getSeason_number());
        }
    }
    public interface OnClickListener{
        void onClick(int id,int s);
    }



}