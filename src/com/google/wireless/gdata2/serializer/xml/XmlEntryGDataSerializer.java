// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.serializer.xml;

import com.google.wireless.gdata2.data.batch.BatchUtils;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;
import com.google.wireless.gdata2.parser.xml.XmlNametable;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.GDataSerializer;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Serializes GData entries to the Atom XML format.
 */
public class XmlEntryGDataSerializer implements GDataSerializer {

  /** The XmlParserFactory that is used to create the XmlSerializer */
  private final XmlParserFactory factory;

  /** The entry being serialized. */
  private final Entry entry;

  private final boolean supportsPartial;

    /**
   * Creates a new XmlEntryGDataSerializer that will serialize the provided
   * entry.
   *
   * @param entry The entry that should be serialized.
   */
  public XmlEntryGDataSerializer(XmlParserFactory factory, Entry entry) {
    this.factory = factory;
    this.entry = entry;
    supportsPartial = !StringUtils.isEmptyOrWhitespace(this.entry.getFields());
}

  /**
   * Returns the entry being serialized.
   * @return The entry being serialized.
   */
  protected Entry getEntry() {
    return entry;
  }

  /* (non-Javadoc)
  * @see GDataSerializer#getContentType()
  */
  public String getContentType() {
    return "application/atom+xml";
  }


  /* (non-Javadoc)
  * @see GDataSerializer#getSupportsPartial()
  */
  public boolean getSupportsPartial() {
    return supportsPartial;
  }


  /* (non-Javadoc)
  * @see GDataSerializer#serialize(java.io.OutputStream)
  */
  public void serialize(OutputStream out, int format)
      throws IOException, ParseException {
    XmlSerializer serializer = null;
    try {
      serializer = factory.createSerializer();
    } catch (XmlPullParserException e) {
      throw new ParseException("Unable to create XmlSerializer.", e);
    }
    // TODO: make the output compact

    serializer.setOutput(out, XmlNametable.UTF8);
    if (format != FORMAT_BATCH) {
      serializer.startDocument(XmlNametable.UTF8, Boolean.FALSE);
      declareEntryNamespaces(serializer);
    }
    final String fields = entry.getFields();
    if (getSupportsPartial()) {
      serializer.startTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.PARTIAL);
      serializer.attribute(null /* ns */, XmlNametable.FIELDS, fields);
    }
   
    serializer.startTag(XmlGDataParser.NAMESPACE_ATOM_URI, XmlNametable.ENTRY);

    serializeEntryContents(serializer, format);

    serializer.endTag(XmlGDataParser.NAMESPACE_ATOM_URI, XmlNametable.ENTRY);

    if (getSupportsPartial()) {
      serializer.endTag(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.PARTIAL);
    }
   
