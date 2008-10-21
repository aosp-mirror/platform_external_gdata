package com.google.wireless.gdata.spreadsheets.serializer.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import com.google.wireless.gdata.spreadsheets.data.ListEntry;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * A serializer for handling GData Spreadsheets List entries.
 */
public class XmlListEntryGDataSerializer extends XmlEntryGDataSerializer {
    /** The prefix to use for the GData Spreadsheets list namespace */
    public static final String NAMESPACE_GSX = "gsx";

    /** The URI of the GData Spreadsheets list namespace */
    public static final String NAMESPACE_GSX_URI =
            "http://schemas.google.com/spreadsheets/2006/extended";

    /**
     * Creates a new XmlListEntryGDataSerializer.
     * 
     * @param entry the entry to be serialized
     */
    public XmlListEntryGDataSerializer(XmlParserFactory xmlFactory, Entry entry) {
        super(xmlFactory, entry);
    }

    /**
     * Sets up the GData Spreadsheets list namespace.
     * 
     * @param serializer the XML serializer to use
     * @throws IOException on stream errors.
     */
    protected void declareExtraEntryNamespaces(XmlSerializer serializer)
            throws IOException {
        serializer.setPrefix(NAMESPACE_GSX, NAMESPACE_GSX_URI);
    }

    /* (non-JavaDoc)
     * Handles the non-Atom data belonging to the GData Spreadsheets Cell
     * namespace.
     */
    protected void serializeExtraEntryContents(XmlSerializer serializer,
            int format) throws ParseException, IOException {
        ListEntry entry = (ListEntry) getEntry();
        Vector names = entry.getNames();
        String name = null;
        String value = null;
        Iterator it = names.iterator();
        while (it.hasNext()) {
            name = (String) it.next();
            value = entry.getValue(name);
            if (value != null) {
                serializer.startTag(NAMESPACE_GSX_URI, name);
                serializer.text(value);
                serializer.endTag(NAMESPACE_GSX_URI, name);
            }
        }
    }
}
