package it.unimib.kaisenapp.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;

public class DataWrapper {
    private static List<AllCategory> list;

    public DataWrapper(List<AllCategory> list) {
        this.list = list;
    }
    public DataWrapper(){
        list=new ArrayList<>();
    }

    public static List<AllCategory> getList() {
        return list;
    }
    public static void setList(List<AllCategory>list){
        DataWrapper.list.addAll(list);
    }
    public static void addCategoryItemList(String title, List<CategoryItem> list){
        if(list==null)
            list=new ArrayList<>();
        DataWrapper.list.add(new AllCategory(title, list));
    }


}
