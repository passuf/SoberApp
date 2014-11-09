package soberapp.vis.ethz.ch.soberapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.*;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.data.Consume;


public class DrinksHistoryActivity extends ListActivity {

    private static final String LOG_TAG = "DrinksHistoryActivity";

    private AlcoholLevelCalculator alc = AlcoholLevelCalculator.getInstance();
    private List<Consume> drinksList;
    private ArrayAdapter<Consume> drinksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_history);
        setTitle(R.string.title_activity_drinks_history);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, Default.CONSUMES_HISTORY);
        drinksList = alc.getConsumor().consumed(c.getTimeInMillis());

        drinksAdapter = new ConsumeAdapter(this, drinksList);
        setListAdapter(drinksAdapter);

        if (!drinksList.isEmpty()) {
            int i = drinksList.size() * 2;
            GraphViewData[] data = new GraphViewData[i];
            Iterator<Consume> it = drinksList.iterator();
            Consume a = it.next();
            i--;
            long now = System.currentTimeMillis();
            double level = a.getLevel() - alc.timeUp(now - a.getTsp());
            data[i] = new GraphViewData(now, level);
            while (it.hasNext()) {
                Consume b = it.next();
                i--;
                data[i] = new GraphViewData(a.getTsp(), a.getLevel());
                i--;
                level = b.getLevel() - alc.timeUp(a.getTsp() - b.getTsp());
                data[i] = new GraphViewData(a.getTsp(), level);
                a = b;
            }
            i--;
            data[i] = new GraphViewData(a.getTsp(), a.getLevel());
            GraphView graphView = new LineGraphView(this, "Blood Alcohol");
            graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(value);
                    }
                    return null; // let graphview generate Y-axis label for us
                }
            });
            graphView.getGraphViewStyle().setNumHorizontalLabels(2);
            graphView.addSeries(new GraphViewSeries(data));
            LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
            layout.addView(graphView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drinks_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            onDeleteLatest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDeleteLatest() {
        // Remove Consume from ListView
        if (drinksList.size() > 0) {
            // Remove Consume from ListView
            drinksList.remove(0);
            drinksAdapter.notifyDataSetChanged();
            // Delete Consume from DB
            alc.getConsumor().pukeLast();
        }
    }

    @Override
    public void onBackPressed() {
        onNavigateUp();
    }
}
