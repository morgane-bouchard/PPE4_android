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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.dao.PasserelleProduction;
import fr.vhb.sio.vhbpcp.metier.Localisation;
import fr.vhb.sio.vhbpcp.metier.Production;
import fr.vhb.sio.vhbpcp.metier.Source;

public class DescriptionProductionActivity extends Activity
{
    private Production laProduction;
    private int position;
    private TextView textViewLibelle;
    private TextView textViewDesignation;
    private EditText editTextDesignation;
    private TextView textViewURI;
    private EditText editTextURI;
    private TextView textViewDelete;
    private CheckBox checkBoxDelete;
    private Button buttonEnregistrer;
    private Spinner spinnerCodeLocalisation;
    private ArrayList<Localisation> lesCodesLocalisation;
    ArrayAdapter<Localisation> dataAdapterCodeLocalisation;
    private Spinner spinnerCodeSource;
    private ArrayList<Source> lesCodesSource;
    ArrayAdapter<Source> dataAdapterCodeSource ;
    /**
     * Méthode appelée lors de la création de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_production_description);
        initialisations();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sitpros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent uneIntention;
        item.setChecked(true);

        switch(item.getItemId()) {
            case R.id.itemDescription :
                uneIntention = new Intent(DescriptionProductionActivity.this, SituationsActivity.class);
                uneIntention.putExtra("laProduction", laProduction);
                startActivityForResult(uneIntention, 1);
                break;
            case R.id.itemActivites :

                break;
            case R.id.itemReformulation :
                break;
            case R.id.itemProduction :
                uneIntention = new Intent(DescriptionProductionActivity.this, ProductionActivity.class);
                uneIntention.putExtra("laProduction", laProduction);
                startActivityForResult(uneIntention, 1);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     */
    private void initialisations() {
        // récupération des widgets pour la description de la situation
        this.editTextDesignation = (EditText) findViewById(R.id.editTextDesignation);
        this.editTextURI = (EditText) findViewById(R.id.editTextURI);
        this.buttonEnregistrer = (Button) findViewById(R.id.buttonEnregistrer);
        this.checkBoxDelete = (CheckBox) findViewById(R.id.checkBoxDelete);

        // récupération de la situation véhiculée par l'intention
        Intent uneIntention;
        uneIntention = getIntent();
        this.laProduction = uneIntention.getParcelableExtra("production");
        this.position = uneIntention.getIntExtra("position", 0);
        // initialisation des zones d'éditions à partir de la situation reçue
        this.editTextDesignation.setText(this.laProduction.getDesignation());
        this.editTextURI.setText(this.laProduction.getURI());
        // initialisation des écouteurs d'événements
        this.buttonEnregistrer.setOnClickListener(new OnButtonClick());
    }


    /**
     * Constitue un dictionnaire des clés / valeurs modifiées sur le formulaire
     * Les clés correspondent aux noms de données attendues par le Web Service
     * @return
     */
    private HashMap<String, String> getHashMapToUpdate() {
        HashMap<String, String> laHashMap;
        laHashMap = new HashMap<String, String>();
        if (! editTextDesignation.getText().toString().equals(laProduction.getDesignation())) {
            laHashMap.put("designation", editTextDesignation.getText().toString());
        }
        if (! editTextURI.getText().toString().equals(laProduction.getURI())) {
            laHashMap.put("adresse", editTextURI.getText().toString());
        }
        return laHashMap;
    }
    /**
     * Classe interne pour prendre en charge l'appel à un service web et sa réponse
     * La consultation des situations professionnelles fait en effet appel au service web et peut donc prendre quelques sec.
     * A partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tâche asynchrone
     * TypeParam1 : Object - 2 paramètres fournis à la tâche, un de type Situation, un de type HashMap
     * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
     * TypeParam3 : Object - type de paramètre Situation ou Exception
     * @see AsyncTask
     */
    private class ProductionUpdate extends AsyncTask<Object, Void, Object> {
        /**
         * Permet de lancer l'exécution de la tâche longue
         * ici, on demande les situations professionnelles de l'étudiant connecté
         */
        @Override
        protected Object doInBackground(Object... params) {
            // Situation laNouvelleSituation;
            PCPApplication monAppli;
            monAppli = (PCPApplication) DescriptionProductionActivity.this.getApplication();
            try {
                PasserelleProduction.updateProduction(monAppli.getVisiteur(), (Production) params[0], (HashMap<String, String>) params[1]);
            }
            catch (Exception ex) {
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
                Toast.makeText(DescriptionProductionActivity.this, getString(R.string.msgErrUpdateSitPro) + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            else {
                // retour à l'activité appelante
                Intent uneIntention;
                uneIntention = new Intent(DescriptionProductionActivity.this, SituationsActivity.class);
                // on renvoie la position sauvegardée et la situation modifiée
                uneIntention.putExtra("position", position);
                uneIntention.putExtra("situation", (Production) result);
                setResult(RESULT_OK, uneIntention);
                finish();
            }
        }
    }
    /**
     * Classe interne servant d'écouteur de l'événement click sur le bouton Modifier
     */
    private class OnButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // on demande à récupérer uniquement les champs de saisie qui ont subi une modification
            HashMap<String, String> hashMapToUpdate;
            hashMapToUpdate = getHashMapToUpdate();
            if ( hashMapToUpdate.size() == 0 ) { // aucun champ de saisie n'a été changée
                Toast.makeText(DescriptionProductionActivity.this, "Aucune donnée modifiée", Toast.LENGTH_LONG).show();
            }
            else { // il faut demander la mise à jour de la situation
                new ProductionUpdate().execute(laProduction, hashMapToUpdate);
            }
        }
    }
}
