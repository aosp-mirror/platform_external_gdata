// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.parser.xml;

import com.google.wireless.gdata.calendar.data.CalendarEntry;
import com.google.wireless.gdata.calendar.data.EventEntry;
import com.google.wireless.gdata.calendar.serializer.xml.XmlEventEntryGDataSerializer;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.serializer.GDataSerializer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;

/**
 * GDataParserFactory that creates XML GDataParsers and GDataSerializers for
 * Google Calendar.
 */
public class XmlCalendarGDataParserFactory implements GDataParserFactory {

    private final XmlParserFactory xmlFactory;

    public XmlCalendarGDataParserFactory(XmlParserFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    /**
     * Returns a parser for a calendars meta-feed.
     *
     * @param is The input stream to be parsed.
     * @return A parser for the stream.
     */
    public GDataParser createCalendarsFeedParser(InputStream is)
            throws ParseException {
        XmlPullParser xmlParser;
        try {
            xmlParser = xmlFactory.createParser();
        } catch (XmlPullParserException xppe) {
            throw new ParseException("Could not create XmlPullParser", xppe);
        }
        return new XmlCalendarsGDataParser(is, xmlParser);
    }

    /*
     * (non-javadoc)
     *
     * @see GDataParserFactory#createParser
     */
    public GDataParser createParser(InputStream is) throws ParseException {
        XmlPullParser xmlParser;
        try {
            xmlParser = xmlFactory.createParser();
        } catch (XmlPullParserException xppe) {
            throw new ParseException("Could not create XmlPullParser", xppe);
        }
        return new XmlEventsGDataParser(is, xmlParser);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.google.wireless.gdata.client.GDataParserFactory#createParser(
     *      int, java.io.InputStream)
     */
    public GDataParser createParser(Class entryClass, InputStream is)
            throws ParseException {
        if (entryClass == CalendarEntry.class) {
            return createCalendarsFeedParser(is);
        } else if (entryClass == EventEntry.class) {
            return createParser(is);
        }
        throw new IllegalArgumentException("Unknown entry class '" + entryClass.getName()
                + "' specified.");
    }

    /**
     * Creates a new {@link GDataSerializer} for the provided entry. The entry
     * <strong>must</strong> be an instance of {@link EventEntry}.
     *
     * @param entry The {@link EventEntry} that should be serialized.
     * @return The {@link GDataSerializer} that will serialize this entry.
     * @throws IllegalArgumentException Thrown if entry is not an
     *         {@link EventEntry}.
     * @see GDataParserFactory#createSerializer
     */
    public GDataSerializer createSerializer(Entry entry) {
        if (!(entry instanceof EventEntry)) {
            throw new IllegalArgumentException("Expected EventEntry!");
        }
        EventEntry eventEntry = (EventEntry) entry;
        return new XmlEventEntryGDataSerializer(xmlFactory, eventEntry);
    }
}
