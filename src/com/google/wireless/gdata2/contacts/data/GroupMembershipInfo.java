// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;

/** The groupMembershipInfo GData type. */
public class GroupMembershipInfo {
  private String group;
  private boolean deleted;

  /**
   * default empty constructor
   */
  public GroupMembershipInfo() {}

  /**
   * constructor that allows initializing the GroupMembershipInfo
   */
  public GroupMembershipInfo(String groupId, boolean deleted) {
    setGroup(groupId);
    setDeleted(deleted);
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public void toString(StringBuffer sb) {
    sb.append("GroupMembershipInfo");
    if (group != null) sb.append(" group:").append(group);
    sb.append(" deleted:").append(deleted);
  }

  public void validate() throws ParseException {
    if (StringUtils.isEmpty(group)) {
      throw new ParseException("the group must be present");
    }
  }
}
