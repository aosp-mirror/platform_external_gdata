package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.parser.ParseException;

/** The Organization GData type. */
public class Organization extends ContactsElement {
  public static final byte TYPE_WORK = 1;
  public static final byte TYPE_OTHER = 2;

  private String name;
  private String title;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void toString(StringBuffer sb) {
    sb.append("Organization");
    super.toString(sb);
    if (name != null) sb.append(" name:").append(name);
    if (title != null) sb.append(" title:").append(title);
  }

  public void validate() throws ParseException {
    super.validate();

    if (name == null && title == null) {
      throw new ParseException("at least one of name or title must be present");
    }
  }
}
