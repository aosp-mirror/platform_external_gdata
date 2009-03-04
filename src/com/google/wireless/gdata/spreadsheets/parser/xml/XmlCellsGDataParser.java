// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.spreadsheets.parser.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import com.google.wireless.gdata.spreadsheets.data.CellEntry;
import com.google.wireless.gdata.spreadsheets.data.CellFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Parser for non-Atom data in a GData Spreadsheets Cell-based feed.
 */
public class XmlCellsGDataParser extends XmlGDataParser {
    /**
     * The rel ID used by the server to identify the URLs for Cell POSTs
     * (updates)
     */
    private static final String CELL_FEED_POST_REL =
            "http://schemas.google.com/g/2005#post";

    /**
     * Creates a new XmlCellsGDataParser.
     * 
     * @param is the stream from which to read the data
     * @param xmlParser the XMLPullParser to use for parsing the raw XML
     * @throws ParseException if the super-class throws one
     */
    public XmlCellsGDataParser(InputStream is, XmlPullParser xmlParser)
            throws ParseException {
        super(is, xmlParser);
    }

    /* (non-JavaDoc)
     * Creates a new Entry that can handle the data parsed by this class.
     */
    protected Entry createEntry() {
        return new CellEntry();
    }

    /* (non-JavaDoc)
     * Creates a new Feed that can handle the data parsed by this class.
     */
    protected Feed createFeed() {
        return new CellFeed();
    }

    /* (non-JavaDoc)
     * Callback to handle non-Atom data present in an Atom entry tag.
     */
    protected void handleExtraElementInEntry(Entry entry)
            throws XmlPullParserException, IOException {
        XmlPullParser parser = getParser();
        if (!(entry instanceof CellEntry)) {
            throw new IllegalArgumentException("Expected CellEntry!");
        }
        CellEntry row = (CellEntry) entry;

        String name = parser.getName();
        // cells can only have row, col, inputValue, & numericValue attrs
        if ("cell".equals(name)) {
            int count = parser.getAttributeCount();
            String attrName = null;
            for (int i = 0; i < count; ++i) {
                attrName = parser.getAttributeName(i);
                if ("row".equals(attrName)) {
                    row.setRow(StringUtils.parseInt(parser
                            .getAttributeValue(i), 0));
                } else if ("col".equals(attrName)) {
                    row.setCol(StringUtils.parseInt(parser
                            .getAttributeValue(i), 0));
                } else if ("numericValue".equals(attrName)) {
                    row.setNumericValue(parser.getAttributeValue(i));
                } else if ("inputValue".equals(attrName)) {
                    row.setInputValue(parser.getAttributeValue(i));
                }
            }

            // also need the data stored in the child text node
            row.setValue(XmlUtils.extractChildText(parser));
        }
    }

    /* (non-JavaDoc)
     * Callback to handle non-Atom data in the feed.
     */
    protected void handleExtraElementInFeed(Feed feed)
            throws XmlPullParserException, IOException {
        XmlPullParser parser = getParser();
        if (!(feed instanceof CellFeed)) {
            throw new IllegalArgumentException("Expected CellFeed!");
        }
        CellFeed cellFeed = (CellFeed) feed;

        String name = parser.getName();
        if (!"link".equals(name)) {
            return;
        }

        int numAttrs = parser.getAttributeCount();
        String rel = null;
        String href = null;
        String attrName = null;
        for (int i = 0; i < numAttrs; ++i) {
            attrName = parser.getAttributeName(i);
            if ("rel".equals(attrName)) {
                rel = parser.getAttributeValue(i);
            } else if ("href".equals(attrName)) {
                href = parser.getAttributeValue(i);
            }
        }
        if (!(StringUtils.isEmpty(rel) || StringUtils.isEmpty(href))) {
            if (CELL_FEED_POST_REL.equals(rel)) {
                cellFeed.setEditUri(href);
            }
        }
    }
}
