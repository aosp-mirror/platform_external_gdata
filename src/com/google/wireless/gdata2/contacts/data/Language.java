/**
 * Copyright (C) 2009 The Android Open Source Project
 */
package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.StringUtils;


/**
 * Specifies the preferred languages of the contact. The element
 * can be repeated. The language must be specified using one of
 * two mutually exclusive methods: using the freeform label
 * attribute, or using the code attribute, whose value must
 * conform to the IETF BCP 47 specification. Describes an ID of
 * the contact in an external system of some kind. This element
 * may be repeated.
 */
public class Language {
 
  private String label;
  private String code;

   /**
   * default empty constructor
   */
  public Language() {}
 
   /**
   * constructor that allows initialization
   */
  public Language(String label, String code) {
     setLabel(label);
     setCode(code);
   }

  /**
   * A freeform name of a language. Must not be empty or all 
   * whitespace. 
   */
  public String getLabel() {
      return this.label;
  }
  
  /**
   * A freeform name of a language. Must not be empty or all 
   * whitespace. 
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * A language code conforming to the IETF BCP 47 specification. 
   * The server returns an error if a nonconformant value is 
   * provided. 
   */
  public String getCode() {
      return this.code;
  }
  
  /**
   * A language code conforming to the IETF BCP 47 specification. 
   * The server returns an error if a nonconformant value is 
   * provided. 
   */
  public void setCode(String code) {
    this.code = code;
  }

 
 
  public String toString() {
    StringBuffer sb = new StringBuffer();
    toString(sb);
    return sb.toString();
  }


  public void toString(StringBuffer sb) {
    sb.append("Language");
    if (!StringUtils.isEmpty(code)) {
      sb.append(" code:").append(code);
    }    
    if (!StringUtils.isEmpty(label)) {
      sb.append(" label:").append(label);
    }
   }

  /**
   * A Language either has a code or a label, not both
   */
  public void validate() throws ParseException {
    if ((StringUtils.isEmpty(label) && 
         StringUtils.isEmpty(code)) || 
        (!StringUtils.isEmpty(label) && 
         !StringUtils.isEmpty(code))) {
      throw new ParseException("exactly one of label or code must be set");
    }
  }
}


