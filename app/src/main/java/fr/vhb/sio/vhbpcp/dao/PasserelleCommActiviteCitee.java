package fr.vhb.sio.vhbpcp.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Competence;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Commentaire;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Created by mbouchard on 10/04/2018.
 */

public class PasserelleCommActiviteCitee extends Passerelle {
    public static final String URL_SITUATIONS_GET = URL_HOTE_WS + "WSSitPros/getCommByActivite";


    public static ArrayList<Commentaire> getCommBySituation(Etudiant unVisiteur, Activite uneActivité, Situation laSituation) throws Exception {
        ArrayList<Commentaire> lesCommentaires = null;
        Commentaire unCommentaire;

        try {
            String uneUrl = getUrlComplete(URL_SITUATIONS_GET, unVisiteur, uneActivité, laSituation);
            HttpURLConnection uneRequeteHttp = prepareHttpRequestGet(uneUrl);
            JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

            // on renvoie une exception si status différent de 0
            controlStatus(unObjetJSON);

            JSONArray comm = unObjetJSON.getJSONArray("comm");

        /* Exemple de situations professionnelles :
              <depts>
        */
            // création d'un objet ArrayList en vue de contenir les situations professionnelles
            lesCommentaires = new ArrayList<Commentaire>();
            // parcours de la liste des noeuds <sitpro>
            for (int i = 0; i < comm.length(); i++) {    // création de l'élément courant à chaque tour de boucle
                JSONObject courant = comm.getJSONObject(i);
                // constitution de la situations à partir de toutes les balises contenues dans <sitpro
                unCommentaire = getCommentaireFromJSONObject(courant);
                // ajoute la situation à la collection des situations
                lesCommentaires.add(unCommentaire);
            }
        } catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
        return lesCommentaires;
    }

    private static Commentaire getCommentaireFromJSONObject(JSONObject unObjetJSON) throws Exception {
        String unId, uneRef, unCommentaire;
        Commentaire leCommentaire;

        unId = unObjetJSON.getString("idActivite");
        uneRef = unObjetJSON.getString("ref");
        unCommentaire = unObjetJSON.getString("commentaire");

        leCommentaire = new Commentaire(unId,uneRef,unCommentaire);
        return leCommentaire;
    }
}