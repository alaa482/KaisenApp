package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CategoryItem  implements Parcelable {
    private Integer itemId;
    private String imageUrl;
    private String type;

    public CategoryItem(Integer itemId, String imageUrl, String type) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.type=type;
    }

    protected CategoryItem(Parcel in) {
        if (in.readByte() == 0) {
            itemId = null;
        } else {
            itemId = in.readInt();
        }
        imageUrl = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (itemId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemId);
        }
        dest.writeString(imageUrl);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Integer getItemId() {
        return itemId;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "itemId=" + itemId +
                ", imageUrl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
