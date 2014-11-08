package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String LOG_TAG = "MainActivity";

    private Settings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        // Load Settings
        settings = new Settings(this);

        // Check if the profile is complete
        if (!settings.isProfileComplete()) {
            // Profile not complete, prompt user
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
        }

        TextView title = (TextView) findViewById(R.id.text_title_main);
        title.setText("Welcome " + settings.getName() + ", you are " + settings.getAge() + " years old.");

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
}
