// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.parser.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/**
 * Factory for creating new {@link org.xmlpull.v1.XmlPullParser}s and
 * {@link org.xmlpull.v1.XmlSerializer}s
 */
public interface XmlParserFactory {

    /**
     * Creates a new {@link XmlPullParser}.
     *
     * @return A new {@link XmlPullParser}.
     * @throws XmlPullParserException Thrown if the parser could not be created.
     */
    XmlPullParser createParser() throws XmlPullParserException;

    /**
     * Creates a new {@link XmlSerializer}.
     *
     * @return A new {@link XmlSerializer}.
     * @throws XmlPullParserException Thrown if the serializer could not be
     * created.
     */
    XmlSerializer createSerializer() throws XmlPullParserException;
}
