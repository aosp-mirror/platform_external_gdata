// Copyright 2008 The Android Open Source Project

package com.google.wireless.gdata2.parser.xml;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.Feed;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.data.XmlUtils;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.batch.BatchInterrupted;
import com.google.wireless.gdata2.data.batch.BatchStatus;
import com.google.wireless.gdata2.data.batch.BatchUtils;

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

  /** The openSearch namespace Uri */
  public static final String NAMESPACE_OPENSEARCH_URI =
      "http://a9.com/-/spec/opensearch/1.1/";

  /** Namespace prefix for GData */
  public static final String NAMESPACE_GD = "gd";

  /** Namespace URI for GData */
  public static final String NAMESPACE_GD_URI =
      "http://schemas.google.com/g/2005";

  /** Namespace prefix for GData batch operations */
  public static final String NAMESPACE_BATCH = "batch";

  /** Namespace uri for GData batch operations */
  public static final String NAMESPACE_BATCH_URI =
      "http://schemas.google.com/gdata/batch";

  private final InputStream is;
  private final XmlPullParser parser;
  private boolean isInBadState;
  private String fields;

  /**
   * Creates a new XmlGDataParser for a feed in the provided InputStream.
   * @param is The InputStream that should be parsed.
   * @param parser The xmlpullparser to be used
   * @throws ParseException Thrown if an XmlPullParser could not be created
   * or set around this InputStream.
   */
  public XmlGDataParser(InputStream is, XmlPullParser parser)
      throws ParseException {
    this.is = is;
    this.parser = parser;

    if (!parser.getFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES))
    {
      throw new IllegalStateException("A XmlGDataParser needs to be "
          + "constructed with a namespace aware XmlPullParser");
    }

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
  * @see com.google.wireless.gdata2.parser.GDataParser#parseFeedEnvelope()
  */
  public final Feed parseFeedEnvelope() throws ParseException {
    int eventType;
    fields = null;
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
          if (XmlNametable.PARTIAL.equals(name)) {
           try {
             return parsePartialFeed();
           } catch (XmlPullParserException xppe) {
             throw new ParseException("Unable to parse <partial> feed start", xppe);
           } catch (IOException ioe) {
             throw new ParseException("Unable to parse <partial> feed start", ioe);
           }
          } else if (XmlNametable.FEED.equals(name)) {
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
   * @return the {@link XmlPullParser} being used to parse this feed.
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
   * Parses the partial feed (but not any entries). This requires a
   * namespace enabled parser
   *
   * @return A new {@link Feed} containing information about the feed.
   * @throws XmlPullParserException Thrown if the XML document cannot be
   * parsed.
   * @throws IOException Thrown if the {@link InputStream} behind the feed
   * cannot be read.
   */
  private final Feed parsePartialFeed() throws XmlPullParserException, IOException {
    // first thing to do is get the attribute we care about from the partial element
    fields = parser.getAttributeValue(null /* ns */, XmlNametable.FIELDS);

    int eventType = parser.next();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          String namespace = parser.getNamespace();

          if (XmlGDataParser.NAMESPACE_ATOM_URI.equals(namespace)) {
            if (XmlNametable.FEED.equals(name)) {
              return parseFeed();
            }
          }
        default:
          break;
      }
      eventType = parser.next();
    }
    // if we get here, we have no feed
    return null;
  }


  /**
   * Parses the feed (but not any entries). This requires a
   * namespace enabled parser
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
   feed.setETag(parser.getAttributeValue(NAMESPACE_GD_URI, XmlNametable.ETAG));

    int eventType = parser.next();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          String namespace = parser.getNamespace();

          if (XmlGDataParser.NAMESPACE_OPENSEARCH_URI.equals(namespace)) {
            if (XmlNametable.TOTAL_RESULTS.equals(name)) {
              feed.setTotalResults(StringUtils.parseInt(
                  XmlUtils.extractChildText(parser), 0));
            } else if (XmlNametable.START_INDEX.equals(name)) {
              feed.setStartIndex(StringUtils.parseInt(
                  XmlUtils.extractChildText(parser), 0));
            } else if (XmlNametable.ITEMS_PER_PAGE.equals(name)) {
              feed.setItemsPerPage(StringUtils.parseInt(
                  XmlUtils.extractChildText(parser), 0));
            }
          } else if (XmlGDataParser.NAMESPACE_ATOM_URI.equals(namespace)) {
            if (XmlNametable.TITLE.equals(name)) {
              feed.setTitle(XmlUtils.extractChildText(parser));
            } else if (XmlNametable.ID.equals(name)) {
              feed.setId(XmlUtils.extractChildText(parser));
            } else if (XmlNametable.UPDATED.equals(name)) {
              feed.setLastUpdated(XmlUtils.extractChildText(parser));
            } else if (XmlNametable.CATEGORY.equals(name)) {
              String category =
                  parser.getAttributeValue(null /* ns */, XmlNametable.TERM);
              if (!StringUtils.isEmpty(category)) {
                feed.setCategory(category);
              }
              String categoryScheme =
                  parser.getAttributeValue(null /* ns */, XmlNametable.SCHEME);
              if (!StringUtils.isEmpty(categoryScheme)) {
                feed.setCategoryScheme(categoryScheme);
              }
            } else if (XmlNametable.ENTRY.equals(name)) {
              // stop parsing here.
              return feed;
            }
          } else {
            handleExtraElementInFeed(feed);
          }
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
   * @throws XmlPullParserException 
   * @throws IOException
   */
  protected void handleExtraElementInFeed(Feed feed)
      throws XmlPullParserException, IOException {
    // no-op in this class.
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata2.parser.GDataParser#hasMoreData()
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
  * @see com.google.wireless.gdata2.parser.GDataParser#readNextEntry
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
    // if, in the future, we have a batch feed with partial results, the next element
    // can be either an entry or a partial element

    if ((!XmlNametable.ENTRY.equals(name) &&
         !XmlNametable.PARTIAL.equals(name))) {
      throw new ParseException("Expected <entry> or <partial>: Actual element: "
          + "<" + name + ">");
    }

    if (entry == null) {
      entry = createEntry();
    } else {
      entry.clear();
    }

    try {
      if (XmlNametable.ENTRY.equals(name)) {
        handleEntry(entry);
      } else {
        handlePartialEntry(entry);
      }
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
   * Parses a GData entry.  You can either call {@link #parseFeedEnvelope()} or
   * {@link #parseStandaloneEntry()} for a given feed.
   *
   * @return The parsed entry.
   * @throws ParseException Thrown if the entry could not be parsed.
   */
  public Entry parseStandaloneEntry() throws ParseException, IOException {
    fields = null;
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
          if (XmlNametable.PARTIAL.equals(name)) {
            try {
              handlePartialEntry(entry);
              return entry;
            } catch (XmlPullParserException xppe) {
              throw new ParseException("Unable to parse <partial> entry.",
                  xppe);
            } catch (IOException ioe) {
              throw new ParseException("Unable to parse <partial> entry.",
                  ioe);
            }
          } else if (XmlNametable.ENTRY.equals(name)) {
            try {
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
   * @throws IOException
   * @throws XmlPullParserException
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
          if (XmlNametable.ENTRY.equals(parser.getName())) {
            return;
          }
          break;
      }
      eventType = parser.next();
    }
  }

  /**
   * Supply a 'skipSubTree' API which, for some reason, the kxml2 pull parser
   * hasn't implemented.
   * @throws IOException
   * @throws XmlPullParserException
   */
  protected void skipSubTree()
      throws XmlPullParserException, IOException {
    // Iterate the remaining structure for this element, discarding events
    // until we hit the element's corresponding end tag.
    int level = 1;
    while (level > 0) {
      int eventType = parser.next();
      switch (eventType) {
        case XmlPullParser.START_TAG:
          ++level;
          break;
        case XmlPullParser.END_TAG:
          --level;
          break;
        default:
          break;
      }
    }
  }

  /**
   * Parses the current partial start in the XML document. Assumes
   * that the parser is currently pointing just at the beginning
   * of an &lt;partial&gt;.
   *
   * @param entry The entry that will be filled.
   * @throws XmlPullParserException Thrown if the XML cannot be parsed.
   * @throws IOException Thrown if the underlying inputstream cannot be read.
   * @throws ParseException Thrown in the stream can not be parsed into gdata
   */
  protected void handlePartialEntry(Entry entry)
      throws XmlPullParserException, IOException, ParseException {
    // first thing we do is to get the attributes out of the parser for this entry
    // so we verify that we are at the start of an entry
    if (!XmlNametable.PARTIAL.equals(parser.getName())) {
       throw new
         IllegalStateException("Expected <partial>: Actual element: <"
         + parser.getName() + ">");
    }

    fields = parser.getAttributeValue(null /* ns */, XmlNametable.FIELDS);
    // now skip to the next parser event
    parser.next();

    int eventType = parser.getEventType();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          if (XmlNametable.ENTRY.equals(name)) {
            handleEntry(entry);
            return;
          }
        default:
          break;
      }
      eventType = parser.next();
    }
  }

  /**
   * Parses the current entry in the XML document.  Assumes that the parser
   * is currently pointing just at the beginning of an
   * &lt;entry&gt;.
   *
   * @param entry The entry that will be filled.
   * @throws XmlPullParserException Thrown if the XML cannot be parsed.
   * @throws IOException Thrown if the underlying inputstream cannot be read.
   * @throws ParseException Thrown in the stream can not be parsed into gdata
   */
  protected void handleEntry(Entry entry)
      throws XmlPullParserException, IOException, ParseException {
    // first thing we do is to get the attributes out of the parser for this entry
    // so we verify that we are at the start of an entry
    if (!XmlNametable.ENTRY.equals(parser.getName())) {
       throw new
         IllegalStateException("Expected <entry>: Actual element: <"
         + parser.getName() + ">");
    }

    entry.setETag(parser.getAttributeValue(NAMESPACE_GD_URI, XmlNametable.ETAG));
    entry.setFields(fields);
    // now skip to the next parser event
    parser.next();

    int eventType = parser.getEventType();
    while (eventType != XmlPullParser.END_DOCUMENT) {
      switch (eventType) {
        case XmlPullParser.START_TAG:
          String name = parser.getName();
          if (XmlNametable.ENTRY.equals(name)) {
            // stop parsing here.
            return;
          } 
          // for each start tag, we call our subclasses first. Only do the default 
          // processing, if they have not done it.
          if (handleDefaultEntryElements(entry)){
            break;
          }

          if (NAMESPACE_BATCH_URI.equals(parser.getNamespace())) {
            // We must check for the BATCH namespace first in case the tag name
            // is reused in another namespace. e.g. "id".
            handleBatchInfo(entry);
          } else if (XmlNametable.ID.equals(name)) {
            entry.setId(XmlUtils.extractChildText(parser));
          } else if (XmlNametable.TITLE.equals(name)) {
            entry.setTitle(XmlUtils.extractChildText(parser));
          } else if (XmlNametable.LINK.equals(name)) {
            String rel =
                parser.getAttributeValue(null /* ns */, XmlNametable.REL);
            String type =
                parser.getAttributeValue(null /* ns */, XmlNametable.TYPE);
            String href =
                parser.getAttributeValue(null /* ns */, XmlNametable.HREF);
            if (XmlNametable.EDIT_REL.equals(rel)) {
              entry.setEditUri(href);
            } else if (XmlNametable.ALTERNATE_REL.equals(rel)
                    && XmlNametable.TEXTHTML.equals(type)) {
                entry.setHtmlUri(href);
            } else {
              handleExtraLinkInEntry(rel,
                  type,
                  href,
                  entry);
            }
          } else if (XmlNametable.SUMMARY.equals(name)) {
            entry.setSummary(XmlUtils.extractChildText(parser));
          } else if (XmlNametable.CONTENT.equals(name)) {
            entry.setContentType(parser.getAttributeValue(null /* ns */, XmlNametable.TYPE));
            entry.setContentSource(parser.getAttributeValue(null /* ns */, XmlNametable.SRC));
            entry.setContent(XmlUtils.extractChildText(parser));
          } else if (XmlNametable.AUTHOR.equals(name)) {
            handleAuthor(entry);
          } else if (XmlNametable.CATEGORY.equals(name)) {
            String category =
                parser.getAttributeValue(null /* ns */, XmlNametable.TERM);
            if (category != null && category.length() > 0) {
              entry.setCategory(category);
            }
            String categoryScheme =
                parser.getAttributeValue(null /* ns */, XmlNametable.SCHEME);
            if (categoryScheme != null && category.length() > 0) {
              entry.setCategoryScheme(categoryScheme);
            }
          } else if (XmlNametable.PUBLISHED.equals(name)) {
            entry.setPublicationDate(
                XmlUtils.extractChildText(parser));
          } else if (XmlNametable.UPDATED.equals(name)) {
            entry.setUpdateDate(XmlUtils.extractChildText(parser));
          } else if (XmlNametable.DELETED.equals(name)) {
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
    entry.validate();
  }

  private void handleAuthor(Entry entry)
      throws XmlPullParserException, IOException {

    int eventType = parser.getEventType();
    String name = parser.getName();

    if (eventType != XmlPullParser.START_TAG ||
        (!XmlNametable.AUTHOR.equals(parser.getName()))) {
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
          if (XmlNametable.NAME.equals(name)) {
            String authorName = XmlUtils.extractChildText(parser);
            entry.setAuthor(authorName);
          } else if (XmlNametable.EMAIL.equals(name)) {
            String email = XmlUtils.extractChildText(parser);
            entry.setEmail(email);
          }
          break;
        case XmlPullParser.END_TAG:
          name = parser.getName();
          if (XmlNametable.AUTHOR.equals(name)) {
            return;
          }
        default:
          // ignore
      }

      eventType = parser.next();
    }
  }

  private void handleBatchInfo(Entry entry)
      throws IOException, XmlPullParserException {
    String name = parser.getName();
    if (XmlNametable.STATUS.equals(name)) {
      BatchStatus status = new BatchStatus();
      BatchUtils.setBatchStatus(entry, status);
      status.setStatusCode(getIntAttribute(parser, XmlNametable.CODE));
      status.setReason(getAttribute(parser, XmlNametable.REASON));
      status.setContentType(getAttribute(parser, XmlNametable.CONTENT_TYPE));
      // TODO: Read sub-tree into content.
      skipSubTree();
    } else if (XmlNametable.ID.equals(name)) {
      BatchUtils.setBatchId(entry, XmlUtils.extractChildText(parser));
    } else if (XmlNametable.OPERATION.equals(name)) {
      BatchUtils.setBatchOperation(entry, getAttribute(parser, XmlNametable.TYPE));
    } else if ("interrupted".equals(name)) {
      BatchInterrupted interrupted = new BatchInterrupted();
      BatchUtils.setBatchInterrupted(entry, interrupted);
      interrupted.setReason(getAttribute(parser, XmlNametable.REASON));
      interrupted.setErrorCount(getIntAttribute(parser, XmlNametable.ERROR));
      interrupted.setSuccessCount(getIntAttribute(parser, XmlNametable.SUCCESS));
      interrupted.setTotalCount(getIntAttribute(parser, XmlNametable.PARSED));
      // TODO: Read sub-tree into content.
      skipSubTree();
    } else {
      throw new XmlPullParserException("Unexpected batch element " + name);
    }
  }

  private static String getAttribute(XmlPullParser parser, String name) {
    return parser.getAttributeValue(null /* ns */, name);
  }

  private static int getIntAttribute(XmlPullParser parser, String name) {
    return Integer.parseInt(getAttribute(parser, name));
  }

  /*
  * (non-Javadoc)
  * @see com.google.wireless.gdata2.parser.GDataParser#close()
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
   * @throws IOException
   * @throws XmlPullParserException
   * @throws ParseException Thrown in the stream can not be parsed into gdata
  */
  protected void handleExtraElementInEntry(Entry entry)
      throws XmlPullParserException, IOException, ParseException {
    // no-op in this class.
  }

   /**
   * Hook that allows a subclass to override default parsing 
   * behaviour. If the subclass returns true from this call, 
   * no default parsing will happen for the currently parsed tag 
   * @param entry The {@link Entry} being filled. 
   * @return true if the subclass handled the parsing.  
   * @throws IOException
   * @throws XmlPullParserException
  */
  protected boolean handleDefaultEntryElements(Entry entry)
      throws XmlPullParserException, IOException {
    // no-op in this class.
    return false;
  }
  /**
   * Hook that allows extra (service-specific) &lt;link&gt;s in an entry to be
   * parsed.
   * @param rel The rel attribute value.
   * @param type The type attribute value.
   * @param href The href attribute value.
   * @param entry The {@link Entry} being filled.
   * @throws IOException
   * @throws XmlPullParserException
  */
  protected void handleExtraLinkInEntry(String rel,
      String type,
      String href,
      Entry entry)
      throws XmlPullParserException, IOException {
    // no-op in this class.
  }
}
