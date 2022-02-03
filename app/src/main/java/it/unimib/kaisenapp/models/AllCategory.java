package it.unimib.kaisenapp.models;

import java.util.List;
import java.util.Objects;

//Classe delle categorie nella home
public class AllCategory {

    private String categoryTitle;
    private List<CategoryItem> categoryItemList;
    public AllCategory(String categoryTitle, List<CategoryItem> categoryItemList) {
        this.categoryItemList=categoryItemList;
        this.categoryTitle = categoryTitle;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllCategory that = (AllCategory) o;
        return categoryTitle.equals(that.categoryTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryTitle);
    }
}
