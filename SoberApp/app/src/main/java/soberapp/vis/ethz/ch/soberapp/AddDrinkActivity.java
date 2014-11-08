package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.data.Drink;


public class AddDrinkActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "AddDrinkActivity";

    private ListView drinksListView;
    private List<Drink> drinks;
    private List<String> drinkNames;
    private ArrayAdapter<String> drinksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        setTitle(R.string.title_activity_add_drink);

        // Get drinks
        drinks = AlcoholLevelCalculator.getInstance().getConsumor().drinks();

        // Create list with drinks
        drinksListView = (ListView) findViewById(R.id.drinks_listView);
        drinksListView.setOnItemClickListener(this);
        drinkNames = new ArrayList<String>();
        for (Drink d : drinks) {
            drinkNames.add(d.toString());
        }
        drinksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drinkNames);
        drinksListView.setAdapter(drinksAdapter);

        // Add button to create new drink
        View footerView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drinks_footer, null, false);
        drinksListView.addFooterView(footerView);

        // Add listener to search field
        EditText searchText = (EditText) findViewById(R.id.search_field);
        searchText.addTextChangedListener(new SearchKeyListener());

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
        // Get the drink
        if (position < drinks.size()) {
            selectDrink(drinks.get(position));
        }
    }

    public void selectDrink(Drink drink) {
        Log.d(LOG_TAG, "Clicked on " + drink);
        AlcoholLevelCalculator.getInstance().addDrink(drink);
        finish();
    }

    public void onCreateDrink(View v) {
        Log.d(LOG_TAG, "create drink");
        Intent intent = new Intent(this, CreateDrinkActivity.class);
        startActivityForResult(intent, Default.REQUEST_EXIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Default.REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    class SearchKeyListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Change the list with search results

            List<String> selectedDrinks = new ArrayList<String>();
            drinkNames.clear();
            String query = String.format("%s", s.toString());

            for (Drink d : drinks) {
                if (d.toString().toLowerCase().contains(query.toLowerCase())) {
                    drinkNames.add(d.toString());
                }
            }

            drinksAdapter.notifyDataSetChanged();
        }
    }
}
