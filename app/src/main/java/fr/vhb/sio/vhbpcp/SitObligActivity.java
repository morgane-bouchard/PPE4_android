package fr.vhb.sio.vhbpcp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import fr.vhb.sio.vhbpcp.dao.PasserelleSituation;
import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

/**
 * Created by mbouchard on 06/06/2018.
 */

public class SitObligActivity extends Activity{
    public static final int CODE_UPDATE = 1;
    private ListView listViewSitOblig;
    private ArrayList<String> lesSitOblig;
    private ArrayAdapter<String> unAdaptateur;
    /**
     * Méthode appelée lors de la création de l'activité
     */
    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitoblig);
        initialisations();
    }

   // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sitpros, menu);
        return true;
    }

    /**
     * Méthode appelée lors de la réception du résultat de l'activité DescriptionSituationActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
   // @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Intent uneIntention;
        int position;
        if (resultCode == RESULT_OK) {

            if (requestCode == CODE_UPDATE) {
                Situation laSituation;
                position = data.getIntExtra("position", 0);
                laSituationOblig = data.getParcelableExtra("situation");
                this.lesSitOblig.set(position, laSituation);
                this.unAdaptateur.notifyDataSetChanged();
            }
        }*/
    }

    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     * Instancie et exécute un thread séparé pour récupérer les situations professionnelles
     */
    private void initialisations() {
        listViewSitOblig = (ListView) findViewById(R.id.listViewSitOblig);
        //listViewSitOblig.setOnItemClickListener(new SituationsActivity.ListViewOnItemClick() );

        PCPApplication monAppli;

        Etudiant lEtudiant;

        monAppli = (PCPApplication) SitObligActivity.this.getApplication();
        lEtudiant = monAppli.getVisiteur();
        this.setTitle(lEtudiant.getPrenom()+ " " + lEtudiant.getNom());
        new SitObligActivity.SitObligGet().execute();
    }

    /**
     * Classe interne pour prendre en charge l'appel à un service web et sa réponse
     * La consultation des situations professionnelles fait en effet appel au service web et peut donc prendre quelques sec.
     * A partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tâche asynchrone
     * TypeParam1 : Void - pas de paramètres fournis à la tâche
     * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
     * TypeParam3 : Object - type de paramètre ArrayList<String> ou Exception
     * @see android.os.AsyncTask
     */
    private class SitObligGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<String> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) SitObligActivity.this.getApplication();
            try {
                lesDonnees = PasserelleSituation.getLesSitOblig(monAppli.getVisiteur());
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
                Toast.makeText(SitObligActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesSitOblig = (ArrayList<String>) result;
                unAdaptateur = new ArrayAdapter<String>(SitObligActivity.this, android.R.layout.simple_list_item_1, lesSitOblig);
                // 	on associe l'adaptateur au composant ListView
                listViewSitOblig.setAdapter(unAdaptateur);
            }
        }
    }
    /**
     * Classe interne servant d'écouteur de l'événement click sur les éléments de la liste
     */
    private class ListViewOnItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            Intent uneIntention;
            // crée une intention pour passer la position et la situation sélectionnées
            uneIntention = new Intent(SitObligActivity.this, DescriptionSituationActivity.class);
            uneIntention.putExtra("position", position);
            uneIntention.putExtra("situation", lesSitOblig.get(position));
            startActivityForResult(uneIntention, CODE_UPDATE);
        }
    }
}
