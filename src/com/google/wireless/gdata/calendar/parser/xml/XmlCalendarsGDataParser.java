// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.parser.xml;

import com.google.wireless.gdata.calendar.data.CalendarEntry;
import com.google.wireless.gdata.calendar.data.CalendarsFeed;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * GDataParser for the meta feed listing a user's calendars.
 */
public class XmlCalendarsGDataParser extends XmlGDataParser {

    /**
     * Creates a new XmlCalendarsGDataParser.
     * @param is The InputStream containing the calendars feed.
     * @throws ParseException Thrown if an XmlPullParser could not be created.
     */
    public XmlCalendarsGDataParser(InputStream is, XmlPullParser parser)
            throws ParseException {
        super(is, parser);
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createFeed()
     */
    protected Feed createFeed() {
        return new CalendarsFeed();
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createEntry()
     */
    protected Entry createEntry() {
        return new CalendarEntry();
    }

    /*
     * (non-Javadoc)
     * @see XmlGDataParser#handleExtraElementInEntry
     */
    protected void handleExtraElementInEntry(Entry entry)
        throws XmlPullParserException, IOException {

        XmlPullParser parser = getParser();

        if (!(entry instanceof CalendarEntry)) {
            throw new IllegalArgumentException("Expected CalendarEntry!");
        }
        CalendarEntry calendarEntry = (CalendarEntry) entry;

        // NOTE: all of these names are assumed to be in the "gcal" namespace.
        // we do not bother checking that here.
        String name = parser.getName();
        if ("accesslevel".equals(name)) {
            String accesslevelStr = parser.getAttributeValue(null /* ns */,
                    "value");
            byte accesslevel = CalendarEntry.ACCESS_READ;
            if ("none".equals(accesslevelStr)) {
                accesslevel = CalendarEntry.ACCESS_NONE;
            } else if ("read".equals(accesslevelStr)) {
                accesslevel = CalendarEntry.ACCESS_READ;
            } else if ("freebusy".equals(accesslevelStr)) {
                accesslevel = CalendarEntry.ACCESS_FREEBUSY;
            } else if ("contributor".equals(accesslevelStr)) {
                // contributor is the access level that used to be used, but it seems to have
                // been deprecated in favor of "editor".
                accesslevel = CalendarEntry.ACCESS_EDITOR;
            } else if ("editor".equals(accesslevelStr)) {
                accesslevel = CalendarEntry.ACCESS_EDITOR;
            } else if ("owner".equals(accesslevelStr)) {
                accesslevel = CalendarEntry.ACCESS_OWNER;
            } else if ("root".equals(accesslevelStr)) {
                accesslevel = CalendarEntry.ACCESS_ROOT;
            }
            calendarEntry.setAccessLevel(accesslevel);
        } else if ("color".equals(name)) {
            String color =
                parser.getAttributeValue(null /* ns */, "value");
            calendarEntry.setColor(color);
        } else if ("hidden".equals(name)) {
            String hiddenStr =
                parser.getAttributeValue(null /* ns */, "value");
            boolean hidden = false;
            if ("false".equals(hiddenStr)) {
                hidden = false;
            } else if ("true".equals(hiddenStr)) {
                hidden = true;
            }
            calendarEntry.setHidden(hidden);
            // if the calendar is hidden, it cannot be selected.
            if (hidden) {
                calendarEntry.setSelected(false);
            }
        } else if ("selected".equals(name)) {
            String selectedStr =
                parser.getAttributeValue(null /* ns */, "value");
            boolean selected = false;
            if ("false".equals(selectedStr)) {
                selected = false;
            } else if ("true".equals(selectedStr)) {
                selected = true;
            }
            calendarEntry.setSelected(selected);
        } else if ("timezone".equals(name)) {
            String timezone =
                parser.getAttributeValue(null /* ns */, "value");
            calendarEntry.setTimezone(timezone);
        }
    }

    /*
     * (non-Javadoc)
     * @see XmlGDataParser#handleExtraLinkInEntry
     */
    protected void handleExtraLinkInEntry(String rel,
                                          String type,
                                          String href,
                                          Entry entry)
        throws XmlPullParserException, IOException {
        if (("alternate".equals(rel)) &&
            ("application/atom+xml".equals(type))) {
            CalendarEntry calendarEntry = (CalendarEntry) entry;
            calendarEntry.setAlternateLink(href);
        }
    }
}
