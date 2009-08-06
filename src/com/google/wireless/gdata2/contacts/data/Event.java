/**
 * Copyright (C) 2009 The Android Open Source Project
 */

package com.google.wireless.gdata2.contacts.data;

import java.util.Date;

import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;


/**
 * These elements describe events associated with a contact. 
 * They may be repeated.
 */
public class Event extends TypedElement {
  public static final byte TYPE_ANNIVERSARY = 1;
  public static final byte TYPE_OTHER = 2;

  private String startDate;
  
  /**
   * default empty constructor
   */
  public Event() {}
  public Event(String startDate, byte type, String label) {
    super(type, label);
    this.startDate = startDate;
  }

  /**
   * StartDate associated with this event
   */
  public String getStartDate() {
      return this.startDate;
  }
  
  /**
   * StartDate associated with this event
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
 
  public void toString(StringBuffer sb) {
    sb.append("Event");
    super.toString(sb);
    sb.append(" date:").append(startDate.toString());
  }
}




