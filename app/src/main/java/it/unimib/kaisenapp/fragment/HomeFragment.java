package it.unimib.kaisenapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.kaisenapp.R;
import it.unimib.kaisenapp.adapter.CategoryItemRecyclerAdapter;
import it.unimib.kaisenapp.adapter.MainRecyclerAdapter;
;
import it.unimib.kaisenapp.utils.DataWrapper;

public class HomeFragment extends Fragment  implements CategoryItemRecyclerAdapter.OnClickListener{
    //RecycleView - Adapter
    private RecyclerView mainCategoryRecycler;
    private MainRecyclerAdapter mainRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeView= inflater.inflate(R.layout.fragment_home, container, false);
        for(int i=0;i<DataWrapper.getList().size();i++)
         System.out.println(DataWrapper.getList().get(i).getCategoryTitle());

        mainCategoryRecycler = homeView.findViewById(R.id.searched_movie_recycleview);
        mainRecyclerAdapter = new MainRecyclerAdapter(getContext(),  DataWrapper.getList(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mainCategoryRecycler.setAdapter(mainRecyclerAdapter);
        mainCategoryRecycler.setLayoutManager(layoutManager);



        return homeView;
    }

    @Override
    public void onClick(int id, String type) {
        Toast.makeText(getActivity(), "ID: "+id, Toast.LENGTH_SHORT).show();
    }
}
