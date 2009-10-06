/**
 * Copyright (C) 2009 The Android Open Source Project
 */

package com.google.wireless.gdata2.contacts.data;

/** The Organization GData type. */
public class Organization extends ContactsElement {
  public static final byte TYPE_WORK = 1;
  public static final byte TYPE_OTHER = 2;

  private String name;
  private String nameYomi;
  private String title;
  private String orgDepartment;
  private String orgJobDescription;
  private String orgSymbol;
  private String where;

  /**
   * default empty constructor
   */
  public Organization() {}
  public Organization(String name, String nameYomi, String title, String orgDepartment,
          String orgJobDescription, String orgSymbol, String where,
        byte type, String label, boolean isPrimary) {
      super(type, label, isPrimary);
      this.name = name;
      this.nameYomi = nameYomi;
      this.title = title;
      this.orgDepartment = orgDepartment;
      this.orgJobDescription = orgJobDescription;
      this.orgSymbol = orgSymbol;
      this.where = where;
  }
 
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for nameYomi
   */
  public String getNameYomi() {
      return this.nameYomi;
  }
  
  /**
   * Setter for nameYomi
   */
  public void setNameYomi(String nameYomi) {
    this.nameYomi = nameYomi;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Getter for orgDepartment
   */
  public String getOrgDepartment() {
      return this.orgDepartment;
  }
  
  /**
   * Setter for orgDepartment
   */
  public void setOrgDepartment(String orgDepartment) {
    this.orgDepartment = orgDepartment;
  }

  /**
   * Getter for orgJobDescription
   */
  public String getOrgJobDescription() {
      return this.orgJobDescription;
  }

  /**
   * Setter for orgJobDescription
   */
  public void setOrgJobDescription(String orgJobDescription) {
    this.orgJobDescription = orgJobDescription;
  }

  /**
   * Getter for orgSymbol
   */
  public String getOrgSymbol() {
      return this.orgSymbol;
  }
  
  /**
   * Setter for orgSymbol
   */
  public void setOrgSymbol(String orgSymbol) {
    this.orgSymbol = orgSymbol;
  }

  /**
   * A place associated with the organization, e.g. office 
   * location. In Contacts, this is just a string value.
   */
  public String getWhere() {
      return this.where;
  }
  
  /**
  * A place associated with the organization, e.g. office 
   * location. In Contacts, this is just a string value.
   */
  public void setWhere(String where) {
    this.where = where;
  }

  public void toString(StringBuffer sb) {
    sb.append("Organization");
    super.toString(sb);
    if (name != null) sb.append(" name:").append(name);
    if (title != null) sb.append(" title:").append(title);
    if (orgDepartment != null) sb.append(" orgDepartment:").append(orgDepartment);
    if (orgJobDescription != null) sb.append(" orgJobDescription:").append(orgJobDescription);
    if (orgSymbol != null) sb.append(" orgSymbol:").append(orgSymbol);
    if (nameYomi != null) sb.append(" nameYomi:").append(nameYomi);
 }
}
