// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.Entry;

/**
 * Entry containing information about a calendar.
 */
public class CalendarEntry extends Entry {
    
    /**
     * Access level constant indicating the user has no access to a calendar.
     */
    public static final byte ACCESS_NONE = 0;
    
    /**
     * Access level constant indicating the user can read (but not write) to
     * a calendar. 
     */
    public static final byte ACCESS_READ = 1;
    
    /**
     * Access level constant indicating the user can only view free-busy 
     * information for a calendar.
     */
    public static final byte ACCESS_FREEBUSY = 2;
    
    /**
     * Access level constant indicating the user can edit this calendar.
     */
    public static final byte ACCESS_EDITOR = 3;
    
    /**
     * Access level constant indicating the user owns this calendar.
     */
    public static final byte ACCESS_OWNER = 4;

    /**
     * Access level constant indicating the user is a domain admin.
     */
    public static final byte ACCESS_ROOT = 5;
    
    private byte accessLevel = ACCESS_READ;
    // TODO: rename to feed Url?
    private String alternateLink = null;
    private String color = null;
    private boolean hidden = false;
    private boolean selected = true;
    private String timezone = null;
    
    /**
     * Creates a new, empty calendar entry.
     */
    public CalendarEntry() {
    }

    public void clear() {
        super.clear();
        accessLevel = ACCESS_READ;
        alternateLink = null;
        color = null;
        hidden = false;
        selected = true;
        timezone = null;
    }

    /**
     * @return the accessLevel
     */
    public byte getAccessLevel() {
        return accessLevel;
    }

    /**
     * @param accessLevel the accessLevel to set
     */
    public void setAccessLevel(byte accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * @return the alternateLink
     */
    public String getAlternateLink() {
        return alternateLink;
    }

    /**
     * @param alternateLink the alternateLink to set
     */
    public void setAlternateLink(String alternateLink) {
        this.alternateLink = alternateLink;
    }
    
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
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
    
    public void toString(StringBuffer sb) {
        sb.append("ACCESS LEVEL: ");
        sb.append(accessLevel);
        sb.append('\n');
        appendIfNotNull(sb, "ALTERNATE LINK", alternateLink);
        appendIfNotNull(sb, "COLOR", color);
        sb.append("HIDDEN: ");
        sb.append(hidden);
        sb.append('\n');
        sb.append("SELECTED: ");
        sb.append(selected);
        sb.append('\n');
        appendIfNotNull(sb, "TIMEZONE", timezone);
    }
}
