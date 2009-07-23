// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.StringUtils;

/**
 * Entry containing information about a contact group.
 */
public class GroupEntry extends Entry {
  // If this is a system group then this field will be set with the name of the system group.
  private String systemGroup = null;

  public GroupEntry() {
    super();
  }

  public String getSystemGroup() {
    return systemGroup;
  }

  public void clear() {
    super.clear();
    systemGroup = null;
  }

  public void setSystemGroup(String systemGroup) {
    this.systemGroup = systemGroup;
  }

  protected void toString(StringBuffer sb) {
    super.toString(sb);
    sb.append("\n");
    sb.append("GroupEntry:");
    if (!StringUtils.isEmpty(systemGroup)) {
      sb.append(" systemGroup:").append(systemGroup).append("\n");
    }
  }
}
