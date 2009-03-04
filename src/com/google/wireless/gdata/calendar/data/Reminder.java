// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.calendar.data;

/**
 * Contains information about a reminder for an event.
 */
public class Reminder {
    /**
     * Default reminder method as defined on the calendar server.
     */
    public static final byte METHOD_DEFAULT = 0;

    /**
     * Reminder that uses e-mail for notification.
     */
    public static final byte METHOD_EMAIL = 1;

    /**
     * Reminder that uses sms for notification.
     */
    public static final byte METHOD_SMS = 2;

    /**
     * Reminder that uses a local alert for notification.
     */
    public static final byte METHOD_ALERT = 3;

    /**
     * Reminder that uses a calendar-wide default time for the alarm.
     */
    public static final int MINUTES_DEFAULT = -1;    

    // do absolute times work with recurrences?
    // private String absoluteTime;
    private int minutes = MINUTES_DEFAULT;
    private byte method = METHOD_DEFAULT;

    /**
     * Creates a new empty reminder.
     */
    public Reminder() {
    }

    /**
     * Returns the method of the reminder.
     * @return The method of the reminder.
     */
    public byte getMethod() {
        return method;
    }

    /**
     * Sets the method of the reminder.
     * @param method The method of the reminder.
     */
    public void setMethod(byte method) {
        this.method = method;
    }

    /**
     * Gets how many minutes before an event that the reminder should be
     * triggered.
     * @return How many minutes before an event that the reminder should be
     * triggered.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets how many minutes before an event that the reminder should be
     * triggered.
     * @param minutes How many minutes before an event that the reminder should
     * be triggered.
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void toString(StringBuffer sb) {
        sb.append("REMINDER MINUTES: " + minutes);
        sb.append("\n");
        sb.append("REMINDER METHOD: " + method);
        sb.append("\n");
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }
}
