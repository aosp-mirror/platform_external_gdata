// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.contacts.parser.xml;

import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.contacts.data.ContactEntry;
import com.google.wireless.gdata2.contacts.data.GroupEntry;
import com.google.wireless.gdata2.data.MediaEntry;
import com.google.wireless.gdata2.contacts.serializer.xml.XmlContactEntryGDataSerializer;
import com.google.wireless.gdata2.contacts.serializer.xml.XmlGroupEntryGDataSerializer;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.parser.xml.XmlMediaEntryGDataParser;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import com.google.wireless.gdata2.serializer.xml.XmlBatchGDataSerializer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.Enumeration;

/**
 * GDataParserFactory that creates XML GDataParsers and GDataSerializers for
 * Google Contacts.
 */
public class XmlContactsGDataParserFactory implements GDataParserFactory {

  private final XmlParserFactory xmlFactory;

  public XmlContactsGDataParserFactory(XmlParserFactory xmlFactory) {
    this.xmlFactory = xmlFactory;
  }

    /**
     * Returns a parser for a contacts group feed.
     *
     * @param is The input stream to be parsed.
     * @return A parser for the stream.
     * @throws com.google.wireless.gdata2.parser.ParseException
     */
    public GDataParser createGroupEntryFeedParser(InputStream is) throws ParseException {
      XmlPullParser xmlParser;
      try {
        xmlParser = xmlFactory.createParser();
      } catch (XmlPullParserException xppe) {
        throw new ParseException("Could not create XmlPullParser", xppe);
      }
      return new XmlGroupEntryGDataParser(is, xmlParser);
    }

  /**
   * Returns a parser for a media entry feed.
   *
   * @param is The input stream to be parsed.
   * @return A parser for the stream.
   * @throws ParseException
   */
  public GDataParser createMediaEntryFeedParser(InputStream is) throws ParseException {
    XmlPullParser xmlParser;
    try {
      xmlParser = xmlFactory.createParser();
    } catch (XmlPullParserException xppe) {
      throw new ParseException("Could not create XmlPullParser", xppe);
    }
    return new XmlMediaEntryGDataParser(is, xmlParser);
  }

  /*
  * (non-javadoc)
  *
  * @see GDataParserFactory#createParser
  */
  public GDataParser createParser(InputStream is) throws ParseException {
    XmlPullParser xmlParser;
    try {
      xmlParser = xmlFactory.createParser();
    } catch (XmlPullParserException xppe) {
      throw new ParseException("Could not create XmlPullParser", xppe);
    }
    return new XmlContactsGDataParser(is, xmlParser);
  }

  /*
  * (non-Javadoc)
  *
  * @see com.google.wireless.gdata2.client.GDataParserFactory#createParser(
  *      int, java.io.InputStream)
  */
  public GDataParser createParser(Class entryClass, InputStream is)
      throws ParseException {
    if (entryClass == ContactEntry.class) {
      return createParser(is);
    }
    if (entryClass == GroupEntry.class) {
      return createGroupEntryFeedParser(is);
    }
    if (entryClass == MediaEntry.class) {
      return createMediaEntryFeedParser(is);
    }
    throw new IllegalArgumentException("unexpected feed type, " + entryClass.getName());
  }

  /**
   * Creates a new {@link GDataSerializer} for the provided entry. The entry
   * <strong>must</strong> be an instance of {@link ContactEntry} or {@link GroupEntry}.
   *
   * @param entry The {@link ContactEntry} that should be serialized.
   * @return The {@link GDataSerializer} that will serialize this entry.
   * @throws IllegalArgumentException Thrown if entry is not a
   *         {@link ContactEntry} or {@link GroupEntry}.
   * @see com.google.wireless.gdata2.client.GDataParserFactory#createSerializer
   */
  public GDataSerializer createSerializer(Entry entry) {
    if (entry instanceof ContactEntry) {
      ContactEntry contactEntry = (ContactEntry) entry;
      return new XmlContactEntryGDataSerializer(xmlFactory, contactEntry);
    }
    if (entry instanceof GroupEntry) {
      GroupEntry groupEntry = (GroupEntry) entry;
      return new XmlGroupEntryGDataSerializer(xmlFactory, groupEntry);
    }
    throw new IllegalArgumentException("unexpected entry type, " + entry.getClass().toString());
  }

  /**
   * Creates a new {@link GDataSerializer} for the given batch.
   *
   * @param batch the {@link Enumeration} of entries in the batch.
   * @return The {@link GDataSerializer} that will serialize this batch.
   */
  public GDataSerializer createSerializer(Enumeration batch) {
    return new XmlBatchGDataSerializer(this, xmlFactory, batch);
  }
}
