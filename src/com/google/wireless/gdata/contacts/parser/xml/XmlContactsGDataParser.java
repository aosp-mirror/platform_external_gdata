// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.parser.xml;

import com.google.wireless.gdata.contacts.data.ContactEntry;
import com.google.wireless.gdata.contacts.data.ContactsElement;
import com.google.wireless.gdata.contacts.data.ContactsFeed;
import com.google.wireless.gdata.contacts.data.EmailAddress;
import com.google.wireless.gdata.contacts.data.ImAddress;
import com.google.wireless.gdata.contacts.data.Organization;
import com.google.wireless.gdata.contacts.data.PhoneNumber;
import com.google.wireless.gdata.contacts.data.PostalAddress;
import com.google.wireless.gdata.contacts.data.GroupMembershipInfo;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.data.ExtendedProperty;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * GDataParser for a contacts feed.
 */
public class XmlContactsGDataParser extends XmlGDataParser {
  /** Namespace prefix for Contacts */
  public static final String NAMESPACE_CONTACTS = "gContact";

  /** Namespace URI for Contacts */
  public static final String NAMESPACE_CONTACTS_URI =
      "http://schemas.google.com/contact/2008";

  /** The photo link rels */
  public static final String LINK_REL_PHOTO = "http://schemas.google.com/contacts/2008/rel#photo";
  public static final String LINK_REL_EDIT_PHOTO =
          "http://schemas.google.com/contacts/2008/rel#edit-photo";

  /** The phone number type gdata string. */
  private static final String GD_NAMESPACE = "http://schemas.google.com/g/2005#";
  public static final String TYPESTRING_MOBILE = GD_NAMESPACE + "mobile";
  public static final String TYPESTRING_HOME = GD_NAMESPACE + "home";
  public static final String TYPESTRING_WORK = GD_NAMESPACE + "work";
  public static final String TYPESTRING_HOME_FAX = GD_NAMESPACE + "home_fax";
  public static final String TYPESTRING_WORK_FAX = GD_NAMESPACE + "work_fax";
  public static final String TYPESTRING_PAGER = GD_NAMESPACE + "pager";
  public static final String TYPESTRING_OTHER = GD_NAMESPACE + "other";

  public static final String IM_PROTOCOL_AIM = GD_NAMESPACE + "AIM";
  public static final String IM_PROTOCOL_MSN = GD_NAMESPACE + "MSN";
  public static final String IM_PROTOCOL_YAHOO = GD_NAMESPACE + "YAHOO";
  public static final String IM_PROTOCOL_SKYPE = GD_NAMESPACE + "SKYPE";
  public static final String IM_PROTOCOL_QQ = GD_NAMESPACE + "QQ";
  public static final String IM_PROTOCOL_GOOGLE_TALK = GD_NAMESPACE + "GOOGLE_TALK";
  public static final String IM_PROTOCOL_ICQ = GD_NAMESPACE + "ICQ";
  public static final String IM_PROTOCOL_JABBER = GD_NAMESPACE + "JABBER";

  private static final Hashtable REL_TO_TYPE_EMAIL;
  private static final Hashtable REL_TO_TYPE_PHONE;
  private static final Hashtable REL_TO_TYPE_POSTAL;
  private static final Hashtable REL_TO_TYPE_IM;
  private static final Hashtable REL_TO_TYPE_ORGANIZATION;
  private static final Hashtable IM_PROTOCOL_STRING_TO_TYPE_MAP;

  public static final Hashtable TYPE_TO_REL_EMAIL;
  public static final Hashtable TYPE_TO_REL_PHONE;
  public static final Hashtable TYPE_TO_REL_POSTAL;
  public static final Hashtable TYPE_TO_REL_IM;
  public static final Hashtable TYPE_TO_REL_ORGANIZATION;
  public static final Hashtable IM_PROTOCOL_TYPE_TO_STRING_MAP;

