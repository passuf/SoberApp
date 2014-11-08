package soberapp.vis.ethz.ch.soberapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Represents the different settings.
 */
public class Settings {

    private static final String LOG_TAG = "Settings";

    /**
     * Strings to represent settings in the SharedPreferences file.
     */
    private static final String PROFILE_COMPLETE = "user:complete";
    private static final String USER_NAME = "user:name";
    private static final String USER_SEX = "user:sex";
    private static final String USER_WEIGHT = "user:weight";
    private static final String USER_HEIGHT = "user:height";
    private static final String USER_BIRTHDAY = "user:birthday";

    /**
     * Default values for the settings.
     */
    private static final boolean DEFAULT_PROFILE_COMPLETE = false;
    private static final String DEFAULT_USER_NAME = "Mrs. X";
    private static final String DEFAULT_USER_FEMALE = "female";
    private static final String DEFAULT_USER_MALE = "male";
    private static final String DEFAULT_USER_SEX = DEFAULT_USER_FEMALE;
    private static final int DEFAULT_USER_WEIGHT = 70;
    private static final int DEFAULT_USER_HEIGHT = 170;


    private SharedPreferences preferences;
    private Context context;


    public Settings(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void editSettings(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void editSettings(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void editSettings(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private void editSettings(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public boolean isProfileComplete() {
        return preferences.getBoolean(PROFILE_COMPLETE, DEFAULT_PROFILE_COMPLETE);
    }

    public void setProfileComplete(boolean complete) {
        editSettings(PROFILE_COMPLETE, complete);
    }

    public String getName() {
        return preferences.getString(USER_NAME, DEFAULT_USER_NAME);
    }

    public void setName(String name) {
        editSettings(USER_NAME, name);
    }

    public String getSex() {
        return preferences.getString(USER_SEX, DEFAULT_USER_SEX);
    }

    public void setSex(String sex) {
        editSettings(USER_SEX, sex);
    }

    public int getWeight() {
        return preferences.getInt(USER_WEIGHT, DEFAULT_USER_WEIGHT);
    }

    public void setWeight(int weight) {
        editSettings(USER_WEIGHT, weight);
    }

    public int getHeight() {
        return preferences.getInt(USER_HEIGHT, DEFAULT_USER_HEIGHT);
    }

    public void setHeight(int height) {
        editSettings(USER_HEIGHT, height);
    }

    public Date getBirthday() {
        return new Date(preferences.getLong(USER_BIRTHDAY, 0));
    }

    public void setBirthday(Date date) {
        editSettings(USER_BIRTHDAY, date.getTime());
    }

    public int getAge() {
        Calendar birthday = getCalendar(getBirthday());
        Calendar now = getCalendar(new Date());
        int age = birthday.get(Calendar.YEAR) - now.get(Calendar.YEAR);
        if (birthday.get(Calendar.MONTH) > now.get(Calendar.MONTH) ||
                (birthday.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                        birthday.get(Calendar.DATE) > now.get(Calendar.DATE))) {
                age--;
        }
        return Math.abs(age);
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}
