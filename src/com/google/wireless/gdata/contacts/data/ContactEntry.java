// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.data;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.ExtendedProperty;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;

import java.util.Vector;
import java.util.Enumeration;

/**
 * Entry containing information about a contact.
 */
public class ContactEntry extends Entry {
  private String linkPhotoHref;
  private String linkEditPhotoHref;
  private String linkPhotoType;
  private String linkEditPhotoType;
  private final Vector emailAddresses = new Vector();
  private final Vector imAddresses = new Vector();
  private final Vector phoneNumbers = new Vector();
  private final Vector postalAddresses = new Vector();
  private final Vector organizations = new Vector();
  private final Vector extendedProperties = new Vector();
  private final Vector groups = new Vector();
  private String yomiName;

  public ContactEntry() {
    super();
  }

  public void setLinkEditPhoto(String href, String type) {
    this.linkEditPhotoHref = href;
    this.linkEditPhotoType = type;
  }

  public String getLinkEditPhotoHref() {
    return linkEditPhotoHref;
  }

  public String getLinkEditPhotoType() {
    return linkEditPhotoType;
  }

  public void setLinkPhoto(String href, String type) {
    this.linkPhotoHref = href;
    this.linkPhotoType = type;
  }

  public String getLinkPhotoHref() {
    return linkPhotoHref;
  }

  public String getLinkPhotoType() {
    return linkPhotoType;
  }

  public void addEmailAddress(EmailAddress emailAddress) {
    emailAddresses.addElement(emailAddress);
  }

  public Vector getEmailAddresses() {
    return emailAddresses;
  }

  public void addImAddress(ImAddress imAddress) {
    imAddresses.addElement(imAddress);
  }

  public Vector getImAddresses() {
    return imAddresses;
  }

  public void addPostalAddress(PostalAddress postalAddress) {
    postalAddresses.addElement(postalAddress);
  }

  public Vector getPostalAddresses() {
    return postalAddresses;
  }

  public void addPhoneNumber(PhoneNumber phoneNumber) {
    phoneNumbers.addElement(phoneNumber);
  }

  public Vector getPhoneNumbers() {
    return phoneNumbers;
  }

  public void addOrganization(Organization organization) {
    organizations.addElement(organization);
  }

  public Vector getExtendedProperties() {
    return extendedProperties;
  }

  public void addExtendedProperty(ExtendedProperty extendedProperty) {
    extendedProperties.addElement(extendedProperty);
  }

  public Vector getGroups() {
    return groups;
  }

  public void addGroup(GroupMembershipInfo group) {
    groups.addElement(group);
  }

  public Vector getOrganizations() {
    return organizations;
  }

  public void setYomiName(String yomiName) {
    this.yomiName = yomiName;
  }

  public String getYomiName() {
    return yomiName;
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.data.Entry#clear()
  */
  public void clear() {
    super.clear();
    linkEditPhotoHref = null;
    linkEditPhotoType = null;
    linkPhotoHref = null;
    linkPhotoType = null;
    emailAddresses.removeAllElements();
    imAddresses.removeAllElements();
    phoneNumbers.removeAllElements();
    postalAddresses.removeAllElements();
    organizations.removeAllElements();
    extendedProperties.removeAllElements();
    groups.removeAllElements();
    yomiName = null;
  }

  protected void toString(StringBuffer sb) {
    super.toString(sb);
    sb.append("\n");
    sb.append("ContactEntry:");
    if (!StringUtils.isEmpty(linkPhotoHref)) {
      sb.append(" linkPhotoHref:").append(linkPhotoHref).append("\n");
    }
    if (!StringUtils.isEmpty(linkPhotoType)) {
      sb.append(" linkPhotoType:").append(linkPhotoType).append("\n");
    }
    if (!StringUtils.isEmpty(linkEditPhotoHref)) {
      sb.append(" linkEditPhotoHref:").append(linkEditPhotoHref).append("\n");
    }
    if (!StringUtils.isEmpty(linkEditPhotoType)) {
      sb.append(" linkEditPhotoType:").append(linkEditPhotoType).append("\n");
    }
    for (Enumeration iter = emailAddresses.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((EmailAddress) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = imAddresses.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((ImAddress) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = postalAddresses.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((PostalAddress) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = phoneNumbers.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((PhoneNumber) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = organizations.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((Organization) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = extendedProperties.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((ExtendedProperty) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = groups.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((GroupMembershipInfo) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    if (!StringUtils.isEmpty(yomiName)) {
      sb.append(" yomiName:").append(yomiName).append("\n");
    }
  }

  public void validate() throws ParseException {
    super.validate();
    for (Enumeration iter = emailAddresses.elements(); iter.hasMoreElements(); ) {
      ((EmailAddress) iter.nextElement()).validate();
    }
    for (Enumeration iter = imAddresses.elements(); iter.hasMoreElements(); ) {
      ((ImAddress) iter.nextElement()).validate();
    }
    for (Enumeration iter = postalAddresses.elements(); iter.hasMoreElements(); ) {
      ((PostalAddress) iter.nextElement()).validate();
    }
    for (Enumeration iter = phoneNumbers.elements(); iter.hasMoreElements(); ) {
      ((PhoneNumber) iter.nextElement()).validate();
    }
    for (Enumeration iter = organizations.elements(); iter.hasMoreElements(); ) {
      ((Organization) iter.nextElement()).validate();
    }
    for (Enumeration iter = extendedProperties.elements(); iter.hasMoreElements(); ) {
      ((ExtendedProperty) iter.nextElement()).validate();
    }
    for (Enumeration iter = groups.elements(); iter.hasMoreElements(); ) {
      ((GroupMembershipInfo) iter.nextElement()).validate();
    }
  }
}
