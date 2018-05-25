package fr.vhb.sio.vhbpcp.metier;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by install on 17/04/2018.
 */

public class Activite implements Parcelable{

    private String _id;
    private String _code;
    private String _libelle;

    public Activite(String unId, String unCode, String unLibelle) {
        _id = unId;
        _code = unCode;
        _libelle = unLibelle;
    }

    protected Activite(Parcel in) {
        _id = in.readString();
        _code = in.readString();
        _libelle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(_code);
        dest.writeString(_libelle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Activite> CREATOR = new Creator<Activite>() {
        @Override
        public Activite createFromParcel(Parcel in) {
            return new Activite(in);
        }

        @Override
        public Activite[] newArray(int size) {
            return new Activite[size];
        }
    };

    public String get_id() {
        return _id;
    }
    public String get_code() {
        return _code;
    }
    public String get_libelle() {
        return _libelle;
    }

    public String toString() {
        return this.get_code() + " - " + this.get_libelle();
    }
}
