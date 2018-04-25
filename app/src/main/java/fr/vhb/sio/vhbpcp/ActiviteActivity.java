package fr.vhb.sio.vhbpcp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.dao.PasserelleActivite;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;
import fr.vhb.sio.vhbpcp.metier.Activite;

/**
 * Created by mbouchard on 16/04/2018.
 */

public class ActiviteActivity extends Activity {
    private Situation laSituation;
    private int position;
    public static final int CODE_UPDATE = 1;
    private ListView listViewActivites;
    private Button buttonAjouter;
    private ArrayList<Activite> lesActivites;
    private ArrayAdapter<Activite> unAdaptateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activite);
        initialisations();
    }

    private void initialisations() {
        // récupération de la situation véhiculée par l'intention
        Intent uneIntention;
        uneIntention = getIntent();
        this.laSituation = uneIntention.getParcelableExtra("situation");
        this.position = uneIntention.getIntExtra("position", 0);

        listViewActivites = (ListView) findViewById(R.id.ListViewActivites);
        buttonAjouter = (Button) findViewById(R.id.buttonAjoutActivite);
        buttonAjouter.setOnClickListener(new OnButtonClick());

        // click sur item
        //listViewActivites.setOnItemClickListener(new SituationsActivity.ListViewOnItemClick() );

        PCPApplication monAppli;

        Etudiant lEtudiant;

        monAppli = (PCPApplication) ActiviteActivity.this.getApplication();
        lEtudiant = monAppli.getVisiteur();
        this.setTitle(lEtudiant.getPrenom()+ " " + lEtudiant.getNom());
        new ActiviteActivity.ActiviteGet().execute();
    }


    public class OnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent uneIntention;
            uneIntention= new Intent(ActiviteActivity.this, AjoutActiviteActivity.class);
            uneIntention.putExtra("situation", laSituation);
            uneIntention.putExtra("position", position);
            startActivityForResult(uneIntention, 1);

        }
    }


    private class ActiviteGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<Activite> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) ActiviteActivity.this.getApplication();
            try {
                lesDonnees = PasserelleActivite.getActivitesBySituation(monAppli.getVisiteur(), laSituation);
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
                Toast.makeText(ActiviteActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesActivites = (ArrayList<Activite>) result;
                unAdaptateur = new ArrayAdapter<Activite>(ActiviteActivity.this, android.R.layout.simple_list_item_1, lesActivites);
                // 	on associe l'adaptateur au composant ListView
                listViewActivites.setAdapter(unAdaptateur);
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
            case R.id.itemDescription:
                uneIntention= new Intent(ActiviteActivity.this, DescriptionSituationActivity.class);
                uneIntention.putExtra("situation", laSituation);
                uneIntention.putExtra("position", position);
                startActivityForResult(uneIntention, 1);
                break;
            case R.id.itemActivites:
                uneIntention= new Intent(ActiviteActivity.this, ActiviteActivity.class);
                uneIntention.putExtra("situation", laSituation);
                uneIntention.putExtra("position", position);
                startActivityForResult(uneIntention, 1);
                break;
            case R.id.itemReformulation:
                break;
            case R.id.itemProduction:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
