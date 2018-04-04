package fr.vhb.sio.vhbpcp.metier;

import java.io.Serializable;

/**
 * Classe regroupant les caractéristiques d'un visiteur
 * @author sio2slam
*/
public class Etudiant {
	private String login;
	private String motPasse;
	private String nom;
	private String prenom;
	/**
	 * Instancie un étudiant
	 */
	public Etudiant(String unLogin, String unMotPasse){
		this.login = unLogin;
		this.motPasse = unMotPasse;
		this.nom = "";
		this.prenom = "";
	}

	public Etudiant(String unLogin, String unMotPasse, String unNom, String unPrenom){
		this.login = unLogin;
		this.motPasse = unMotPasse;
		this.nom = unNom;
		this.prenom = unPrenom;
	}
	/**
	 * Fournit le login de l'étudiant
	 * @return login de l'étudiant
	 */
	public String getLogin() {
		return this.login;
	}
	/**
	 * Fournit le mot de passe de l'étudiant
	 * @return mot de passe de l'étudiant
	 */
	public String getMotPasse() {
		return this.motPasse;
	}
	/**
	 * Fournit le nom de l'étudiant
	 * @return nom de l'étudiant
	 */
	public String getNom() {
		return this.nom;
	}
	/**
	 * Fournit le prénom de l'étudiant
	 * @return prénom de l'étudiant
	 */
	public String getPrenom() {
		return this.prenom;
	}
	/**
	 * Fournit la représentation textuelle d'une instance d'un étudiant
	 * @return représentation textuelle
	 */
	public String toString() {
		return this.login + ":" + this.nom + ":" + this.prenom;
	}
}
