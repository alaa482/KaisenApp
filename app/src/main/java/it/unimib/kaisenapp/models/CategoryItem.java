package it.unimib.kaisenapp.models;

public class CategoryItem {
    private Integer itemId;
    private String imageUrl;
    private String type;

    public CategoryItem(Integer itemId, String imageUrl, String type) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.type=type;
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

    @Override
    public String toString() {
        return "CategoryItem{" +
                "itemId=" + itemId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
