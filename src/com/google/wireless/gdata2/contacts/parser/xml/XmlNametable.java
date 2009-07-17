// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.parser.xml;

/**
 * Class to hold static strings that are used to parse and 
 * serialize the xml elements in the contacts feed 
 */
public final class XmlNametable {
  private XmlNametable() {}
  
  // generic strings for attributes
  public static String HREF = "href";
  public static String LABEL = "label";
  public static String VALUE = "value";
  public static String CODE = "code";
  public static String REL = "rel";
  public static String KEY = "key";
  public static String ETAG = "etag";
  public static String VALUESTRING = "valueString";
  public static String STARTTIME = "startTime";
  public static String PRIMARY = "primary";

  // GD namespace
  public static String GD_EMAIL = "email";
  public static String GD_EMAIL_DISPLAYNAME = "displayName";
  public static String GD_ADDRESS = "address";
  public static String GD_PROTOCOL = "protocol";
  public static String GD_IM = "im";
  public static String GD_DELETED = "deleted";
  public static String GD_NAME = "name";
  public static String GD_NAME_GIVENNAME = "givenName";
  public static String GD_NAME_ADDITIONALNAME = "additionalName";
  public static String GD_NAME_YOMI = "yomi";
  public static String GD_NAME_FAMILYNAME = "familyName";
  public static String GD_NAME_PREFIX = "namePrefix";
  public static String GD_NAME_SUFFIX = "nameSuffix";
  public static String GD_NAME_FULLNAME = "fullName";
  public static String GD_SPA = "structuredPostalAddress";
  public static String GD_SPA_STREET = "street";
  public static String GD_SPA_POBOX = "pobox";
  public static String GD_SPA_NEIGHBORHOOD = "neighborhood";
  public static String GD_SPA_CITY = "city";
  public static String GD_SPA_REGION = "region";
  public static String GD_SPA_POSTCODE = "postcode";
  public static String GD_SPA_COUNTRY = "country";
  public static String GD_SPA_FORMATTEDADDRESS = "formattedAddress";   
  public static String GD_PHONENUMBER = "phoneNumber";
  public static String GD_ORGANIZATION = "organization";
  public static String GD_EXTENDEDPROPERTY = "extendedProperty";
  public static String GD_WHEN = "when";
  public static String GD_WHERE = "where";
  public static String GD_ORG_NAME = "orgName";
  public static String GD_ORG_TITLE = "orgTitle";
  public static String GD_ORG_DEPARTMENT = "orgDepartment";
  public static String GD_ORG_JOBDESC = "orgJobDescription";
  public static String GD_ORG_SYMBOL = "orgSymbol";

  // Contacts namespace
  public static String GC_GMI = "groupMembershipInfo"; 
  public static String GC_BIRTHDAY = "birthday";
  public static String GC_BILLINGINFO = "billingInformation";
  public static String GC_CALENDARLINK = "calendarLink";
  public static String GC_DIRECTORYSERVER = "directoryServer";
  public static String GC_EVENT = "event";
  public static String GC_EXTERNALID = "externalId";
  public static String GC_GENDER = "gender";
  public static String GC_HOBBY = "hobby";
  public static String GC_INITIALS = "initials";
  public static String GC_JOT = "jot";
  public static String GC_LANGUAGE = "language";
  public static String GC_MAIDENNAME = "maidenName";
  public static String GC_MILEAGE = "mileage";
  public static String GC_NICKNAME = "nickname";
  public static String GC_OCCUPATION = "occupation";
  public static String GC_PRIORITY = "priority";
  public static String GC_RELATION = "relation";
  public static String GC_SENSITIVITY = "sensitivity";
  public static String GC_SHORTNAME = "shortName";
  public static String GC_SUBJECT = "subject";
  public static String GC_UDF = "userDefinedField";
  public static String GC_WEBSITE ="website";
}