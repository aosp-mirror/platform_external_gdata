// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.parser.xml;

import com.google.wireless.gdata2.data.MediaEntry;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.parser.ParseException;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * GDataParser for a MediaEntry. This must only be used to parse an entry, not a feed, since
 * there is no such thing as a feed of media entries.
 */
public class XmlMediaEntryGDataParser extends XmlGDataParser {
  /**
   * Creates a new XmlMediaEntryGDataParser.
   * @param is The InputStream that should be parsed.
   * @param parser the XmlPullParser to use for the xml parsing
   * @throws com.google.wireless.gdata2.parser.ParseException Thrown if a parser cannot be created.
   */
  public XmlMediaEntryGDataParser(InputStream is, XmlPullParser parser) throws ParseException {
    super(is, parser);
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata2.parser.xml.XmlGDataParser#createFeed()
  */
  protected Feed createFeed() {
    throw new IllegalStateException("there is no such thing as a feed of media entries");
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata2.parser.xml.XmlGDataParser#createEntry()
  */
  protected Entry createEntry() {
    return new MediaEntry();
  }
}
