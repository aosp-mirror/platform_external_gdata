/**
 * Copyright (C) 2007 The Android Open Source Project
 */

package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.StringUtils;


/**
 * Specifies the preferred languages of the contact. The element
 * can be repeated.* The language must be specified using one of
 * two mutually exclusive methods: using the freeform label
 * attribute, or using the code attribute, whose value must
 * conform to the IETF BCP 47 specification. Describes an ID of
 * the contact in an external system of some kind. This element
 * may be repeated..
 */
public class Relation {
 
  private String label;
  private String type;
  private String text;
 
  /**
   * default empty constructor
   */
  public Relation() {}
  
  /**
   * A simple string value used to name this relation. 
   * The value must not be empty or all whitespace 
   */
  public String getLabel() {
      return this.label;
  }
  
  /**
   * A simple string value used to name this relation. 
   * The value must not be empty or all whitespace 
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * A programmatic value that identifies the type of relation.
   */
  public String getType() {
      return this.type;
  }
  
  /**
   * A programmatic value that identifies the type of relation.
   */
  public void setType(String type) {
    this.type = type;
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
 
 
  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);
    return sb.toString();
  }


  public void toString(StringBuffer sb) {
    sb.append("ExternalId");
    if (!StringUtils.isEmpty(text)) {
      sb.append(" text:").append(text);
    }    
    if (!StringUtils.isEmpty(label)) {
      sb.append(" label:").append(label);
    }
    if (!StringUtils.isEmpty(type)) {
      sb.append(" type:").append(type);
    }
  }

  /**
   * Currently empty, will be filled when the parser is done
   * 
   */
  public void validate() throws ParseException {
  }
}



