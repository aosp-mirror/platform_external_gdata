// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.serializer.xml;

import com.google.wireless.gdata.contacts.data.ContactEntry;
import com.google.wireless.gdata.contacts.data.ContactsElement;
import com.google.wireless.gdata.contacts.data.EmailAddress;
import com.google.wireless.gdata.contacts.data.ImAddress;
import com.google.wireless.gdata.contacts.data.Organization;
import com.google.wireless.gdata.contacts.data.PhoneNumber;
import com.google.wireless.gdata.contacts.data.PostalAddress;
import com.google.wireless.gdata.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata.contacts.parser.xml.XmlContactsGDataParser;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.ExtendedProperty;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *  Serializes Google Contact entries into the Atom XML format.
 */
public class XmlContactEntryGDataSerializer extends XmlEntryGDataSerializer {

  public XmlContactEntryGDataSerializer(XmlParserFactory factory, ContactEntry entry) {
    super(factory, entry);
  }

  @Override
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

    serializeLink(serializer, XmlContactsGDataParser.LINK_REL_EDIT_PHOTO,
        entry.getLinkEditPhotoHref(), entry.getLinkEditPhotoType());
    serializeLink(serializer, XmlContactsGDataParser.LINK_REL_PHOTO,
        entry.getLinkPhotoHref(), entry.getLinkPhotoType());

    // Serialize the contact specific parts of this entry.  Note that
    // gd:ContactSection and gd:geoPt are likely to be deprecated, and
    // are not currently serialized.
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
      serialize(serializer, (PostalAddress) eachAddress.nextElement());
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

    serializeYomiName(serializer, entry.getYomiName());
  }

  private static void serialize(XmlSerializer serializer, EmailAddress email)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(email.getAddress())) return;
    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "email");
    serializeContactsElement(serializer, email, XmlContactsGDataParser.TYPE_TO_REL_EMAIL);
    serializer.attribute(null /* ns */, "address", email.getAddress());
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "email");
  }

  private static void serialize(XmlSerializer serializer, ImAddress im)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(im.getAddress())) return;

    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "im");
    serializeContactsElement(serializer, im, XmlContactsGDataParser.TYPE_TO_REL_IM);
    serializer.attribute(null /* ns */, "address", im.getAddress());

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
        serializer.attribute(null /* ns */, "protocol", protocolString);
        break;

      default:
        protocolString = (String)XmlContactsGDataParser.IM_PROTOCOL_TYPE_TO_STRING_MAP.get(
            new Byte(im.getProtocolPredefined()));
        serializer.attribute(null /* ns */, "protocol", protocolString);
        break;
    }

    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "im");
  }

  private static void serialize(XmlSerializer serializer, PhoneNumber phone)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(phone.getPhoneNumber())) return;
    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "phoneNumber");
    serializeContactsElement(serializer, phone, XmlContactsGDataParser.TYPE_TO_REL_PHONE);
    serializer.text(phone.getPhoneNumber());
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "phoneNumber");
  }

  private static void serialize(XmlSerializer serializer, Organization organization)
      throws IOException, ParseException {
    final String name = organization.getName();
    final String title = organization.getTitle();

    if (StringUtils.isEmptyOrWhitespace(name) && StringUtils.isEmptyOrWhitespace(title)) return;

    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "organization");
    serializeContactsElement(serializer,
            organization, XmlContactsGDataParser.TYPE_TO_REL_ORGANIZATION);
    if (!StringUtils.isEmpty(name)) {
      serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "orgName");
      serializer.text(name);
      serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "orgName");
    }

    if (!StringUtils.isEmpty(title)) {
      serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "orgTitle");
      serializer.text(title);
      serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "orgTitle");
    }
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "organization");
  }

  private static void serialize(XmlSerializer serializer, PostalAddress addr)
      throws IOException, ParseException {
    if (StringUtils.isEmptyOrWhitespace(addr.getValue())) return;
    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "postalAddress");
    serializeContactsElement(serializer, addr, XmlContactsGDataParser.TYPE_TO_REL_POSTAL);
    final String addressValue = addr.getValue();
    if (addressValue != null) serializer.text(addressValue);
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "postalAddress");
  }

  private static void serializeContactsElement(XmlSerializer serializer, ContactsElement element,
      Hashtable typeToRelMap) throws IOException, ParseException {
    final String label = element.getLabel();
    boolean hasType = element.getType() != ContactsElement.TYPE_NONE;

    if (((label == null) && !hasType) || ((label != null) && hasType)) {
      throw new ParseException("exactly one of label or rel must be set");
    }

    if (label != null) {
      serializer.attribute(null /* ns */, "label", label);
    }
    if (hasType) {
      serializer.attribute(null /* ns */, "rel",
          (String)typeToRelMap.get(new Byte(element.getType())));
    }
    if (element.isPrimary()) {
      serializer.attribute(null /* ns */, "primary", "true");
    }
  }

  private static void serialize(XmlSerializer serializer, GroupMembershipInfo groupMembershipInfo)
      throws IOException, ParseException {
    final String group = groupMembershipInfo.getGroup();
    final boolean isDeleted = groupMembershipInfo.isDeleted();

    if (StringUtils.isEmptyOrWhitespace(group)) {
      throw new ParseException("the group must not be empty");
    }

    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, "groupMembershipInfo");
    serializer.attribute(null /* ns */, "href", group);
    serializer.attribute(null /* ns */, "deleted", isDeleted ? "true" : "false");
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, "groupMembershipInfo");
  }

  private static void serialize(XmlSerializer serializer, ExtendedProperty extendedProperty)
      throws IOException, ParseException {
    final String name = extendedProperty.getName();
    final String value = extendedProperty.getValue();
    final String xmlBlob = extendedProperty.getXmlBlob();

    serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, "extendedProperty");
    if (!StringUtils.isEmpty(name)) {
      serializer.attribute(null /* ns */, "name", name);
    }
    if (!StringUtils.isEmpty(value)) {
      serializer.attribute(null /* ns */, "value", value);
    }
    if (!StringUtils.isEmpty(xmlBlob)) {
      serializeBlob(serializer, xmlBlob);
    }
    serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, "extendedProperty");
  }

  private static void serializeBlob(XmlSerializer serializer, String blob)
      throws IOException, ParseException {
     serializer.text(blob);
  }

  private static void serializeYomiName(XmlSerializer serializer,
      String yomiName)
      throws IOException {
    if (StringUtils.isEmpty(yomiName)) {
      return;
    }
    serializer.startTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, "yomiName");
    serializer.text(yomiName);
    serializer.endTag(XmlContactsGDataParser.NAMESPACE_CONTACTS_URI, "yomiName");
  }
}
