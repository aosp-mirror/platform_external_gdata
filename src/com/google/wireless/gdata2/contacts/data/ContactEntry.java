// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

import java.util.Enumeration;
import java.util.Vector;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.ExtendedProperty;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;

/**
 * Entry containing information about a contact.
 */
public class ContactEntry extends Entry {
  private String linkPhotoHref;
  private String linkPhotoType;
  private String linkPhotoEtag;
  private final Vector emailAddresses = new Vector();
  private final Vector imAddresses = new Vector();
  private final Vector phoneNumbers = new Vector();
  private final Vector postalAddresses = new Vector();
  private final Vector organizations = new Vector();
  private final Vector extendedProperties = new Vector();
  private final Vector groups = new Vector();

  // new collections in Contacts v3
  private final Vector calendarLinks = new Vector();
  private final Vector events = new Vector();
  private final Vector externalIds = new Vector();
  private final Vector hobbies = new Vector();
  private final Vector jots = new Vector();
  private final Vector languages = new Vector();
  private final Vector relations = new Vector();
  private final Vector userDefinedFields = new Vector();
  private final Vector webSites = new Vector();

  // new properties in contacts v3
  private String directoryServer;
  private String gender;
  private String initials;
  private String maidenName;
  private String mileage;
  private String nickname;
  private String occupation;
  private String shortName;
  private String subject;
  private String birthday;
  private String billingInformation;

  public static final String GENDER_MALE = "male";
  public static final String GENDER_FEMALE = "female";
  public static final byte TYPE_PRIORITY_HIGH = 1;
  public static final byte TYPE_PRIORITY_NORMAL = 2;
  public static final byte TYPE_PRIORITY_LOW = 3;
  private byte priority = TypedElement.TYPE_NONE;

  public static final byte TYPE_SENSITIVITY_CONFIDENTIAL = 1;
  public static final byte TYPE_SENSITIVITY_NORMAL = 2;
  public static final byte TYPE_SENSITIVITY_PERSONAL = 3;
  public static final byte TYPE_SENSITIVITY_PRIVATE = 4;
  private byte sensitivity = TypedElement.TYPE_NONE;

  private Name name;

  /**
   * default empty constructor
   */
  public ContactEntry() {
    super();
  }

  public void setLinkPhoto(String href, String type, String photoEtag) {
    this.linkPhotoHref = href;
    this.linkPhotoType = type;
    this.linkPhotoEtag = photoEtag;
  }

