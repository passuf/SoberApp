package soberapp.vis.ethz.ch.soberapp;

import java.util.Date;

/**
 * Created by karl on 08/11/14.
 */

public class CalendarInstance {

    public CalendarInstance(long startTime, long endTime, String eventTitle, long ID) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventTitle = eventTitle;
        this.ID = ID;
    }

    public Date getEventStart(){
        return new Date(startTime);
    }

    public Date getEventEnd(){
        return new Date(endTime);
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    /* start and end time in ms since epoch */
    private long startTime;
    private long endTime;

    /* event title */
    private String eventTitle;

    private long ID;

    public long getID() {
        return ID;
    }
}