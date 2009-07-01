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
public class Event {
 
  private String label;
  private String type;
  private String startDate;
  
  /**
   * default empty constructor
   */
  public Event() {}


  /**
   * A simple string value used to name this event. It allows UIs to 
   * display a label such as "Start Date". May not be empty or all 
   * whitespace. 
   */
  public String getLabel() {
      return this.label;
  }
  
  /**
   * A simple string value used to name this event. It allows UIs to 
   * display a label such as "Start Date". May not be empty or all 
   * whitespace. 
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * A programmatic value that identifies the type of event.
   */
  public String getType() {
      return this.type;
  }
  
  /**
   * A programmatic value that identifies the type of event.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Contacts supports an xs:date value here 
   * Describes when the event starts.
   */
  public String getStartDate() {
      return this.startDate;
  }
  
  /**
   * Contacts supports an xs:date value here 
   * Describes when the event starts.
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
 
  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);
    return sb.toString();
  }


  public void toString(StringBuffer sb) {
    sb.append("ExternalId");
    sb.append(" date:").append(startDate.toString());
    if (!StringUtils.isEmpty(label)) {
      sb.append(" label:").append(label);
    }
    if (!StringUtils.isEmpty(type)) {
      sb.append(" type:").append(type);
    }
  }

  /**
   * Currently empty, will be filled when the parser is done
   * 
   */
  public void validate() throws ParseException {
  }
}