  static {
    Hashtable map;

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(EmailAddress.TYPE_HOME));
    map.put(TYPESTRING_WORK, new Byte(EmailAddress.TYPE_WORK));
    map.put(TYPESTRING_OTHER, new Byte(EmailAddress.TYPE_OTHER));
    // TODO: this is a hack to support the old feed
    map.put(GD_NAMESPACE + "primary", (byte)4);
    REL_TO_TYPE_EMAIL = map;
    TYPE_TO_REL_EMAIL = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(PhoneNumber.TYPE_HOME));
    map.put(TYPESTRING_MOBILE, new Byte(PhoneNumber.TYPE_MOBILE));
    map.put(TYPESTRING_PAGER, new Byte(PhoneNumber.TYPE_PAGER));
    map.put(TYPESTRING_WORK, new Byte(PhoneNumber.TYPE_WORK));
    map.put(TYPESTRING_HOME_FAX, new Byte(PhoneNumber.TYPE_HOME_FAX));
    map.put(TYPESTRING_WORK_FAX, new Byte(PhoneNumber.TYPE_WORK_FAX));
    map.put(TYPESTRING_OTHER, new Byte(PhoneNumber.TYPE_OTHER));
    REL_TO_TYPE_PHONE = map;
    TYPE_TO_REL_PHONE = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(PostalAddress.TYPE_HOME));
    map.put(TYPESTRING_WORK, new Byte(PostalAddress.TYPE_WORK));
    map.put(TYPESTRING_OTHER, new Byte(PostalAddress.TYPE_OTHER));
    REL_TO_TYPE_POSTAL = map;
    TYPE_TO_REL_POSTAL = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(ImAddress.TYPE_HOME));
    map.put(TYPESTRING_WORK, new Byte(ImAddress.TYPE_WORK));
    map.put(TYPESTRING_OTHER, new Byte(ImAddress.TYPE_OTHER));
    REL_TO_TYPE_IM = map;
    TYPE_TO_REL_IM = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_WORK, new Byte(Organization.TYPE_WORK));
    map.put(TYPESTRING_OTHER, new Byte(Organization.TYPE_OTHER));
    REL_TO_TYPE_ORGANIZATION = map;
    TYPE_TO_REL_ORGANIZATION = swapMap(map);

    map = new Hashtable();
    map.put(IM_PROTOCOL_AIM, new Byte(ImAddress.PROTOCOL_AIM));
    map.put(IM_PROTOCOL_MSN, new Byte(ImAddress.PROTOCOL_MSN));
    map.put(IM_PROTOCOL_YAHOO, new Byte(ImAddress.PROTOCOL_YAHOO));
    map.put(IM_PROTOCOL_SKYPE, new Byte(ImAddress.PROTOCOL_SKYPE));
    map.put(IM_PROTOCOL_QQ, new Byte(ImAddress.PROTOCOL_QQ));
    map.put(IM_PROTOCOL_GOOGLE_TALK, new Byte(ImAddress.PROTOCOL_GOOGLE_TALK));
    map.put(IM_PROTOCOL_ICQ, new Byte(ImAddress.PROTOCOL_ICQ));
    map.put(IM_PROTOCOL_JABBER, new Byte(ImAddress.PROTOCOL_JABBER));
    IM_PROTOCOL_STRING_TO_TYPE_MAP = map;
    IM_PROTOCOL_TYPE_TO_STRING_MAP = swapMap(map);
  }

  private static Hashtable swapMap(Hashtable originalMap) {
    Hashtable newMap = new Hashtable();
    Enumeration enumeration = originalMap.keys();
    while (enumeration.hasMoreElements()) {
      Object key = enumeration.nextElement();
      Object value = originalMap.get(key);
      if (newMap.containsKey(value)) {
        throw new IllegalArgumentException("value " + value
            + " was already encountered");
      }
      newMap.put(value, key);
    }
    return newMap;
  }

  /**
   * Creates a new XmlEventsGDataParser.
   * @param is The InputStream that should be parsed.
   * @throws ParseException Thrown if a parser cannot be created.
   */
  public XmlContactsGDataParser(InputStream is, XmlPullParser parser)
      throws ParseException {
    super(is, parser);
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createFeed()
  */
  protected Feed createFeed() {
    return new ContactsFeed();
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createEntry()
  */
  protected Entry createEntry() {
    return new ContactEntry();
  }

  protected void handleExtraElementInEntry(Entry entry) throws XmlPullParserException, IOException {
    XmlPullParser parser = getParser();

    if (!(entry instanceof ContactEntry)) {
      throw new IllegalArgumentException("Expected ContactEntry!");
    }
    ContactEntry contactEntry = (ContactEntry) entry;
    String name = parser.getName();
    if ("email".equals(name)) {
      EmailAddress emailAddress = new EmailAddress();
      parseContactsElement(emailAddress, parser, REL_TO_TYPE_EMAIL);
      // TODO: remove this when the feed is upgraded
      if (emailAddress.getType() == 4) {
        emailAddress.setType(EmailAddress.TYPE_OTHER);
        emailAddress.setIsPrimary(true);
        emailAddress.setLabel(null);
      }
      emailAddress.setAddress(parser.getAttributeValue(null  /* ns */, "address"));
      contactEntry.addEmailAddress(emailAddress);
    } else if ("deleted".equals(name)) {
      contactEntry.setDeleted(true);
    } else if ("im".equals(name)) {
      ImAddress imAddress = new ImAddress();
      parseContactsElement(imAddress, parser, REL_TO_TYPE_IM);
      imAddress.setAddress(parser.getAttributeValue(null  /* ns */, "address"));
      imAddress.setLabel(parser.getAttributeValue(null  /* ns */, "label"));
      String protocolString = parser.getAttributeValue(null  /* ns */, "protocol");
      if (protocolString == null) {
        imAddress.setProtocolPredefined(ImAddress.PROTOCOL_NONE);
        imAddress.setProtocolCustom(null);
      } else {
        Byte predefinedProtocol = (Byte) IM_PROTOCOL_STRING_TO_TYPE_MAP.get(protocolString);
        if (predefinedProtocol == null) {
          imAddress.setProtocolPredefined(ImAddress.PROTOCOL_CUSTOM);
          imAddress.setProtocolCustom(protocolString);
        } else {
          imAddress.setProtocolPredefined(predefinedProtocol.byteValue());
          imAddress.setProtocolCustom(null);
        }
      }
      contactEntry.addImAddress(imAddress);
    } else if ("postalAddress".equals(name)) {
      PostalAddress postalAddress = new PostalAddress();
      parseContactsElement(postalAddress, parser, REL_TO_TYPE_POSTAL);
      postalAddress.setValue(XmlUtils.extractChildText(parser));
      contactEntry.addPostalAddress(postalAddress);
    } else if ("phoneNumber".equals(name)) {
      PhoneNumber phoneNumber = new PhoneNumber();
      parseContactsElement(phoneNumber, parser, REL_TO_TYPE_PHONE);
      phoneNumber.setPhoneNumber(XmlUtils.extractChildText(parser));
      contactEntry.addPhoneNumber(phoneNumber);
    } else if ("organization".equals(name)) {
      Organization organization = new Organization();
      parseContactsElement(organization, parser, REL_TO_TYPE_ORGANIZATION);
      handleOrganizationSubElement(organization, parser);
      contactEntry.addOrganization(organization);
    } else if ("extendedProperty".equals(name)) {
      ExtendedProperty extendedProperty = new ExtendedProperty();
      parseExtendedProperty(extendedProperty);
      contactEntry.addExtendedProperty(extendedProperty);
    } else if ("groupMembershipInfo".equals(name)) {
      GroupMembershipInfo group = new GroupMembershipInfo();
      group.setGroup(parser.getAttributeValue(null  /* ns */, "href"));
      group.setDeleted("true".equals(parser.getAttributeValue(null  /* ns */, "deleted")));
      contactEntry.addGroup(group);
    } else if ("yomiName".equals(name)) {
      String yomiName = XmlUtils.extractChildText(parser);
      contactEntry.setYomiName(yomiName);
    }
  }

  @Override
  protected void handleExtraLinkInEntry(String rel, String type, String href, Entry entry)
      throws XmlPullParserException, IOException {
    if (LINK_REL_PHOTO.equals(rel)) {
      ContactEntry contactEntry = (ContactEntry) entry;
      contactEntry.setLinkPhoto(href, type);
    } else if (LINK_REL_EDIT_PHOTO.equals(rel)) {
      ContactEntry contactEntry = (ContactEntry) entry;
      contactEntry.setLinkEditPhoto(href, type);
    }
  }

  private static void parseContactsElement(ContactsElement element, XmlPullParser parser,
      Hashtable relToTypeMap) throws XmlPullParserException {
    String rel = parser.getAttributeValue(null  /* ns */, "rel");
    String label = parser.getAttributeValue(null  /* ns */, "label");

    if ((label == null && rel == null) || (label != null && rel != null)) {
      // TODO: remove this once the focus feed is fixed to not send this case
      rel = TYPESTRING_OTHER;
    }

    if (rel != null) {
      final Object type = relToTypeMap.get(rel.toLowerCase());
      if (type == null) {
        throw new XmlPullParserException("unknown rel, " + rel);
      }
      element.setType(((Byte) type).byteValue());
    }
    element.setLabel(label);
    element.setIsPrimary("true".equals(parser.getAttributeValue(null  /* ns */, "primary")));
  }

  private static void handleOrganizationSubElement(Organization element, XmlPullParser parser)
    throws XmlPullParserException, IOException {
    int depth = parser.getDepth();
    while (true) {
      String tag = XmlUtils.nextDirectChildTag(parser, depth);
      if (tag == null) break;
      if ("orgName".equals(tag)) {
        element.setName(XmlUtils.extractChildText(parser));
      } else if ("orgTitle".equals(tag)) {
        element.setTitle(XmlUtils.extractChildText(parser));
      }
    }
  }

  /**
   * Parse the ExtendedProperty. The parser is assumed to be at the beginning of the tag
   * for the ExtendedProperty.
   * @param extendedProperty the ExtendedProperty object to populate
   */
  private void parseExtendedProperty(ExtendedProperty extendedProperty)
      throws IOException, XmlPullParserException {
    XmlPullParser parser = getParser();
    extendedProperty.setName(parser.getAttributeValue(null  /* ns */, "name"));
    extendedProperty.setValue(parser.getAttributeValue(null  /* ns */, "value"));
    extendedProperty.setXmlBlob(XmlUtils.extractFirstChildTextIgnoreRest(parser));
  }
}
