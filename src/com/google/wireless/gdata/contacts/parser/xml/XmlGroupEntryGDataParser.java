package com.google.wireless.gdata.contacts.parser.xml;

import com.google.wireless.gdata.contacts.data.GroupEntry;
import com.google.wireless.gdata.contacts.data.GroupsFeed;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * GDataParser for a contact groups feed.
 */
public class XmlGroupEntryGDataParser extends XmlGDataParser {
  /**
   * Creates a new XmlGroupEntryGDataParser.
   * @param is The InputStream that should be parsed.
   * @param parser the XmlPullParser to use for the xml parsing
   * @throws ParseException Thrown if a parser cannot be created.
   */
  public XmlGroupEntryGDataParser(InputStream is, XmlPullParser parser) throws ParseException {
    super(is, parser);
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createFeed()
  */
  protected Feed createFeed() {
    return new GroupsFeed();
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createEntry()
  */
  protected Entry createEntry() {
    return new GroupEntry();
  }

  protected void handleExtraElementInEntry(Entry entry) {
    XmlPullParser parser = getParser();

    if (!(entry instanceof GroupEntry)) {
      throw new IllegalArgumentException("Expected GroupEntry!");
    }
    GroupEntry groupEntry = (GroupEntry) entry;
    String name = parser.getName();
    if ("systemGroup".equals(name)) {
      String systemGroup = parser.getAttributeValue(null /* ns */, "id");
      // if the systemGroup is the empty string, convert it to a null
      if (StringUtils.isEmpty(systemGroup)) systemGroup = null;
      groupEntry.setSystemGroup(systemGroup);
    }
  }
}
