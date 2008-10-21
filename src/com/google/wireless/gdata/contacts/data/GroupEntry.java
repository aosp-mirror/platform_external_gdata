package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;

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

  @Override
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
