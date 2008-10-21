// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.data;

/**
 * The PostalAddress gdata type
 */
public class PostalAddress extends ContactsElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_OTHER = 3;

  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void toString(StringBuffer sb) {
    sb.append("PostalAddress");
    super.toString(sb);
    if (value != null) sb.append(" value:").append(value);
  }
}
