package fr.vhb.sio.vhbpcp.daotest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Classe simulant l'appel des web services pour obtenir ou modifier les données
 * concernant les étudiants
 * @author sio2slam
 */
public class PasserelleSituation {
    /**
     * Fournit la liste des situations d'un étudiant donné
     * @return cette liste d'objets de classe Situation
     * @throws Exception
     */
    public static ArrayList<Situation> getLesSPs(Etudiant leVisiteur) throws Exception {
        ArrayList<Situation> lesSPs;
        lesSPs = new ArrayList<Situation> ();
        Situation uneSituation;
        uneSituation = new Situation("50", "Libellé court", "Descriptif très très long","context","environement technologique","Moyens","avis personnel","1", "3","1","1", Date.valueOf("2015-12-09"), Date.valueOf("2015-12-20"));
        lesSPs.add(uneSituation);
        uneSituation = new Situation("55", "Libellé super court", "Descriptif super super long","context","environement technologique","Moyens","avis personnel","2", "2","2", "2", Date.valueOf("2016-01-05"), Date.valueOf("2016-01-25"));
        lesSPs.add(uneSituation);
        uneSituation = new Situation("60", "Libellé archi court", "Descriptif archi archi long","context","environement technologique","Moyens","avis personnel","1", "4","1", "3", Date.valueOf("2016-02-29"), Date.valueOf("2016-03-10"));
        lesSPs.add(uneSituation);

        return lesSPs;
    }
    /**
     * Prend en charge la mise à jour des données à modifier d'une situation professionnelle
     * et rend la situation modifiée lorsque la mise à jour a été réellement réalisée
     * @return Situation
     * @throws Exception
     */
    public static Situation updateSituation(Etudiant leVisiteur, Situation laSituation, HashMap<String, String> laHashMapToUpdate) throws Exception {
        // on met à jour la situation pour chaque caractéristique ayant subi une modification
        laSituation.setLibcourt((laHashMapToUpdate.containsKey("libcourt")) ? laHashMapToUpdate.get("libcourt") : laSituation.getLibcourt());
        laSituation.setDescriptif((laHashMapToUpdate.containsKey("descriptif")) ? laHashMapToUpdate.get("descriptif") : laSituation.getDescriptif());
        laSituation.setContext((laHashMapToUpdate.containsKey("contexte")) ? laHashMapToUpdate.get("cont" +
                "exte") : laSituation.getContext());
        laSituation.setEnvTechno((laHashMapToUpdate.containsKey("environnement")) ? laHashMapToUpdate.get("environnement") : laSituation.getEnvTechno());
        laSituation.setMoyens((laHashMapToUpdate.containsKey("moyen")) ? laHashMapToUpdate.get("moyen") : laSituation.getMoyens());
        laSituation.setAvisPerso((laHashMapToUpdate.containsKey("avisPerso")) ? laHashMapToUpdate.get("avisPerso") : laSituation.getAvisPerso());
        laSituation.setCodeLocalisation((laHashMapToUpdate.containsKey("codeLocalisation")) ? laHashMapToUpdate.get("codeLocalisation") : laSituation.getCodeLocalisation());
        laSituation.setCodeSource((laHashMapToUpdate.containsKey("codeSource")) ? laHashMapToUpdate.get("codeSource") : laSituation.getCodeSource());
        laSituation.setCodeCadre((laHashMapToUpdate.containsKey("codeCadre")) ? laHashMapToUpdate.get("codeCadre") : laSituation.getCodeCadre());
        laSituation.setCodetype((laHashMapToUpdate.containsKey("codetype")) ? laHashMapToUpdate.get("codeType") : laSituation.getCodeType());
        laSituation.setDateDebut((laHashMapToUpdate.containsKey("datedebut")) ? Date.valueOf(laHashMapToUpdate.get("datedebut")) : laSituation.getDateDebut());
        laSituation.setDateFin((laHashMapToUpdate.containsKey("datefin")) ? Date.valueOf(laHashMapToUpdate.get("datefin")) : laSituation.getDateFin());
        return laSituation;
    }
}
