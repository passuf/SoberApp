package soberapp.vis.ethz.ch.soberapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Drink;
import soberapp.vis.ethz.ch.soberapp.data.Produc0r;


public class CreateDrinkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drink);
        setTitle(R.string.create_drink);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_drink, menu);
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

    public void onClickCreate(View v) {
        // Get name
        EditText nameText = (EditText) findViewById(R.id.create_drink_name);
        String name = nameText.getText().toString();
        if (name == null || name.length() < Default.MIN_NAME_LENGTH) {
            createAlert(getString(R.string.form_error), getString(R.string.invalid_name));
            return;
        }

        // Get volume
        EditText volumeText = (EditText) findViewById(R.id.create_drink_volume);
        int volume = Integer.parseInt(volumeText.getText().toString());
        if (volume < Default.MIN_VOLUME || volume > Default.MAX_VOLUME) {
            createAlert(getString(R.string.form_error), getString(R.string.invalid_volume));
            return;
        }

        // Get volume percent
        EditText percentageText = (EditText) findViewById(R.id.create_drink_percentage);
        Double percentage = Double.parseDouble(percentageText.getText().toString());
        if (percentage < Default.MIN_PERCENTAGE || percentage > Default.MAX_PERCENTAGE) {
            createAlert(getString(R.string.form_error), getString(R.string.invalid_percentage));
            return;
        }

        // Create drink
        Drink drink = Produc0r.getInstance().addDrink(name, percentage, volume);

        // Consume drink
        AlcoholLevelCalculator.getInstance().addDrink(drink);

        // Close parent activity and return to MainActivity
        setResult(RESULT_OK, null);
        finish();

    }

    public void onClickCancel(View v) {
        finish();
    }

    public void createAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
