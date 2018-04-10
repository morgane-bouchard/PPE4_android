package fr.vhb.sio.vhbpcp;

import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.dao.PasserelleSituation;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

public class PasserelleSituationTest extends TestCase {

	public void testGetLesSituations_Plusieurs() throws Exception {
		ArrayList<Situation> lesSituations;
		lesSituations = PasserelleSituation.getLesSPs(new Etudiant("jeanfrancois.doulin@gmail.com", "motdepasse"));
		assertNotNull(lesSituations);
		assertEquals(lesSituations.size(), 4);
		// on vérifie libellé court, code localisation et code source
		// les situations professionnelles sont triées sur leur libellé court par ordre croissant
		
		assertEquals("Générateur de données", lesSituations.get(0).getLibcourt());
		assertEquals("2", lesSituations.get(0).getCodeLocalisation());
		assertEquals("4", lesSituations.get(0).getCodeSource());
		assertEquals("GSB-Evolution d'une maquette réseau à base de routeurs", lesSituations.get(1).getLibcourt());

		assertEquals("2", lesSituations.get(1).getCodeLocalisation());
		assertEquals("4", lesSituations.get(1).getCodeSource());
		assertEquals("GSB-Evolutions de l'application de gestion des frais", lesSituations.get(2).getLibcourt());
		assertEquals("2", lesSituations.get(2).getCodeLocalisation());
		assertEquals("4", lesSituations.get(2).getCodeSource());
		assertEquals("TP Assistance", lesSituations.get(3).getLibcourt());
		assertEquals("2", lesSituations.get(3).getCodeLocalisation());
		assertEquals("3", lesSituations.get(3).getCodeSource());

		//Test nbCommentaires à zéro
		assertEquals(6, lesSituations.get(2).getNbActivitees());
		assertEquals(6, lesSituations.get(2).getNbProductions());
		assertEquals(0, lesSituations.get(2).getNbCommentaires());

		//Test tout > 0
		assertEquals(36, lesSituations.get(1).getNbActivitees());
		assertEquals(36, lesSituations.get(1).getNbProductions());
		assertEquals(36, lesSituations.get(1).getNbCommentaires());

		//Test nbProductions à zero
		assertEquals(0, lesSituations.get(3).getNbProductions());
	}
	public void testGetLesSituations_Aucune() throws Exception {
		ArrayList<Situation> lesSituations;
		lesSituations = PasserelleSituation.getLesSPs(new Etudiant("sylvain.brouillat@gmail.com", "motdepasse"));
		assertNotNull(lesSituations);
		assertEquals(0, lesSituations.size());
	}
	public void testUpdateSituation() throws Exception {
		Situation uneSituation;
		ArrayList<Situation> lesSituations;
		lesSituations = PasserelleSituation.getLesSPs(new Etudiant("jeanfrancois.doulin@gmail.com", "motdepasse"));
		assertNotNull(lesSituations);
		assertEquals(4, lesSituations.size());
		uneSituation = lesSituations.get(3);
		HashMap<String, String> maHashMap = new HashMap<String, String>();
		maHashMap.put("codeSource", "4");
		maHashMap.put("datedebut", "2017-03-05");
		uneSituation = PasserelleSituation.updateSituation(new Etudiant("jeanfrancois.doulin@gmail.com", "motdepasse"),
				uneSituation, maHashMap);
		assertEquals("4", uneSituation.getCodeSource());
		assertEquals(Date.valueOf("2017-03-05"), uneSituation.getDateDebut());
	}
}
