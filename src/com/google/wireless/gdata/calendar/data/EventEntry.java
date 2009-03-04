// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.Entry;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

/**
 * Entry containing information about an event in a calendar.
 */
public class EventEntry extends Entry {

    // TODO: pack all of these enums into an int

    /**
     * Status constant indicating that a user's attendance at an event is
     * tentative.
     */
    public static final byte STATUS_TENTATIVE = 0;

    /**
     * Status constant indicating that a user's attendance at an event is
     * confirmed.
     */
    public static final byte STATUS_CONFIRMED = 1;

    /**
     * Status constant indicating that an event has been cancelled.
     */
    public static final byte STATUS_CANCELED = 2;

    /**
     * Visibility constant indicating that an event uses the user's default
     * visibility.
     */
    public static final byte VISIBILITY_DEFAULT = 0;

    /**
     * Visibility constant indicating that an event has been marked
     * confidential.
     */
    public static final byte VISIBILITY_CONFIDENTIAL = 1;

    /**
     * Visibility constant indicating that an event has been marked private.
     */
    public static final byte VISIBILITY_PRIVATE = 2;

    /**
     * Visibility constant indicating that an event has been marked public.
     */
    public static final byte VISIBILITY_PUBLIC = 3;

    /**
     * Transparency constant indicating that an event has been marked opaque.
     */
    public static final byte TRANSPARENCY_OPAQUE = 0;

    /**
     * Transparency constant indicating that an event has been marked
     * transparent.
     */
    public static final byte TRANSPARENCY_TRANSPARENT = 1;

    private byte status = STATUS_TENTATIVE;
    private String recurrence = null;
    private byte visibility = VISIBILITY_DEFAULT;
    private byte transparency = TRANSPARENCY_OPAQUE;
    private Vector attendees = new Vector();
    private Vector whens = new Vector();
    private Vector reminders = null;
    private String originalEventId = null;
    private String originalEventStartTime = null;
    private String where = null;
    private String commentsUri = null;
    private Hashtable extendedProperties = null;

    /**
     * Creates a new empty event entry.
     */
    public EventEntry() {
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.data.Entry#clear()
     */
    public void clear() {
        super.clear();
        status = STATUS_TENTATIVE;
        recurrence = null;
        visibility = VISIBILITY_DEFAULT;
        transparency = TRANSPARENCY_OPAQUE;
        attendees.removeAllElements();
        whens.removeAllElements();
        reminders = null;
        originalEventId = null;
        originalEventStartTime = null;
        where = null;
        commentsUri = null;
        extendedProperties = null;
    }

    /**
     * @return the recurrence
     */
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * @param recurrence the recurrence to set
     */
    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    /**
     * @return the status
     */
    public byte getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(byte status) {
        this.status = status;
    }

    /**
     * @return the transparency
     */
    public byte getTransparency() {
        return transparency;
    }

    /**
     * @param transparency the transparency to set
     */
    public void setTransparency(byte transparency) {
        this.transparency = transparency;
    }

    /**
     * @return the visibility
     */
    public byte getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(byte visibility) {
        this.visibility = visibility;
    }

    public void clearAttendees() {
        attendees.clear();
    }

    public void addAttendee(Who attendee) {
        attendees.add(attendee);
    }

    public Vector getAttendees() {
        return attendees;
    }

    public void clearWhens() {
        whens.clear();
    }

    public void addWhen(When when) {
        whens.add(when);
    }

    public Vector getWhens() {
        return whens;
    }

    public When getFirstWhen() {
        if (whens.isEmpty()) {
            return null;
        }
        return (When) whens.elementAt(0);
    }

    public Vector getReminders() {
        return reminders;
    }

    public void addReminder(Reminder reminder) {
        if (reminders == null) {
            reminders = new Vector();
        }
        reminders.add(reminder);
    }

    public void clearReminders() {
        reminders = null;
    }

    public String getOriginalEventId() {
        return originalEventId;
    }

    public void setOriginalEventId(String originalEventId) {
        this.originalEventId = originalEventId;
    }

    public String getOriginalEventStartTime() {
        return originalEventStartTime;
    }

    public void setOriginalEventStartTime(String originalEventStartTime) {
        this.originalEventStartTime = originalEventStartTime;
    }

    /**
     * @return the where
     */
    public String getWhere() {
        return where;
    }

    /**
     * @param where the where to set
     */
    public void setWhere(String where) {
        this.where = where;
    }

    public Hashtable getExtendedProperties() {
        return extendedProperties;
    }

    public String getExtendedProperty(String name) {
        if (extendedProperties == null) {
            return null;
        }
        String value = null;
        if (extendedProperties.containsKey(name)) {
            value = (String) extendedProperties.get(name);
        }
        return value;
    }

    public void addExtendedProperty(String name, String value) {
        if (extendedProperties == null) {
            extendedProperties = new Hashtable();
        }
        extendedProperties.put(name, value);
    }

    public void clearExtendedProperties() {
        extendedProperties = null;
    }

    public String getCommentsUri() {
        return commentsUri;
    }

    public void setCommentsUri(String commentsUri) {
        this.commentsUri = commentsUri;
    }

    public void toString(StringBuffer sb) {
        super.toString(sb);
        sb.append("STATUS: " + status + "\n");
        appendIfNotNull(sb, "RECURRENCE", recurrence);
        sb.append("VISIBILITY: " + visibility + "\n");
        sb.append("TRANSPARENCY: " + transparency + "\n");
        
        appendIfNotNull(sb, "ORIGINAL_EVENT_ID", originalEventId);
        appendIfNotNull(sb, "ORIGINAL_START_TIME", originalEventStartTime);

        Enumeration whos = this.attendees.elements();
        while (whos.hasMoreElements()) {
            Who who = (Who) whos.nextElement();
            who.toString(sb);
        }

        Enumeration times = this.whens.elements();
        while (times.hasMoreElements()) {
            When when = (When) times.nextElement();
            when.toString(sb);
        }
        if (reminders != null) {
            Enumeration alarms = reminders.elements();
            while (alarms.hasMoreElements()) {
                Reminder reminder = (Reminder) alarms.nextElement();
                reminder.toString(sb);
            }
        }
        appendIfNotNull(sb, "WHERE", where);
        appendIfNotNull(sb, "COMMENTS", commentsUri);
        if (extendedProperties != null) {
            Enumeration entryNames = extendedProperties.keys();
            while (entryNames.hasMoreElements()) {
                String name = (String) entryNames.nextElement();
                String value = (String) extendedProperties.get(name);
                sb.append(name);
                sb.append(':');
                sb.append(value);
                sb.append('\n');
            }
        }
    }
}
