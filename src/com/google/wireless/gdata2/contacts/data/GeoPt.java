// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

/**
 * The GeoPt GData type.
 */
public class GeoPt {
    private String label;
    private Float latitude;
    private Float longitude;
    private Float elevation;

    /**
     * default empty constructor
     */
    public GeoPt() {}

    // TODO: figure out how to store the GeoPt time
    private String time;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getLatitute() {
        return latitude;
    }

    public void setLatitude(Float lat) {
        this.latitude = lat;
    }

    public Float getLongitute() {
        return longitude;
    }

    public void setLongitude(Float lon) {
        this.longitude = lon;
    }

    public Float getElevation() {
        return elevation;
    }

    public void setElevation(Float elev) {
        this.elevation = elev;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public void toString(StringBuffer sb) {
        sb.append("GeoPt");
        if (latitude != null) sb.append(" latitude:").append(latitude);
        if (longitude != null) sb.append(" longitude:").append(longitude);
        if (elevation != null) sb.append(" elevation:").append(elevation);
        if (time != null) sb.append(" time:").append(time);
        if (label != null) sb.append(" label:").append(label);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }
}