    if (format != FORMAT_BATCH) {
      serializer.endDocument();
    }
    serializer.flush();
  }

  private void declareEntryNamespaces(XmlSerializer serializer)
      throws IOException {
    serializer.setPrefix("" /* default ns */, XmlGDataParser.NAMESPACE_ATOM_URI);
    serializer.setPrefix(XmlGDataParser.NAMESPACE_GD, XmlGDataParser.NAMESPACE_GD_URI);
    declareExtraEntryNamespaces(serializer);
  }

  protected void declareExtraEntryNamespaces(XmlSerializer serializer)
      throws IOException {
    // no-op in this class
  }

  /**
   * @param serializer
   * @throws IOException
   */
  private void serializeEntryContents(XmlSerializer serializer, int format)
      throws ParseException, IOException {

    if (format == FORMAT_BATCH) {
      serializeBatchInfo(serializer);
    }

    if (format != FORMAT_CREATE) {
      serializeId(serializer, entry.getId());
    }

    serializeTitle(serializer, entry.getTitle());

    if (format != FORMAT_CREATE) {
      serializeLink(serializer, XmlNametable.EDIT_REL /* rel */,
                    entry.getEditUri(), null /* type */, null /* etag */ );
      serializeLink(serializer, XmlNametable.ALTERNATE_REL /* rel */,
                    entry.getHtmlUri(), XmlNametable.TEXTHTML /* type */, null /* etag */ );
    }

    serializeSummary(serializer, entry.getSummary());

    serializeContent(serializer, entry.getContent());

    serializeAuthor(serializer, entry.getAuthor(), entry.getEmail());

    serializeCategory(serializer, entry.getCategory(), entry.getCategoryScheme());

    if (format == FORMAT_FULL) {
      serializePublicationDate(serializer,
          entry.getPublicationDate());
    }

    if (format != FORMAT_CREATE) {
      serializeUpdateDate(serializer,
          entry.getUpdateDate());
    }

    serializeExtraEntryContents(serializer, format);
  }

  /**
   * Hook for subclasses to serialize extra fields within the entry.
   * @param serializer The XmlSerializer being used to serialize the entry.
   * @param format The serialization format for the entry.
   * @throws ParseException Thrown if the entry cannot be serialized.
   * @throws IOException Thrown if the entry cannot be written to the
   * underlying {@link OutputStream}.
   */
  protected void serializeExtraEntryContents(XmlSerializer serializer, int format)
      throws ParseException, IOException {
    // no-op in this class.
  }

  // TODO: make these helper methods protected so sublcasses can use them?

  private static void serializeId(XmlSerializer serializer, String id) throws IOException {
    if (StringUtils.isEmpty(id)) {
      return;
    }
    serializer.startTag(null /* ns */,  XmlNametable.ID);
    serializer.text(id);
    serializer.endTag(null /* ns */, XmlNametable.ID);
  }

  private static void serializeTitle(XmlSerializer serializer,
      String title)
      throws IOException {
    if (StringUtils.isEmpty(title)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.TITLE);
    serializer.text(title);
    serializer.endTag(null /* ns */, XmlNametable.TITLE);
  }

  /**
   * Serializes a link relationship into the xml serializer passed in.
   * 
   * @param serializer The serializer to be used
   * @param rel        The relationship value (like alternate, edit etc)
   * @param href       the URI this link points to
   * @param type       the content type
   * @param etag       the optional etag if this link specifies a resource
   * 
   * @exception IOException
   */
  protected static void serializeLink(XmlSerializer serializer, String rel, String href,
          String type, String etag) throws IOException {
    if (StringUtils.isEmpty(href)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.LINK);
    serializer.attribute(null /* ns */, XmlNametable.REL, rel);
    serializer.attribute(null /* ns */, XmlNametable.HREF, href);
    if (!StringUtils.isEmpty(type)) serializer.attribute(null /* ns */, XmlNametable.TYPE, type);
    if (!StringUtils.isEmpty(etag)) serializer.attribute(null /* ns */, XmlNametable.ETAG, etag);
    serializer.endTag(null /* ns */, XmlNametable.LINK);
  }

  private static void serializeSummary(XmlSerializer serializer,
      String summary)
      throws IOException {
    if (StringUtils.isEmpty(summary)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.SUMMARY);
    serializer.text(summary);
    serializer.endTag(null /* ns */, XmlNametable.SUMMARY);
  }

  private static void serializeContent(XmlSerializer serializer,
      String content)
      throws IOException {
    if (content == null) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.CONTENT);
    serializer.attribute(null /* ns */, XmlNametable.TYPE, XmlNametable.TEXT);
    serializer.text(content);
    serializer.endTag(null /* ns */, XmlNametable.CONTENT);
  }

  private static void serializeAuthor(XmlSerializer serializer,
      String author,
      String email)
      throws IOException {
    if (StringUtils.isEmpty(author) || StringUtils.isEmpty(email)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.AUTHOR);
    serializer.startTag(null /* ns */, XmlNametable.NAME);
    serializer.text(author);
    serializer.endTag(null /* ns */, XmlNametable.NAME);
    serializer.startTag(null /* ns */, XmlNametable.EMAIL);
    serializer.text(email);
    serializer.endTag(null /* ns */,  XmlNametable.EMAIL);
    serializer.endTag(null /* ns */, XmlNametable.AUTHOR);
  }

  private static void serializeCategory(XmlSerializer serializer,
      String category,
      String categoryScheme)
      throws IOException {
    if (StringUtils.isEmpty(category) &&
        StringUtils.isEmpty(categoryScheme)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.CATEGORY);
    if (!StringUtils.isEmpty(category)) {
      serializer.attribute(null /* ns */, XmlNametable.TERM, category);
    }
    if (!StringUtils.isEmpty(categoryScheme)) {
      serializer.attribute(null /* ns */, XmlNametable.SCHEME, categoryScheme);
    }
    serializer.endTag(null /* ns */, XmlNametable.CATEGORY);
  }

  private static void
  serializePublicationDate(XmlSerializer serializer,
      String publicationDate)
      throws IOException {
    if (StringUtils.isEmpty(publicationDate)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.PUBLISHED);
    serializer.text(publicationDate);
    serializer.endTag(null /* ns */, XmlNametable.PUBLISHED);
  }

  private static void
  serializeUpdateDate(XmlSerializer serializer,
      String updateDate)
      throws IOException {
    if (StringUtils.isEmpty(updateDate)) {
      return;
    }
    serializer.startTag(null /* ns */, XmlNametable.UPDATED);
    serializer.text(updateDate);
    serializer.endTag(null /* ns */,  XmlNametable.UPDATED);
  }

  private void serializeBatchInfo(XmlSerializer serializer)
      throws IOException {
    if (!StringUtils.isEmpty(entry.getETag())) {
     serializer.attribute(XmlGDataParser.NAMESPACE_GD_URI, XmlNametable.ETAG,
         entry.getETag());
    }
    if (!StringUtils.isEmpty(BatchUtils.getBatchOperation(entry))) {
      serializer.startTag(XmlGDataParser.NAMESPACE_BATCH_URI, XmlNametable.OPERATION);
      serializer.attribute(null /* ns */, XmlNametable.TYPE,
          BatchUtils.getBatchOperation(entry));
      serializer.endTag(XmlGDataParser.NAMESPACE_BATCH_URI, XmlNametable.OPERATION);
    }
    if (!StringUtils.isEmpty(BatchUtils.getBatchId(entry))) {
      serializer.startTag(XmlGDataParser.NAMESPACE_BATCH_URI, XmlNametable.ID);
      serializer.text(BatchUtils.getBatchId(entry));
      serializer.endTag(XmlGDataParser.NAMESPACE_BATCH_URI, XmlNametable.ID);
    }
  }

}
