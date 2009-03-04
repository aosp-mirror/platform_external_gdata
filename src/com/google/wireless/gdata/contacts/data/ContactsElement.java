package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.parser.ParseException;

/**
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Contains attributes that are common to all elements in a ContactEntry.
 */
public abstract class ContactsElement {
  public static final byte TYPE_NONE = -1;
  private byte type = TYPE_NONE;

  private String label;

  private boolean isPrimary;

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(boolean primary) {
    isPrimary = primary;
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
    sb.append(" isPrimary:").append(isPrimary);
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
