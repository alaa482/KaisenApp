package it.unimib.kaisenapp.models;

public class Film {

    String Title,plot,imageUrl;

    public Film(String title, String plot, String imageUrl) {
        Title = title;
        this.plot = plot;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
