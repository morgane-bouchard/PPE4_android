package fr.vhb.sio.vhbpcp.metier;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Date;

/**
 * Classe regroupant les caractéristiques d'une situation
 * Elle implémente l'interface Parcelable pour autoriser l'échange d'objets entre activités
 * @author sio2slam
 */
public class Production implements Parcelable{
    private String ref;
    private String URI;
    private String designation;
    /**
     * Instancie une situation
     */
    public Production(String ref, String URI, String designation) {
        this.ref = ref;
        this.URI = URI;
        this.designation = designation;
    }
    /**
     * Instancie une situation à partir d'un Parcel
     */
    public Production(Parcel in) {
        this.ref = in.readString();
        this.URI = in.readString();
        this.designation = in.readString();
    }

    /**
     * Fournit la représentation textuelle d'une situation
     * @return représentation textuelle
     */
    @Override
    public String toString() {
        String message;
        message = this.designation;
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Construit le Parcel à partir de l'instance d'une situation
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getRef());
        dest.writeString(this.getURI());
        dest.writeString(this.getDesignation());
    }

    /**
     * Constructeur statique de l'interface Parcelable utilisé pour instancier des objets et des
     * tableaux d'objets de la classe.
     */
    public static final Parcelable.Creator<Production> CREATOR = new Parcelable.Creator<Production>()
    {
        @Override
        public Production createFromParcel(Parcel source)
        {
            return new Production(source);
        }

        @Override
        public Production[] newArray(int size)
        {
            return new Production[size];
        }
    };

    public String getRef() {
        return ref;
    }

    public String getURI() {
        return URI;
    }

    public String getDesignation() {
        return designation;
    }
}