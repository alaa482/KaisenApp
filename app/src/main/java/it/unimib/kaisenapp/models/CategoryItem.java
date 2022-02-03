package it.unimib.kaisenapp.models;

public class CategoryItem {
    private Integer itemId;
    private String imageUrl;

    public CategoryItem(Integer itemId, String imageUrl) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
