package fr.vhb.sio.vhbpcp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.dao.PasserelleProduction;
import fr.vhb.sio.vhbpcp.dao.PasserelleSituation;
import fr.vhb.sio.vhbpcp.metier.Production;
import fr.vhb.sio.vhbpcp.metier.Situation;

public class ProductionActivity extends Activity{
    public static final int CODE_UPDATE = 1;
    private ListView listViewProductions;
    private ArrayList<Production> lesProductions;
    private ProductionAdapter unAdaptateur;
    private TextView textView;
    private Situation laSituation;
    private Intent uneIntention;
    private Button buttonAddProduction;
    /**
     * Méthode appelée lors de la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitpros);
        textView = (TextView) findViewById(R.id.textViewInviteSPs);
        textView.setText("Mes productions");
        initialisations();
    }

    @Override
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent uneIntention;
        int position;
        if (resultCode == RESULT_OK) {

            if (requestCode == CODE_UPDATE) {
                Production laProduction;
                position = data.getIntExtra("position", 0);
                laProduction = data.getParcelableExtra("production");
                this.lesProductions.set(position, laProduction);
                this.unAdaptateur.notifyDataSetChanged();
            }
        }
    }

    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     * Instancie et exécute un thread séparé pour récupérer les situations professionnelles
     */
    private void initialisations() {
        listViewProductions = (ListView) findViewById(R.id.listViewSitPros);
        listViewProductions.setOnItemClickListener(new ListViewOnItemClick());
        uneIntention = getIntent();
        this.laSituation = uneIntention.getParcelableExtra("situation");
        new ProductionsGet().execute();
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
    private class ProductionsGet extends AsyncTask<Void, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Void... params) {
            ArrayList<Production> lesDonnees;
            PCPApplication monAppli;
            monAppli = (PCPApplication) ProductionActivity.this.getApplication();
            try {
                lesDonnees = PasserelleProduction.getLesProductions(monAppli.getVisiteur(), laSituation.getRef());
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
                Toast.makeText(ProductionActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                lesProductions = (ArrayList<Production>) result;
                unAdaptateur = new ProductionAdapter(ProductionActivity.this, R.layout.layout_liste_productions, lesProductions);
                // 	on associe l'adaptateur au composant ListView
                listViewProductions.setAdapter(unAdaptateur);
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
            uneIntention = new Intent(ProductionActivity.this, DescriptionProductionActivity.class);
            uneIntention.putExtra("position", position);
            uneIntention.putExtra("production", lesProductions.get(position));
            uneIntention.putExtra("situation", laSituation);
            uneIntention.putExtra("type", "Modifier");
            startActivityForResult(uneIntention, CODE_UPDATE);
        }
    }
}