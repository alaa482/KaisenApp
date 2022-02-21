package it.unimib.kaisenapp.utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import it.unimib.kaisenapp.models.AllCategory;
import it.unimib.kaisenapp.models.CategoryItem;

public final class DataWrapper {
    private static List<AllCategory> list;

    private DataWrapper(){
        list=new ArrayList<>();
    }

    public static List<AllCategory> getList() {
        return list;
    }

    public static void setList(List<AllCategory>list){
        DataWrapper.list=list;
    }

    public static void addCategoryItemList(String title,List<CategoryItem> list){
        if(DataWrapper.list!=null && list!=null){
            for(AllCategory category: DataWrapper.list)
                if(category.getCategoryTitle().equalsIgnoreCase(title))
                    DataWrapper.list.get(DataWrapper.list.indexOf(new AllCategory(title, new ArrayList<>()))).setCategoryItemList(list);
        }

    }
    public static void addTitle(String title){
        DataWrapper.list.add(new AllCategory(title, new ArrayList<>()));
    }
    public static void clear(){
        list.clear();
    }


}
