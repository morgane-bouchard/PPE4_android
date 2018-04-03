package fr.vhb.sio.vhbpcp.metier;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

/**
 * Classe regroupant les caractéristiques d'une situation
 * Elle implémente l'interface Parcelable pour autoriser l'échange d'objets entre activités
 * @author sio2slam
 */
public class Situation implements Parcelable{
	private String ref;
	private String libcourt;
	private String descriptif;
	private String codeLocalisation;
	private String codeSource;
	private Date dateDebut;
	private Date dateFin;
	private int nbActivitees;
	private int nbProductions;
	private int nbCommentaires;

	/**
	 * Instancie une situation
	 */
	public Situation(String uneRef, String unLibcourt, String unDescriptif, String unCodeLocalisation,String unCodeSource, Date uneDateDebut, Date uneDateFin,
					 int nbActivitees, int nbProductions, int nbCommentaires) {
		this.ref = uneRef;
		this.libcourt = unLibcourt;
		this.descriptif	= unDescriptif;
		this.codeLocalisation = unCodeLocalisation;
		this.codeSource = unCodeSource;
		this.dateDebut = uneDateDebut;
		this.dateFin = uneDateFin;
		this.nbActivitees = nbActivitees;
		this.nbProductions = nbProductions;
		this.nbCommentaires = nbCommentaires;
	}
	/**
	 * Instancie une situation à partir d'un Parcel
	 */
	public Situation(Parcel in) {
		this.ref = in.readString();
		this.libcourt = in.readString();
		this.descriptif	= in.readString();
		this.codeLocalisation = in.readString();
		this.codeSource = in.readString();
		this.dateDebut = new Date(in.readLong());
		this.dateFin = new Date(in.readLong());
	}
	/**
	 * Fournit la référence de la situation
	 * @return référence de la situation
	 */
	public String getRef() {
		return this.ref;
	}
	/**
	 * Fournit le libellé court de la situation
	 * @return libellé de la situation
	 */
	public String getLibcourt() {
		return this.libcourt;
	}
	/**
	 * Fournit le descriptif de la situation
	 * @return descriptif de la situation
	 */
	public String getDescriptif() {
		return this.descriptif;
	}
	/**
	 * Fournit le code localisation de la situation
	 * @return code localisation de la situation
	 */
	public String getCodeLocalisation() {
		return this.codeLocalisation;
	}
	/**
	 * Fournit le code source de la situation
	 * @return code source de la situation
	 */
	public String getCodeSource() {
		return this.codeSource;
	}
	/**
	 * Fournit la date de début de la situation
	 * @return date début de la situation
	 */
	public Date getDateDebut() {
		return this.dateDebut;
	}
	/**
	 * Fournit la date fin de la situation
	 * @return date fin de la situation
	 */
	public Date getDateFin() {
		return this.dateFin;
	}
	/**
	 * Affecte le libellé court de la situation
	 * @param unLibCourt
	 */
	public void setLibcourt(String unLibCourt) {
		this.libcourt = unLibCourt;
	}
	/**
	 * Affecte le descriptif  de la situation
	 * @param unDescriptif
	 */
	public void setDescriptif(String unDescriptif) {
		this.descriptif = unDescriptif;
	}
	/**
	 * Affecte le code localisation  de la situation
	 * @param unCodeLocalisation
	 */
	public void setCodeLocalisation(String unCodeLocalisation) {
		this.codeLocalisation = unCodeLocalisation;
	}
	/**
	 * Affecte le code source  de la situation
	 * @param unCodeSource
	 */
	public void setCodeSource(String unCodeSource) {
		this.codeSource = unCodeSource;
	}
	/**
	 * Affecte la date de début  de la situation
	 * @param uneDateDebut
	 */
	public void setDateDebut(Date uneDateDebut) {
		this.dateDebut = uneDateDebut;
	}
	/**
	 * Affecte la date de fin  de la situation
	 * @param uneDateFin
	 */
	public void setDateFin(Date uneDateFin) {
		this.dateFin = uneDateFin;
	}

	/**
	 * Fournit la représentation textuelle d'une situation
	 * @return représentation textuelle
	 */
	@Override
	public String toString() {
		String message;
        message = this.libcourt;
		return message;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Construit le Parcel à partir de l'instance d'une situation
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getRef());
		dest.writeString(this.getLibcourt());
		dest.writeString(this.getDescriptif());
		dest.writeString(this.getCodeLocalisation());
		dest.writeString(this.getCodeSource());
		dest.writeLong(this.getDateDebut().getTime());
		dest.writeLong(this.getDateFin().getTime());
	}

    /**
     * Constructeur statique de l'interface Parcelable utilisé pour instancier des objets et des
     * tableaux d'objets de la classe.
     */
	public static final Parcelable.Creator<Situation> CREATOR = new Parcelable.Creator<Situation>()
	{
		@Override
		public Situation createFromParcel(Parcel source)
		{
			return new Situation(source);
		}

		@Override
		public Situation[] newArray(int size)
		{
			return new Situation[size];
		}
	};

	public int getNbCommentaires() {
		return nbCommentaires;
	}

	public int getNbProductions() {
		return nbProductions;
	}

	public int getNbActivitees() {
		return nbActivitees;
	}
}