package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CategoryItem  implements Parcelable, Serializable {
    private Integer itemId;
    private String imageUrl;
    private String type;
    private String title;

    public CategoryItem(Integer itemId, String imageUrl, String type, String title) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.type = type;
        this.title = title;
    }

    protected CategoryItem(Parcel in) {
        if (in.readByte() == 0) {
            itemId = null;
        } else {
            itemId = in.readInt();
        }
        imageUrl = in.readString();
        type = in.readString();
        title = in.readString();
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };
    public CategoryItem(CategoryItem categoryItem){
        this.itemId = categoryItem.itemId;
        this.imageUrl = categoryItem.getImageUrl();
        this.title = categoryItem.title;
        this.type = categoryItem.type;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (itemId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(itemId);
        }
        parcel.writeString(imageUrl);
        parcel.writeString(type);
        parcel.writeString(title);
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "itemId=" + itemId +
                ", imageUrl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
