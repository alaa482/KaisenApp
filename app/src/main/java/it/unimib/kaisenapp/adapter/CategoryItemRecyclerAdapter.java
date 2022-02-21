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
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.utils.Constants;

public class CategoryItemRecyclerAdapter extends RecyclerView.Adapter<CategoryItemRecyclerAdapter.CategoryItemViewHolder> {

    private final Context context;
    private final List<CategoryItem> categoryItemList;
    private final OnClickListener onClickListener;
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
        String prefix="https://image.tmdb.org/t/p/w500";
        Glide.with(holder.itemImage)
                .load(Constants.PREFIX +categoryItemList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemImage);

        holder.id.setText(categoryItemList.get(position).getItemId().toString());
        holder.id.setVisibility(View.INVISIBLE);
        holder.type=categoryItemList.get(position).getType();
        Glide.get(holder.itemImage.getContext()).clearMemory();

    }

    @Override
    public int getItemCount() {
        if(categoryItemList != null)
            return categoryItemList.size();
        return 0;
    }

    public static final class CategoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView id;
        ImageView itemImage;
        OnClickListener onClickListener;
        String type;

        public CategoryItemViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            type="";
            itemImage = itemView.findViewById(R.id.item_image1);
            id=itemView.findViewById(R.id.movie_id);
            this.onClickListener=onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(Integer.parseInt(id.getText().toString()), type);
        }
    }
    public interface OnClickListener{
        void onClick(int id,String type);
    }


}