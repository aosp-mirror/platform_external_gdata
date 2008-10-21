// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.Feed;

/**
 * Feed containing events in a calendar.
 */
public class EventsFeed extends Feed {
    
    private String timezone = null;
    
    /**
     * Creates a new empty events feed.
     */
    public EventsFeed() {
    }

    /**
     * @return the timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone the timezone to set
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
