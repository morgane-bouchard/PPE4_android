package fr.vhb.sio.vhbpcp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.dao.PasserelleActiviteParIdEtud;
import fr.vhb.sio.vhbpcp.metier.Activite;

/**
 * Classe gérant l'interface utilisateur d'une liste de situations professionnelles
 * @author sio2slam
 */
public class ActiviteByIdEtudActivity extends Activity {
	public static final int CODE_UPDATE = 1;
	private ListView listViewActiviteByIdEtud;
	private ArrayList<Activite> lesActivites;
	private ArrayAdapter<Activite> unAdaptateur;
	/**
	 * Méthode appelée lors de la création de l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_synthese_activite);
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
     * Instancie et exécute un thread séparé pour récupérer les situations professionnelles
     */
	private void initialisations() {
		listViewActiviteByIdEtud = (ListView) findViewById(R.id.listViewActiviteByIdEtud);

		new ActiviteByIdEtudGet().execute();
   	}

	/**
	 * Classe interne pour prendre en charge l'appel à un service web et sa réponse
	 * La consultation des situations professionnelles fait en ef9
	 * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
	 * TypeParam3 : Object - type de paramètre ArrayList<String> ou Exception
	 * @see AsyncTask
	 */
	private class ActiviteByIdEtudGet extends AsyncTask<Void, Void, Object> {
		/**
		 * Permet de lancer l'exécution de la tâche longue
		 * ici, on demande les situations professionnelles de l'étudiant connecté
		 */
		@Override
		protected Object doInBackground(Void... params) {
			ArrayList<Activite> lesDonnees;
			PCPApplication monAppli;
			monAppli = (PCPApplication) ActiviteByIdEtudActivity.this.getApplication();
	    	try {
				lesDonnees = PasserelleActiviteParIdEtud.getActiviteByStudent(monAppli.getVisiteur());
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
				Toast.makeText(ActiviteByIdEtudActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				lesActivites = (ArrayList<Activite>) result;
				unAdaptateur = new ArrayAdapter<Activite>(ActiviteByIdEtudActivity.this, android.R.layout.simple_list_item_1, lesActivites);
				// 	on associe l'adaptateur au composant ListView
				listViewActiviteByIdEtud.setAdapter(unAdaptateur);
			}
		}

	}

}