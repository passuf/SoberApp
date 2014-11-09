package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class IntroActivity extends Activity {

    private static final String LOG_TAG = "IntroActivity";

    private Settings settings;
    private Date birthday;


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

            birthday = settings.getBirthday();
            updateBirthday();

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
            onNavigateUp();
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

        // Get the gender
        Spinner gender = (Spinner) findViewById(R.id.spinner_gender);

        // Get the birthday
        if (birthday == null) {
            Log.e(LOG_TAG, "Could not parse birthday");
            createAlert(getString(R.string.form_error), getString(R.string.invalid_birthday));
            return;
        }

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

        // Update settings
        settings.setProfileComplete(true);
        settings.setName(name);
        settings.setSex(gender.getSelectedItem().toString());
        settings.setBirthday(birthday);
        settings.setHeight(height);
        settings.setWeight(weight);

        // Close this activity
        finish();

    }

    public void updateBirthday() {
        TextView birthdayText = (TextView) findViewById(R.id.text_birthday);
        birthdayText.setText(new SimpleDateFormat(Default.DATE_FORMAT).format(birthday));
        birthdayText.setTextSize(24);
    }

    public void createAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showDatePickerDialog(View v) {
        Calendar c = Calendar.getInstance();
        if (birthday != null) {
            c.setTime(birthday);
        } else {
            c.add(Calendar.YEAR, Default.INITIAL_YEARS);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog newFragment = new DatePickerDialog(this, new DatePickerFragment(), year, month, day);
        newFragment.show();
    }

    class DatePickerFragment implements DatePickerDialog.OnDateSetListener {

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            birthday = c.getTime();
            updateBirthday();
        }
    }
}
