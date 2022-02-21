package it.unimib.kaisenapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.CategoryItem;
import it.unimib.kaisenapp.utils.Constants;

public class SearchedMovieRecycleAdapter extends RecyclerView.Adapter<SearchedMovieRecycleAdapter.SearchedMovieViewHolder> {

    private final Context context;
    private final List<CategoryItem> categoryItemList;
    private final SearchedMovieRecycleAdapter.OnClickListener onClickListener;
    
    public SearchedMovieRecycleAdapter(Context context, List<CategoryItem> categoryItemList, SearchedMovieRecycleAdapter.OnClickListener onClickListener) {
        this.context = context;
        this.categoryItemList = categoryItemList;
        this.onClickListener=onClickListener;
    }

    public SearchedMovieRecycleAdapter.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @NonNull
    @Override
    public SearchedMovieRecycleAdapter.SearchedMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchedMovieRecycleAdapter.SearchedMovieViewHolder(LayoutInflater.from(context).inflate(R.layout.searched_movie_item, parent, false), onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedMovieRecycleAdapter.SearchedMovieViewHolder holder, int position) {
        String prefix="https://image.tmdb.org/t/p/w500";

        Glide.with(holder.itemImage)
                .load(Constants.PREFIX+categoryItemList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply( new RequestOptions().transform(new CenterCrop(), new RoundedCorners(16)))
                .into(holder.itemImage);


        holder.id.setText(categoryItemList.get(position).getItemId().toString());
        holder.id.setVisibility(View.INVISIBLE);
        holder.title.setText(categoryItemList.get(position).getTitle());
        holder.type=categoryItemList.get(position).getType();


    }

    @Override
    public int getItemCount() {
        if(categoryItemList != null)
            return categoryItemList.size();
        return 0;
    }

    public static final class SearchedMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView id;
        ImageView itemImage;
        SearchedMovieRecycleAdapter.OnClickListener onClickListener;
        String type;
        TextView title;

        public SearchedMovieViewHolder(@NonNull View itemView, SearchedMovieRecycleAdapter.OnClickListener onClickListener) {
            super(itemView);
            type="";
            title=itemView.findViewById(R.id.search_title);
            itemImage = itemView.findViewById(R.id.search_item);
            id=itemView.findViewById(R.id.search_movie_id);
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