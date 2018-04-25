package fr.vhb.sio.vhbpcp;

import junit.framework.TestCase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.dao.PasserelleActivite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;
import fr.vhb.sio.vhbpcp.metier.Activite;

public class PasserelleActiviteTest extends TestCase {

	public void testGetAllActivities() throws Exception {
		ArrayList<Activite> lesActivites;
		lesActivites = PasserelleActivite.getAllActivities(new Etudiant("jeanfrancois.doulin@gmail.com", "motdepasse"));
		assertNotNull(lesActivites);
		assertEquals(lesActivites.size(), 3);
		
		assertEquals("1", lesActivites.get(0).get_id());
		assertEquals("A1.1.1", lesActivites.get(0).get_code());
		assertEquals("Analyse du cahier des charges d'un service à produire", lesActivites.get(0).get_libelle());
	}
	public void testGetActivitesBySituation() throws Exception {
		ArrayList<Activite> lesActivites;
		lesActivites = PasserelleActivite.getActivitesBySituation(new Etudiant("sylvain.brouillat@gmail.com", "motdepasse"), new Situation("1", "unLibCourt", "unDescriptif", "unCode", "unAutreCode", Date.valueOf("1998-08-10"), Date.valueOf("1998-05-04")));
		assertNotNull(lesActivites);
		assertEquals(0, lesActivites.size());
	}
	public void testAddActivityAtSituation() throws Exception {
		PasserelleActivite.addActivityToSituation(new Etudiant("amelie.bacle@gmail.com", "motdepasse"), new Situation("128", "STAGE 1 - Application de visualisation des machines à évoluer", "Ajout d'un taux opérateur, ce taux permettrait une meilleur flexibilité sur les résultats de production. Par exemple il permet de faire une différence suivant le nombre de personnes affectés à la production d'un produit, qui avant cela avait un taux de productivité standard (un nombre de mètres à obtenir en un laps de temps) qui ne dépendait pas du nombre de personnes affectés.", "1", "1", Date.valueOf("1998-08-10"), Date.valueOf("1998-05-04")), new Activite("1", "A1.1.1", "Analyse du cahier des charges d'un service à produire"));

	}
	//public void testDeleteActivityAtSituation() throws Exception {}
}
