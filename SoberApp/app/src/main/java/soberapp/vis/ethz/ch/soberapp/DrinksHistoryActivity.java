package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.data.Consume;


public class DrinksHistoryActivity extends Activity {

    private static final String LOG_TAG = "DrinksHistoryActivity";

    private AlcoholLevelCalculator alc = AlcoholLevelCalculator.getInstance();
    private ListView drinksListView;
    private List<Consume> drinksList;
    private List<String> drinkNames;
    private ArrayAdapter<String> drinksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_history);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, Default.CONSUMES_HISTORY);

        // TODO: get correct data
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        //drinksList = alc.getConsumor().consumed(ts);
        drinksList = new ArrayList<Consume>();

        drinksListView = (ListView) findViewById(R.id.drinks_listView);
        drinkNames = new ArrayList<String>();
        for (Consume con : drinksList) {
            drinkNames.add(con.toString());
        }
        drinksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drinkNames);
        drinksListView.setAdapter(drinksAdapter);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
