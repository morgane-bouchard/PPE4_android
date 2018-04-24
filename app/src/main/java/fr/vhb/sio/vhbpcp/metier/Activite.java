package fr.vhb.sio.vhbpcp.metier;

import java.io.Serializable;

/**
 * Created by install on 17/04/2018.
 */

public class Activite implements Serializable{
    private String _id;
    private String _code;
    private String _libelle;

    public Activite(String unId, String unCode, String unLibelle) {
        _id = unId;
        _code = unCode;
        _libelle = unLibelle;
    }

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
