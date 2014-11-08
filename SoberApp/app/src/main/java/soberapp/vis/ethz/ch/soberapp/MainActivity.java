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
import soberapp.vis.ethz.ch.soberapp.data.InitialData;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

    private Settings settings;
    private AlcoholLevelCalculator alc;

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

        if (eventList.size() > 0) {
            Button addLast = (Button) findViewById(R.id.button_add_last_drink);
            addLast.setTextColor(Color.RED);
            Button add2Last = (Button) findViewById(R.id.button_add_2last_drink);
            add2Last.setTextColor(Color.RED);
            Button add3Last = (Button) findViewById(R.id.button_add_3last_drink);
            add3Last.setTextColor(Color.RED);
            Button addDrink = (Button) findViewById(R.id.button_add_drink);
            addDrink.setTextColor(Color.RED);
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
        /* TODO */
    }

    public void add2LastDrink(View view) {
        /* TODO */
    }

    public void add3LastDrink(View view) {
        /* TODO */
    }
}
