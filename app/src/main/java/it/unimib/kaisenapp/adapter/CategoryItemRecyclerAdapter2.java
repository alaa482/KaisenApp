package it.unimib.kaisenapp.adapter;

import java.util.List;
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


import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.models.MovieModel;

public class CategoryItemRecyclerAdapter2 extends RecyclerView.Adapter<CategoryItemRecyclerAdapter2.ItemViewHolder> {

    private Context context;
    private List<CategoryItem> recommendations;

    public CategoryItemRecyclerAdapter2(Context context, List<CategoryItem> recommendations) {
        this.context = context;
        this.recommendations = recommendations;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Glide.with(holder.itemImage)
                .load("https://image.tmdb.org/t/p/w500/c1AiZTXyyzmPOlTLSubp7CEeYj.jpg")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage);
        holder.id.setText(recommendations.get(position).getItemId().toString());
        holder.id.setVisibility(View.INVISIBLE);
        holder.type=recommendations.get(position).getType();
        Glide.get(holder.itemImage.getContext()).clearMemory();

    }

    @Override
    public int getItemCount() {
        if(recommendations != null)
            return recommendations.size();
        return 0;
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        ImageView itemImage;

        String type;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            type="";
            itemImage = itemView.findViewById(R.id.item_image);
            id=itemView.findViewById(R.id.movie_id);


        }


    }




}