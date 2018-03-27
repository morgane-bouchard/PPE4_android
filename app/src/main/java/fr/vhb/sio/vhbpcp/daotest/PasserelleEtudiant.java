package fr.vhb.sio.vhbpcp.daotest;
import java.util.ArrayList;
import fr.vhb.sio.vhbpcp.metier.Etudiant;

/**
 * Classe simulant l'appel des web services pour obtenir ou modifier les données
 * concernant les étudiants
 * @author sio2slam
 */
public class PasserelleEtudiant {
	/**
	 * Valide les informations de connexion spécifiées.
	 * Si valide, retourne l'étudiant correctement instancié, null sinon.
	 * @param login
	 * @param motPasse
	 * @return Etudiant
	 * @throws Exception
	 */
	public static Etudiant seConnecter(String login, String motPasse) throws Exception{
		Etudiant lEtudiant = null;
		if ( login.equals("Bravo") && motPasse.equals("bravo" ) ) {
			lEtudiant = new Etudiant("Bravo", "Bond");
		}
		return lEtudiant;
	}
}
