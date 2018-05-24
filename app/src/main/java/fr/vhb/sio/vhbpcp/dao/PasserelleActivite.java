package fr.vhb.sio.vhbpcp.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Created by mbouchard on 10/04/2018.
 */

public class PasserelleActivite extends Passerelle{
    public static final String URL_SITUATIONS_GET = URL_HOTE_WS + "WSActivite/getBySituation";
    public static final String URL_SITUATIONS_GET_ALL = URL_HOTE_WS + "WSActivite/getAllActivities";
    public static final String URL_SITUATION_ADD = URL_HOTE_WS + "WSActivite/add";
    public static final String URL_SITUATION_DELETE = URL_HOTE_WS + "WSActivite/delete";

    public static ArrayList<Activite> getActivitesBySituation(Etudiant unVisiteur, Situation laSituation) throws Exception {
        ArrayList<Activite> lesActivites = null;
        Activite uneActivite;

        try {
            String uneUrl = getUrlComplete(URL_SITUATIONS_GET, unVisiteur, laSituation);
            HttpURLConnection uneRequeteHttp = prepareHttpRequestGet(uneUrl);
            JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

            // on renvoie une exception si status différent de 0
            controlStatus(unObjetJSON);

            JSONArray arrayJson = unObjetJSON.getJSONArray("sitpros");

			/* Exemple de situations professionnelles :
					<depts>
			*/
            // création d'un objet ArrayList en vue de contenir les situations professionnelles
            lesActivites = new ArrayList<Activite>();
            // parcours de la liste des noeuds <sitpro>
            for (int i = 0; i < arrayJson.length(); i++) {    // création de l'élément courant à chaque tour de boucle
                JSONObject courant = arrayJson.getJSONObject(i);
                // constitution de la situations à partir de toutes les balises contenues dans <sitpro
                uneActivite = getActiviteFromJSONObject(courant);
                // ajoute la situation à la collection des situations
                lesActivites.add(uneActivite);
            }
        } catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
        return lesActivites;
    }

    private static Activite getActiviteFromJSONObject(JSONObject unObjetJSON) throws Exception {
        String unId, uneNomenclature, unLibelle;
        Activite uneActivite;

        unId = unObjetJSON.getString("id");
        uneNomenclature = unObjetJSON.getString("nomenclature");
        unLibelle = unObjetJSON.getString("libelle");
        uneActivite = new Activite(unId, uneNomenclature, unLibelle);
        return uneActivite;
    }


    public static ArrayList<Activite> getAllActivities(Etudiant unVisiteur) throws Exception {
        ArrayList<Activite> lesActivites = null;
        Activite uneActivite;

        try {
            String uneUrl = getUrlComplete(URL_SITUATIONS_GET_ALL, unVisiteur);
            HttpURLConnection uneRequeteHttp = prepareHttpRequestGet(uneUrl);
            JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

            // on renvoie une exception si status différent de 0
            controlStatus(unObjetJSON);

            JSONArray arrayJson = unObjetJSON.getJSONArray("sitpros");

            // création d'un objet ArrayList en vue de contenir les situations professionnelles
            lesActivites = new ArrayList<Activite>();
            // parcours de la liste des noeuds <sitpro>
            for (int i = 0; i < arrayJson.length(); i++) {    // création de l'élément courant à chaque tour de boucle
                JSONObject courant = arrayJson.getJSONObject(i);
                // constitution de la situations à partir de toutes les balises contenues dans <sitpro
                uneActivite = getActiviteFromJSONObject(courant);
                // ajoute la situation à la collection des situations
                lesActivites.add(uneActivite);
            }
        } catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
        return lesActivites;
    }

    private static Activite getAllActivitiesFromJSONObject(JSONObject unObjetJSON) throws Exception {
        String unId, uneNomenclature, unLibelle;
        Activite uneActivite;

        unId = unObjetJSON.getString("id");
        uneNomenclature = unObjetJSON.getString("nomenclature");
        unLibelle = unObjetJSON.getString("libelle");
        uneActivite = new Activite(unId, uneNomenclature, unLibelle);
        return uneActivite;
    }

    public static void addActivityToSituation(Etudiant unVisiteur, Situation laSituation, Activite lActivite) throws Exception{
        try {
            String uneUrl = getUrlComplete(URL_SITUATION_ADD, unVisiteur, laSituation, lActivite);

            HttpURLConnection uneRequeteHttp = prepareHttpRequest(uneUrl, laSituation, lActivite);
            /*JSONObject unObjetJSON = */loadResultJSON(uneRequeteHttp);

        } catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
    }

    private static HttpURLConnection prepareHttpRequest(String uri, Situation laSituation, Activite lActivite) throws Exception{
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        return con;
    }

    public static void deleteActiviteFromSituation(Etudiant unVisiteur, Situation laSituation, Activite lActivite) throws Exception{
        try {
            String uneUrl = getUrlComplete(URL_SITUATION_DELETE, unVisiteur, laSituation, lActivite);

            HttpURLConnection uneRequeteHttp = prepareHttpRequest(uneUrl, laSituation, lActivite);
            loadResultJSON(uneRequeteHttp);

        } catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
    }
}
