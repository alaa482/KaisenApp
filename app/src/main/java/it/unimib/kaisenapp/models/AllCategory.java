package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

//Classe delle categorie nella home
public class AllCategory implements Parcelable, Serializable {

    private String categoryTitle;
    private List<CategoryItem> categoryItemList;
    public AllCategory(String categoryTitle, List<CategoryItem> categoryItemList) {
        this.categoryItemList=categoryItemList;
        this.categoryTitle = categoryTitle;
    }

    protected AllCategory(Parcel in) {
        categoryTitle = in.readString();
    }

    public static final Creator<AllCategory> CREATOR = new Creator<AllCategory>() {
        @Override
        public AllCategory createFromParcel(Parcel in) {
            return new AllCategory(in);
        }

        @Override
        public AllCategory[] newArray(int size) {
            return new AllCategory[size];
        }
    };

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

    @Override
    public String toString() {
        return "AllCategory{" +
                "categoryItemList=" + categoryItemList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryTitle);
    }
}
