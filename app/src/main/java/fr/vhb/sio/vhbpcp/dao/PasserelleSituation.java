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
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant les situations professionnelles
 * @author sio2slam
 */
public class PasserelleSituation extends Passerelle {

	public static final String URL_SITUATIONS_GET = URL_HOTE_WS + "WSSitPros/getByStudent";
    public static final String URL_SITUATION_UPDATE = URL_HOTE_WS + "WSSitPros/update";
	/**
	 * Fournit la liste des situations professionnelles de l'étudiant spécifié
	 * @return cette liste d'objets de classe Situation
	 * @throws Exception
	 */
	public static ArrayList<Situation> getLesSPs(Etudiant leVisiteur) throws Exception{
		ArrayList<Situation> lesSPs = null;
		Situation uneSituation;
		
		try
		{
			String uneUrl = getUrlComplete(URL_SITUATIONS_GET, leVisiteur);
			HttpURLConnection uneRequeteHttp = prepareHttpRequestGet(uneUrl);
			JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);
	
			// on renvoie une exception si status différent de 0
			controlStatus(unObjetJSON);

			JSONArray lesSitPros = unObjetJSON.getJSONArray("sitpros");

			/* Exemple de situations professionnelles :
					<depts>
			*/
			// création d'un objet ArrayList en vue de contenir les situations professionnelles
			lesSPs = new ArrayList<Situation> ();
			// parcours de la liste des noeuds <sitpro>
			for (int i = 0 ; i < lesSitPros.length() ; i++) {    // création de l'élément courant à chaque tour de boucle
				JSONObject courant = lesSitPros.getJSONObject(i);
				// constitution de la situations à partir de toutes les balises contenues dans <sitpro
				uneSituation = getSituationFromJSONObject(courant);
				// ajoute la situation à la collection des situations
				lesSPs.add(uneSituation);
			}
		}
		catch (Exception ex) {	
			Log.e("Passerelle", "Erreur exception : " + ex.toString());
			throw ex;
		}
		return lesSPs;
	}
    /**
     * Prend en charge la mise à jour des données à modifier d'une situation professionnelle
     * et rend la situation modifiée lorsque la mise à jour a été réellement réalisée
     * @return Situation
     * @throws Exception
     */
    public static Situation updateSituation(Etudiant leVisiteur, Situation laSituation, HashMap<String, String> laHashMapToUpdate) throws Exception {
        Situation laSituationFinale = laSituation;
		try
		{
			String uneUrl = getUrlComplete(URL_SITUATION_UPDATE, leVisiteur);

			HttpURLConnection uneRequeteHttp = prepareHttpRequestUpdate(uneUrl, laSituation, laHashMapToUpdate);
			JSONObject unObjetJSON = loadResultJSON(uneRequeteHttp);

			// on renvoie une exception si status différent de 0
			controlStatus(unObjetJSON);

			// on met à jour la situation pour chaque caractéristique ayant subi une modification
            laSituation.setLibcourt((laHashMapToUpdate.containsKey("libCourt")) ? laHashMapToUpdate.get("libCourt") : laSituation.getLibcourt());
			laSituation.setDescriptif((laHashMapToUpdate.containsKey("descriptif")) ? laHashMapToUpdate.get("descriptif") : laSituation.getDescriptif());
			laSituation.setContext((laHashMapToUpdate.containsKey("contexte")) ? laHashMapToUpdate.get("contexte") : laSituation.getContext());
			laSituation.setEnvTechno((laHashMapToUpdate.containsKey("environnement ")) ? laHashMapToUpdate.get("environnement ") : laSituation.getEnvTechno());
			laSituation.setMoyens((laHashMapToUpdate.containsKey("moyen")) ? laHashMapToUpdate.get("moyen") : laSituation.getMoyens());
			laSituation.setAvisPerso((laHashMapToUpdate.containsKey("avisPerso")) ? laHashMapToUpdate.get("avisPerso") : laSituation.getAvisPerso());
            laSituation.setCodeLocalisation((laHashMapToUpdate.containsKey("codeLocalisation")) ? laHashMapToUpdate.get("codeLocalisation") : laSituation.getCodeLocalisation());
            laSituation.setCodeSource((laHashMapToUpdate.containsKey("codeSource"))? laHashMapToUpdate.get("codeSource"):laSituation.getCodeSource());
			laSituation.setCodeCadre((laHashMapToUpdate.containsKey("codeCadre"))? laHashMapToUpdate.get("codeCadre"):laSituation.getCodeCadre());
			laSituation.setCodetype((laHashMapToUpdate.containsKey("codeType"))? laHashMapToUpdate.get("codeType"):laSituation.getCodeType());
            laSituation.setDateDebut((laHashMapToUpdate.containsKey("dateDebut")) ? Date.valueOf(laHashMapToUpdate.get("dateDebut")) : laSituation.getDateDebut());
            laSituation.setDateFin((laHashMapToUpdate.containsKey("dateFin")) ? Date.valueOf(laHashMapToUpdate.get("dateFin")) : laSituation.getDateFin());
		}
		catch (Exception ex) {
			Log.e("Passerelle", "Erreur exception : \n" + ex.toString());
			throw ex;
		}
        return laSituation;
	}
    /**
     * Prépare la requête HTTP Post avec les données de la situation à modifier dans le corps de la requête
     * @param uri
     * @param laSituation
     * @param hashMapToUpdate
     * @return Document
     * @throws Exception
     */
    private static HttpURLConnection prepareHttpRequestUpdate(String uri, Situation laSituation, HashMap<String, String> hashMapToUpdate) throws Exception{
		String data;

        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

		// constitution des données à envoyer sous forme de couples clés / valeurs
		data = "ref=" + laSituation.getRef();

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
	private static Situation getSituationFromJSONObject(JSONObject unObjetJSON) throws Exception {
		String unLibelle, uneRef, unDescriptif,unContext, unEnvTechno, unMoyens, unAvisPerso, unCodeLocalisation, unCodeSource, unCodeCadre, unCodeType;
        Date uneDateDebut, uneDateFin;
        Situation uneSituation;

        uneRef = unObjetJSON.getString("ref");
        unLibelle = unObjetJSON.getString("libCourt");
        unDescriptif = unObjetJSON.getString("descriptif");
		unContext = unObjetJSON.getString("contexte");
		unEnvTechno = unObjetJSON.getString("environnement");
		unMoyens = unObjetJSON.getString("moyen");
		unAvisPerso = unObjetJSON.getString("avisPerso");
        unCodeLocalisation = unObjetJSON.getString("codeLocalisation");
        unCodeSource = unObjetJSON.getString("codeSource");
		unCodeCadre = unObjetJSON.getString("codeCadre");
		unCodeType = unObjetJSON.getString("codeType");
        uneDateDebut = Date.valueOf(unObjetJSON.getString("dateDebut"));
        uneDateFin = Date.valueOf(unObjetJSON.getString("dateFin"));
        uneSituation = new Situation (uneRef, unLibelle, unDescriptif,unContext, unEnvTechno, unMoyens, unAvisPerso, unCodeLocalisation, unCodeSource,unCodeCadre,unCodeType, uneDateDebut, uneDateFin);
		return uneSituation;
	}
}
