package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class IntroActivity extends Activity {

    private static final String LOG_TAG = "IntroActivity";

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setTitle(R.string.title_activity_intro);

        // Load settings
        settings = new Settings(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intro, menu);
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

    public void onClickStart(View v) {
        Log.d(LOG_TAG, "onClickStart");

        // TODO: validate input before saving anything

        // Get the name
        EditText name = (EditText) findViewById(R.id.text_name);
        settings.setName(name.getText().toString());

        // Get the gender
        Spinner gender = (Spinner) findViewById(R.id.spinner_gender);
        settings.setSex(gender.getSelectedItem().toString());

        // Get the birthday
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        EditText birthdayText = (EditText) findViewById(R.id.text_birthday);
        try {
            String birthdayString = birthdayText.getText().toString();
            birthday = dateFormat.parse(birthdayString);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Could not parse birthday");
        }
        settings.setBirthday(birthday);

        // Get the height
        EditText height = (EditText) findViewById(R.id.text_height);
        settings.setHeight(Integer.parseInt(height.getText().toString()));

        // Get the weight
        EditText weight = (EditText) findViewById(R.id.text_weight);
        settings.setWeight(Integer.parseInt(weight.getText().toString()));

        // Update settings to not display this activity again
        settings.setProfileComplete(true);

        Log.d(LOG_TAG, "Created the profile!");
        Log.d(LOG_TAG, "Name: " + settings.getName());
        Log.d(LOG_TAG, "Gender: " + settings.getSex());
        Log.d(LOG_TAG, "Birthday: " + settings.getBirthday().toString());
        Log.d(LOG_TAG, "Height: " + settings.getHeight());
        Log.d(LOG_TAG, "Weight: " + settings.getWeight());

        // Close this activity
        finish();

    }
}
