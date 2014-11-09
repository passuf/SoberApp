package soberapp.vis.ethz.ch.soberapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;

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
}
