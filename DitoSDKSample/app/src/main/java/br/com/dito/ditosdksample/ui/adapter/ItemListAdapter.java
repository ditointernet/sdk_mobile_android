package br.com.dito.ditosdksample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.dito.ditosdksample.R;

/**
 * Created by gabriel.araujo on 08/04/15.
 */
public class ItemListAdapter extends ArrayAdapter<String> {

    private LayoutInflater layoutInflater;

    public ItemListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.adapter_list, null);
        TextView text = (TextView) convertView.findViewById(R.id.op);
        text.setText(getItem(position));
        return convertView;
    }
}
