package fr.vhb.sio.vhbpcp.dao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.Exception;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant étudiants et situations professionnelles
 * @author sio2slam
 */
public class Passerelle {

	//public static String URL_HOTE_WS = "http://172.17.196.154/SIO2/gtouze/PPE41_VHBPCP_Gr2/";
	public static String URL_HOTE_WS = "http://172.17.193.174/SIO2/gtouze/PPE41_VHBPCP_Gr2/index.php/";


    /**
     * Retourne une requête HTTP de méthode GET à partir d'une URL donnée
     * @param uri
     * @return HttpURLConnection
     * @throws Exception
     */
    protected static HttpURLConnection prepareHttpRequestGet(String uri)  throws Exception {
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con;
    }
	/**
	 * Fournit le flux JSON reçu suite à l'appel du service web localisé à l'uri spécifié
	 * @param uneRequeteHTTP
	 * @return JSONObject
    * @throws Exception
    */
	protected static JSONObject loadResultJSON(HttpURLConnection uneRequeteHTTP) throws Exception{
		JSONObject unJsonObject = null;
		String leResultat;

		// exécution de la requête récupération de la réponse dans un flux en lecture (InputStream)
		InputStream unFluxEnEntree = uneRequeteHTTP.getInputStream();

		// conversion du flux en chaîne
		leResultat = convertStreamToString(unFluxEnEntree);

		// transformation de la chaîne en objet JSON
		JSONObject json = new JSONObject(leResultat);
		return json;
	}

	/**
	 * Lecture de l'intégralité du contenu à partir du flux en entrée is
	 * @param is  flux d'entrée
	 * @return string chaîne lue sur le fluc d'entrée
	 */
	public static String convertStreamToString(InputStream is) {
		// instanciation d'un flux bufferisé sur le flux en entrée
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		// instanciation d'un constructeur de chaîne pour mettre bout à bout les caractères reçus
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		}
		catch (IOException e) {
		}
		finally {
			try {
				is.close();  // fermeture du flux en entrée
			}
			catch (IOException e) {
			}
		}
		return sb.toString();
	} 	/**
	 * Contrôle la valeur du status et lance une exception avec status et error
	 * stipulés dans le message de l'exception
	 * @param jsonRoot
	 * @throws Exception
	 */
	protected static void controlStatus(JSONObject jsonRoot) throws Exception {
		int status;
		String error;

		if ( ! jsonRoot.has("status") || ! jsonRoot.has("error")) {
			throw new Exception("Format réponse inattendu");
		}
		else {
			status = jsonRoot.getInt("status");
			error = jsonRoot.getString("error");
			if ( status != 0 ) {
				throw new Exception(status + ":" + error);
			}
		}		
	}
	/**
	 * Complète l'url spécifiée avec les données d'authentification login et motPasse
	 * @param uneUrl
	 * @param login
	 * @param motPasse
	 * @return url complète
	 */
	protected static String getUrlComplete(String uneUrl, String login, String motPasse) throws UnsupportedEncodingException {
        uneUrl += "/format/json/";
        uneUrl += "/login/" + URLEncoder.encode(login, "UTF-8");
		return uneUrl +  "/mdp/" + URLEncoder.encode(motPasse, "UTF-8") ;
	}
	/**
	 * Complète l'url spécifiée avec les données d'authentification contenues dans unVisiteur
	 * @param uneUrl
	 * @param unVisiteur
	 * @return url complète
	 */
	protected static String getUrlComplete(String uneUrl, Etudiant unVisiteur) throws UnsupportedEncodingException {
        uneUrl += "/format/json";
        uneUrl += "/login/" + URLEncoder.encode(unVisiteur.getLogin(), "UTF-8");
		return uneUrl + "/mdp/" + URLEncoder.encode(unVisiteur.getMotPasse(), "UTF-8");
	}
	protected static String getUrlComplete(String uneUrl, Etudiant unVisiteur, Situation uneSituation) throws UnsupportedEncodingException {
		uneUrl += "/ref/" + URLEncoder.encode(uneSituation.getRef(), "UTF-8");
		uneUrl += "/format/json";
		uneUrl += "/login/" + URLEncoder.encode(unVisiteur.getLogin(), "UTF-8");
		return uneUrl + "/mdp/" + URLEncoder.encode(unVisiteur.getMotPasse(), "UTF-8");
	}
	protected static String getUrlComplete(String uneUrl, Etudiant unVisiteur, Activite uneActivite) throws UnsupportedEncodingException {
		uneUrl += "/idActivite/" + URLEncoder.encode(uneActivite.get_id(), "UTF-8");
		uneUrl += "/format/json";
		uneUrl += "/login/" + URLEncoder.encode(unVisiteur.getLogin(), "UTF-8");
		return uneUrl + "/mdp/" + URLEncoder.encode(unVisiteur.getMotPasse(), "UTF-8");
	}
	protected static String getUrlComplete(String uneUrl, Etudiant unVisiteur, Activite uneActivite,Situation uneSituation) throws UnsupportedEncodingException {
		uneUrl += "/idActivite/" + URLEncoder.encode(uneActivite.get_id(), "UTF-8");
		uneUrl += "/ref/" + URLEncoder.encode(uneSituation.getRef(), "UTF-8");
		uneUrl += "/format/json";
		uneUrl += "/login/" + URLEncoder.encode(unVisiteur.getLogin(), "UTF-8");
		return uneUrl + "/mdp/" + URLEncoder.encode(unVisiteur.getMotPasse(), "UTF-8");

	}
}
