// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.data;

/**
 * Container for information about a Recurrence.
 */
// TODO: get rid of this?
public class Recurrence {
    
    private final String recurrence;
    
    /**
     * Creates a new recurrence for the provide recurrence string.
     * @param recurrence The recurrence string that should be parsed.
     */
    public Recurrence(String recurrence) {
        this.recurrence = recurrence;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return recurrence;
    }
}
