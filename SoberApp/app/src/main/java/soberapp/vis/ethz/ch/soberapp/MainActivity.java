package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Drink;
import soberapp.vis.ethz.ch.soberapp.data.InitialData;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

    private Settings settings;
    private AlcoholLevelCalculator alc;
    private List<Drink> last3Drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alc = new AlcoholLevelCalculator(this, Consum0r.getInstance());
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        // initialize the Database
        InitialData.initDB();

        // Load Settings
        settings = new Settings(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Check if the profile is complete
        if (!settings.isProfileComplete()) {
            // Profile not complete, prompt user
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }

        TextView title = (TextView) findViewById(R.id.text_title_main);
        title.setText("Welcome " + settings.getName() + ", you are " + settings.getAge() + " years old.");
        update();
    }

    private void update(){

        long timeDiffMin = (alc.timeSober().getTime() - System.currentTimeMillis())/(1000*60);
        if (timeDiffMin < 0) {
            timeDiffMin = 0;
        }
        long timeDiffHour = timeDiffMin/60;
        timeDiffMin = timeDiffMin % 60;
        TextView soberTime = (TextView) findViewById(R.id.soberTime);
        soberTime.setText(String.format("sober in %dh %dmin", timeDiffHour, timeDiffMin));

        TextView bac = (TextView) findViewById(R.id.BAC);
        bac.setText(alc.getAlcoholLevel() + " \u2030");

        List<CalendarInstance> eventList = CollisionDetector.getCollisions(this);
        EventListAdapter adapter = new EventListAdapter(eventList, this, alc.timeSober());
        ListView eventView = (ListView) findViewById(R.id.calendarInstancesView);
        eventView.setAdapter(adapter);


        Button addLast = (Button) findViewById(R.id.button_add_last_drink);
        Button add2Last = (Button) findViewById(R.id.button_add_2last_drink);
        Button add3Last = (Button) findViewById(R.id.button_add_3last_drink);
        Button addDrink = (Button) findViewById(R.id.button_add_drink);

        if (eventList.size() > 0) {
            addLast.setTextColor(Color.RED);
            add2Last.setTextColor(Color.RED);
            add3Last.setTextColor(Color.RED);
            addDrink.setTextColor(Color.RED);
        }

        last3Drinks = Consum0r.getInstance().topN();
        if (last3Drinks.size()>=1) {
            addLast.setText(last3Drinks.get(0).getName());
        }
        if (last3Drinks.size()>=2) {
            add2Last.setText(last3Drinks.get(1).getName());
        }
        if (last3Drinks.size()>=3) {
            add3Last.setText(last3Drinks.get(2).getName());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void addDrink(View v) {
        Log.d(LOG_TAG, "addDrink");
        Intent intent = new Intent(this, AddDrinkActivity.class);
        startActivity(intent);
    }

    public void addLastDrink(View view) {
        if (last3Drinks.size()<1) {
            return;
        }
        Consum0r.getInstance().consume(last3Drinks.get(0));
        update();
    }

    public void add2LastDrink(View view) {
        if (last3Drinks.size()<2) {
            return;
        }
        Consum0r.getInstance().consume(last3Drinks.get(1));
        update();
    }

    public void add3LastDrink(View view) {
        if (last3Drinks.size()<3) {
            return;
        }
        Consum0r.getInstance().consume(last3Drinks.get(2));
        update();
    }
}
