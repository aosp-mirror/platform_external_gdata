/**
 * Copyright (C) 2009 The Android Open Source Project
 */
package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;


/**
 * Contains attributes that are common to all elements in a ContactEntry.
 */
public abstract class TypedElement {
  public static final byte TYPE_NONE = -1;
  private byte type = TYPE_NONE;

  private String label;

  public TypedElement() {}
  public TypedElement(byte type, String label) {
    this.type = type;
    this.label = label;
  }

  public byte getType() {
    return type;
  }

  public void setType(byte rel) {
    this.type = rel;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void toString(StringBuffer sb) {
    sb.append(" type:").append(type);
    if (label != null) sb.append(" label:").append(label);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);
    return sb.toString();
  }

  public void validate() throws ParseException {
    if ((label == null && type == TYPE_NONE) || (label != null && type != TYPE_NONE)) {
      throw new ParseException("exactly one of label or type must be set");
    }
  }
}
