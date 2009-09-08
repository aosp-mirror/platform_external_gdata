// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.data;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 * Utility class for working with an XmlPullParser.
 */
public final class XmlUtils {
    // utility class
    private XmlUtils() {
    }

    /**
     * Extracts the child text for the current element in the pull parser.
     * @param parser The XmlPullParser parsing an XML document. 
     * @return The child text for the current element.  May be null, if there
     * is no child text.
     * @throws XmlPullParserException Thrown if the child text could not be
     * parsed.
     * @throws IOException Thrown if the InputStream behind the parser cannot
     * be read.
     */
    public static String extractChildText(XmlPullParser parser) 
        throws XmlPullParserException, IOException {
        // TODO: check that the current node is an element?
        int eventType = parser.next();
        if (eventType != XmlPullParser.TEXT) {
            return null;
        }
        return parser.getText();
    }

    /**
     * Extracts the child text for the first child element in the pull parser.
     * Other child elements will be discarded
     * @param parser The XmlPullParser parsing an XML document. 
     * @return The child text for the first child element.  May be null, if there
     * is no child text.
     * @throws XmlPullParserException Thrown if the child text could not be
     * parsed.
     * @throws IOException Thrown if the InputStream behind the parser cannot
     * be read.
     */
    public static String extractFirstChildTextIgnoreRest(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int parentDepth = parser.getDepth();
        int eventType = parser.next();
        String child = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            int depth = parser.getDepth();

            if (eventType == XmlPullParser.TEXT) {
                if (child == null) {
                    child = parser.getText();
                }
            } else if (eventType == XmlPullParser.END_TAG && depth == parentDepth) {
                return child;
            }
            eventType = parser.next();
        }
        throw new XmlPullParserException("End of document reached; never saw expected end tag at "
                + "depth " + parentDepth);
    }

    /**
     * Returns the nextDirectChildTag
     * @param parser The XmlPullParser parsing an XML document. 
     * @param parentDepth the depth in the hierachy of the parent node
     * @return The child element 
     * @throws XmlPullParserException Thrown if the child text could not be
     * parsed.
     * @throws IOException Thrown if the InputStream behind the parser cannot
     * be read.
     */
    public static String nextDirectChildTag(XmlPullParser parser, int parentDepth)
            throws XmlPullParserException, IOException {
        int targetDepth = parentDepth + 1;
        int eventType = parser.next();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            int depth = parser.getDepth();

            if (eventType == XmlPullParser.START_TAG && depth == targetDepth) {
                return parser.getName();
            }

            if (eventType == XmlPullParser.END_TAG && depth == parentDepth) {
                return null;
            }
            eventType = parser.next();            
        }
        throw new XmlPullParserException("End of document reached; never saw expected end tag at "
                + "depth " + parentDepth);
    }
}
