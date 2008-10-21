// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.StringUtils;

/**
 * Contains information about the start and end of an instance of an event.
 */
public class When {
    private final String startTime;
    private final String endTime;

    /**
     * Creates a new When.
     * @param startTime The start of the event.
     * @param endTime The end of the event.
     */
    public When(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the start time for the event.
     * @return The start time for the event.
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time for the event.
     * @return The end time for the event.
     */
    public String getEndTime() {
        return endTime;
    }

    public void toString(StringBuffer sb) {
        if (!StringUtils.isEmpty(startTime)) {
            sb.append("START TIME: " + startTime + "\n");
        }
        if (!StringUtils.isEmpty(endTime)) {
            sb.append("END TIME: " + endTime + "\n");
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }
}
