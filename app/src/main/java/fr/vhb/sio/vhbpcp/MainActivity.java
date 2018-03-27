package fr.vhb.sio.vhbpcp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import fr.vhb.sio.vhbpcp.dao.PasserelleEtudiant;
import fr.vhb.sio.vhbpcp.metier.Etudiant;

/**
 * Classe gérant l'interface utilisateur de connexion d'un étudiant
 * @author baraban
 */
public class MainActivity extends Activity {
	private Button buttonValider;
	private EditText editTextLogin, editTextMdp;

	/**
	 * Méthode appelée lors de la création de l'activité
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialisations();
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    /**
     * Initialise les attributs privés référençant les widgets de l'interface utilisateur
     * Instancie les écouteurs et les affecte sur les objets souhaités
     */
	private void initialisations() {
	    this.buttonValider = (Button) this.findViewById(R.id.buttonValider);
	    this.editTextLogin = (EditText) this.findViewById(R.id.editTextLogin);
	    this.editTextMdp = (EditText) this.findViewById(R.id.editTextMotPasse);
	    
	    // on affecte un écouteur d'événement clic au bouton Valider
	    this.buttonValider.setOnClickListener(new ButtonValiderClick());		
	}

    /**
     * Classe interne servant d'écouteur de l'événement click sur le bouton Valider
     */
	private class ButtonValiderClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			String login, mdp;
			login = editTextLogin.getText().toString();
			mdp = editTextMdp.getText().toString();
			if ( ! login.equals("") && ! mdp.equals("") ) {
				// instancie et exécute un thread séparé pour vérifier la connexion
				new SeConnecterGet().execute(login, mdp);
			}
			else {
				Toast.makeText(MainActivity.this, R.string.msgErrChampVide, Toast.LENGTH_LONG).show();
			}
		}
	}
	/**
	 * Classe interne pour prendre en charge l'appel à un service web et sa réponse
	 * La vérification de la connexion d'un étudiant fait en effet appel au service web et peut donc prendre quelques sec.
	 * A partir d'Android 3.0, une requête HTTP doit être effectuée à l'intérieur d'une tâche asynchrone
	 * TypeParam1 : String - paramètres login et mdp
	 * TypeParam2 : Void - pas de paramètres fournis pendant la durée du traitement
	 * TypeParam3 : Object - type de paramètre Etudiant ou Exception
	 * @see android.os.AsyncTask
	 */
	private class SeConnecterGet extends AsyncTask<String, Void, Object> {
		@Override
		/**
		 * Permet de lancer l'exécution de la tâche longue
		 * ici, on demande à vérifier la connexion d'un étudiant à partir de son login et mot de passe
		 */
		protected Object doInBackground(String... params) {
			// cette méthode permet de lancer l'exécution de la tâche longue
			// ici, on demande l'authentification de l'étudiant
			Etudiant lEtudiant = null;
	    	try {
	    		lEtudiant = PasserelleEtudiant.seConnecter((String) params[0], (String) params[1]);
	    	
	    	}
	    	catch (Exception ex) {
	    		return ex;
	    	}
	    	return lEtudiant;
		}

		/**
		 * Méthode automatiquement appelée quand la tâche longue se termine
		 * ici, on teste le type de résultat, exception ou liste
		 * @param result objet de type Etudiant ou Exception
		 */
		@Override
		protected void onPostExecute(Object result) {
			// cette méthode est automatiquement appelée quand la tâche longue se termine
			// ici, on affiche les informations récupérées
			PCPApplication monAppli;
			Intent uneIntention;
			if ( result instanceof Exception ) {
				Exception ex = (Exception) result;
				Toast.makeText(MainActivity.this, getString(R.string.msgErrConnexionInvalide) + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				Etudiant lEtudiant = (Etudiant) result;
				// conserve l'étudiant dans le contexte de l'application
				// il pourra ainsi être retrouvé dans n'importe quelle activité
				monAppli = (PCPApplication) MainActivity.this.getApplication();
				monAppli.setVisiteur(lEtudiant);
				// crée une intention passée à l'activité de classe SituationsActivity
				uneIntention = new Intent(MainActivity.this, SituationsActivity.class);
				startActivity(uneIntention);
			}
		}
	}
}
