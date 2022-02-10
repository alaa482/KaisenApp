package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductionCompaniesModel implements Parcelable {
    private String name;
    private int id;
    private String logo_path;
    private String origin_country;

    protected ProductionCompaniesModel(Parcel in) {
        name = in.readString();
        id = in.readInt();
        logo_path = in.readString();
        origin_country = in.readString();
    }

    public static final Creator<ProductionCompaniesModel> CREATOR = new Creator<ProductionCompaniesModel>() {
        @Override
        public ProductionCompaniesModel createFromParcel(Parcel in) {
            return new ProductionCompaniesModel(in);
        }

        @Override
        public ProductionCompaniesModel[] newArray(int size) {
            return new ProductionCompaniesModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public ProductionCompaniesModel(String name, int id, String logo_path, String origin_country) {
        this.name = name;
        this.id = id;
        this.logo_path = logo_path;
        this.origin_country = origin_country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(logo_path);
        dest.writeString(origin_country);
    }

    @Override
    public String toString() {
        return "ProductionCompaniesModel{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", logo_path='" + logo_path + '\'' +
                ", origin_country='" + origin_country + '\'' +
                '}';
    }
}
