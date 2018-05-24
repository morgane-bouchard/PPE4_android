package fr.vhb.sio.vhbpcp;

/**
 * Created by mbouchard on 17/04/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import fr.vhb.sio.vhbpcp.dao.PasserelleActivite;
import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

public class AjoutActiviteActivity extends Activity {
    private Situation laSituation;
    private int position;
    private Button buttonAjouter;
    private Button buttonBack;
    private Spinner spinnerActivites;
    private ArrayList<Activite> lesActivites;
    ArrayAdapter<Activite> dataAdapterActivite ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activite);
        initialisations();
    }

    private void initialisations() {
        Intent uneIntention;
        uneIntention = getIntent();
        this.laSituation = uneIntention.getParcelableExtra("situation");
        this.position = uneIntention.getIntExtra("position", 0);

        PCPApplication monAppli = (PCPApplication) AjoutActiviteActivity.this.getApplication();
        Etudiant lEtudiant;
        lEtudiant = monAppli.getVisiteur();
        this.setTitle(lEtudiant.getPrenom()+ " " + lEtudiant.getNom());
        new ActivitesGet().execute();
    }

    private void initSpinnerActivites() {
        // récupération du widget spinner pour sélectionner la localisation
        spinnerActivites = (Spinner) findViewById(R.id.spinnerActivites);

        // initialisation de la source de données, les localisations possibles sont initialisées en "dur"
        // ces sources pourraient être récupérées par appel de Web Services
        // mais on considère que ces données sont très stables

        // création de l'adaptateur pour le spinner et lien avec la source de données
        dataAdapterActivite = new ArrayAdapter<Activite>(this, android.R.layout.simple_spinner_item, lesActivites);

        // Drop down layout style - list view with radio button
        dataAdapterActivite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // lie l'adaptateur et le spinner
        spinnerActivites.setAdapter(dataAdapterActivite);
        // sélectionne la source initiale
        /*for (int i=0;i<lesActivitesLocalisation.size();i++){
            //if (lesActivitesLocalisation.get(i).get_code().equals(laSituation.getCodeSource())){
                spinnerActivites.setSelection(i);
            //}
        }*/
    }

    private class ActivitesGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<Situation> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) AjoutActiviteActivity.this.getApplication();
            try {
                lesActivites = PasserelleActivite.getAllActivities(monAppli.getVisiteur());
            }
            catch (Exception ex) {
                return ex;
            }
            return lesActivites;
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
                Toast.makeText(AjoutActiviteActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                // récupération des widgets pour la description de la situation
                spinnerActivites = (Spinner) findViewById(R.id.spinnerActivites);
                buttonAjouter = (Button) findViewById(R.id.buttonAjouterActivites);
                buttonBack = (Button) findViewById(R.id.buttonBack);

                // récupération de la situation véhiculée par l'intention
                Intent uneIntention;
                uneIntention = getIntent();
                laSituation = uneIntention.getParcelableExtra("situation");
                position = uneIntention.getIntExtra("position", 0);
                // initialisation des zones d'éditions à partir de la situation reçue
                // initialisation des écouteurs d'événements
                buttonAjouter.setOnClickListener(new AjoutActiviteActivity.OnButtonClick());
                buttonBack.setOnClickListener(new AjoutActiviteActivity.OnButtonBackClick());

                initSpinnerActivites();
            }
        }
    }


    public class OnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // on demande à récupérer uniquement les champs de saisie qui ont subi une modification
            //HashMap<String, String> hashMapToUpdate;
            //hashMapToUpdate = getHashMapToAdd();
            position = spinnerActivites.getSelectedItemPosition();
            new AjoutActiviteActivity.SituationAdd().execute(laSituation, lesActivites.get(position));

        }
    }


    private class SituationAdd extends AsyncTask<Object, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Object... params) {
            PCPApplication monAppli;
            monAppli = (PCPApplication) AjoutActiviteActivity.this.getApplication();
            try {
                PasserelleActivite.addActivityToSituation(monAppli.getVisiteur(), (Situation) params[0], (Activite) params[1]);
            }
            catch (Exception ex) {
                Toast.makeText(AjoutActiviteActivity.this, "Activité déjà ajoutée à la situation.", Toast.LENGTH_LONG).show();
                return ex;
            }
            return params[0];
        }
        /**
         * Méthode automatiquement appelée quand la tâche longue se termine
         * ici, on teste le type de résultat reçu, exception ou liste
         * @param result objet de type Situation ou Exception
         */
        @Override
        protected void onPostExecute(Object result) {
            if ( result instanceof Exception ) {
                Exception ex = (Exception) result;
                Toast.makeText(AjoutActiviteActivity.this, getString(R.string.msgErrUpdateSitPro) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                // retour à l'activité appelante
                Intent uneIntention;
                uneIntention = new Intent(AjoutActiviteActivity.this, ActiviteActivity.class);
                // on renvoie la position sauvegardée et la situation modifiée
                uneIntention.putExtra("position", position);
                uneIntention.putExtra("situation", (Situation) result);
                setResult(RESULT_OK, uneIntention);
                startActivityForResult(uneIntention, 2);
            }
        }
    }


    private class OnButtonBackClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
