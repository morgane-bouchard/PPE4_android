package fr.vhb.sio.vhbpcp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.dao.PasserelleActiviteCitee;
import fr.vhb.sio.vhbpcp.dao.PasserelleCompActiviteCitee;
import fr.vhb.sio.vhbpcp.dao.PasserelleCommActiviteCitee;
import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Commentaire;
import fr.vhb.sio.vhbpcp.metier.Competence;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Created by install on 24/04/2018.
 */

public class DescriptionReformulationActivity extends Activity {

    private Activite uneActivite;
    private Situation uneSituation;
    private int position;
    private ListView ListViewCompétence;
    private ListView ListViewReformulation;
    private ArrayList<Competence> lesCompetences;
    private ArrayList<Commentaire> lesCommentaire;
    private ArrayAdapter<Competence> unAdaptateur;
    private ArrayAdapter<Commentaire> unAdaptateur2;

    /**
     * Méthode appelée lors de la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reformulation_description);
        initialisations();
    }

    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     * Instancie et exécute un thread séparé pour récupérer les situations professionnelles
     */
    private void initialisations() {
        Intent uneIntention;
        uneIntention = getIntent();
        this.uneActivite = uneIntention.getParcelableExtra("activite");
        this.position = uneIntention.getIntExtra("position",0);
        this.uneSituation = uneIntention.getParcelableExtra("situation");
        this.position = uneIntention.getIntExtra("position",0);
        ListViewCompétence = (ListView) findViewById(R.id.ListViewCompétence);
        ListViewReformulation = (ListView) findViewById(R.id.ListViewCompétence);


        new CompBySituationGet().execute();
        new CommByActiviteGet().execute();
    }

    private class CompBySituationGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<Competence> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) DescriptionReformulationActivity.this.getApplication();
            try {
                lesDonnees = PasserelleCompActiviteCitee.getCompBySituation(monAppli.getVisiteur(),uneActivite);
            }
            catch (Exception ex) {
                return ex;
            }
            return lesDonnees;
        }

        /**
         * Méthode automatiquement appelée quand la tâche longue se termine
         * ici, on teste le type de résultat reçu, exception ou liste
         * @param result objet de type ArrayList<String> ou Exception
         */
        @Override
        protected void onPostExecute(Object result) {
            if ( result instanceof Exception ) {
                Exception ex = (Exception) result;
                Toast.makeText(DescriptionReformulationActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesCompetences = (ArrayList<Competence>) result;
                unAdaptateur = new ArrayAdapter<Competence>(DescriptionReformulationActivity.this, android.R.layout.simple_list_item_1, lesCompetences);
                // 	on associe l'adaptateur au composant ListView
                ListViewCompétence.setAdapter(unAdaptateur);
            }
        }
    }

    private class CommByActiviteGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<Commentaire> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) DescriptionReformulationActivity.this.getApplication();
            try {
                lesDonnees = PasserelleCommActiviteCitee.getCommBySituation(monAppli.getVisiteur(),uneActivite,uneSituation);
            }
            catch (Exception ex) {
                return ex;
            }
            return lesDonnees;
        }

        /**
         * Méthode automatiquement appelée quand la tâche longue se termine
         * ici, on teste le type de résultat reçu, exception ou liste
         * @param result objet de type ArrayList<String> ou Exception
         */
        @Override
        protected void onPostExecute(Object result) {
            if ( result instanceof Exception ) {
                Exception ex = (Exception) result;
                Toast.makeText(DescriptionReformulationActivity.this, getString(R.string.msgErrRecupComm) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesCommentaire = (ArrayList<Commentaire>) result;
                unAdaptateur2 = new ArrayAdapter<Commentaire>(DescriptionReformulationActivity.this, android.R.layout.simple_list_item_1, lesCommentaire);
                // 	on associe l'adaptateur au composant ListView
                ListViewCompétence.setAdapter(unAdaptateur2);
            }
        }
    }

}
