// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.data.StringUtils;

/**
 * Storage for URL of the contact's calendar. The element can be 
 * repeated. 
 */
public class CalendarLink extends ContactsElement {
  /** The phone number type. */
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_FREE_BUSY = 3;
 
  /**
   * default empty constructor
   */
  public CalendarLink() {}

  /**
   * constructor that allows initialization
   */
  public CalendarLink(String href, byte type, String label, boolean isPrimary) {
    super(type, label, isPrimary);
    setHRef(href);
  }

  private String href;

  /**
   * The URL of the calendar.
   */
  public String getHRef() {
      return this.href;
  }
  
  /**
   * The URL of the calendar.
   */
  public void setHRef(String href) {
    this.href = href;
  }
  
 
  public void toString(StringBuffer sb) {
    sb.append("CalendarLink");
    super.toString(sb);
    if (!StringUtils.isEmpty(href)) {
      sb.append(" href:").append(href);
    }    
   }
}
