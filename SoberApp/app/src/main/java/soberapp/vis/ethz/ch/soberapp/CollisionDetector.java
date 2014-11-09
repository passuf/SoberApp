package soberapp.vis.ethz.ch.soberapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karl on 08/11/14.
 */
public class CollisionDetector {

    public static List<CalendarInstance> getCollisions(Context activityContext) {
        long timeNow = System.currentTimeMillis();
        long timeEnd = System.currentTimeMillis() + 48*60*60*1000;
        return getCollisions(activityContext, timeNow, timeEnd);
    }

    public static List<CalendarInstance> getCollisions(Context activityContext,long timeEnd) {
        long timeNow = System.currentTimeMillis();
        return getCollisions(activityContext, timeNow, timeEnd);
    }

    public static List<CalendarInstance> getCollisions(Context activityContext, long timeNow, long timeEnd) {
        List<CalendarInstance> collisionList = new ArrayList<CalendarInstance>();

        String[] INSTANCE_PROJECTION =
                new String[]{
                        CalendarContract.Instances._ID,
                        CalendarContract.Instances.BEGIN,
                        CalendarContract.Instances.END,
                        CalendarContract.Instances.TITLE,
                        CalendarContract.Instances.EVENT_ID};
        Cursor cursor =
                CalendarContract.Instances.query(activityContext.getContentResolver(), INSTANCE_PROJECTION, timeNow, timeEnd);


        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String instanceTitle = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE));
            Log.d(LOG_TAG, "Event: " + instanceTitle);
            long instanceStart = Long.valueOf(cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.BEGIN))).longValue();
            long instanceEnd = Long.valueOf(cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.END))).longValue();
            long ID = Long.valueOf(cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.EVENT_ID)));
            collisionList.add(new CalendarInstance(instanceStart, instanceEnd, instanceTitle, ID));
        }
        return collisionList;
    }

    private static final String LOG_TAG = "CollisionDetector";
}
