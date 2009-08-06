/**
 * Copyright (C) 2009 The Android Open Source Project
 */
package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;


/**
 * Contains attributes that are common to all elements in a ContactEntry.
 */
public abstract class ContactsElement extends TypedElement {
  private boolean isPrimary;

  public ContactsElement() {}
  public ContactsElement(byte type, String label, boolean isPrimary) {
    super(type, label);
    this.isPrimary = isPrimary;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(boolean primary) {
    isPrimary = primary;
  }

  public void toString(StringBuffer sb) {
    super.toString(sb);
    sb.append(" isPrimary:").append(isPrimary);
  }
}
