package fr.vhb.sio.vhbpcp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import fr.vhb.sio.vhbpcp.metier.Situation;

public class SituationsAdapter extends ArrayAdapter<Situation>
{
    private final Context context;
    private final int resource;
    private final ArrayList<Situation> values;

    public SituationsAdapter(Context context, int resource, ArrayList<Situation> values)
    {
        super(context, resource, values);
        this.context = context;
        this.resource = resource;
        this.values = values;
    }

    @Override
    /**
     * Redéfinit la méthode getView qui construit la vue pour l'élément situé à
     * la position spécifiée
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        View rowView;
        TextView textViewLibelCourt, textViewNbActivites, textViewNbProductions, textViewNbCommentaires;
        Situation uneSituation;

        // demande d'obtention d'un désérialisateur de layout xml,
        // càd un objet qui sait transformer un fichier xml en objet View
        inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        // demande à désérialiser le fichier xml identifié par l'id de ressource en objet View
        // rowView est alors un objet regroupant les vues définies dans le layout d'une ligne
        rowView = inflater.inflate(this.resource, parent, false);
        // récupère chaque widget du layout d'un élément
        textViewLibelCourt = (TextView) rowView.findViewById(R.id.textViewLibelCourt);
        textViewNbActivites = (TextView) rowView.findViewById(R.id.textViewNbActivites);
        textViewNbProductions = (TextView) rowView.findViewById(R.id.textViewNbProductions);
        textViewNbCommentaires = (TextView) rowView.findViewById(R.id.textViewNbCommentaires);

        // affecte le contenu des widgets d'après le contenu de l'élément reçu
        uneSituation = values.get(position);
        textViewLibelCourt.setText(uneSituation.getLibcourt());
        textViewNbActivites.setText(uneSituation.getNbActivitees());
        textViewNbProductions.setText(uneSituation.getNbProductions());
        textViewNbCommentaires.setText(uneSituation.getNbCommentaires());
        return rowView;

    }


}
