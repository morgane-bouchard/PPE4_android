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
    private String context;
    private String envTechno;
    private String moyens;
    private String avisPerso;
	private String codeLocalisation;
	private String codeSource;
	private String codeCadre;
	private String codetype;
	private Date dateDebut;
	private Date dateFin;
	/**
	 * Instancie une situation
	 */
	public Situation(String uneRef, String unLibcourt, String unDescriptif, String unContext, String unEnvTechno, String unMoyens, String unAvisPerso, String unCodeLocalisation,String unCodeSource,String unCodeCadre,String unCodeType, Date uneDateDebut, Date uneDateFin) {
		this.ref = uneRef;
		this.libcourt = unLibcourt;
		this.descriptif	= unDescriptif;
        this.context = unContext;
        this.envTechno = unEnvTechno;
        this.moyens = unMoyens;
        this.avisPerso = unAvisPerso;
		this.codeLocalisation = unCodeLocalisation;
		this.codeSource = unCodeSource;
		this.codeCadre = unCodeCadre;
		this.codetype = unCodeType;
		this.dateDebut = uneDateDebut;
		this.dateFin = uneDateFin;
	}
	/**
	 * Instancie une situation à partir d'un Parcel
	 */
	public Situation(Parcel in) {
		this.ref = in.readString();
		this.libcourt = in.readString();
		this.descriptif	= in.readString();
        this.context = in.readString();
        this.envTechno = in.readString();
        this.moyens = in.readString();
        this.avisPerso = in.readString();
		this.codeLocalisation = in.readString();
		this.codeSource = in.readString();
		this.codeCadre = in.readString();
		this.codetype = in.readString();
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
     * Fournit le context de la situation
     * @return context de la situation
     */
    public String getContext() {
        return this.context;
    }
    /**
     * Fournit l'environement technologique de la situation
     * @return l'environement technologique de la situation
     */
    public String getEnvTechno() {
        return this.envTechno;
    }
    /**
     * Fournit le moyens de la situation
     * @return le moyens de la situation
     */
    public String getMoyens() {
        return this.moyens;
    }
    /**
     * Fournit l'avis personnel de la situation
     * @return l'avis personnel de la situation
     */
    public String getAvisPerso() {
        return this.avisPerso;
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
	 * Fournit le code source de la situation
	 * @return code source de la situation
	 */
	public String getCodeCadre() {
		return this.codeCadre;
	}
	/**
	 * Fournit le code source de la situation
	 * @return code source de la situation
	 */
	public String getCodeType() {
		return this.codetype;
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
	public void setDescriptif(String unDescriptif) {this.descriptif = unDescriptif;
	}
    /**
     * Affecte le context  de la situation
     * @param unContext
     */
    public void setContext(String unContext) {this.context = unContext;
    }
    /**
     * Affecte l'environement technologique  de la situation
     * @param unEnvTechno
     */
    public void setEnvTechno(String unEnvTechno) {this.envTechno = unEnvTechno;
    }
    /**
     * Affecte le moyens  de la situation
     * @param unMoyens
     */
    public void setMoyens(String unMoyens) {this.moyens = unMoyens;
    }
    /**
     * Affecte l'avis personnel  de la situation
     * @param unAvisPerso
     */
    public void setAvisPerso(String unAvisPerso) {this.avisPerso = unAvisPerso;
    }
	/**
	 * Affecte le code localisation  de la situation
	 * @param unCodeLocalisation
	 */
	public void setCodeLocalisation(String unCodeLocalisation) {this.codeLocalisation = unCodeLocalisation;
	}
	/**
	 * Affecte le code source  de la situation
	 * @param unCodeSource
	 */
	public void setCodeSource(String unCodeSource) {
		this.codeSource = unCodeSource;
	}
	/**
	 * Affecte le code source  de la situation
	 * @param unCodeCadre
	 */
	public void setCodeCadre(String unCodeCadre) {
		this.codeSource = unCodeCadre;
	}
	/**
	 * Affecte le code source  de la situation
	 * @param unCodetype
	 */
	public void setCodetype(String unCodetype) {
		this.codeSource = unCodetype;
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
        dest.writeString(this.getContext());
        dest.writeString(this.getEnvTechno());
        dest.writeString(this.getMoyens());
        dest.writeString(this.getAvisPerso());
		dest.writeString(this.getCodeLocalisation());
		dest.writeString(this.getCodeSource());
		dest.writeString(this.getCodeCadre());
		dest.writeString(this.getCodeType());
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

}