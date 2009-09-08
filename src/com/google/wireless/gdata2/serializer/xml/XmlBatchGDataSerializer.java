// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.wireless.gdata2.serializer.xml;

import com.google.wireless.gdata2.serializer.GDataSerializer;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.parser.xml.XmlGDataParser;
import com.google.wireless.gdata2.parser.xml.XmlNametable;
import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.data.Entry;

import org.xmlpull.v1.XmlSerializer;
import org.xmlpull.v1.XmlPullParserException;

import java.io.OutputStream;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Serializes a batch of GData requests as an XML stream.
 */
public class XmlBatchGDataSerializer implements GDataSerializer {

  private final GDataParserFactory gdataFactory;
  private final XmlParserFactory xmlFactory;
  private final Enumeration batch;

  /*
   * Constructs an XmlBatchGDataSerializer for serializing the given batch
   * of GData requests
   *
   * @param gdataFactory used to create serializers for individual requests
   * @param xmlFactory used to create the XML stream
   * @param Enumeration the batch of requests to serialize
   */
  public XmlBatchGDataSerializer(GDataParserFactory gdataFactory,
      XmlParserFactory xmlFactory, Enumeration batch) {
    this.gdataFactory = gdataFactory;
    this.xmlFactory = xmlFactory;
    this.batch = batch;
  }

  /* (non-Javadoc)
  * @see GDataSerializer#getContentType()
  */
  public String getContentType() {
    return "application/atom+xml";
  }

  /* (non-Javadoc)
   * @see GDataSerializer#doesSupportPartial()
   */
  public boolean getSupportsPartial() {
    return false;
  }


  /* (non-Javadoc)
   * @see GDataSerializer#serialize()
   */
  public void serialize(OutputStream out, int format)
      throws IOException, ParseException {
    XmlSerializer serializer;
    try {
      serializer = xmlFactory.createSerializer();
    } catch (XmlPullParserException e) {
      throw new ParseException("Unable to create XmlSerializer.", e);
    }

    serializer.setOutput(out, XmlNametable.UTF8);
    serializer.startDocument(XmlNametable.UTF8, Boolean.FALSE);

    declareNamespaces(serializer);

    boolean first = true;
    while (batch.hasMoreElements()) {
      Entry entry = (Entry) batch.nextElement();
      XmlEntryGDataSerializer entrySerializer = (XmlEntryGDataSerializer)
          gdataFactory.createSerializer(entry);

      if (first) {
        // Let the first entry serializer declare extra namespaces.
        first = false;
        serializer.startTag(XmlGDataParser.NAMESPACE_ATOM_URI, XmlNametable.FEED);
        entrySerializer.declareExtraEntryNamespaces(serializer);
      }
      entrySerializer.serialize(out, GDataSerializer.FORMAT_BATCH);
    }

    if (first) {
      serializer.startTag(XmlGDataParser.NAMESPACE_ATOM_URI, XmlNametable.FEED);
    }

    serializer.endTag(XmlGDataParser.NAMESPACE_ATOM_URI, XmlNametable.FEED);
    serializer.endDocument();
    serializer.flush();
  }

  private static void declareNamespaces(XmlSerializer serializer) throws IOException {
    serializer.setPrefix("" /* default ns */,
        XmlGDataParser.NAMESPACE_ATOM_URI);
    serializer.setPrefix(XmlGDataParser.NAMESPACE_GD,
        XmlGDataParser.NAMESPACE_GD_URI);
    serializer.setPrefix(XmlGDataParser.NAMESPACE_BATCH,
        XmlGDataParser.NAMESPACE_BATCH_URI);
  }
}
