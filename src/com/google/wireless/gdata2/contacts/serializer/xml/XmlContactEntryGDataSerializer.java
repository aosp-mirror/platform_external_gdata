// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.serializer.xml;

import com.google.wireless.gdata2.contacts.data.CalendarLink;
import com.google.wireless.gdata2.contacts.data.ContactEntry;
import com.google.wireless.gdata2.contacts.data.ContactsElement;
import com.google.wireless.gdata2.contacts.data.EmailAddress;
import com.google.wireless.gdata2.contacts.data.Event;
import com.google.wireless.gdata2.contacts.data.ExternalId;
import com.google.wireless.gdata2.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata2.contacts.data.ImAddress;
import com.google.wireless.gdata2.contacts.data.Jot;
import com.google.wireless.gdata2.contacts.data.Language;
import com.google.wireless.gdata2.contacts.data.Name;
import com.google.wireless.gdata2.contacts.data.Organization;
import com.google.wireless.gdata2.contacts.data.PhoneNumber;
import com.google.wireless.gdata2.contacts.data.Relation;
import com.google.wireless.gdata2.contacts.data.StructuredPostalAddress;
import com.google.wireless.gdata2.contacts.data.TypedElement;
import com.google.wireless.gdata2.contacts.data.UserDefinedField;
import com.google.wireless.gdata2.contacts.data.WebSite;
import com.google.wireless.gdata2.contacts.parser.xml.XmlContactsGDataParser;
import com.google.wireless.gdata2.contacts.parser.xml.XmlNametable;
import com.google.wireless.gdata2.data.ExtendedProperty;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.xml.XmlEntryGDataSerializer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.xmlpull.v1.XmlSerializer;


/**
 *  Serializes Google Contact entries into the Atom XML format.
 */
public class XmlContactEntryGDataSerializer extends XmlEntryGDataSerializer {

  public XmlContactEntryGDataSerializer(XmlParserFactory factory, ContactEntry entry) {
    super(factory, entry);
  }

  protected void declareExtraEntryNamespaces(XmlSerializer serializer) throws IOException {
    super.declareExtraEntryNamespaces(serializer);
    serializer.setPrefix(XmlContactsGDataParser.NAMESPACE_CONTACTS,
        XmlContactsGDataParser.NAMESPACE_CONTACTS_URI);
  }

  protected ContactEntry getContactEntry() {
    return (ContactEntry) getEntry();
  }

