package soberapp.vis.ethz.ch.soberapp;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import soberapp.vis.ethz.ch.soberapp.data.Consume;

public class ConsumeAdapter extends ArrayAdapter<Consume> {

    private final Context context;
    private final ArrayList<Consume> itemsArrayList;

    public ConsumeAdapter(Context context, ArrayList<Consume> itemsArrayList) {

        super(context, R.layout.history_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Get rowView from inflater
        View rowView = inflater.inflate(R.layout.history_row, parent, false);

        // Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);

        // Set the text for textView
        labelView.setText(itemsArrayList.get(position).getDrink().toString());
        Date date = new Date(itemsArrayList.get(position).getTsp());
        PrettyTime prettyTime = new PrettyTime(Locale.UK);
        String dateString = prettyTime.format(date);
        valueView.setText(dateString);

        valueView.setGravity(Gravity.CENTER_VERTICAL);
        labelView.setGravity(Gravity.CENTER_VERTICAL);

        return rowView;
    }
}
