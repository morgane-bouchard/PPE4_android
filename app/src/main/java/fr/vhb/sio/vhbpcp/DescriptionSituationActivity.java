package fr.vhb.sio.vhbpcp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import fr.vhb.sio.vhbpcp.dao.PasserelleSituation;
import fr.vhb.sio.vhbpcp.metier.Cadre;
import fr.vhb.sio.vhbpcp.metier.Localisation;
import fr.vhb.sio.vhbpcp.metier.Situation;
import fr.vhb.sio.vhbpcp.metier.Source;
import fr.vhb.sio.vhbpcp.metier.TypeSP;

/**
 * Classe gérant l'interface utilisateur de la description d'une situation professionnelle
 * @author sio2slam
 */
public class DescriptionSituationActivity extends Activity {
	private Situation laSituation;
    private int position;
    private EditText editTextLibcourt;
    private EditText editTextDescriptif;
    private EditText editTextContext;
    private EditText editTextEnvTechno;
    private EditText editTextMoyens;
    private EditText editTextAvisPerso;
    private EditText editTextDateDebut;
    private EditText editTextDateFin;
    private Button buttonUpdate;
    private Spinner spinnerCodeLocalisation;
    private ArrayList<Localisation> lesCodesLocalisation;
    private ArrayAdapter<Localisation> dataAdapterCodeLocalisation;
    private Spinner spinnerCodeSource;
    private ArrayList<Source> lesCodesSource;
    private ArrayAdapter<Source> dataAdapterCodeSource ;
    private Spinner spinnerCadre;
    private ArrayList<Cadre> lesCadres;
    private ArrayAdapter<Cadre> dataAdapterCadre ;
    private Spinner spinnerType;
    private ArrayList<TypeSP> lesTypes;
    private ArrayAdapter<TypeSP> dataAdapterTypeSP ;


    /**
	 * Méthode appelée lors de la création de l'activité
     * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_situation_description);
		initialisations();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sitpros, menu);
		return true;
	}

    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     */
	private void initialisations() {
        // récupération des widgets pour la description de la situation
        this.editTextLibcourt = (EditText) findViewById(R.id.editTextLibcourt);
        this.editTextDescriptif = (EditText) findViewById(R.id.editTextDescriptif);
        this.editTextContext = (EditText) findViewById(R.id.editTextContext);
        this.editTextEnvTechno = (EditText) findViewById(R.id.editTextEnvTechno);
        this.editTextMoyens = (EditText) findViewById(R.id.editTextMoyens);
        this.editTextAvisPerso = (EditText) findViewById(R.id.editTextAvisPerso);
        this.editTextDateDebut = (EditText) findViewById(R.id.editTextDateDebut);
        this.editTextDateFin = (EditText) findViewById(R.id.editTextDateFin);
        this.buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        // récupération de la situation véhiculée par l'intention
        Intent uneIntention;
        uneIntention = getIntent();
        this.laSituation = uneIntention.getParcelableExtra("situation");
        this.position = uneIntention.getIntExtra("position", 0);
        // initialisation des zones d'éditions à partir de la situation reçue
        this.editTextLibcourt.setText(this.laSituation.getLibcourt());
        this.editTextDescriptif.setText(this.laSituation.getDescriptif());
        this.editTextContext.setText(this.laSituation.getContext());
        this.editTextEnvTechno.setText(this.laSituation.getEnvTechno());
        this.editTextMoyens.setText(this.laSituation.getMoyens());
        this.editTextAvisPerso.setText(this.laSituation.getAvisPerso());
        this.editTextDateDebut.setText(String.valueOf(this.laSituation.getDateDebut()));
        this.editTextDateFin.setText(String.valueOf(this.laSituation.getDateFin()));
        // initialisation des écouteurs d'événements
        this.buttonUpdate.setOnClickListener(new OnButtonClick());

        this.initSpinnerLocalisation();
        this.initSpinnerSource();
        this.initSpinnerCadre();
        this.initSpinnerType();
   	}

