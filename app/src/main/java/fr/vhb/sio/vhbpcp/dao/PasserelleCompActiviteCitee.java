package fr.vhb.sio.vhbpcp.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Competence;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Created by mbouchard on 10/04/2018.
 */

public class PasserelleCompActiviteCitee extends Passerelle {
    public static final String URL_SITUATIONS_GET = URL_HOTE_WS + "WSSitPros/getCompBySituation";


    public static ArrayList<Competence> getCompBySituation(Etudiant unVisiteur, Activite uneActivité) throws Exception {
        ArrayList<Competence> lesCompetence = null;
        Competence uneCompetence;

        try {
            String uneUrl = getUrlComplete(URL_SITUATIONS_GET, unVisiteur, uneActivité);
            HttpURLConnection uneRequeteHttp = prepareHttpRequestGet(uneUrl);
            JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

            // on renvoie une exception si status différent de 0
            controlStatus(unObjetJSON);

            JSONArray comp = unObjetJSON.getJSONArray("comp");

        /* Exemple de situations professionnelles :
              <depts>
        */
            // création d'un objet ArrayList en vue de contenir les situations professionnelles
            lesCompetence = new ArrayList<Competence>();
            // parcours de la liste des noeuds <sitpro>
            for (int i = 0; i < comp.length(); i++) {    // création de l'élément courant à chaque tour de boucle
                JSONObject courant = comp.getJSONObject(i);
                // constitution de la situations à partir de toutes les balises contenues dans <sitpro
                uneCompetence = getActiviteFromJSONObject(courant);
                // ajoute la situation à la collection des situations
                lesCompetence.add(uneCompetence);
            }
        } catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
        return lesCompetence;
    }

    private static Competence getActiviteFromJSONObject(JSONObject unObjetJSON) throws Exception {
        String unId, uneNomenclature, unLibelle;
        Competence uneCompetence;

        unId = unObjetJSON.getString("id");
        uneNomenclature = unObjetJSON.getString("nomenclature");
        unLibelle = unObjetJSON.getString("libelle");
        uneCompetence = new Competence(unId, uneNomenclature, unLibelle);
        return uneCompetence;
    }
}