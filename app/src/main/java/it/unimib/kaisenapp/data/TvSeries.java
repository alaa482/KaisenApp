package it.unimib.kaisenapp.data;

import java.util.HashMap;
import java.util.Map;

public class TvSeries extends ContenutoMultimediale {
    private String titolo;
    private int thumbnail;
    private Map<Integer,Integer> stagioni; //stagione - # episodi

    public TvSeries(String titolo, int thumbnail, Map<Integer, Integer> stagioni) {
        this.titolo = titolo;
        this.thumbnail = thumbnail;
        this.stagioni = new HashMap<>();
        this.stagioni.putAll(stagioni);
    }

    public TvSeries(String titolo, int thumbnail) {
        this.titolo = titolo;
        this.thumbnail = thumbnail;
        this.stagioni = new HashMap<>();
    }

    public String getTitolo() {
        return titolo;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public Map<Integer, Integer> getStagioni() {
        return stagioni;
    }

    @Override
    public String toString() {
        return "SerieTv{ titolo='" + titolo +"}";
    }
}
