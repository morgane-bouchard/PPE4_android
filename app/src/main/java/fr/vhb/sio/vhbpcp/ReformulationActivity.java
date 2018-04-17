package fr.vhb.sio.vhbpcp;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.dao.PasserelleSituation;
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


public class ReformulationActivity {

    public class SituationsActivity extends Activity {
        public static final int CODE_UPDATE = 1;
        private ListView listViewSitPros;
        private ArrayList<Situation> lesSitPros;
        private ArrayAdapter<Situation> unAdaptateur;
        /**
         * Méthode appelée lors de la création de l'activité
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sitpros);
            initialisations();
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
            listViewSitPros.setOnItemClickListener(new fr.vhb.sio.vhbpcp.SituationsActivity.ListViewOnItemClick() );
            new fr.vhb.sio.vhbpcp.SituationsActivity.SitProsGet().execute();
        }




    }