  public String getLinkPhotoETag() {
      return linkPhotoEtag;
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

  public void addPostalAddress(StructuredPostalAddress postalAddress) {
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

  /**
   * Accessor to the CalendarLink Collection
   */
  public Vector getCalendarLinks() {
      return calendarLinks;
  }

  /**
   * Adds a new member to the CalendarLink collection
   */
  public void addCalendarLink(CalendarLink calendarLink) {
    calendarLinks.addElement(calendarLink);
  }

  /**
   * Accessor to the Event Collection
   */
  public Vector getEvents() {
      return events;
  }

  /**
   * Adds a new member to the Event collection
   */
  public void addEvent(Event event) {
    events.addElement(event);
  }



  /**
   * Accessor to the ExternalId Collection
   */
  public Vector getExternalIds() {
      return externalIds;
  }

  /**
   * Adds a new member to the ExternalId collection
   */
  public void addExternalId(ExternalId externalId) {
    externalIds.addElement(externalId);
  }

  /**
   * Accessor to the Hobbies Collection
   */
  public Vector getHobbies() {
      return hobbies;
  }

  /**
   * Adds a new member to the Hobbies collection
   */
  public void addHobby(String hobby) {
    hobbies.addElement(hobby);
  }

  /**
   * Accessor to the Jots Collection
   */
  public Vector getJots() {
      return jots;
  }

  /**
   * Adds a new member to the Jot collection
   */
  public void addJot(Jot jot) {
    jots.addElement(jot);
  }

  /**
   * Accessor to the Language Collection
   */
  public Vector getLanguages() {
      return languages;
  }

  /**
   * Adds a new member to the Language collection
   */
  public void addLanguage(Language language) {
    languages.addElement(language);
  }

  /**
   * Accessor to the Relation Collection
   */
  public Vector getRelations() {
      return relations;
  }

  /**
   * Adds a new member to the Relation collection
   */
  public void addRelation(Relation relation) {
    relations.addElement(relation);
  }

  /**
   * Accessor to the UserDefinedField Collection
   */
  public Vector getUserDefinedFields() {
      return userDefinedFields;
  }

  /**
   * Adds a new member to the UserDefinedField collection
   */
  public void addUserDefinedField(UserDefinedField userDefinedField) {
    userDefinedFields.addElement(userDefinedField);
  }

  /**
   * Accessor to the WebSite Collection
   */
  public Vector getWebSites() {
      return webSites;
  }

  /**
   * Adds a new member to the WebSite collection
   */
  public void addWebSite(WebSite webSite) {
    webSites.addElement(webSite);
  }


  /**
  * Directory server associated with the contact
  */
  public String getDirectoryServer() {
      return this.directoryServer;
  }
  /**
   * Directory server associated with the contact
   */
  public void setDirectoryServer(String directoryServer) {
    this.directoryServer = directoryServer;
  }

  /**
   * Gender associated with the contact.
   */
  public String getGender() {
      return this.gender;
  }

  /**
   * Gender associated with the contact.
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * Contact's initials.
   */
  public String getInitials() {
      return this.initials;
  }

  /**
   * Contact's initials.
   */
  public void setInitials(String initials) {
    this.initials = initials;
  }

  /**
   * Maiden name associated with the contact.
   */
  public String getMaidenName() {
      return this.maidenName;
  }

  /**
   * Maiden name associated with the contact.
   */
  public void setMaidenName(String maidenName) {
    this.maidenName = maidenName;
  }

  /**
   * Mileage associated with the contact.
   */
  public String getMileage() {
      return this.mileage;
  }

  /**
   * Mileage associated with the contact.
   */
  public void setMileage(String mileage) {
    this.mileage = mileage;
  }

  /**
   * Nickname associated with this Contact
   */
  public String getNickname() {
      return this.nickname;
  }

  /**
   * Nickname associated with this Contact
   */
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  /**
   * Occupation associated with this Contact
   */
  public String getOccupation() {
      return this.occupation;
  }

  /**
   * Occupation associated with this Contact
   */
  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  /**
   * Priority associated with this Contact
   */
  public byte getPriority() {
      return this.priority;
  }

  /**
   * Priority associated with this Contact
   */
  public void setPriority(byte priority) {
    this.priority = priority;
  }

  /**
   * Specifies contact's sensitivity. Can be either confidential,
   * normal, personal or private.
   */
  public byte getSensitivity() {
      return this.sensitivity;
  }

  /**
   * Specifies contact's sensitivity. Can be either confidential,
   * normal, personal or private.
   */
  public void setSensitivity(byte sensitiviy) {
    this.sensitivity = sensitiviy;
  }

  /**
   * ShortName associated with this Contact
   */
  public String getShortName() {
      return this.shortName;
  }

  /**
   * ShortName associated with this Contact
   */
  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  /**
   * Subject associated with this Contact
   */
  public String getSubject() {
      return this.subject;
  }

  /**
   * Subject associated with this Contact
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

   /**
   * Name associated with this Contact
   */
  public Name getName() {
      return this.name;
  }

  /**
   * Name associated with this Contact
   */
  public void setName(Name name) {
    this.name = name;
  }

   /**
   * Birthday associated with this Contact
   */
  public String getBirthday() {
      return this.birthday;
  }

  /**
   * Birthday associated with this Contact
   */
  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  /**
   * BillingInformation associated with this Contact
   */
  public String getBillingInformation() {
      return this.billingInformation;
  }

  /**
   * BillingInformation associated with this Contact
   */
  public void setBillingInformation(String billingInformation) {
    this.billingInformation = billingInformation;
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata2.data.Entry#clear()
  */
  public void clear() {
    super.clear();
    linkPhotoHref = null;
    linkPhotoType = null;
    linkPhotoEtag = null;
    directoryServer = null;
    gender = null;
    initials = null;
    maidenName = null;
    mileage = null;
    nickname = null;
    occupation = null;
    priority = TypedElement.TYPE_NONE;
    sensitivity = TypedElement.TYPE_NONE;
    shortName = null;
    subject = null;
    birthday = null;
    billingInformation = null;
    name = null;
    emailAddresses.removeAllElements();
    imAddresses.removeAllElements();
    phoneNumbers.removeAllElements();
    postalAddresses.removeAllElements();
    organizations.removeAllElements();
    extendedProperties.removeAllElements();
    groups.removeAllElements();
    calendarLinks.removeAllElements();
    events.removeAllElements();
    externalIds.removeAllElements();
    hobbies.removeAllElements();
    jots.removeAllElements();
    languages.removeAllElements();
    relations.removeAllElements();
    userDefinedFields.removeAllElements();
    webSites.removeAllElements();

  }

  protected void toString(StringBuffer sb) {
    super.toString(sb);
    sb.append("\n");
    sb.append("ContactEntry:");
    if (!StringUtils.isEmpty(linkPhotoHref)) {
      sb.append(" linkPhotoHref:").append(linkPhotoHref);
    }
    if (!StringUtils.isEmpty(linkPhotoType)) {
      sb.append(" linkPhotoType:").append(linkPhotoType);
    }
     if (!StringUtils.isEmpty(linkPhotoEtag)) {
      sb.append(" linkPhotoEtag:").append(linkPhotoEtag);
    }
    if (!StringUtils.isEmpty(directoryServer)) {
      sb.append(" directoryServer:").append(directoryServer);
    }
    if (!StringUtils.isEmpty(gender)) {
      sb.append(" gender:").append(gender);
    }
    if (!StringUtils.isEmpty(initials)) {
      sb.append(" initials:").append(initials);
    }
    if (!StringUtils.isEmpty(maidenName)) {
      sb.append(" maidenName:").append(maidenName);
    }
    if (!StringUtils.isEmpty(mileage)) {
      sb.append(" mileage:").append(mileage);
    }
    if (!StringUtils.isEmpty(nickname)) {
      sb.append(" nickname:").append(nickname);
    }
    if (!StringUtils.isEmpty(occupation)) {
      sb.append(" occupaton:").append(occupation);
    }
    sb.append(" priority:").append(priority);

    sb.append(" sensitivity:").append(sensitivity);

    if (!StringUtils.isEmpty(shortName)) {
      sb.append(" shortName:").append(shortName);
    }
    if (!StringUtils.isEmpty(subject)) {
      sb.append(" subject:").append(subject);
    }
    if (!StringUtils.isEmpty(birthday)) {
      sb.append(" birthday:").append(birthday);
    }
    if (!StringUtils.isEmpty(billingInformation)) {
      sb.append(" billingInformation:").append(billingInformation);
    }
    sb.append("\n");
    if (name != null) {
      name.toString(sb);
      sb.append("\n");
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
      ((StructuredPostalAddress) iter.nextElement()).toString(sb);
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
    for (Enumeration iter = calendarLinks.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((CalendarLink) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = events.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((Event) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = externalIds.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((ExternalId) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = hobbies.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      sb.append ((String) iter.nextElement());
      sb.append("\n");
    }
    for (Enumeration iter = jots.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      sb.append ((Jot) iter.nextElement());
      sb.append("\n");
    }
     for (Enumeration iter = languages.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((Language) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
     for (Enumeration iter = relations.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((Relation) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = userDefinedFields.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((UserDefinedField) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
    for (Enumeration iter = webSites.elements();
        iter.hasMoreElements(); ) {
      sb.append("  ");
      ((WebSite) iter.nextElement()).toString(sb);
      sb.append("\n");
    }
  }

  public void validate() throws ParseException {
    super.validate();
    if (gender != null && !GENDER_FEMALE.equals(gender) && !GENDER_MALE.equals(gender)) {
      throw new ParseException(
              String.format("invalid gender \"%s\", must be one of \"%s\" or \"%s\"",
                      gender, GENDER_FEMALE, GENDER_MALE));
    }
    for (Enumeration iter = emailAddresses.elements(); iter.hasMoreElements(); ) {
      ((EmailAddress) iter.nextElement()).validate();
    }
    for (Enumeration iter = imAddresses.elements(); iter.hasMoreElements(); ) {
      ((ImAddress) iter.nextElement()).validate();
    }
    for (Enumeration iter = postalAddresses.elements(); iter.hasMoreElements(); ) {
      ((StructuredPostalAddress) iter.nextElement()).validate();
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

    for (Enumeration iter = calendarLinks.elements(); iter.hasMoreElements(); ) {
      ((CalendarLink) iter.nextElement()).validate();
    }
    for (Enumeration iter = events.elements(); iter.hasMoreElements(); ) {
      ((Event) iter.nextElement()).validate();
    }
    for (Enumeration iter = externalIds.elements(); iter.hasMoreElements(); ) {
      ((ExternalId) iter.nextElement()).validate();
    }
    for (Enumeration iter = languages.elements(); iter.hasMoreElements(); ) {
      ((Language) iter.nextElement()).validate();
    }
    for (Enumeration iter = relations.elements(); iter.hasMoreElements(); ) {
      ((Relation) iter.nextElement()).validate();
    }
    for (Enumeration iter = userDefinedFields.elements(); iter.hasMoreElements(); ) {
      ((UserDefinedField) iter.nextElement()).validate();
    }
    for (Enumeration iter = webSites.elements(); iter.hasMoreElements(); ) {
      ((WebSite) iter.nextElement()).validate();
    }
  }
}
