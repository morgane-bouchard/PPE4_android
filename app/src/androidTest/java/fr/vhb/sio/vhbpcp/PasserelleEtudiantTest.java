package fr.vhb.sio.vhbpcp;

import junit.framework.TestCase;

import fr.vhb.sio.vhbpcp.dao.PasserelleEtudiant;
import fr.vhb.sio.vhbpcp.metier.Etudiant;

public class PasserelleEtudiantTest extends TestCase {

	public void testSeConnecter_Succes() throws Exception {
		Etudiant unEtudiant;
		unEtudiant = PasserelleEtudiant.seConnecter("jeanfrancois.doulin@gmail.com", "motdepasse");
		assertNotNull(unEtudiant);
		assertEquals("jeanfrancois.doulin@gmail.com", unEtudiant.getLogin());
		assertEquals("motdepasse", unEtudiant.getMotPasse());
	}
	public void testSeConnecter_Echec() throws Exception {
		Etudiant unEtudiant=null;
		try {
			unEtudiant = PasserelleEtudiant.seConnecter("jeanfrancois.doulin@gmail.com", "mdp");
		}
		catch (Exception ex) {
			assertNull(unEtudiant);
			assertEquals("1:Authentification incorrecte.", ex.getMessage());
		}
	}
}
