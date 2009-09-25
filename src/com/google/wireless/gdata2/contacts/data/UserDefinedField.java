/**
 * Copyright (C) 2007 The Android Open Source Project
 */

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.StringUtils;


/**
 * Represents an arbitrary key-value pair attached to the contact.
 */
public class UserDefinedField {
 
  private String key;
  private String value;
 

  /** 
   * default empty constructor
   */
  public UserDefinedField() {}

  /**
   * Default constructor
   */
  public UserDefinedField(String key, String value)
  {
    this.key = key;
    this.value = value;
  }


  /**
   * A simple string value used to name this field. 
   * Case-sensitive 
   */
  public String getKey() {
      return this.key;
  }
  
  /**
   * A simple string value used to name this field. Case-sensitive
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * The value of this field.
   */
  public String getValue() {
      return this.value;
  }
  
  /**
   * The value of this field.
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
    sb.append("UserDefinedField");
    if (!StringUtils.isEmpty(key)) {
      sb.append(" key:").append(key);
    }    
    if (!StringUtils.isEmpty(value)) {
      sb.append(" value:").append(value);
    }
   }

  /**
   * Currently empty, will be filled when the parser is done
   * 
   */
  public void validate() throws ParseException {
    if (StringUtils.isEmpty(key)) {
      throw new ParseException("key has to be set");
    }
  }
}



