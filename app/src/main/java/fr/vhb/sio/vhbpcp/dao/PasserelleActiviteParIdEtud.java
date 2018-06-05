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

import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Classe prenant en charge l'appel des web services pour obtenir ou modifier les données
 * concernant les situations professionnelles
 * @author sio2slam
 */
public class PasserelleActiviteParIdEtud extends Passerelle {

	public static final String URL_SITUATIONS_GET = URL_HOTE_WS + "WSSitPros/getActiviteByStudent";

	/**
	 * Fournit la liste des situations professionnelles de l'étudiant spécifié
	 * @return cette liste d'objets de classe Situation
	 * @throws Exception
	 */
	public static ArrayList<Activite> getActiviteByStudent(Etudiant leVisiteur) throws Exception{
		ArrayList<Activite> lesActivites = null;
		Activite uneActivite;
		
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
			lesActivites = new ArrayList<Activite> ();
			// parcours de la liste des noeuds <sitpro>
			for (int i = 0 ; i < lesSitPros.length() ; i++) {    // création de l'élément courant à chaque tour de boucle
				JSONObject courant = lesSitPros.getJSONObject(i);
				// constitution de la situations à partir de toutes les balises contenues dans <sitpro
				uneActivite = getActiviteByIdEtudFromJSONObject(courant);
				// ajoute la situation à la collection des situations
				lesActivites.add(uneActivite);
			}
		}
		catch (Exception ex) {	
			Log.e("Passerelle", "Erreur exception : " + ex.toString());
			throw ex;
		}
		return lesActivites;
	}


	/**
	 * Instancie une situation à partir d'un élément portant sitpro comme nom de balise
	 * et contenant tous les éléments caractéristiques d'une situation
	 * @param unObjetJSON
	 * @return Situation
	 */

	private static Activite getActiviteByIdEtudFromJSONObject(JSONObject unObjetJSON) throws Exception {
		String unId, uneNomenclature, unLibelle;
		Activite uneActivite;

		unId = unObjetJSON.getString("id");
		uneNomenclature = unObjetJSON.getString("nomenclature");
		unLibelle = unObjetJSON.getString("libelle");
		uneActivite = new Activite(unId, uneNomenclature, unLibelle);
		return uneActivite;
	}
	}
}
