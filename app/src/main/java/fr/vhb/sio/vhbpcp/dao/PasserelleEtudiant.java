package fr.vhb.sio.vhbpcp.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import fr.vhb.sio.vhbpcp.metier.Etudiant;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant les étudiants
 * @author sio2slam
 */
public class PasserelleEtudiant extends Passerelle {

	public static final String URL_CONNEXION = URL_HOTE_WS + "WSEtudiants/authentifier";
	/**
	 * Valide les informations de connexion spécifiées.
	 * Si valide, retourne le visiteur correctement instancié, null sinon.
	 * @param login
	 * @param motPasse
	 * @return Visiteur
	 * @throws Exception
	 */
	public static Etudiant seConnecter(String login, String motPasse) throws Exception {
		Etudiant leVisiteur = null;
		String nom, prenom;
		try
		{	
			String uneUrl = getUrlComplete(URL_CONNEXION, login, motPasse);
			HttpURLConnection uneRequete = prepareHttpRequestGet(uneUrl);
			JSONObject unObjetJSON = loadResultJSON(uneRequete);


			// on renvoie une exception si status différent de 0
			controlStatus(unObjetJSON);

            JSONObject unObject = unObjetJSON.getJSONObject("etudiant");
            nom = unObject.getString("nom");
            prenom  = unObject.getString("prenom");

			leVisiteur = new Etudiant(login, motPasse, nom, prenom);
			
		}
		catch (Exception ex) {	
			Log.e("Passerelle", "Erreur exception : " + ex.toString());
			throw ex;
		}
		return leVisiteur;
	}
}
