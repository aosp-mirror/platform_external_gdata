// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.data;

/**
 * The EmailAddress GData type.
 */
public class EmailAddress extends ContactsElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_OTHER = 3;

  private String address;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void toString(StringBuffer sb) {
    sb.append("EmailAddress");
    super.toString(sb);
    if (address != null) sb.append(" address:").append(address);
  }
}
