package soberapp.vis.ethz.ch.soberapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.*;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import soberapp.vis.ethz.ch.soberapp.data.Consume;


public class DrinksHistoryActivity extends ListActivity {

    private static final String LOG_TAG = "DrinksHistoryActivity";

    private AlcoholLevelCalculator alc = AlcoholLevelCalculator.getInstance();
    private ArrayList<Consume> drinksList;
    private ArrayAdapter<Consume> drinksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_history);
        setTitle(R.string.title_activity_drinks_history);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, Default.CONSUMES_HISTORY);
        drinksList = (ArrayList<Consume>) alc.getConsumor().consumed(c.getTimeInMillis());

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
            graphView.addSeries(new GraphViewSeries(data));
            LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
            layout.addView(graphView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.drinks_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
