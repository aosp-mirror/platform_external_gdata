// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.data;

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

  /**
   * Supply a 'skipSubTree' API which, for some reason, the kxml2 pull parser
   * hasn't implemented.
   */
  public void skipSubTree(XmlPullParser parser)
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

//    public static void parseChildrenToSerializer(XmlPullParser parser, XmlSerializer serializer)
//            throws XmlPullParserException, IOException {
//        int parentDepth = parser.getDepth();
//        int eventType = parser.getEventType();
//        while (eventType != XmlPullParser.END_DOCUMENT) {
//            // TODO: call parser.nextToken(), so we get all entities, comments, whitespace, etc.?
//            // find out if this is necessary.
//            eventType = parser.next();
//            int depth = parser.getDepth();
//            String name;
//            String ns;
//            switch (eventType) {
//                case XmlPullParser.START_TAG:
//                    name = parser.getName();
//                    ns = parser.getNamespace();
//                    // grab all of the namespace definitions between the previous depth and the
//                    // current depth (e.g., what was just defined in the start tag).
//                    int nstackBegin = parser.getNamespaceCount(depth - 1);
//                    int nstackEnd = parser.getNamespaceCount(depth);
//                    for (int i = nstackBegin; i < nstackEnd; ++i) {
//                        serializer.setPrefix(parser.getNamespacePrefix(i),
//                                parser.getNamespaceUri(i));
//                    }
//                    serializer.startTag(ns, name);
//
//                    int numAttrs = parser.getAttributeCount();
//                    for (int i = 0; i < numAttrs; ++i) {
//                        String attrNs = parser.getAttributeNamespace(i);
//                        String attrName = parser.getAttributeName(i);
//                        String attrValue = parser.getAttributeValue(i);
//                        serializer.attribute(attrNs, attrName, attrValue);
//                    }
//                    break;
//                case XmlPullParser.END_TAG:
//                    if (depth == parentDepth) {
//                        // we're done.
//                        return;
//                    }
//                    name = parser.getName();
//                    ns = parser.getNamespace();
//                    serializer.endTag(ns, name);
//                    break;
//                case XmlPullParser.TEXT:
//                    serializer.text(parser.getText());
//                    break;
//                default:
//                    // ignore the rest.
//                    break;
//            }
//        }
//        throw new XmlPullParserException("End of document reached; never saw expected end tag "
//            + "at depth " + parentDepth);
//    }
}
