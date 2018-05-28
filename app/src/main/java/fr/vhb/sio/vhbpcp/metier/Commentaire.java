package fr.vhb.sio.vhbpcp.metier;

import java.io.Serializable;

/**
 * Created by install on 17/04/2018.
 */

public class Commentaire implements Serializable{
    private String _idActivite;
    private String _refSituation;
    private String _commentaire;

    public Commentaire(String unId, String uneRef, String unCommentaire) {
        _idActivite = unId;
        _refSituation = uneRef;
        _commentaire = unCommentaire;
    }

    public String get_idActivite() {
        return _idActivite;
    }
    public String get_refSituation() {
        return _refSituation;
    }
    public String get_commentaire() {
        return _commentaire;
    }

    public String toString() {
        return this.get_commentaire();
    }
}
