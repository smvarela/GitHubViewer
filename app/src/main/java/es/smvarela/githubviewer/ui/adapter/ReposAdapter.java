package es.smvarela.githubviewer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.smvarela.githubviewer.R;
import es.smvarela.githubviewer.model.Repository;

/**
 * This adapter holds the repositories data to be shown in a ListView in UserActivity
 */
public class ReposAdapter extends ArrayAdapter<Repository> {

    public ReposAdapter(Context context, ArrayList<Repository> datos) {
        super(context, R.layout.listitem_repository, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Repository repository = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listitem_repository, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.lbl_Name);
        TextView lblSubtitulo = (TextView)item.findViewById(R.id.lbl_Language);

        lblTitulo.setText(repository.getName());
        lblSubtitulo.setText(repository.getLanguage());

        return(item);
    }
}
