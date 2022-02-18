package it.unimib.kaisenapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;

public class MainRecyclerAdapter2 extends RecyclerView.Adapter<MainRecyclerAdapter2.MainViewHolder> {
    private Context context;
    private List<AllCategory> allCategoryList;
    private List<AllCategory> recommendations;

    public MainRecyclerAdapter2(Context context, List<AllCategory> recommendations) {
        this.context=context;
        this.recommendations = recommendations;

    }

    public void addAllCategory(List<AllCategory> allCategoryList) {
        this.allCategoryList=allCategoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycle_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.categoryTitle.setText(allCategoryList.get(position).getCategoryTitle());
        setCategoryItemRecycler(holder.itemRecycler, allCategoryList.get(position).getCategoryItemList());
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    private void setCategoryItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItemList){
        CategoryItemRecyclerAdapter2 itemRecyclerAdapter=new CategoryItemRecyclerAdapter2(context, categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);

    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        RecyclerView itemRecycler;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.category_title);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
        }
    }

}
