package soberapp.vis.ethz.ch.soberapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventListAdapter extends ArrayAdapter<CalendarInstance> {

    private List<CalendarInstance> eventList;
    private Context context;
    private Date soberDate;

    public EventListAdapter(List<CalendarInstance> eventList, Context ctx, Date soberDate) {
        super(ctx, R.layout.row_layout, eventList);
        this.eventList = eventList;
        this.context = ctx;
        this.soberDate = soberDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setTextColor(Color.WHITE);
        TextView dateView = (TextView) convertView.findViewById(R.id.date);
        dateView.setTextColor(Color.rgb(128,128,128));
        CalendarInstance event = eventList.get(position);

        tv.setText(event.getEventTitle());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = formatter.format(event.getEventStart());
        dateView.setText(s);
        if (event.getEventStart().before(soberDate)) {
            tv.setTextColor(Color.rgb(208, 64, 64));
        }


        return convertView;
    }
}