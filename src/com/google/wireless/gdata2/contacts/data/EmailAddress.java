// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

/**
 * The EmailAddress GData type.
 */
public class EmailAddress extends ContactsElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_OTHER = 3;

  private String address;
  private String displayName;

  /**
   * default empty constructor
   */
  public EmailAddress() {}
  public EmailAddress(String address, String displayName,
      byte type, String label, boolean isPrimary) {
    super(type, label, isPrimary);
    this.address = address;
    this.displayName = displayName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Getter for displayName
   */
  public String getDisplayName() {
      return this.displayName;
  }
  
  /**
   * Setter for displayName
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void toString(StringBuffer sb) {
    sb.append("EmailAddress");
    super.toString(sb);
    if (address != null) sb.append(" address:").append(address);
    if (displayName != null) sb.append(" displayName:").append(displayName);
  }
}
