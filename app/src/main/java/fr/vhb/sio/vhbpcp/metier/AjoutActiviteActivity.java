package fr.vhb.sio.vhbpcp.metier;

/**
 * Created by mbouchard on 17/04/2018.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.DescriptionSituationActivity;
import fr.vhb.sio.vhbpcp.PCPApplication;
import fr.vhb.sio.vhbpcp.R;

public class AjoutActiviteActivity extends Activity {
    private Situation laSituation;
    private int position;
    private Button buttonAjouter;
    private Button buttonBack;
    private Spinner spinnerActivites;
    private ArrayList<Localisation> lesActivitesLocalisation;
    ArrayAdapter<Localisation> dataAdapterActivitesLocalisation;
    ArrayAdapter<Source> dataAdapterCodeSource ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situation_description);
        initialisations();
    }

    private void initialisations() {
        // récupération des widgets pour la description de la situation
        this.spinnerActivites = (Spinner) findViewById(R.id.spinnerActivites);
        this.buttonAjouter = (Button) findViewById(R.id.buttonAjouterActivites);
        this.buttonBack = (Button) findViewById(R.id.buttonBack);

        // récupération de la situation véhiculée par l'intention
        Intent uneIntention;
        uneIntention = getIntent();
        this.laSituation = uneIntention.getParcelableExtra("situation");
        this.position = uneIntention.getIntExtra("position", 0);
        // initialisation des zones d'éditions à partir de la situation reçue
        // initialisation des écouteurs d'événements
        this.buttonAjouter.setOnClickListener(new DescriptionSituationActivity.OnButtonClick());
        this.buttonBack.setOnClickListener(new DescriptionSituationActivity.OnButtonBackClick());

        PCPApplication monAppli = (PCPApplication) DescriptionSituationActivity.this.getApplication();;
        Etudiant lEtudiant;
        lEtudiant = monAppli.getVisiteur();

        this.setTitle(lEtudiant.getPrenom()+ " " + lEtudiant.getNom());

        this.initSpinnerLocalisation();
        this.initSpinnerSource();
    }
}
