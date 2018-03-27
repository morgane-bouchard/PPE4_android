package fr.vhb.sio.vhbpcp;

import fr.vhb.sio.vhbpcp.metier.Etudiant;

import android.app.Application;

/**
 * Classe permettant de m�moriser les donn�es qui doivent �tre visibles au niveau de l'application
 * c�d au niveau de toutes les activit�s
 * @author sio2slam
 *
 */
public class PCPApplication extends Application {
	private Etudiant leVisiteur;
	/**
	 * Fournit le visiteur connecté
	 * @return Visiteur
	 */
	public Etudiant getVisiteur() {
		return leVisiteur;
	}
	/**
	 * Affecte le visiteur connect�
	 * @param unVisiteur Visiteur
	 */
	public void setVisiteur(Etudiant unVisiteur) {
		this.leVisiteur = unVisiteur;
	}	
}