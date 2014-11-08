package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        // Restore values
        if (settings.isProfileComplete()) {
            TextView title = (TextView) findViewById(R.id.text_intro_title);
            title.setText(R.string.action_settings);

            TextView description = (TextView) findViewById(R.id.text_description);
            description.setText("");

            EditText name = (EditText) findViewById(R.id.text_name);
            name.setText(settings.getName());

            EditText birthday = (EditText) findViewById(R.id.text_birthday);
            birthday.setText(new SimpleDateFormat("yyy-MM-dd").format(settings.getBirthday()));

            EditText height = (EditText) findViewById(R.id.text_height);
            height.setText(String.format("%d", settings.getHeight()));

            EditText weight = (EditText) findViewById(R.id.text_weight);
            weight.setText(String.format("%d", settings.getWeight()));

            Spinner gender = (Spinner) findViewById(R.id.spinner_gender);
            int id = 0;
            for (String s : getResources().getStringArray(R.array.gender_selection)) {
                if (s.equals(settings.getSex())) {
                    break;
                }
                id++;
            }
            gender.setSelection(id);
        }

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

    @Override
    public void onBackPressed() {
        if (settings.isProfileComplete()) {
            finish();
        }
    }

    public void onClickStart(View v) {
        Log.d(LOG_TAG, "onClickStart");

        // Get the name
        EditText nameText = (EditText) findViewById(R.id.text_name);
        String name = nameText.getText().toString();
        if (name == null || name.length() < Default.MIN_NAME_LENGTH) {
            // Name is too short
            createAlert(getString(R.string.form_error), getString(R.string.invalid_name));
            return;
        }
        settings.setName(name);

        // Get the gender
        Spinner gender = (Spinner) findViewById(R.id.spinner_gender);
        settings.setSex(gender.getSelectedItem().toString());

        // Get the birthday
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = new Date();
        EditText birthdayText = (EditText) findViewById(R.id.text_birthday);
        try {
            String birthdayString = birthdayText.getText().toString();
            birthday = dateFormat.parse(birthdayString);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Could not parse birthday");
        }
        settings.setBirthday(birthday);

        // Get the height
        EditText heightText = (EditText) findViewById(R.id.text_height);
        int height = 0;
        try {
            height = Integer.parseInt(heightText.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Invalid height");
        }
        if (height < Default.MIN_HEIGHT) {
            // Invalid height
            createAlert(getString(R.string.form_error), getString(R.string.invalid_height));
            return;
        }
        settings.setHeight(height);

        // Get the weight
        EditText weightText = (EditText) findViewById(R.id.text_weight);
        int weight = 0;
        try {
            weight = Integer.parseInt(weightText.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "Invalid weight");
        }
        if (weight < Default.MIN_WEIGHT) {
            // Invalid weight
            createAlert(getString(R.string.form_error), getString(R.string.invalid_weight));
            return;
        }
        settings.setWeight(weight);

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

    public void createAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