    /**
     * Initialise widget, adaptateur et source de données pour la saisie de la localisation
     */
    private void initSpinnerLocalisation() {
        // récupération du widget spinner pour sélectionner la localisation
        spinnerCodeLocalisation = (Spinner) findViewById(R.id.spinnerCodelocalisation);

        // initialisation de la source de données, les localisations possibles sont initialisées en "dur"
        // ces localisations pourraient être récupérées par appel de Web Services
        // mais on considère que ces données sont très stables
        lesCodesLocalisation = new ArrayList<Localisation>();
        lesCodesLocalisation.add(new Localisation("1","Organisation"));
        lesCodesLocalisation.add(new Localisation("2", "Centre de formation"));

        // création de l'adaptateur pour le spinner et lien avec la source de données
        dataAdapterCodeLocalisation = new ArrayAdapter<Localisation>(this, android.R.layout.simple_spinner_item, lesCodesLocalisation);

        // Drop down layout style - list view with radio button
        dataAdapterCodeLocalisation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // lie l'adaptateur et le spinner
        spinnerCodeLocalisation.setAdapter(dataAdapterCodeLocalisation);

        // sélectionne la localisation initiale
        for (int i=0;i<lesCodesLocalisation.size();i++){
            if (lesCodesLocalisation.get(i).getCode().equals(laSituation.getCodeLocalisation())){
                spinnerCodeLocalisation.setSelection(i);
            }
        }
    }

    /**
     * Initialise widget, adaptateur et source de données pour la saisie de la source
     */
    private void initSpinnerSource() {
        // récupération du widget spinner pour sélectionner la localisation
        spinnerCodeSource = (Spinner) findViewById(R.id.spinnerCodeSource);

        // initialisation de la source de données, les localisations possibles sont initialisées en "dur"
        // ces sources pourraient être récupérées par appel de Web Services
        // mais on considère que ces données sont très stables
        lesCodesSource = new ArrayList<Source>();
        lesCodesSource.add(new Source("1","Stage 1"));
        lesCodesSource.add(new Source("2", "Stage 2"));
        lesCodesSource.add(new Source("3", "TP"));
        lesCodesSource.add(new Source("4", "PPE"));

        // création de l'adaptateur pour le spinner et lien avec la source de données
        dataAdapterCodeSource = new ArrayAdapter<Source>(this, android.R.layout.simple_spinner_item, lesCodesSource);

        // Drop down layout style - list view with radio button
        dataAdapterCodeSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // lie l'adaptateur et le spinner
        spinnerCodeSource.setAdapter(dataAdapterCodeSource);
        // sélectionne la source initiale
        for (int i=0;i<lesCodesSource.size();i++){
            if (lesCodesSource.get(i).getCode().equals(laSituation.getCodeSource())){
                spinnerCodeSource.setSelection(i);
            }
        }
    }

    private void initSpinnerCadre() {
        // récupération du widget spinner pour sélectionner la localisation
        spinnerCadre = (Spinner) findViewById(R.id.spinnerCadre);

        // initialisation de la source de données, les localisations possibles sont initialisées en "dur"
        // ces localisations pourraient être récupérées par appel de Web Services
        // mais on considère que ces données sont très stables
        lesCadres = new ArrayList<Cadre>();
        lesCadres.add(new Cadre("1","Equipe"));
        lesCadres.add(new Cadre("2", "Seul"));

        // création de l'adaptateur pour le spinner et lien avec la source de données
        dataAdapterCadre = new ArrayAdapter<Cadre>(this, android.R.layout.simple_spinner_item, lesCadres);

        // Drop down layout style - list view with radio button
        dataAdapterCadre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // lie l'adaptateur et le spinner
        spinnerCadre.setAdapter(dataAdapterCadre);

        // sélectionne la localisation initiale
        for (int i=0;i<lesCadres.size();i++){
            if (lesCadres.get(i).getCode().equals(laSituation.getCodeCadre())){
                spinnerCadre.setSelection(i);
            }
        }
    }

    private void initSpinnerType() {
        // récupération du widget spinner pour sélectionner la localisation
        spinnerType = (Spinner) findViewById(R.id.spinnerType);

        // initialisation de la source de données, les localisations possibles sont initialisées en "dur"
        // ces localisations pourraient être récupérées par appel de Web Services
        // mais on considère que ces données sont très stables
        lesTypes = new ArrayList<TypeSP>();
        lesTypes.add(new TypeSP("1","Vécu"));
        lesTypes.add(new TypeSP("2", "Observé"));
        lesTypes.add(new TypeSP("3", "Simulé"));


        // création de l'adaptateur pour le spinner et lien avec la source de données
        dataAdapterTypeSP = new ArrayAdapter<TypeSP>(this, android.R.layout.simple_spinner_item, lesTypes);

        // Drop down layout style - list view with radio button
        dataAdapterTypeSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // lie l'adaptateur et le spinner
        spinnerType.setAdapter(dataAdapterTypeSP);

        // sélectionne la localisation initiale
        for (int i=0;i<lesTypes.size();i++){
            if (lesTypes.get(i).getCode().equals(laSituation.getCodeType())){
                spinnerType.setSelection(i);
            }
        }
    }

