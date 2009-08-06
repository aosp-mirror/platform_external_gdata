// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.data.StringUtils;

/**
 * Describes websites associated with the contact, including links. 
 * May be repeated. 
 */
public class WebSite extends ContactsElement {
  /** The phone number type. */
  public static final byte TYPE_HOMEPAGE = 1;
  public static final byte TYPE_BLOG = 2;
  public static final byte TYPE_PROFILE = 3;
  public static final byte TYPE_HOME = 4;
  public static final byte TYPE_WORK = 5;
  public static final byte TYPE_OTHER = 6;
  public static final byte TYPE_FTP = 7;
  
  private String href;

  /**
   * default empty constructor
   */
  public WebSite() {}
  public WebSite(String href, byte type, String label, boolean isPrimary) {
    super(type, label, isPrimary);
    this.href = href;
  }

  /**
   * The URL of the website.
   */
  public String getHRef() {
      return this.href;
  }
  
  /**
   * The URL of the website.
   */
  public void setHRef(String href) {
    this.href = href;
  }
  
 
  public void toString(StringBuffer sb) {
    sb.append("WebSite");
    super.toString(sb);
    if (!StringUtils.isEmpty(href)) {
      sb.append(" href:").append(href);
    }    
   }
}

