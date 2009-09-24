/**
 * Copyright (C) 2009 The Android Open Source Project
 */
package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.StringUtils;


/**
 * Storage for arbitrary pieces of information about the 
 * contact. Each jot has a type specified by the rel attribute 
 * and a text value. The element can be repeated. 
 */
public class Jot extends TypedElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_KEYWORDS = 3;
  public static final byte TYPE_USER = 4;
  public static final byte TYPE_OTHER = 5;

  private String value;

  /**
   * default empty constructor
   */
  public Jot() {}

  /**
   * constructor that allows initialization
   */
  public Jot(String value, byte type, String label) {
    super(type, label);
    setValue(value);
  }

  /**
   * override default behaviour, a jot is not relying on either 
   * label or type 
   */
  public void validate() throws ParseException {}

  /**
   * The value of this Jot
   */
  public String getValue() {
      return this.value;
  }

  /**
   * The value of this Jot.
   */
  public void setValue(String value) {
    this.value = value;
  }

  public void toString(StringBuffer sb) {
    sb.append("Jot");
    super.toString(sb);
    if (!StringUtils.isEmpty(value)) {
      sb.append(" value:").append(value);
    }
  }
}
