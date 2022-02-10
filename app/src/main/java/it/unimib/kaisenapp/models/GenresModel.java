package it.unimib.kaisenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class GenresModel implements Parcelable {
    private int id;
    private String name;

    protected GenresModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<GenresModel> CREATOR = new Creator<GenresModel>() {
        @Override
        public GenresModel createFromParcel(Parcel in) {
            return new GenresModel(in);
        }

        @Override
        public GenresModel[] newArray(int size) {
            return new GenresModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GenresModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return
                 name + " , "
                ;
    }
}
