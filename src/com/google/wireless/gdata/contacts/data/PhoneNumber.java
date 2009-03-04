// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.data;

/**
 * The PhoneNumber gdata type
 */
public class PhoneNumber extends ContactsElement {
  /** The phone number type. */
  public static final byte TYPE_MOBILE = 1;
  public static final byte TYPE_HOME = 2;
  public static final byte TYPE_WORK = 3;
  public static final byte TYPE_WORK_FAX = 4;
  public static final byte TYPE_HOME_FAX = 5;
  public static final byte TYPE_PAGER = 6;
  public static final byte TYPE_OTHER = 7;

  private String phoneNumber;

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void toString(StringBuffer sb) {
    sb.append("PhoneNumber");
    super.toString(sb);
    if (phoneNumber != null) sb.append(" phoneNumber:").append(phoneNumber);
  }
}
