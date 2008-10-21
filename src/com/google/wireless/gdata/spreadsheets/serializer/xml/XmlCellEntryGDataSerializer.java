package com.google.wireless.gdata.spreadsheets.serializer.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import com.google.wireless.gdata.spreadsheets.data.CellEntry;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 * A serializer for handling GData Spreadsheets Cell entries.
 */
public class XmlCellEntryGDataSerializer extends XmlEntryGDataSerializer {
    /** The namespace to use for the GData Cell attributes */
    public static final String NAMESPACE_GS = "gs";

    /** The URI of the GData Cell namespace */
    public static final String NAMESPACE_GS_URI =
            "http://schemas.google.com/spreadsheets/2006";

    /**
     * Creates a new XmlCellEntryGDataSerializer.
     * 
     * @param entry the entry to be serialized
     */
    public XmlCellEntryGDataSerializer(XmlParserFactory xmlFactory,
            Entry entry) {
        super(xmlFactory, entry);
    }

    /**
     * Sets up the GData Cell namespace.
     * 
     * @param serializer the serializer to use
     */
    protected void declareExtraEntryNamespaces(XmlSerializer serializer)
            throws IOException {
        serializer.setPrefix(NAMESPACE_GS, NAMESPACE_GS_URI);
    }

    /*
     * Handles the non-Atom data belonging to the GData Spreadsheets Cell
     * namespace.
     * 
     * @param serializer the XML serializer to use
     * @param format unused
     * @throws ParseException if the data could not be serialized
     * @throws IOException on network error
     */
    protected void serializeExtraEntryContents(XmlSerializer serializer,
            int format) throws ParseException, IOException {
        CellEntry entry = (CellEntry) getEntry();
        int row = entry.getRow();
        int col = entry.getCol();
        String value = entry.getValue();
        String inputValue = entry.getInputValue();
        if (row < 0 || col < 0) {
            throw new ParseException("Negative row or column value");
        }

        // cells require row & col attrs, and allow inputValue and
        // numericValue
        serializer.startTag(NAMESPACE_GS_URI, "cell");
        serializer.attribute(null /* ns */, "row", "" + row);
        serializer.attribute(null /* ns */, "col", "" + col);
        if (inputValue != null) {
            serializer.attribute(null /* ns */, "inputValue", inputValue);
        }
        if (entry.hasNumericValue()) {
            serializer.attribute(null /* ns */, "numericValue", entry
                    .getNumericValue());
        }

        // set the child text...
        value = StringUtils.isEmpty(value) ? "" : value;
        serializer.text(value);
        serializer.endTag(NAMESPACE_GS_URI, "cell");
    }
}
