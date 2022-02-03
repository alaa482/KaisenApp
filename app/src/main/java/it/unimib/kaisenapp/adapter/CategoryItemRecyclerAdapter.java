package it.unimib.kaisenapp.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.CategoryItem;

public class CategoryItemRecyclerAdapter extends RecyclerView.Adapter<CategoryItemRecyclerAdapter.CategoryItemViewHolder> {

    private Context context;
    private List<CategoryItem> categoryItemList;
    private OnClickListener onClickListener;
    public CategoryItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList, OnClickListener onClickListener) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        this.onClickListener=onClickListener;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false), onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, int position) {
        String prefix="https://image.tmdb.org/t/p/w500/";
        Glide.with(holder.itemImage)
                .load(prefix+categoryItemList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage);
        //Glide.get(holder.itemImage.getContext()).clearMemory();
        //Log.d("Body", prefix+categoryItemList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        if(categoryItemList != null)
            return categoryItemList.size();
        return 0;
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView itemImage;
        OnClickListener onClickListener;
        public CategoryItemViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            this.onClickListener=onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(getAdapterPosition());
        }
    }
    public interface OnClickListener{
        void onClick(int position);
    }


    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
}