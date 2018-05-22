package fr.vhb.sio.vhbpcp;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.dao.PasserelleActiviteCitee;
import fr.vhb.sio.vhbpcp.metier.Activite;
import fr.vhb.sio.vhbpcp.metier.Situation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ReformulationActivity extends Activity {

        private Situation laSituation;
        private int position;
        public static final int CODE_UPDATE = 1;
        private ListView ListViewActiviteCitee;
        private ArrayList<Activite> lesActivitePos;
        private ArrayAdapter<Activite> unAdaptateur;

        /**
         * Méthode appelée lors de la création de l'activité
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_situation_reformulation);
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
        this.laSituation = uneIntention.getParcelableExtra("situation");
        this.position = uneIntention.getIntExtra("position",0);

        ListViewActiviteCitee = (ListView) findViewById(R.id.ListViewActiviteCitee);
        ListViewActiviteCitee.setOnItemClickListener(new ListViewOnItemClick() );
        new ActiviteCiteeGet().execute();
    }

    private class ActiviteCiteeGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<Activite> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) ReformulationActivity.this.getApplication();
            try {
                lesDonnees = PasserelleActiviteCitee.getBySituation(monAppli.getVisiteur(),laSituation);
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
                Toast.makeText(ReformulationActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesActivitePos = (ArrayList<Activite>) result;
                unAdaptateur = new ArrayAdapter<Activite>(ReformulationActivity.this, android.R.layout.simple_list_item_1, lesActivitePos);
                // 	on associe l'adaptateur au composant ListView
                ListViewActiviteCitee.setAdapter(unAdaptateur);
            }
        }
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.sitpros, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            Intent uneIntention;

            switch (item.getItemId()) {
                case R.id.itemReformulation:
                    uneIntention= new Intent(ReformulationActivity.this, ReformulationActivity.class);
                    uneIntention.putExtra("situation", laSituation);
                    uneIntention.putExtra("position", position);
                    startActivityForResult(uneIntention, 1);
                    break;
                case R.id.itemDescription:
                    uneIntention= new Intent(ReformulationActivity.this, DescriptionSituationActivity.class);
                    uneIntention.putExtra("situation", laSituation);
                    uneIntention.putExtra("position", position);
                    startActivityForResult(uneIntention, 1);
                    break;
                case R.id.itemActivites:
                    break;
                case R.id.itemProduction:
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
            return true;
        }

        /**
         * Méthode appelée lors de la réception du résultat de l'activité DescriptionSituationActivity
         * @param requestCode
         * @param resultCode
         * @param data
         */
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Intent uneIntention;
            int position;
            if (resultCode == RESULT_OK) {

                if (requestCode == CODE_UPDATE) {
                    Activite uneActivite;
                    position = data.getIntExtra("position", 0);
                    uneActivite = data.getParcelableExtra("activite");
                    this.lesActivitePos.set(position, uneActivite);
                    this.unAdaptateur.notifyDataSetChanged();
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
            uneIntention = new Intent(ReformulationActivity.this, DescriptionReformulationActivity.class);
            uneIntention.putExtra("position", position);
            uneIntention.putExtra("activite", lesActivitePos.get(position));
            startActivityForResult(uneIntention, CODE_UPDATE);
        }
    }
}






