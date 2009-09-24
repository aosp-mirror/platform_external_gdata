// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.parser.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.google.wireless.gdata2.contacts.data.*;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.ExtendedProperty;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.data.XmlUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;

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

    /** The phone number type gdata string. */
  private static final String GD_NAMESPACE = "http://schemas.google.com/g/2005#";
  public static final String TYPESTRING_MOBILE = GD_NAMESPACE + "mobile";
  public static final String TYPESTRING_HOME = GD_NAMESPACE + "home";
  public static final String TYPESTRING_WORK = GD_NAMESPACE + "work";
  public static final String TYPESTRING_HOME_FAX = GD_NAMESPACE + "home_fax";
  public static final String TYPESTRING_WORK_FAX = GD_NAMESPACE + "work_fax";
  public static final String TYPESTRING_PAGER = GD_NAMESPACE + "pager";
  public static final String TYPESTRING_ASSISTANT = GD_NAMESPACE + "assistant";
  public static final String TYPESTRING_CALLBACK = GD_NAMESPACE + "callback";
  public static final String TYPESTRING_CAR = GD_NAMESPACE + "car";
  public static final String TYPESTRING_COMPANY_MAIN = GD_NAMESPACE + "company_main";
  public static final String TYPESTRING_ISDN = GD_NAMESPACE + "isdn";
  public static final String TYPESTRING_MAIN = GD_NAMESPACE + "main";
  public static final String TYPESTRING_OTHER_FAX = GD_NAMESPACE + "other_fax";
  public static final String TYPESTRING_RADIO = GD_NAMESPACE + "radio";
  public static final String TYPESTRING_TELEX = GD_NAMESPACE + "telex";
  public static final String TYPESTRING_TTY_TDD = GD_NAMESPACE + "tty_tdd";
  public static final String TYPESTRING_WORK_MOBILE = GD_NAMESPACE + "work_mobile";
  public static final String TYPESTRING_WORK_PAGER = GD_NAMESPACE + "work_pager";
  public static final String TYPESTRING_OTHER = GD_NAMESPACE + "other";

  public static final String IM_PROTOCOL_AIM = GD_NAMESPACE + "AIM";
  public static final String IM_PROTOCOL_MSN = GD_NAMESPACE + "MSN";
  public static final String IM_PROTOCOL_YAHOO = GD_NAMESPACE + "YAHOO";
  public static final String IM_PROTOCOL_SKYPE = GD_NAMESPACE + "SKYPE";
  public static final String IM_PROTOCOL_QQ = GD_NAMESPACE + "QQ";
  public static final String IM_PROTOCOL_GOOGLE_TALK = GD_NAMESPACE + "GOOGLE_TALK";
  public static final String IM_PROTOCOL_ICQ = GD_NAMESPACE + "ICQ";
  public static final String IM_PROTOCOL_JABBER = GD_NAMESPACE + "JABBER";
  public static final String IM_PROTOCOL_NETMEETING = GD_NAMESPACE + "netmeeting";

  public static final String TYPESTRING_CALENDARLINK_HOME = "home";
  public static final String TYPESTRING_CALENDARLINK_WORK = "work";
  public static final String TYPESTRING_CALENDARLINK_FREEBUSY = "free-busy";

  public static final String TYPESTRING_EVENT_ANNIVERARY = "anniversary";
  public static final String TYPESTRING_EVENT_OTHER = "other";

  public static final String TYPESTRING_EXTERNALID_ACCOUNT = "account";
  public static final String TYPESTRING_EXTERNALID_CUSTOMER = "customer";
  public static final String TYPESTRING_EXTERNALID_NETWORK = "network";
  public static final String TYPESTRING_EXTERNALID_ORGANIZATION = "organization";

  public static final String TYPESTRING_JOT_HOME = TYPESTRING_CALENDARLINK_HOME;
  public static final String TYPESTRING_JOT_WORK = TYPESTRING_CALENDARLINK_WORK;
  public static final String TYPESTRING_JOT_OTHER = TYPESTRING_EVENT_OTHER;
  public static final String TYPESTRING_JOT_KEYWORDS = "keywords";
  public static final String TYPESTRING_JOT_USER = "user";

  public static final String TYPESTRING_PRIORITY_HIGH = "high";
  public static final String TYPESTRING_PRIORITY_LOW = "low";
  public static final String TYPESTRING_PRIORITY_NORMAL = "normal";

  public static final String TYPESTRING_RELATION_ASSISTANT = "assistant";
  public static final String TYPESTRING_RELATION_BROTHER = "brother";
  public static final String TYPESTRING_RELATION_CHILD = "child";
  public static final String TYPESTRING_RELATION_DOMESTICPARTNER = "domestic-partner";
  public static final String TYPESTRING_RELATION_FATHER = "father";
  public static final String TYPESTRING_RELATION_FRIEND = "friend";
  public static final String TYPESTRING_RELATION_MANAGER = "manager";
  public static final String TYPESTRING_RELATION_MOTHER = "mother";
  public static final String TYPESTRING_RELATION_PARENT = "parent";
  public static final String TYPESTRING_RELATION_PARTNER = "partner";
  public static final String TYPESTRING_RELATION_REFERREDBY = "referred-by";
  public static final String TYPESTRING_RELATION_RELATIVE = "relative";
  public static final String TYPESTRING_RELATION_SISTER = "sister";
  public static final String TYPESTRING_RELATION_SPOUSE = "spouse";

  public static final String TYPESTRING_SENSITIVITY_CONFIDENTIAL = "confidential";
  public static final String TYPESTRING_SENSITIVITY_NORMAL = "normal";
  public static final String TYPESTRING_SENSITIVITY_PERSONAL = "personal";
  public static final String TYPESTRING_SENSITIVITY_PRIVATE = "private";

  public static final String TYPESTRING_WEBSITE_HOMEPAGE = "home-page";
  public static final String TYPESTRING_WEBSITE_BLOG = "blog";
  public static final String TYPESTRING_WEBSITE_PROFILE = "profile";
  public static final String TYPESTRING_WEBSITE_HOME = TYPESTRING_CALENDARLINK_HOME;
  public static final String TYPESTRING_WEBSITE_WORK = TYPESTRING_CALENDARLINK_WORK;
  public static final String TYPESTRING_WEBSITE_OTHER = TYPESTRING_EVENT_OTHER;
  public static final String TYPESTRING_WEBSITE_FTP = "ftp";

  private static final Hashtable REL_TO_TYPE_EMAIL;
  private static final Hashtable REL_TO_TYPE_PHONE;
  private static final Hashtable REL_TO_TYPE_POSTAL;
  private static final Hashtable REL_TO_TYPE_IM;
  private static final Hashtable REL_TO_TYPE_ORGANIZATION;
  private static final Hashtable IM_PROTOCOL_STRING_TO_TYPE_MAP;
  private static final Hashtable REL_TO_TYPE_CALENDARLINK;
  private static final Hashtable REL_TO_TYPE_EVENT;
  private static final Hashtable REL_TO_TYPE_EXTERNALID;
  private static final Hashtable REL_TO_TYPE_JOT;
  private static final Hashtable REL_TO_TYPE_PRIORITY;
  private static final Hashtable REL_TO_TYPE_RELATION;
  private static final Hashtable REL_TO_TYPE_SENSITIVITY;
  private static final Hashtable REL_TO_TYPE_WEBSITE;

  public static final Hashtable TYPE_TO_REL_EMAIL;
  public static final Hashtable TYPE_TO_REL_PHONE;
  public static final Hashtable TYPE_TO_REL_POSTAL;
  public static final Hashtable TYPE_TO_REL_IM;
  public static final Hashtable TYPE_TO_REL_ORGANIZATION;
  public static final Hashtable IM_PROTOCOL_TYPE_TO_STRING_MAP;
  public static final Hashtable TYPE_TO_REL_CALENDARLINK;
  public static final Hashtable TYPE_TO_REL_EVENT;
  public static final Hashtable TYPE_TO_REL_EXTERNALID;
  public static final Hashtable TYPE_TO_REL_JOT;
  public static final Hashtable TYPE_TO_REL_PRIORITY;
  public static final Hashtable TYPE_TO_REL_RELATION;
  public static final Hashtable TYPE_TO_REL_SENSITIVITY;
  public static final Hashtable TYPE_TO_REL_WEBSITE;

  static {
    Hashtable map;

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(EmailAddress.TYPE_HOME));
    map.put(TYPESTRING_WORK, new Byte(EmailAddress.TYPE_WORK));
    map.put(TYPESTRING_OTHER, new Byte(EmailAddress.TYPE_OTHER));
    REL_TO_TYPE_EMAIL = map;
    TYPE_TO_REL_EMAIL = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(PhoneNumber.TYPE_HOME));
    map.put(TYPESTRING_MOBILE, new Byte(PhoneNumber.TYPE_MOBILE));
    map.put(TYPESTRING_PAGER, new Byte(PhoneNumber.TYPE_PAGER));
    map.put(TYPESTRING_WORK, new Byte(PhoneNumber.TYPE_WORK));
    map.put(TYPESTRING_HOME_FAX, new Byte(PhoneNumber.TYPE_HOME_FAX));
    map.put(TYPESTRING_WORK_FAX, new Byte(PhoneNumber.TYPE_WORK_FAX));
    map.put(TYPESTRING_ASSISTANT, new Byte(PhoneNumber.TYPE_ASSISTANT));
    map.put(TYPESTRING_CALLBACK, new Byte(PhoneNumber.TYPE_CALLBACK));
    map.put(TYPESTRING_CAR, new Byte(PhoneNumber.TYPE_CAR));
    map.put(TYPESTRING_COMPANY_MAIN, new Byte(PhoneNumber.TYPE_COMPANY_MAIN));
    map.put(TYPESTRING_ISDN, new Byte(PhoneNumber.TYPE_ISDN));
    map.put(TYPESTRING_MAIN, new Byte(PhoneNumber.TYPE_MAIN));
    map.put(TYPESTRING_OTHER_FAX, new Byte(PhoneNumber.TYPE_OTHER_FAX));
    map.put(TYPESTRING_RADIO, new Byte(PhoneNumber.TYPE_RADIO));
    map.put(TYPESTRING_TELEX, new Byte(PhoneNumber.TYPE_TELEX));
    map.put(TYPESTRING_TTY_TDD, new Byte(PhoneNumber.TYPE_TTY_TDD));
    map.put(TYPESTRING_WORK_MOBILE, new Byte(PhoneNumber.TYPE_WORK_MOBILE));
    map.put(TYPESTRING_WORK_PAGER, new Byte(PhoneNumber.TYPE_WORK_PAGER));
    map.put(TYPESTRING_OTHER, new Byte(PhoneNumber.TYPE_OTHER));
    REL_TO_TYPE_PHONE = map;
    TYPE_TO_REL_PHONE = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_HOME, new Byte(StructuredPostalAddress.TYPE_HOME));
    map.put(TYPESTRING_WORK, new Byte(StructuredPostalAddress.TYPE_WORK));
    map.put(TYPESTRING_OTHER, new Byte(StructuredPostalAddress.TYPE_OTHER));
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
    map.put(IM_PROTOCOL_NETMEETING, new Byte(ImAddress.PROTOCOL_NETMEETING));

    IM_PROTOCOL_STRING_TO_TYPE_MAP = map;
    IM_PROTOCOL_TYPE_TO_STRING_MAP = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_CALENDARLINK_HOME, new Byte(CalendarLink.TYPE_HOME));
    map.put(TYPESTRING_CALENDARLINK_WORK, new Byte(CalendarLink.TYPE_WORK));
    map.put(TYPESTRING_CALENDARLINK_FREEBUSY, new Byte(CalendarLink.TYPE_FREE_BUSY));
    REL_TO_TYPE_CALENDARLINK = map;
    TYPE_TO_REL_CALENDARLINK = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_EVENT_ANNIVERARY, new Byte(Event.TYPE_ANNIVERSARY));
    map.put(TYPESTRING_EVENT_OTHER, new Byte(Event.TYPE_OTHER));
    REL_TO_TYPE_EVENT = map;
    TYPE_TO_REL_EVENT = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_EXTERNALID_ACCOUNT, new Byte(ExternalId.TYPE_ACCOUNT));
    map.put(TYPESTRING_EXTERNALID_CUSTOMER, new Byte(ExternalId.TYPE_CUSTOMER));
    map.put(TYPESTRING_EXTERNALID_NETWORK, new Byte(ExternalId.TYPE_NETWORK));
    map.put(TYPESTRING_EXTERNALID_ORGANIZATION, new Byte(ExternalId.TYPE_ORGANIZATION));
    REL_TO_TYPE_EXTERNALID = map;
    TYPE_TO_REL_EXTERNALID = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_JOT_HOME, new Byte(Jot.TYPE_HOME));
    map.put(TYPESTRING_JOT_KEYWORDS, new Byte(Jot.TYPE_KEYWORDS));
    map.put(TYPESTRING_JOT_OTHER, new Byte(Jot.TYPE_OTHER));
    map.put(TYPESTRING_JOT_USER, new Byte(Jot.TYPE_USER));
    map.put(TYPESTRING_JOT_WORK, new Byte(Jot.TYPE_WORK));
    REL_TO_TYPE_JOT = map;
    TYPE_TO_REL_JOT = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_PRIORITY_HIGH, new Byte(ContactEntry.TYPE_PRIORITY_HIGH));
    map.put(TYPESTRING_PRIORITY_NORMAL, new Byte(ContactEntry.TYPE_PRIORITY_NORMAL));
    map.put(TYPESTRING_PRIORITY_LOW, new Byte(ContactEntry.TYPE_PRIORITY_LOW));
    REL_TO_TYPE_PRIORITY = map;
    TYPE_TO_REL_PRIORITY = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_RELATION_ASSISTANT, new Byte(Relation.TYPE_ASSISTANT));
    map.put(TYPESTRING_RELATION_BROTHER, new Byte(Relation.TYPE_BROTHER));
    map.put(TYPESTRING_RELATION_CHILD, new Byte(Relation.TYPE_CHILD));
    map.put(TYPESTRING_RELATION_DOMESTICPARTNER, new Byte(Relation.TYPE_DOMESTICPARTNER));
    map.put(TYPESTRING_RELATION_FATHER, new Byte(Relation.TYPE_FATHER));
    map.put(TYPESTRING_RELATION_FRIEND, new Byte(Relation.TYPE_FRIEND));
    map.put(TYPESTRING_RELATION_MANAGER, new Byte(Relation.TYPE_MANAGER));
    map.put(TYPESTRING_RELATION_MOTHER, new Byte(Relation.TYPE_MOTHER));
    map.put(TYPESTRING_RELATION_PARENT, new Byte(Relation.TYPE_PARENT));
    map.put(TYPESTRING_RELATION_PARTNER, new Byte(Relation.TYPE_PARTNER));
    map.put(TYPESTRING_RELATION_REFERREDBY, new Byte(Relation.TYPE_REFERREDBY));
    map.put(TYPESTRING_RELATION_RELATIVE, new Byte(Relation.TYPE_RELATIVE));
    map.put(TYPESTRING_RELATION_SISTER, new Byte(Relation.TYPE_SISTER));
    map.put(TYPESTRING_RELATION_SPOUSE, new Byte(Relation.TYPE_SPOUSE));
    REL_TO_TYPE_RELATION = map;
    TYPE_TO_REL_RELATION = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_SENSITIVITY_CONFIDENTIAL,
            new Byte(ContactEntry.TYPE_SENSITIVITY_CONFIDENTIAL));
    map.put(TYPESTRING_SENSITIVITY_NORMAL,
            new Byte(ContactEntry.TYPE_SENSITIVITY_NORMAL));
    map.put(TYPESTRING_SENSITIVITY_PERSONAL,
            new Byte(ContactEntry.TYPE_SENSITIVITY_PERSONAL));
    map.put(TYPESTRING_SENSITIVITY_PRIVATE,
            new Byte(ContactEntry.TYPE_SENSITIVITY_PRIVATE));
    REL_TO_TYPE_SENSITIVITY= map;
    TYPE_TO_REL_SENSITIVITY = swapMap(map);

    map = new Hashtable();
    map.put(TYPESTRING_WEBSITE_BLOG, new Byte(WebSite.TYPE_BLOG));
    map.put(TYPESTRING_WEBSITE_HOMEPAGE, new Byte(WebSite.TYPE_HOMEPAGE));
    map.put(TYPESTRING_WEBSITE_PROFILE, new Byte(WebSite.TYPE_PROFILE));
    map.put(TYPESTRING_WEBSITE_HOME, new Byte(WebSite.TYPE_HOME));
    map.put(TYPESTRING_WEBSITE_WORK, new Byte(WebSite.TYPE_WORK));
    map.put(TYPESTRING_WEBSITE_OTHER, new Byte(WebSite.TYPE_OTHER));
    map.put(TYPESTRING_WEBSITE_FTP, new Byte(WebSite.TYPE_FTP));

    REL_TO_TYPE_WEBSITE = map;
    TYPE_TO_REL_WEBSITE = swapMap(map);

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
  * @see com.google.wireless.gdata2.parser.xml.XmlGDataParser#createFeed()
  */
  protected Feed createFeed() {
    return new ContactsFeed();
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata2.parser.xml.XmlGDataParser#createEntry()
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
    String ns = parser.getNamespace();

    if (XmlGDataParser.NAMESPACE_GD_URI.equals(ns)) {
      if (XmlNametable.GD_EMAIL.equals(name)) {
        EmailAddress emailAddress = new EmailAddress();
        parseContactsElement(emailAddress, parser, REL_TO_TYPE_EMAIL);
        emailAddress.setDisplayName(parser.getAttributeValue(null /* ns */,
                                XmlNametable.GD_EMAIL_DISPLAYNAME));
        emailAddress.setAddress(parser.getAttributeValue(null  /* ns */,
                                XmlNametable.GD_ADDRESS));
        contactEntry.addEmailAddress(emailAddress);
      } else if (XmlNametable.GD_DELETED.equals(name)) {
        contactEntry.setDeleted(true);
      } else if (XmlNametable.GD_IM.equals(name)) {
        ImAddress imAddress = new ImAddress();
        parseContactsElement(imAddress, parser, REL_TO_TYPE_IM);
        imAddress.setAddress(parser.getAttributeValue(null  /* ns */,
                                                     XmlNametable.GD_ADDRESS));
        imAddress.setLabel(parser.getAttributeValue(null  /* ns */,
                                                    XmlNametable.LABEL));
        String protocolString = parser.getAttributeValue(null  /* ns */,
                                                    XmlNametable.GD_PROTOCOL);
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
      } else if (XmlNametable.GD_SPA.equals(name)) {
        StructuredPostalAddress postalAddress = new StructuredPostalAddress();
        parseContactsElement(postalAddress, parser, REL_TO_TYPE_POSTAL);
        handleStructuredPostalAddressSubElement(postalAddress, parser);
        contactEntry.addPostalAddress(postalAddress);
      } else if (XmlNametable.GD_PHONENUMBER.equals(name)) {
        PhoneNumber phoneNumber = new PhoneNumber();
        parseContactsElement(phoneNumber, parser, REL_TO_TYPE_PHONE);
        phoneNumber.setPhoneNumber(XmlUtils.extractChildText(parser));
        contactEntry.addPhoneNumber(phoneNumber);
      } else if (XmlNametable.GD_ORGANIZATION.equals(name)) {
        Organization organization = new Organization();
        parseContactsElement(organization, parser, REL_TO_TYPE_ORGANIZATION);
        handleOrganizationSubElement(organization, parser);
        contactEntry.addOrganization(organization);
      } else if (XmlNametable.GD_EXTENDEDPROPERTY.equals(name)) {
        ExtendedProperty extendedProperty = new ExtendedProperty();
        parseExtendedProperty(extendedProperty);
        contactEntry.addExtendedProperty(extendedProperty);
      } else if (XmlNametable.GD_NAME.equals(name)) {
        Name n = new Name();
        handleNameSubElement(n, parser);
        contactEntry.setName(n);
      }
    } else if (XmlContactsGDataParser.NAMESPACE_CONTACTS_URI.equals(ns)) {
      if (XmlNametable.GC_GMI.equals(name)) {
        GroupMembershipInfo group = new GroupMembershipInfo();
        group.setGroup(parser.getAttributeValue(null  /* ns */,
                                                XmlNametable.HREF));
        group.setDeleted("true".equals(parser.getAttributeValue(null  /* ns */,
                                                XmlNametable.GD_DELETED)));
        contactEntry.addGroup(group);
      } else if (XmlNametable.GC_BIRTHDAY.equals(name)) {
        contactEntry.setBirthday(parser.getAttributeValue(null  /* ns */,
                                                          XmlNametable.GD_WHEN));
      } else if (XmlNametable.GC_BILLINGINFO.equals(name)) {
        contactEntry.setBillingInformation(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_CALENDARLINK.equals(name)) {
        CalendarLink cl = new CalendarLink();
        parseContactsElement(cl, parser, REL_TO_TYPE_CALENDARLINK);
        cl.setHRef(parser.getAttributeValue(null /* ns */, XmlNametable.HREF));
        contactEntry.addCalendarLink(cl);
      } else if (XmlNametable.GC_DIRECTORYSERVER.equals(name)) {
        contactEntry.setDirectoryServer(XmlUtils.extractChildText(parser));
      } else if ("event".equals(name)) {
        Event event = new Event();
        parseTypedElement(event, parser, REL_TO_TYPE_EVENT);
        handleEventSubElement(event, parser);
        contactEntry.addEvent(event);
      } else if (XmlNametable.GC_EXTERNALID.equals(name)) {
        ExternalId externalId = new ExternalId();
        parseTypedElement(externalId, parser, REL_TO_TYPE_EXTERNALID);
        externalId.setValue(parser.getAttributeValue(null /* ns */, XmlNametable.VALUE));
        contactEntry.addExternalId(externalId);
      } else if (XmlNametable.GC_GENDER.equals(name)) {
        contactEntry.setGender(parser.getAttributeValue(null /* ns */, XmlNametable.VALUE));
      } else if (XmlNametable.GC_HOBBY.equals(name)) {
        contactEntry.addHobby(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_INITIALS.equals(name)) {
        contactEntry.setInitials(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_JOT.equals(name)) {
        Jot jot = new Jot();
        parseTypedElement(jot, parser, REL_TO_TYPE_JOT);
        jot.setLabel(XmlUtils.extractChildText(parser));
        contactEntry.addJot(jot);
      } else if (XmlNametable.GC_LANGUAGE.equals(name)) {
        Language language = new Language();
        language.setCode(parser.getAttributeValue(null /* ns */, XmlNametable.CODE));
        language.setLabel(parser.getAttributeValue(null /* */, XmlNametable.LABEL));
        contactEntry.addLanguage(language);
      } else if (XmlNametable.GC_MAIDENNAME.equals(name)) {
        contactEntry.setMaidenName(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_MILEAGE.equals(name)) {
        contactEntry.setMileage(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_NICKNAME.equals(name)) {
        contactEntry.setNickname(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_OCCUPATION.equals(name)) {
        contactEntry.setOccupation(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_PRIORITY.equals(name)) {
        contactEntry.setPriority(relToType(
          parser.getAttributeValue(null /* ns */, XmlNametable.REL),
          REL_TO_TYPE_PRIORITY));
      } else if (XmlNametable.GC_RELATION.equals(name)) {
        Relation relation = new Relation();
        parseTypedElement(relation, parser, REL_TO_TYPE_RELATION);
        relation.setText(XmlUtils.extractChildText(parser));
        contactEntry.addRelation(relation);
      } else if (XmlNametable.GC_SENSITIVITY.equals(name)) {
        contactEntry.setSensitivity(relToType(
          parser.getAttributeValue(null /* ns */, XmlNametable.REL),
          REL_TO_TYPE_SENSITIVITY));
      } else if (XmlNametable.GC_SHORTNAME.equals(name)) {
        contactEntry.setShortName(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_SUBJECT.equals(name)) {
        contactEntry.setSubject(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GC_UDF.equals(name)) {
        UserDefinedField udf = new UserDefinedField();
        udf.setKey(parser.getAttributeValue(null /* ns */, XmlNametable.KEY));
        udf.setValue(parser.getAttributeValue(null /* ns */, XmlNametable.VALUE));
        contactEntry.addUserDefinedField(udf);
      } else if (XmlNametable.GC_WEBSITE.equals(name)) {
        WebSite ws = new WebSite();
        parseContactsElement(ws, parser, REL_TO_TYPE_WEBSITE);
        ws.setHRef(parser.getAttributeValue(null /* ns */, XmlNametable.HREF));
        contactEntry.addWebSite(ws);
      }
    }
  }

  protected void handleExtraLinkInEntry(String rel, String type, String href, Entry entry)
      throws XmlPullParserException, IOException {
    if (LINK_REL_PHOTO.equals(rel)) {
      ContactEntry contactEntry = (ContactEntry) entry;
      XmlPullParser parser = getParser();
      String etag = parser.getAttributeValue(NAMESPACE_GD_URI, XmlNametable.ETAG);
      contactEntry.setLinkPhoto(href, type, etag);
    }
  }

  private static void parseContactsElement(ContactsElement element, XmlPullParser parser,
      Hashtable relToTypeMap) throws XmlPullParserException {
    parseTypedElement(element, parser, relToTypeMap);
    element.setIsPrimary("true".equals(parser.getAttributeValue(null  /* ns */, XmlNametable.PRIMARY)));
  }

  private static void parseTypedElement(TypedElement element, XmlPullParser parser,
      Hashtable relToTypeMap) throws XmlPullParserException {
    String rel = parser.getAttributeValue(null  /* ns */, XmlNametable.REL);
    String label = parser.getAttributeValue(null  /* ns */, XmlNametable.LABEL);

    if ((label == null && rel == null) || (label != null && rel != null)) {
      // TODO: remove this once the focus feed is fixed to not send this case
      rel = TYPESTRING_OTHER;
    }

    if (rel != null) {
      element.setType(relToType(rel, relToTypeMap));
    }
    element.setLabel(label);
  }

  private static byte relToType(String rel, Hashtable relToTypeMap)
    throws XmlPullParserException {
    if (rel != null) {
      final Object type = relToTypeMap.get(rel.toLowerCase());
      if (type == null) {
        throw new XmlPullParserException("unknown rel, " + rel);
      }
      return ((Byte) type).byteValue();
    }
    return TypedElement.TYPE_NONE;
  }

  private static void handleOrganizationSubElement(Organization element, XmlPullParser parser)
    throws XmlPullParserException, IOException {
    int depth = parser.getDepth();
    while (true) {
      String tag = XmlUtils.nextDirectChildTag(parser, depth);
      if (tag == null) break;
      if (XmlNametable.GD_ORG_NAME.equals(tag)) {
        element.setName(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GD_ORG_TITLE.equals(tag)) {
        element.setTitle(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GD_ORG_DEPARTMENT.equals(tag)) {
        element.setOrgDepartment(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GD_ORG_JOBDESC.equals(tag)) {
        element.setOrgJobDescription(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GD_ORG_SYMBOL.equals(tag)) {
        element.setOrgSymbol(XmlUtils.extractChildText(parser));
      } else if (XmlNametable.GD_WHERE.equals(tag)) {
        String where = parser.getAttributeValue(null  /* ns */,
                                               XmlNametable.VALUESTRING);
        element.setWhere(where);
      }
    }
  }


  private static void handleEventSubElement(Event element, XmlPullParser parser)
    throws XmlPullParserException, IOException {
    int depth = parser.getDepth();
    while (true) {
      String tag = XmlUtils.nextDirectChildTag(parser, depth);
      if (tag == null) break;
      if (XmlNametable.GD_WHEN.equals(tag)) {
        String startDate = parser.getAttributeValue(null /* ns */,
                                                    XmlNametable.STARTTIME);
        element.setStartDate(startDate);
      }
    }
  }


  private static void handleNameSubElement(Name element, XmlPullParser parser)
    throws XmlPullParserException, IOException {
      int depth = parser.getDepth();
      while (true) {
        String tag = XmlUtils.nextDirectChildTag(parser, depth);
        if (tag == null) break;
        if (XmlNametable.GD_NAME_GIVENNAME.equals(tag)) {
          element.setGivenName(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_NAME_ADDITIONALNAME.equals(tag)) {
          element.setAdditionalNameYomi(
            parser.getAttributeValue(null /* ns */, XmlNametable.GD_NAME_YOMI));
          element.setAdditionalName(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_NAME_FAMILYNAME.equals(tag)) {
          element.setFamilyNameYomi(
            parser.getAttributeValue(null /* ns */, XmlNametable.GD_NAME_YOMI));
          element.setFamilyName(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_NAME_PREFIX.equals(tag)) {
          element.setNamePrefix(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_NAME_SUFFIX.equals(tag)) {
          element.setNameSuffix(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_NAME_FULLNAME.equals(tag)) {
          element.setFullName(XmlUtils.extractChildText(parser));
        }
      }
    }

  private static void handleStructuredPostalAddressSubElement(
          StructuredPostalAddress element, XmlPullParser parser)
    throws XmlPullParserException, IOException {
      int depth = parser.getDepth();
      while (true) {
        String tag = XmlUtils.nextDirectChildTag(parser, depth);
        if (tag == null) break;
        if (XmlNametable.GD_SPA_STREET.equals(tag)) {
          element.setStreet(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_POBOX.equals(tag)) {
          element.setPobox(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_NEIGHBORHOOD.equals(tag)) {
          element.setNeighborhood(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_CITY.equals(tag)) {
          element.setCity(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_REGION.equals(tag)) {
          element.setRegion(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_POSTCODE.equals(tag)) {
          element.setPostcode(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_COUNTRY.equals(tag)) {
          element.setCountry(XmlUtils.extractChildText(parser));
        } else if (XmlNametable.GD_SPA_FORMATTEDADDRESS.equals(tag)) {
          element.setFormattedAddress(XmlUtils.extractChildText(parser));
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
    extendedProperty.setName(parser.getAttributeValue(null  /* ns */, XmlNametable.GD_NAME));
    extendedProperty.setValue(parser.getAttributeValue(null  /* ns */, XmlNametable.VALUE));
    extendedProperty.setXmlBlob(XmlUtils.extractFirstChildTextIgnoreRest(parser));
  }
}
