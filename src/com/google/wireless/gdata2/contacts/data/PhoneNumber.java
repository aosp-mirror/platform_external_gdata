// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

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
  public static final byte TYPE_ASSISTANT = 7;
  public static final byte TYPE_CALLBACK = 8;
  public static final byte TYPE_CAR = 9;
  public static final byte TYPE_COMPANY_MAIN = 10;
  public static final byte TYPE_ISDN = 11;
  public static final byte TYPE_MAIN = 12;
  public static final byte TYPE_OTHER_FAX = 13;
  public static final byte TYPE_RADIO = 14;
  public static final byte TYPE_TELEX = 15;
  public static final byte TYPE_TTY_TDD = 16;
  public static final byte TYPE_WORK_MOBILE = 17;
  public static final byte TYPE_WORK_PAGER = 18;
   public static final byte TYPE_OTHER = 19;

  private String phoneNumber;

  /**
   * default empty constructor
   */
  public PhoneNumber() {}
  public PhoneNumber(String phoneNumber, byte type, String label, boolean isPrimary) {
    super(type, label, isPrimary);
    this.phoneNumber = phoneNumber;
  }
  

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
