// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.data;

import com.google.wireless.gdata2.parser.ParseException;

/**
 * The extendedProperty gdata type
 * Allows you to store a limited amount of custom data as an auxiliary
 * property of the enclosing entity. 
 * Note that the presence of anyForeignElement allows feed to optionally 
 * embed any valid XML within gd:extendedProperty element 
 * (mutually exclusive with value attribute).
 */
public class ExtendedProperty {
  private String name;
  private String value;
  private String xmlBlob;

  public ExtendedProperty() {}
  public ExtendedProperty(String name, String value, String xmlBlob) {
      this.name = name;
      this.value = value;
      this.xmlBlob = xmlBlob;
  }

  /** 
   * Returns the xml embedded inside the extended property 
   * element 
   * Mutually exclusive with the value property 
   * @return the xml as a string 
   */  
  public String getXmlBlob() {
    return xmlBlob;
  }

  /** 
   * Sets the embedded xml for the extended property element. 
   * Mutually exclusive with the value property
   * @param xmlBlob xml as a string
   */
  public void setXmlBlob(String xmlBlob) {
    this.xmlBlob = xmlBlob;
  }

  /**
   * @return the name of the extended property expressed as a URI. Extended  
   * property URIs usually follow the {scheme}#{local-name} convention. 
   */
  public String getName() {
    return name;
  }

  /**
   * @param name set's the name of the extended property
   */
  public void setName(String name) {
    this.name = name;
  }

  /** 
   * Returns the value attribute of the extended property 
   * element.Mutually exclusive with the xmlBlob property 
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /** 
   * Sets the value attribute of the extended property. Mutually
   * exclusive with the xmlBlog property 
   * @param value 
   */
  public void setValue(String value) {
    this.value = value;
  }

  public void toString(StringBuffer sb) {
    sb.append("ExtendedProperty");
    if (name != null) sb.append(" name:").append(name);
    if (value != null) sb.append(" value:").append(value);
    if (xmlBlob != null) sb.append(" xmlBlob:").append(xmlBlob);
  }

  public void validate() throws ParseException {
    if (name == null) {
      throw new ParseException("name must not be null");
    }

    if ((value == null && xmlBlob == null) || (value != null && xmlBlob != null)) {
      throw new ParseException("exactly one of value and xmlBlob must be present");
    }
  }
}
