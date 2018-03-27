package fr.vhb.sio.vhbpcp.metier;

/**
 * Classe regroupant les caractéristiques d'une localisation
 * @author sio2slam
 */
public class Localisation {
    private String code;
    private String libelle;
    /**
     * Instancie une localisation
      * @param unCode
     * @param unLibelle
     */
    public Localisation(String unCode, String unLibelle) {
        this.code = unCode;
        this.libelle = unLibelle;
    }
    /**
     * Retourne le code de la localisation courante
     * @return code de la localisation
     */
    public String getCode() {
        return code;
    }
    /**
     * Fournit la représentation textuelle d'une localisation
     * @return représentation textuelle
     */
    @Override
    public String toString() {
        return this.libelle;
    }
}
