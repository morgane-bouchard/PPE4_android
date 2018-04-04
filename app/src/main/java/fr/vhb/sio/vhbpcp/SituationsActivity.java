package fr.vhb.sio.vhbpcp;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.dao.PasserelleSituation;
import fr.vhb.sio.vhbpcp.metier.Etudiant;
import fr.vhb.sio.vhbpcp.metier.Situation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

/**
 * Classe gérant l'interface utilisateur d'une liste de situations professionnelles
 * @author sio2slam
 */
public class SituationsActivity extends Activity {
	public static final int CODE_UPDATE = 1;
	private ListView listViewSitPros;
	private ArrayList<Situation> lesSitPros;
	private ArrayAdapter<Situation> unAdaptateur;
	private Toolbar unTitre;
	private String unNom, unPrenom;
	/**
	 * Méthode appelée lors de la création de l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sitpros);
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
                Situation laSituation;
                position = data.getIntExtra("position", 0);
                laSituation = data.getParcelableExtra("situation");
                this.lesSitPros.set(position, laSituation);
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
		listViewSitPros = (ListView) findViewById(R.id.listViewSitPros);
		listViewSitPros.setOnItemClickListener(new ListViewOnItemClick() );

		PCPApplication monAppli;

		Etudiant lEtudiant;

		monAppli = (PCPApplication) SituationsActivity.this.getApplication();
		lEtudiant = monAppli.getVisiteur();
		this.setTitle(lEtudiant.getPrenom()+ " " + lEtudiant.getNom());
		new SitProsGet().execute();
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
	private class SitProsGet extends AsyncTask<Void, Void, Object> {
		/**
		 * Permet de lancer l'exécution de la tâche longue
		 * ici, on demande les situations professionnelles de l'étudiant connecté
		 */
		@Override
		protected Object doInBackground(Void... params) {
			ArrayList<Situation> lesDonnees;
			PCPApplication monAppli;
			monAppli = (PCPApplication) SituationsActivity.this.getApplication();
	    	try {
	    		lesDonnees = PasserelleSituation.getLesSPs(monAppli.getVisiteur());
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
				Toast.makeText(SituationsActivity.this, getString(R.string.msgErrRecupSitPros) + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			else {
				lesSitPros = (ArrayList<Situation>) result;
				unAdaptateur = new ArrayAdapter<Situation>(SituationsActivity.this, android.R.layout.simple_list_item_1, lesSitPros);
				// 	on associe l'adaptateur au composant ListView
				listViewSitPros.setAdapter(unAdaptateur);
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
    		uneIntention = new Intent(SituationsActivity.this, DescriptionSituationActivity.class);
            uneIntention.putExtra("position", position);
    		uneIntention.putExtra("situation", lesSitPros.get(position));
    		startActivityForResult(uneIntention, CODE_UPDATE);
    	}
	}
}