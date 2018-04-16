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
 * concernant les étudiants
 * @author sio2slam
 */
public class PasserelleActiviteCitee extends Passerelle {

    public static final String URL_ACTIVITECITEE_GET = URL_HOTE_WS + "WSActiviteCitee/get_all_bysituation";
/**
 * Valide les informations de connexion spécifiées.
 * Si valide, retourne le visiteur correctement instancié, null sinon.
 * @param login
 * @param motPasse
 * @return Visiteur
 * @throws Exception

public class PasserelleActiviteCitee extend Pasee {
}
