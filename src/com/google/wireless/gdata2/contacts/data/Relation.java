/**
 * Copyright (C) 2007 The Android Open Source Project
 */

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.StringUtils;


/**
 * This element describe another entity (usually a person) 
 * that is in a relation of some kind with the contact
 */
public class Relation extends TypedElement {
  public static final byte TYPE_ASSISTANT = 1;
  public static final byte TYPE_BROTHER = 2;
  public static final byte TYPE_CHILD = 3;
  public static final byte TYPE_DOMESTICPARTNER = 4;
  public static final byte TYPE_FATHER = 5;
  public static final byte TYPE_FRIEND = 6;
  public static final byte TYPE_MANAGER = 7;
  public static final byte TYPE_MOTHER = 8;
  public static final byte TYPE_PARENT = 9;
  public static final byte TYPE_PARTNER = 10;
  public static final byte TYPE_REFERREDBY = 11;
  public static final byte TYPE_RELATIVE = 12;
  public static final byte TYPE_SISTER = 13;
  public static final byte TYPE_SPOUSE = 14;

  private String text;
 
  /**
   * default empty constructor
   */
  public Relation() {}
  public Relation(String text, byte type, String label) {
    super(type, label);
    this.text = text;
  }

 
  
  /**
   * The entity in relation with the contact.
   */
  public String getText() {
      return this.text;
  }
  
  /**
   * The entity in relation with the contact.
   */
  public void setText(String text) {
    this.text = text;
  }
 
 
   public void toString(StringBuffer sb) {
    sb.append("Relation");
    super.toString(sb);
    if (!StringUtils.isEmpty(text)) {
      sb.append(" text:").append(text);
    }    
  }
}



