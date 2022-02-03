package it.unimib.kaisenapp.data;

public class Movie extends ContenutoMultimediale {
    private String titolo;
    private int rating;
    private String descrizione;
    private int anteprima;
    private int durata;
    private String trailerURL;

    public Movie(String titolo, int rating, String descrizione, int anteprima, int durata, String trailerURL) {
        this.titolo = titolo;
        this.anteprima=anteprima;
        this.rating = rating;
        this.descrizione = descrizione;
        this.durata = durata;
        this.trailerURL = trailerURL;
    }

    public Movie(String titolo, int anteprima) {
        this.titolo = titolo;
        this.anteprima = anteprima;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getRating() {
        return rating;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getDurata() {
        return durata;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public int getAnteprima() {
        return anteprima;
    }

    public void setAnteprima(int anteprima) {
        this.anteprima = anteprima;
    }

    @Override
    public String toString() {
        return "Film{" +
                "titolo='" + titolo + '\'' +
                ", rating=" + rating +
                ", descrizione='" + descrizione + '\'' +
                ", anteprima=" + anteprima +
                ", durata=" + durata +
                ", trailerURL='" + trailerURL + '\'' +
                '}';
    }
}
