// Copyright 2008 The Android Open Source Project

package com.google.wireless.gdata.parser.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@link GDataParser} that uses an {@link XmlPullParser} to parse a GData feed.
 */
// NOTE: we do not perform any validity checks on the XML.
public class XmlGDataParser implements GDataParser {

  /** Namespace URI for Atom */
  public static final String NAMESPACE_ATOM_URI =
      "http://www.w3.org/2005/Atom";

  public static final String NAMESPACE_OPENSEARCH = "openSearch";

  public static final String NAMESPACE_OPENSEARCH_URI =
      "http://a9.com/-/spec/opensearchrss/1.0/";

  /** Namespace prefix for GData */
  public static final String NAMESPACE_GD = "gd";

  /** Namespace URI for GData */
  public static final String NAMESPACE_GD_URI =
      "http://schemas.google.com/g/2005";

  private final InputStream is;
  private final XmlPullParser parser;
  private boolean isInBadState;

  /**
   * Creates a new XmlGDataParser for a feed in the provided InputStream.
   * @param is The InputStream that should be parsed.
   * @throws ParseException Thrown if an XmlPullParser could not be created
   * or set around this InputStream.
   */
  public XmlGDataParser(InputStream is, XmlPullParser parser)
      throws ParseException {
    this.is = is;
    this.parser = parser;
    this.isInBadState = false;
    if (this.is != null) {
      try {
        this.parser.setInput(is, null /* encoding */);
      } catch (XmlPullParserException e) {
        throw new ParseException("Could not create XmlGDataParser", e);
      }
    }
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.GDataParser#init()
  */
  public final Feed init() throws ParseException {
    int eventType;
    try {
      eventType = parser.getEventType();
    } catch (XmlPullParserException e) {
      throw new ParseException("Could not parse GData feed.", e);
    }
    if (eventType != XmlPullParser.START_DOCUMENT) {
      throw new ParseException("Attempting to initialize parsing beyond "
          + "the start of the document.");
    }

    try {
      eventType = parser.next();
    } catch (XmlPullParserException xppe) {
      throw new ParseException("Could not read next event.", xppe);
    } catch (IOException ioe) {
      throw new ParseException("Could not read next event.", ioe);
    }
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          if ("feed".equals(name)) {
            try {
              return parseFeed();
            } catch (XmlPullParserException xppe) {
              throw new ParseException("Unable to parse <feed>.",
                  xppe);
            } catch (IOException ioe) {
              throw new ParseException("Unable to parse <feed>.",
                  ioe);
            }
          }
          break;
        default:
          // ignore
          break;
      }

      try {
        eventType = parser.next();
      } catch (XmlPullParserException xppe) {
        throw new ParseException("Could not read next event.", xppe);
      } catch (IOException ioe) {
        throw new ParseException("Could not read next event." , ioe);
      }
    }
    throw new ParseException("No <feed> found in document.");
  }

  /**
   * Returns the {@link XmlPullParser} being used to parse this feed.
   */
  protected final XmlPullParser getParser() {
    return parser;
  }

  /**
   * Creates a new {@link Feed} that should be filled with information about
   * the feed that will be parsed.
   * @return The {@link Feed} that should be filled.
   */
  protected Feed createFeed() {
    return new Feed();
  }

  /**
   * Creates a new {@link Entry} that should be filled with information about
   * the entry that will be parsed.
   * @return The {@link Entry} that should be filled.
   */
  protected Entry createEntry() {
    return new Entry();
  }

  /**
   * Parses the feed (but not any entries).
   *
   * @return A new {@link Feed} containing information about the feed.
   * @throws XmlPullParserException Thrown if the XML document cannot be
   * parsed.
   * @throws IOException Thrown if the {@link InputStream} behind the feed
   * cannot be read.
   */
  private final Feed parseFeed() throws XmlPullParserException, IOException {
    Feed feed = createFeed();
    // parsing <feed>
    // not interested in any attributes -- move onto the children.
    int eventType = parser.next();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          if ("totalResults".equals(name)) {
            feed.setTotalResults(StringUtils.parseInt(
                XmlUtils.extractChildText(parser), 0));
          } else if ("startIndex".equals(name)) {
            feed.setStartIndex(StringUtils.parseInt(
                XmlUtils.extractChildText(parser), 0));
          } else if ("itemsPerPage".equals(name)) {
            feed.setItemsPerPage(StringUtils.parseInt(
                XmlUtils.extractChildText(parser), 0));
          } else if ("title".equals(name)) {
            feed.setTitle(XmlUtils.extractChildText(parser));
          } else if ("id".equals(name)) {
            feed.setId(XmlUtils.extractChildText(parser));
          } else if ("updated".equals(name)) {
            feed.setLastUpdated(XmlUtils.extractChildText(parser));
          } else if ("category".equals(name)) {
            String category =
                parser.getAttributeValue(null /* ns */, "term");
            if (!StringUtils.isEmpty(category)) {
              feed.setCategory(category);
            }
            String categoryScheme =
                parser.getAttributeValue(null /* ns */, "scheme");
            if (!StringUtils.isEmpty(categoryScheme)) {
              feed.setCategoryScheme(categoryScheme);
            }
          } else if ("entry".equals(name)) {
            // stop parsing here.
            // TODO: pay attention to depth?
            return feed;
          } else {
            handleExtraElementInFeed(feed);
          }
          break;
        default:
          break;
      }
      eventType = parser.next();
    }
    // if we get here, we have a feed with no entries.
    return feed;
  }

  /**
   * Hook that allows extra (service-specific) elements in a &lt;feed&gt; to
   * be parsed.
   * @param feed The {@link Feed} being filled.
   */
  protected void handleExtraElementInFeed(Feed feed)
      throws XmlPullParserException, IOException {
    // no-op in this class.
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.GDataParser#hasMoreData()
  */
  public boolean hasMoreData() {
    if (isInBadState) {
      return false;
    }
    try {
      int eventType = parser.getEventType();
      return (eventType != XmlPullParser.END_DOCUMENT);
    } catch (XmlPullParserException xppe) {
      return false;
    }
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.GDataParser#readNextEntry
  */
  public Entry readNextEntry(Entry entry) throws ParseException, IOException {
    if (!hasMoreData()) {
      throw new IllegalStateException("you shouldn't call this if hasMoreData() is false");
    }

    int eventType;
    try {
      eventType = parser.getEventType();
    } catch (XmlPullParserException e) {
      throw new ParseException("Could not parse entry.", e);
    }

    if (eventType != XmlPullParser.START_TAG) {
      throw new ParseException("Expected event START_TAG: Actual event: "
          + XmlPullParser.TYPES[eventType]);
    }

    String name = parser.getName();
    if (!"entry".equals(name)) {
      throw new ParseException("Expected <entry>: Actual element: "
          + "<" + name + ">");
    }

    if (entry == null) {
      entry = createEntry();
    } else {
      entry.clear();
    }

    try {
      parser.next();
      handleEntry(entry);
      entry.validate();
    } catch (ParseException xppe1) {
      try {
        if (hasMoreData()) skipToNextEntry();
      } catch (XmlPullParserException xppe2) {
        // squelch the error -- let the original one stand.
        // set isInBadState to ensure that the next call to hasMoreData() will return false.
        isInBadState = true;
      }
      throw new ParseException("Could not parse <entry>, " + entry, xppe1);
    } catch (XmlPullParserException xppe1) {
      try {
        if (hasMoreData()) skipToNextEntry();
      } catch (XmlPullParserException xppe2) {
        // squelch the error -- let the original one stand.
        // set isInBadState to ensure that the next call to hasMoreData() will return false.
        isInBadState = true;
      }
      throw new ParseException("Could not parse <entry>, " + entry, xppe1);
    }
    return entry;
  }

  /**
   * Parses a GData entry.  You can either call {@link #init()} or
   * {@link #parseStandaloneEntry()} for a given feed.
   *
   * @return The parsed entry.
   * @throws ParseException Thrown if the entry could not be parsed.
   */
  public Entry parseStandaloneEntry() throws ParseException, IOException {
    Entry entry = createEntry();

    int eventType;
    try {
      eventType = parser.getEventType();
    } catch (XmlPullParserException e) {
      throw new ParseException("Could not parse GData entry.", e);
    }
    if (eventType != XmlPullParser.START_DOCUMENT) {
      throw new ParseException("Attempting to initialize parsing beyond "
          + "the start of the document.");
    }

    try {
      eventType = parser.next();
    } catch (XmlPullParserException xppe) {
      throw new ParseException("Could not read next event.", xppe);
    } catch (IOException ioe) {
      throw new ParseException("Could not read next event.", ioe);
    }
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          if ("entry".equals(name)) {
            try {
              parser.next();
              handleEntry(entry);
              return entry;
            } catch (XmlPullParserException xppe) {
              throw new ParseException("Unable to parse <entry>.",
                  xppe);
            } catch (IOException ioe) {
              throw new ParseException("Unable to parse <entry>.",
                  ioe);
            }
          }
          break;
        default:
          // ignore
          break;
      }

      try {
        eventType = parser.next();
      } catch (XmlPullParserException xppe) {
        throw new ParseException("Could not read next event.", xppe);
      }
    }
    throw new ParseException("No <entry> found in document.");
  }

  /**
   * Skips the rest of the current entry until the parser reaches the next entry, if any.
   * Does nothing if the parser is already at the beginning of an entry.
   */
  protected void skipToNextEntry() throws IOException, XmlPullParserException {
    if (!hasMoreData()) {
      throw new IllegalStateException("you shouldn't call this if hasMoreData() is false");
    }

    int eventType = parser.getEventType();

    // skip ahead until we reach an <entry> tag.
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          if ("entry".equals(parser.getName())) {
            return;
          }
          break;
      }
      eventType = parser.next();
    }
  }

  /**
   * Parses the current entry in the XML document.  Assumes that the parser
   * is currently pointing just after an &lt;entry&gt;.
   *
   * @param entry The entry that will be filled.
   * @throws XmlPullParserException Thrown if the XML cannot be parsed.
   * @throws IOException Thrown if the underlying inputstream cannot be read.
   */
  protected void handleEntry(Entry entry)
      throws XmlPullParserException, IOException, ParseException {
    int eventType = parser.getEventType();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          // TODO: make sure these elements are at the expected depth.
          String name = parser.getName();
          if ("entry".equals(name)) {
            // stop parsing here.
            return;
          } else if ("id".equals(name)) {
            entry.setId(XmlUtils.extractChildText(parser));
          } else if ("title".equals(name)) {
            entry.setTitle(XmlUtils.extractChildText(parser));
          } else if ("link".equals(name)) {
            String rel =
                parser.getAttributeValue(null /* ns */, "rel");
            String type =
                parser.getAttributeValue(null /* ns */, "type");
            String href =
                parser.getAttributeValue(null /* ns */, "href");
            if ("edit".equals(rel)) {
              entry.setEditUri(href);
            } else if (("alternate").equals(rel) && ("text/html".equals(type))) {
                entry.setHtmlUri(href);
            } else {
              handleExtraLinkInEntry(rel,
                  type,
                  href,
                  entry);
            }
          } else if ("summary".equals(name)) {
            entry.setSummary(XmlUtils.extractChildText(parser));
          } else if ("content".equals(name)) {
            // TODO: parse the type
            entry.setContent(XmlUtils.extractChildText(parser));
          } else if ("author".equals(name)) {
            handleAuthor(entry);
          } else if ("category".equals(name)) {
            String category =
                parser.getAttributeValue(null /* ns */, "term");
            if (category != null && category.length() > 0) {
              entry.setCategory(category);
            }
            String categoryScheme =
                parser.getAttributeValue(null /* ns */, "scheme");
            if (categoryScheme != null && category.length() > 0) {
              entry.setCategoryScheme(categoryScheme);
            }
          } else if ("published".equals(name)) {
            entry.setPublicationDate(
                XmlUtils.extractChildText(parser));
          } else if ("updated".equals(name)) {
            entry.setUpdateDate(XmlUtils.extractChildText(parser));
          } else if ("deleted".equals(name)) {
            entry.setDeleted(true);
          } else {
            handleExtraElementInEntry(entry);
          }
          break;
        default:
          break;
      }

      eventType = parser.next();
    }
  }

  private void handleAuthor(Entry entry)
      throws XmlPullParserException, IOException {

    int eventType = parser.getEventType();
    String name = parser.getName();

    if (eventType != XmlPullParser.START_TAG ||
        (!"author".equals(parser.getName()))) {
      // should not happen.
      throw new
          IllegalStateException("Expected <author>: Actual element: <"
          + parser.getName() + ">");
    }

    eventType = parser.next();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          name = parser.getName();
          if ("name".equals(name)) {
            String authorName = XmlUtils.extractChildText(parser);
            entry.setAuthor(authorName);
          } else if ("email".equals(name)) {
            String email = XmlUtils.extractChildText(parser);
            entry.setEmail(email);
          }
          break;
        case XmlPullParser.END_TAG:
          name = parser.getName();
          if ("author".equals(name)) {
            return;
          }
        default:
          // ignore
      }

      eventType = parser.next();
    }
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata.parser.GDataParser#close()
  */
  public void close() {
    if (is != null) {
      try {
        is.close();
      } catch (IOException ioe) {
        // ignore
      }
    }
  }

  /**
   * Hook that allows extra (service-specific) elements in an &lt;entry&gt;
   * to be parsed.
   * @param entry The {@link Entry} being filled.
   */
  protected void handleExtraElementInEntry(Entry entry)
      throws XmlPullParserException, IOException, ParseException {
    // no-op in this class.
  }

  /**
   * Hook that allows extra (service-specific) &lt;link&gt;s in an entry to be
   * parsed.
   * @param rel The rel attribute value.
   * @param type The type attribute value.
   * @param href The href attribute value.
   * @param entry The {@link Entry} being filled.
   */
  protected void handleExtraLinkInEntry(String rel,
      String type,
      String href,
      Entry entry)
      throws XmlPullParserException, IOException {
    // no-op in this class.
  }
}
