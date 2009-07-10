// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

/**
 * Allows storing person's name in a structured way. Consists of 
 * given name, additional name, family name, prefix, suffix and 
 * full name.  
 */
public class Name  {
  
  private String fullName;
  private String nameSuffix;
  private String namePrefix;
  private String familyName;
  private String additionalName;
  private String givenName;
  private String givenNameYomi;
  private String familyNameYomi;
  private String additionalNameYomi;

  /**
   * default empty constructor
   */
  public Name() {}

  /**
   * Getter for givenName, Person's given name.
   */
  public String getGivenName() {
      return this.givenName;
  }
  
  /**
   * Setter for givenName, Person's given name.
   */
  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  /**
   * Getter for addtionalName, Additional name of the person, eg. 
   * middle name. 
   */ 
  public String getAdditionalName() {
      return this.additionalName;
  }
  
  /**
   * Setter for addtionalName, Additional name of the person, eg. 
   * middle name. 
   */
  public void setAdditionalName(String addtionalName) {
    this.additionalName = addtionalName;
  }

  /**
   * Getter for familyName, Person's family name.
   */
  public String getFamilyName() {
      return this.familyName;
  }
  
  /**
   * Setter for familyName, Person's family name.
   */
  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  /**
   * Getter for namePrefix, Honorific prefix, eg. 'Mr' or 'Mrs'.
   */
  public String getNamePrefix() {
      return this.namePrefix;
  }
  
  /**
   * Setter for namePrefix, Honorific prefix, eg. 'Mr' or 'Mrs'.
   */
  public void setNamePrefix(String namePrefix) {
    this.namePrefix = namePrefix;
  }

  /**
   * Getter for nameSuffix, Honorific suffix, eg. 'san' or 'III'.
   */
  public String getNameSuffix() {
      return this.nameSuffix;
  }
  
  /**
   * Setter for nameSuffix, Honorific suffix, eg. 'san' or 'III'.
   */
  public void setNameSuffix(String nameSuffix) {
    this.nameSuffix = nameSuffix;
  }

  /**
   * Getter for fullName, Unstructured representation of the name.
   */
  public String getFullName() {
      return this.fullName;
  }
  
  /**
   * Setter for fullName, Unstructured representation of the name.
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Getter for addtionalNameYomi, Phonetic representation
   */
  public String getAdditionalNameYomi() {
      return this.additionalNameYomi;
  }
  
  /**
   * Setter for addtionalNameYomi, Phonetic representation
   */
  public void setAdditionalNameYomi(String addtionalNameYomi) {
    this.additionalNameYomi = addtionalNameYomi;
  }

  /**
   * Getter for familyNameYomi, Phonetic representation
   */
  public String getFamilyNameYomi() {
      return this.familyNameYomi;
  }
  
  /**
   * Setter for familyNameYomi, Phonetic representation
   */
  public void setFamilyNameYomi(String familyNameYomi) {
    this.familyNameYomi = familyNameYomi;
  }

  /**
   * Getter for givenNameYomi, Phonetic representation
   */
  public String getGivenNameYomi() {
      return this.givenNameYomi;
  }
  
  /**
   * Setter for givenNameYomi, Phonetic representation
   */
  public void setGivenNameYomi(String givenNameYomi) {
    this.givenNameYomi = givenNameYomi;
  }

  public void toString(StringBuffer sb) {
    sb.append("Name");
    if (fullName != null) sb.append(" fullName:").append(fullName);
    if (nameSuffix != null) sb.append(" nameSuffix:").append(nameSuffix);
    if (namePrefix != null) sb.append(" namePrefix:").append(namePrefix);
    if (familyName != null) sb.append(" familyName:").append(familyName);
    if (additionalName != null) sb.append(" additionalName:").append(additionalName);
    if (givenName != null) sb.append(" givenName:").append(givenName);
    if (givenNameYomi != null) sb.append(" givenNameYomi:").append(givenNameYomi);
    if (familyNameYomi != null) sb.append(" familyNameYomi:").append(familyNameYomi);
    if (additionalNameYomi != null) sb.append(" additionalNameYomi:").append(additionalNameYomi);
  }
}
