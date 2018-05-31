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

import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Production;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Created by install on 17/04/2018.
 */

public class PasserelleProduction extends Passerelle {
    public static final String URL_PRODUCTIONS_GET = URL_HOTE_WS + "WSProductions/getProductionByStudent";
    public static final String URL_PRODUCTIONS_UPDATE = URL_HOTE_WS + "WSSitPros/update";
    public static final String URL_DELETE_UPDATE = URL_HOTE_WS + "WSSitPros/delete";
    private static String unLibCourt;

    /**
     * Fournit la liste des situations professionnelles de l'étudiant spécifié
     * @return cette liste d'objets de classe Situation
     * @throws Exception
     */
    public static ArrayList<Production> getLesProductions(Etudiant leVisiteur, String ref) throws Exception{
        ArrayList<Production> lesProductions = null;
        Production uneProduction;

        try
        {
            String uneUrl = getUrlComplete(URL_PRODUCTIONS_GET, leVisiteur);
            uneUrl += "/ref/" + ref;
            HttpURLConnection uneRequeteHttp = prepareHttpRequestGet(uneUrl);
            JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

            // on renvoie une exception si status différent de 0
            controlStatus(unObjetJSON);

            JSONArray lesProds = unObjetJSON.getJSONArray("productions");

			/* Exemple de situations professionnelles :
					<depts>
			*/
            // création d'un objet ArrayList en vue de contenir les situations professionnelles
            lesProductions = new ArrayList<Production> ();
            // parcours de la liste des noeuds <sitpro>
            for (int i = 0 ; i < lesProds.length() ; i++) {    // création de l'élément courant à chaque tour de boucle
                JSONObject courant = lesProds.getJSONObject(i);
                // constitution de la situations à partir de toutes les balises contenues dans <sitpro
                uneProduction = getProductionFromJSONObject(courant);
                // ajoute la situation à la collection des situations
                lesProductions.add(uneProduction);
            }
        }
        catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : " + ex.toString());
            throw ex;
        }
        return lesProductions;
    }
    /**
     * Prend en charge la mise à jour des données à modifier d'une situation professionnelle
     * et rend la situation modifiée lorsque la mise à jour a été réellement réalisée
     * @return Situation
     * @throws Exception
     */
    public static Production updateProduction(Etudiant leVisiteur, Production laProduction, HashMap<String, String> laHashMapToUpdate) throws Exception {
        Production laProductionFinale = laProduction;
        try
        {
            String uneUrl = getUrlComplete(URL_PRODUCTIONS_UPDATE, leVisiteur);

            HttpURLConnection uneRequeteHttp = prepareHttpRequestUpdate(uneUrl, laProduction, laHashMapToUpdate);
            JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

            // on renvoie une exception si status différent de 0
            controlStatus(unObjetJSON);

            // on met à jour la situation pour chaque caractéristique ayant subi une modification
            /**
            unLibCourt = (laHashMapToUpdate.containsKey("libCourt")) ? laHashMapToUpdate.get("libCourt") : laSituation.getLibcourt();
            laSituation.setLibcourt(unLibCourt);
            laSituation.setDescriptif((laHashMapToUpdate.containsKey("descriptif")) ? laHashMapToUpdate.get("descriptif") : laSituation.getDescriptif());
            laSituation.setCodeLocalisation((laHashMapToUpdate.containsKey("codeLocalisation")) ? laHashMapToUpdate.get("codeLocalisation") : laSituation.getCodeLocalisation());
            laSituation.setCodeSource((laHashMapToUpdate.containsKey("codeSource"))? laHashMapToUpdate.get("codeSource"):laSituation.getCodeSource());
            laSituation.setDateDebut((laHashMapToUpdate.containsKey("dateDebut")) ? Date.valueOf(laHashMapToUpdate.get("dateDebut")) : laSituation.getDateDebut());
            laSituation.setDateFin((laHashMapToUpdate.containsKey("dateFin")) ? Date.valueOf(laHashMapToUpdate.get("dateFin")) : laSituation.getDateFin());
             */
        }
        catch (Exception ex) {
            Log.e("Passerelle", "Erreur exception : \n" + ex.toString());
            throw ex;
        }
        return laProduction;
    }
    /**
     * Prépare la requête HTTP Post avec les données de la situation à modifier dans le corps de la requête
     * @param uri
     * @param laProduction
     * @param hashMapToUpdate
     * @return Document
     * @throws Exception
     */
    private static HttpURLConnection prepareHttpRequestUpdate(String uri, Production laProduction, HashMap<String, String> hashMapToUpdate) throws Exception{
        String data;

        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // constitution des données à envoyer sous forme de couples clés / valeurs
        data = "ref=" + laProduction.getRef();

        // parcours de chacun des éléments du dictionnaire hashMapToUpdate
        for (String key : hashMapToUpdate.keySet()) {
            data += "&" + key + "=" + URLEncoder.encode((String) hashMapToUpdate.get(key), "UTF-8");
        }

        // écriture des données sur le flux de sortie
        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(data);
        // fermeture des flux en sortie
        writer.close();
        os.close();

        return con;
    }
    /**
     * Instancie une situation à partir d'un élément portant sitpro comme nom de balise
     * et contenant tous les éléments caractéristiques d'une situation
     * @param unObjetJSON
     * @return Situation
     */
    private static Production getProductionFromJSONObject(JSONObject unObjetJSON) throws Exception {
        String uneDesignation, uneURI, uneRef;
        Production uneProduction;

        uneRef = unObjetJSON.getString("ref");
        uneDesignation = unObjetJSON.getString("designation");
        uneURI = unObjetJSON.getString("adresse");
        uneProduction = new Production (uneRef, uneDesignation, uneURI);
        return uneProduction;
    }
}