    /**
     * Constitue un dictionnaire des clés / valeurs modifiées sur le formulaire
     * Les clés correspondent aux noms de données attendues par le Web Service
     * @return
     */
    private HashMap<String, String> getHashMapToUpdate() {
        HashMap<String, String> laHashMap;
        laHashMap = new HashMap<String, String>();
        if (! editTextLibcourt.getText().toString().equals(laSituation.getLibcourt())) {
            laHashMap.put("libCourt", editTextLibcourt.getText().toString());
        }
        if (! editTextDescriptif.getText().toString().equals(laSituation.getDescriptif())) {
            laHashMap.put("descriptif", editTextDescriptif.getText().toString());
        }
        if (! editTextContext.getText().toString().equals(laSituation.getContext())) {
            laHashMap.put("contexte", editTextContext.getText().toString());
        }
        if (! editTextEnvTechno.getText().toString().equals(laSituation.getEnvTechno())) {
            laHashMap.put("environnement", editTextEnvTechno.getText().toString());
        }
        if (! editTextMoyens.getText().toString().equals(laSituation.getMoyens())) {
            laHashMap.put("moyen", editTextMoyens.getText().toString());
        }
        if (! editTextAvisPerso.getText().toString().equals(laSituation.getAvisPerso())) {
            laHashMap.put("avisPerso", editTextAvisPerso.getText().toString());
        }
        if (! editTextDateDebut.getText().toString().equals(laSituation.getDateDebut())) {
            laHashMap.put("dateDebut", editTextDateDebut.getText().toString());
        }
        if (! editTextDateFin.getText().toString().equals(laSituation.getDateFin())) {
            laHashMap.put("dateFin", editTextDateFin.getText().toString());
        }
        int position;
        position = spinnerCodeLocalisation.getSelectedItemPosition();
        if (! lesCodesLocalisation.get(position).getCode().equals(laSituation.getCodeLocalisation())) {
            laHashMap.put("codeLocalisation", lesCodesLocalisation.get(position).getCode());
        }
        position = spinnerCodeSource.getSelectedItemPosition();
        if (! lesCodesSource.get(position).getCode().equals(laSituation.getCodeSource())) {
            laHashMap.put("codeSource", lesCodesSource.get(position).getCode());
        }
        position = spinnerCadre.getSelectedItemPosition();
        if (! lesCadres.get(position).getCode().equals(laSituation.getCodeCadre())) {
            laHashMap.put("codeCadre", lesCadres.get(position).getCode());
        }
        position = spinnerType.getSelectedItemPosition();
        if (! lesTypes.get(position).getCode().equals(laSituation.getCodeType())) {
            laHashMap.put("codeType", lesTypes.get(position).getCode());
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
	private class SituationUpdate extends AsyncTask<Object, Void, Object> {
		/**
		 * Permet de lancer l'exécution de la tâche longue
		 * ici, on demande les situations professionnelles de l'étudiant connecté
		 */
		@Override
		protected Object doInBackground(Object... params) {
			// Situation laNouvelleSituation;
            PCPApplication monAppli;
            monAppli = (PCPApplication) DescriptionSituationActivity.this.getApplication();
	    	try {
	    		PasserelleSituation.updateSituation(monAppli.getVisiteur(), (Situation) params[0], (HashMap<String, String>) params[1]);
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
				Toast.makeText(DescriptionSituationActivity.this, getString(R.string.msgErrUpdateSitPro) + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				// retour à l'activité appelante
                Intent uneIntention;
                uneIntention = new Intent(DescriptionSituationActivity.this, SituationsActivity.class);
                // on renvoie la position sauvegardée et la situation modifiée
                uneIntention.putExtra("position", position);
                uneIntention.putExtra("situation", (Situation) result);
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
                Toast.makeText(DescriptionSituationActivity.this, "Aucune donnée modifiée", Toast.LENGTH_LONG).show();
            }
            else { // il faut demander la mise à jour de la situation
                new SituationUpdate().execute(laSituation, hashMapToUpdate);
            }
    	}
	}
}