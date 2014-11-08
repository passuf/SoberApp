package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.data.Drink;


public class AddDrinkActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "AddDrinkActivity";

    private ListView drinksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        setTitle(R.string.title_activity_add_drink);

        // FIXME: remove those debug drinks
        // Create some debug drinks
        List<Drink> drinks = new ArrayList<Drink>();
        drinks.add(new Drink("Beer", 5));
        drinks.add(new Drink("Wine", 12));
        drinks.add(new Drink("Vodka", 30));

        drinksListView = (ListView) findViewById(R.id.drinks_listView);
        drinksListView.setOnItemClickListener(this);

        List<String> drinkNames = new ArrayList<String>();
        for (Drink d : drinks) {
            drinkNames.add(d.getName());
        }

        drinksListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drinkNames));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_drink, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(LOG_TAG, "onItemClick");
        // TODO: Select drink
    }

    public void selectDrink(Drink drink) {
        // TODO
    }
}