  /* (non-Javadoc)
  * @see XmlEntryGDataSerializer#serializeExtraEntryContents
  */
  protected void serializeExtraEntryContents(XmlSerializer serializer, int format)
      throws ParseException, IOException {
    ContactEntry entry = getContactEntry();
    entry.validate();

    serializeLink(serializer, XmlContactsGDataParser.LINK_REL_PHOTO,
        entry.getLinkPhotoHref(), entry.getLinkPhotoType(), entry.getLinkPhotoETag());

    Enumeration eachEmail = entry.getEmailAddresses().elements();
    while (eachEmail.hasMoreElements()) {
      serialize(serializer, (EmailAddress) eachEmail.nextElement());
    }

    Enumeration eachIm = entry.getImAddresses().elements();
    while (eachIm.hasMoreElements()) {
      serialize(serializer, (ImAddress) eachIm.nextElement());
    }

    Enumeration eachPhone = entry.getPhoneNumbers().elements();
    while (eachPhone.hasMoreElements()) {
      serialize(serializer, (PhoneNumber) eachPhone.nextElement());
    }

    Enumeration eachAddress = entry.getPostalAddresses().elements();
    while (eachAddress.hasMoreElements()) {
      serialize(serializer, (StructuredPostalAddress) eachAddress.nextElement());
    }

    Enumeration eachOrganization = entry.getOrganizations().elements();
    while (eachOrganization.hasMoreElements()) {
      serialize(serializer, (Organization) eachOrganization.nextElement());
    }

    Enumeration eachExtendedProperty = entry.getExtendedProperties().elements();
    while (eachExtendedProperty.hasMoreElements()) {
      serialize(serializer, (ExtendedProperty) eachExtendedProperty.nextElement());
    }

    Enumeration eachGroup = entry.getGroups().elements();
    while (eachGroup.hasMoreElements()) {
      serialize(serializer, (GroupMembershipInfo) eachGroup.nextElement());
    }

    Enumeration eachCalendar = entry.getCalendarLinks().elements();
    while (eachCalendar.hasMoreElements()) {
      serialize(serializer, (CalendarLink) eachCalendar.nextElement());
    }

    Enumeration eachEvent = entry.getEvents().elements();
    while (eachEvent.hasMoreElements()) {
      serialize(serializer, (Event) eachEvent.nextElement());
    }

    Enumeration eachWebsite = entry.getWebSites().elements();
    while (eachWebsite.hasMoreElements()) {
      serialize(serializer, (WebSite) eachWebsite.nextElement());
    }

    Enumeration eachExternalId = entry.getExternalIds().elements();
    while (eachExternalId.hasMoreElements()) {
      serialize(serializer, (ExternalId) eachExternalId.nextElement());
    }

    Enumeration eachHobby = entry.getHobbies().elements();
    while (eachHobby.hasMoreElements()) {
      serializeHobby(serializer, (String) eachHobby.nextElement());
    }

    Enumeration eachJot = entry.getJots().elements();
    while (eachJot.hasMoreElements()) {
      serialize(serializer, (Jot) eachJot.nextElement());
    }

    Enumeration eachLanguage = entry.getLanguages().elements();
    while (eachLanguage.hasMoreElements()) {
      serialize(serializer, (Language) eachLanguage.nextElement());
    }

    Enumeration eachRelation = entry.getRelations().elements();
    while (eachRelation.hasMoreElements()) {
      serialize(serializer, (Relation) eachRelation.nextElement());
    }

    Enumeration eachUDF = entry.getUserDefinedFields().elements();
    while (eachUDF.hasMoreElements()) {
      serialize(serializer, (UserDefinedField) eachUDF.nextElement());
    }

    serializeBirthday(serializer, entry.getBirthday());

    // now serialize simple properties

    serializeElement(serializer, entry.getDirectoryServer(), XmlNametable.GC_DIRECTORYSERVER);
    serializeGenderElement(serializer, entry.getGender());
    serializeElement(serializer, entry.getInitials(), XmlNametable.GC_INITIALS);
    serializeElement(serializer, entry.getMaidenName(), XmlNametable.GC_MAIDENNAME);
    serializeElement(serializer, entry.getMileage(), XmlNametable.GC_MILEAGE);
    serializeElement(serializer, entry.getNickname(), XmlNametable.GC_NICKNAME);
    serializeElement(serializer, entry.getOccupation(), XmlNametable.GC_OCCUPATION);
    serializeElement(serializer, entry.getShortName(), XmlNametable.GC_SHORTNAME);
    serializeElement(serializer, entry.getSubject(), XmlNametable.GC_SUBJECT);
    serializeElement(serializer, entry.getBillingInformation(), XmlNametable.GC_BILLINGINFO);
    serializeElement(serializer, entry.getPriority(),
                     XmlNametable.GC_PRIORITY, XmlContactsGDataParser.TYPE_TO_REL_PRIORITY);
    serializeElement(serializer, entry.getSensitivity(),
                     XmlNametable.GC_SENSITIVITY, XmlContactsGDataParser.TYPE_TO_REL_SENSITIVITY);

    serializeName(serializer, entry.getName());
  }

