/**
 * Copyright (C) 2009 The Android Open Source Project
 */

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.StringUtils;

/**
 * Describes an ID of the contact in an external system of some 
 * kind. This element may be repeated.. 
 */
public class ExternalId {
 
  private String label;
  private String value;
  private String type;

  /**
   * A simple string value used to name this ID.
   */
  public String getLabel() {
      return this.label;
  }
  
  /**
   * A simple string value used to name this ID.
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * A programmatic value that identifies the type of external ID
   */
  public String getType() {
      return this.type;
  }
  
  /**
   * A programmatic value that identifies the type of external ID
   */
  public void setRel(String type) {
    this.type = type;
  }

  /**
   * The value of this external ID.
   */
  public String getValue() {
      return this.value;
  }
  
  /**
   * The value of this external ID.
   */
  public void setValue(String value) {
    this.value = value;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);
    return sb.toString();
  }


  public void toString(StringBuffer sb) {
    sb.append("ExternalId");
    if (!StringUtils.isEmpty(type)) {
      sb.append(" type:").append(type);
    }    
    if (!StringUtils.isEmpty(value)) {
      sb.append(" value:").append(value);
    }
    if (!StringUtils.isEmpty(label)) {
      sb.append(" label:").append(label);
    }
   }

  /**
   * Currently empty, will be filled when the parser is done
   * 
   */
  public void validate() throws ParseException {
  }
}

