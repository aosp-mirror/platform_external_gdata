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
public class ExternalId extends TypedElement {
  public static final byte TYPE_ACCOUNT = 1;
  public static final byte TYPE_CUSTOMER = 2;
  public static final byte TYPE_NETWORK = 3;
  public static final byte TYPE_ORGANIZATION = 4;


  private String value;

  /**
   * default empty constructor
   */
  public ExternalId() {}

  /**
   * constructor that allows initialization
   */
  public ExternalId(String value, byte type, String label) {
    super(type, label);
    setValue(value);
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

  public void toString(StringBuffer sb) {
    sb.append("ExternalId");
    super.toString(sb);
    if (!StringUtils.isEmpty(value)) {
      sb.append(" value:").append(value);
   }
  }

  /**
   * override default behaviour, an externalId has its own rules for type and label
   */
  public void validate() throws ParseException {
    if (value == null) {
      throw new ParseException("the value must be set");
    }
  }
}

