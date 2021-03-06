package fr.vhb.sio.vhbpcp.metier;

/**
 * Classe regroupant les caractéristiques d'une source
 * @author sio2slam
 */
public class Source {
    private String code;
    private String libelle;
    /**
     * Instancie une source
      * @param unCode
     * @param unLibelle
     */
    public Source(String unCode, String unLibelle) {
        this.code = unCode;
        this.libelle = unLibelle;
    }
    /**
     * Retourne le code de la source courante
     * @return code de la source
     */
    public String getCode() {
        return code;
    }
    /**
     * Fournit la représentation textuelle d'une source
     * @return représentation textuelle
     */
    @Override
    public String toString() {
        return this.libelle;
    }
}