  private static void serialize(XmlSerializer serializer, EmailAddress email)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(email.getAddress())) return;
    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_EMAIL);
    serializeContactsElement(serializer, email, XmlContactsGDataParser.TYPE_TO_REL_EMAIL);
    serializer.attribute(null /* ns */, XmlNametable.GD_ADDRESS, email.getAddress());
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_EMAIL);
  }

  private static void serialize(XmlSerializer serializer, ImAddress im)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(im.getAddress())) return;

    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_IM);
    serializeContactsElement(serializer, im, XmlContactsGDataParser.TYPE_TO_REL_IM);
    serializer.attribute(null /* ns */, XmlNametable.GD_ADDRESS, im.getAddress());

    String protocolString;
    switch (im.getProtocolPredefined()) {
      case ImAddress.PROTOCOL_NONE:
        // don't include the attribute if no protocol was specified
        break;

      case ImAddress.PROTOCOL_CUSTOM:
        protocolString = im.getProtocolCustom();
        if (protocolString == null) {
          throw new IllegalArgumentException(
              "the protocol is custom, but the custom string is null");
        }
        serializer.attribute(null /* ns */, XmlNametable.GD_PROTOCOL, protocolString);
        break;

      default:
        protocolString = (String)XmlContactsGDataParser.IM_PROTOCOL_TYPE_TO_STRING_MAP.get(
            new Byte(im.getProtocolPredefined()));
        serializer.attribute(null /* ns */, XmlNametable.GD_PROTOCOL, protocolString);
        break;
    }

    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_IM);
  }

  private static void serialize(XmlSerializer serializer, PhoneNumber phone)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(phone.getPhoneNumber())) return;
    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_PHONENUMBER);
    serializeContactsElement(serializer, phone, XmlContactsGDataParser.TYPE_TO_REL_PHONE);
    serializer.text(phone.getPhoneNumber());
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_PHONENUMBER);
  }

  private static void serialize(XmlSerializer serializer, Organization organization)
      throws IOException, ParseException {

    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_ORGANIZATION);
    serializeContactsElement(serializer,
            organization, XmlContactsGDataParser.TYPE_TO_REL_ORGANIZATION);
    serializeGDSubelement(serializer, organization.getName(),
                          XmlNametable.GD_ORG_NAME);
    serializeGDSubelement(serializer, organization.getTitle(),
                          XmlNametable.GD_ORG_TITLE);
    serializeGDSubelement(serializer, organization.getOrgDepartment(),
                          XmlNametable.GD_ORG_DEPARTMENT);
    serializeGDSubelement(serializer, organization.getOrgJobDescription(),
                          XmlNametable.GD_ORG_JOBDESC);
    serializeGDSubelement(serializer, organization.getOrgSymbol(),
                          XmlNametable.GD_ORG_SYMBOL);

    final String where = organization.getWhere();
    if (!StringUtils.isEmpty(where)) {
      serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_WHERE);
      serializer.attribute(null /* ns */, XmlNametable.VALUESTRING, where);
      serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_WHERE);
    }

    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_ORGANIZATION);
  }


  /**
   * Gets called out of the main serializer loop. Parameters are
   * not null.
   *
   * @param serializer
   * @param addr
   */
  private static void serialize(XmlSerializer serializer, StructuredPostalAddress addr)
      throws IOException, ParseException {
    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_SPA);
    serializeContactsElement(serializer, addr, XmlContactsGDataParser.TYPE_TO_REL_POSTAL);
    serializeGDSubelement(serializer, addr.getStreet(), XmlNametable.GD_SPA_STREET);
    serializeGDSubelement(serializer, addr.getPobox(), XmlNametable.GD_SPA_POBOX);
    serializeGDSubelement(serializer, addr.getNeighborhood(), XmlNametable.GD_SPA_NEIGHBORHOOD);
    serializeGDSubelement(serializer, addr.getCity(), XmlNametable.GD_SPA_CITY);
    serializeGDSubelement(serializer, addr.getRegion(), XmlNametable.GD_SPA_REGION);
    serializeGDSubelement(serializer, addr.getPostcode(), XmlNametable.GD_SPA_POSTCODE);
    serializeGDSubelement(serializer, addr.getCountry(), XmlNametable.GD_SPA_COUNTRY);
    serializeGDSubelement(serializer, addr.getFormattedAddress(),
                          XmlNametable.GD_SPA_FORMATTEDADDRESS);
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_SPA);
  }

  private static void serializeGDSubelement(XmlSerializer serializer, String value,
                                            String elementName)
      throws IOException {
    if (StringUtils.isEmpty(value)) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_GD_URI, elementName);
    serializer.text(value);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_GD_URI, elementName);
  }


  private static void serializeTypedElement(XmlSerializer serializer, TypedElement element,
                     Hashtable typeToRelMap) throws IOException, ParseException {

    final String label = element.getLabel();
    byte type = element.getType();
    boolean hasType = type != TypedElement.TYPE_NONE;

    // validate the element
    element.validate();

    if (label != null) {
      serializer.attribute(null /* ns */, XmlNametable.LABEL, label);
    }
    if (hasType) {
      serializeRelation(serializer, type, typeToRelMap);
    }
  }

  private static void serializeRelation(XmlSerializer serializer, byte type,
                     Hashtable typeToRelMap) throws IOException {

    serializer.attribute(null /* ns */, XmlNametable.REL,
          (String)typeToRelMap.get(new Byte(type)));
  }

  private static void serializeContactsElement(XmlSerializer serializer, ContactsElement element,
      Hashtable typeToRelMap) throws IOException, ParseException {
    serializeTypedElement(serializer, element, typeToRelMap);
    if (element.isPrimary()) {
      serializer.attribute(null /* ns */, XmlNametable.PRIMARY, "true");
    }
  }

  private static void serialize(XmlSerializer serializer, GroupMembershipInfo groupMembershipInfo)
      throws IOException, ParseException {
    final String group = groupMembershipInfo.getGroup();
    final boolean isDeleted = groupMembershipInfo.isDeleted();

    if (StringUtils.isEmptyOrWhitespace(group)) {
      throw new ParseException("the group must not be empty");
    }

    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_GMI);
    serializer.attribute(null /* ns */, XmlNametable.HREF, group);
    serializer.attribute(null /* ns */, XmlNametable.GD_DELETED, isDeleted ? "true" : "false");
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_GMI);
  }

  private static void serialize(XmlSerializer serializer, ExtendedProperty extendedProperty)
      throws IOException {
    final String name = extendedProperty.getName();
    final String value = extendedProperty.getValue();
    final String xmlBlob = extendedProperty.getXmlBlob();

    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_EXTENDEDPROPERTY);
    if (!StringUtils.isEmpty(name)) {
      serializer.attribute(null /* ns */, XmlNametable.GD_NAME, name);
    }
    if (!StringUtils.isEmpty(value)) {
      serializer.attribute(null /* ns */, XmlNametable.VALUE, value);
    }
    if (!StringUtils.isEmpty(xmlBlob)) {
      serializeBlob(serializer, xmlBlob);
    }
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_EXTENDEDPROPERTY);
  }

  private static void serializeBlob(XmlSerializer serializer, String blob)
      throws IOException {
     serializer.text(blob);
  }

  /**
   * takes a typed element and a string value and determines if
   * the element and the string together should be serialized.
   * if the string is non empty, or the typedelement is worthy of
   * serialization, this will return true.
   *
   * @param element
   * @param value
   *
   * @return boolean
   */
  private static boolean shouldSerialize(TypedElement element, String value)
  {
    if (element.getType() != TypedElement.TYPE_NONE) {
      return true;
    }
    if (!StringUtils.isEmptyOrWhitespace(element.getLabel())) {
      return true;
    }
    if (!StringUtils.isEmptyOrWhitespace(value)) {
      return true;
    }
    return false;
  }

  private static void serialize(XmlSerializer serializer, CalendarLink calendarLink)
        throws IOException, ParseException {
    final String href = calendarLink.getHRef();

    if (shouldSerialize(calendarLink, href)) {
      serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI,
                          XmlNametable.GC_CALENDARLINK);
      serializeContactsElement(serializer, calendarLink,
                               XmlContactsGDataParser.TYPE_TO_REL_CALENDARLINK);
      if (!StringUtils.isEmpty(href)) {
        serializer.attribute(null /* ns */, XmlNametable.HREF, href);
      }
      serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI,
                        XmlNametable.GC_CALENDARLINK);
    }
  }

  private static void serialize(XmlSerializer serializer, Event event)
        throws IOException, ParseException {
    final String startDate = event.getStartDate();
    if (shouldSerialize(event, startDate)) {
      serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_EVENT);
      serializeTypedElement(serializer, event, XmlContactsGDataParser.TYPE_TO_REL_EVENT);
      if (!StringUtils.isEmpty(startDate)) {
        serializer.startTag(XmlContactsGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_WHEN);
        serializer.attribute(null /* ns */, XmlNametable.STARTTIME, startDate);
        serializer.endTag(XmlContactsGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_WHEN);
      }
      serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_EVENT);
    }
  }

  private static void serialize(XmlSerializer serializer, ExternalId externalId)
        throws IOException, ParseException {
    final String value = externalId.getValue();
    if (shouldSerialize(externalId, value)) {
      serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI,
                          XmlNametable.GC_EXTERNALID);
      serializeTypedElement(serializer, externalId,
                            XmlContactsGDataParser.TYPE_TO_REL_EXTERNALID);
      if (!StringUtils.isEmpty(value)) {
        serializer.attribute(null /* ns */, XmlNametable.VALUE, value);
      }
      serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI,
                        XmlNametable.GC_EXTERNALID);
    }
  }

  private static void serializeHobby(XmlSerializer serializer, String hobby)
        throws IOException {
    if (StringUtils.isEmptyOrWhitespace(hobby)) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_HOBBY);
    serializer.text(hobby);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_HOBBY);
  }

  private static void serializeBirthday(XmlSerializer serializer, String birthday)
        throws IOException {
    if (StringUtils.isEmptyOrWhitespace(birthday)) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_BIRTHDAY);
    serializer.attribute(null /* ns */, XmlNametable.GD_WHEN, birthday);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_BIRTHDAY);
  }

  private static void serialize(XmlSerializer serializer, Jot jot)
        throws IOException {
    final String value = jot.getLabel();

    if (!StringUtils.isEmptyOrWhitespace(value)) {
      serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_JOT);
      serializeRelation(serializer, jot.getType(), XmlContactsGDataParser.TYPE_TO_REL_JOT);
      serializer.text(value);
      serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_JOT);
    }
  }

  private static void serialize(XmlSerializer serializer, Language language)
        throws IOException, ParseException {
    language.validate();
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_LANGUAGE);
    final String value = language.getCode();
    if (!StringUtils.isEmptyOrWhitespace(value)) {
      serializer.attribute(null /* ns */, XmlNametable.CODE, value);
    } else {
      serializer.attribute(null /* ns */, XmlNametable.LABEL, language.getLabel());
    }
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_LANGUAGE);
  }

  private static void serialize(XmlSerializer serializer, Relation relation)
        throws IOException, ParseException {
    final String value = relation.getText();
    if (shouldSerialize(relation, value)) {
      serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_RELATION);
      serializeTypedElement(serializer, relation, XmlContactsGDataParser.TYPE_TO_REL_RELATION);
      serializer.text(value);
      serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_RELATION);
    }
  }

  private static void serialize(XmlSerializer serializer, UserDefinedField udf)
        throws IOException, ParseException {
    udf.validate();
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_UDF);
    serializer.attribute(null /* ns */, XmlNametable.KEY, udf.getKey());
    serializer.attribute(null /* ns */, XmlNametable.VALUE, udf.getValue());
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_UDF);
  }

  private static void serialize(XmlSerializer serializer, WebSite webSite)
        throws IOException, ParseException {
    final String href = webSite.getHRef();

    if (shouldSerialize(webSite, href)) {
      serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_WEBSITE);
      serializeContactsElement(serializer, webSite, XmlContactsGDataParser.TYPE_TO_REL_WEBSITE);
      if (!StringUtils.isEmpty(href)) {
        serializer.attribute(null /* ns */, XmlNametable.HREF, href);
      }
      serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_WEBSITE);
    }
  }

  private static void serializeElement(XmlSerializer serializer, String value, String elementName)
        throws IOException {
    if (StringUtils.isEmpty(value)) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, elementName);
    serializer.text(value);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, elementName);
  }

  private static void serializeGenderElement(XmlSerializer serializer, String value)
        throws IOException {
    if (StringUtils.isEmpty(value)) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_GENDER);
    serializer.attribute(null /* ns */, XmlNametable.VALUE, value);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, XmlNametable.GC_GENDER);
  }

  private static void serializeElement(XmlSerializer serializer, byte value, String elementName,
        Hashtable typeToRelMap) throws IOException {
    if (value == TypedElement.TYPE_NONE) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, elementName);
    serializeRelation(serializer, value, typeToRelMap);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, elementName);
  }

  private static void serializeName(XmlSerializer serializer, Name name)
        throws IOException {
    if (name == null) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_NAME);
    serializeNameSubelement(serializer, name.getGivenName(),
                            name.getGivenNameYomi(), XmlNametable.GD_NAME_GIVENNAME);
    serializeNameSubelement(serializer, name.getAdditionalName(),
                            name.getAdditionalNameYomi(), XmlNametable.GD_NAME_ADDITIONALNAME);
    serializeNameSubelement(serializer, name.getFamilyName(),
                            name.getFamilyNameYomi(), XmlNametable.GD_NAME_FAMILYNAME);
    serializeNameSubelement(serializer, name.getNamePrefix(),
                        null /* yomi */, XmlNametable.GD_NAME_PREFIX);
    serializeNameSubelement(serializer, name.getNameSuffix(),
                        null /* yomi */, XmlNametable.GD_NAME_SUFFIX);
    serializeNameSubelement(serializer, name.getFullName(),
                        null /* yomi */, XmlNametable.GD_NAME_FULLNAME);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_GD_URI, XmlNametable.GD_NAME);
  }

  private static void serializeNameSubelement(XmlSerializer serializer, String value,
                                              String yomi, String elementName)
        throws IOException {
    if (StringUtils.isEmpty(value)) return;
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_GD_URI, elementName);
    if (!StringUtils.isEmpty(yomi)) {
      serializer.attribute(null /* ns */, XmlNametable.GD_NAME_YOMI, yomi);
    }
    serializer.text(value);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_GD_URI, elementName);
  }
}
