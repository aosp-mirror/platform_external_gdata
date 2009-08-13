// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.parser.xml;

import com.google.wireless.gdata.calendar.data.EventEntry;
import com.google.wireless.gdata.calendar.data.EventsFeed;
import com.google.wireless.gdata.calendar.data.When;
import com.google.wireless.gdata.calendar.data.Reminder;
import com.google.wireless.gdata.calendar.data.Who;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * GDataParser for an events feed containing events in a calendar.
 */
public class XmlEventsGDataParser extends XmlGDataParser {

    // whether or not we've seen reminders directly under the entry.
    // the calendar feed sends duplicate <reminder> entries in case of
    // recurrences, if the recurrences are expanded.
    // if the <reminder> elements precede the <when> elements, we'll only
    // process the <reminder> elements directly under the entry and ignore
    // the <reminder> elements within a <when>.
    // if the <when> elements precede the <reminder> elements, we'll first
    // process reminders under the when, and then we'll clear them and process
    // the reminders directly under the entry (which should take precedence).
    // if we only see <reminder> as direct children of the entry or only see
    // <reminder> as children of <when> elements, there is no conflict.
    private boolean hasSeenReminder = false;

    /**
     * Creates a new XmlEventsGDataParser.
     * @param is The InputStream that should be parsed.
     * @throws ParseException Thrown if a parser cannot be created.
     */
    public XmlEventsGDataParser(InputStream is, XmlPullParser parser)
            throws ParseException {
        super(is, parser);
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createFeed()
     */
    protected Feed createFeed() {
        return new EventsFeed();
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createEntry()
     */
    protected Entry createEntry() {
        return new EventEntry();
    }

    @Override
    protected void handleEntry(Entry entry) throws XmlPullParserException,
            IOException, ParseException {
        hasSeenReminder = false; // Reset the state for the new entry
        super.handleEntry(entry);
    }

    protected void handleExtraElementInFeed(Feed feed)
            throws XmlPullParserException, IOException {
        XmlPullParser parser = getParser();
        if (!(feed instanceof EventsFeed)) {
            throw new IllegalArgumentException("Expected EventsFeed!");
        }
        EventsFeed eventsFeed = (EventsFeed) feed;
        String name = parser.getName();
        if ("timezone".equals(name)) {
            String timezone = parser.getAttributeValue(null /* ns */, "value");
            if (!StringUtils.isEmpty(timezone)) {
                eventsFeed.setTimezone(timezone);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see XmlGDataParser#handleExtraElementInEntry
     */
    protected void handleExtraElementInEntry(Entry entry)
            throws XmlPullParserException, IOException, ParseException {

        XmlPullParser parser = getParser();

        if (!(entry instanceof EventEntry)) {
            throw new IllegalArgumentException("Expected EventEntry!");
        }
        EventEntry eventEntry = (EventEntry) entry;

        // NOTE: all of these names are assumed to be in the "gd" namespace.
        // we do not bother checking that here.

        String name = parser.getName();
        if ("eventStatus".equals(name)) {
            String eventStatusStr = parser.getAttributeValue(null, "value");
            byte eventStatus = EventEntry.STATUS_TENTATIVE;
            if ("http://schemas.google.com/g/2005#event.canceled".
                    equals(eventStatusStr)) {
                eventStatus = EventEntry.STATUS_CANCELED;
            } else if ("http://schemas.google.com/g/2005#event.confirmed".
                    equals(eventStatusStr)) {
                eventStatus = EventEntry.STATUS_CONFIRMED;
            } else if ("http://schemas.google.com/g/2005#event.tentative".
                    equals(eventStatusStr)) {
                eventStatus = EventEntry.STATUS_TENTATIVE;
            }
            eventEntry.setStatus(eventStatus);
        } else if ("recurrence".equals(name)) {
            String recurrence = XmlUtils.extractChildText(parser);
            eventEntry.setRecurrence(recurrence);
        } else if ("transparency".equals(name)) {
            String transparencyStr = parser.getAttributeValue(null, "value");
            byte transparency = EventEntry.TRANSPARENCY_OPAQUE;
            if ("http://schemas.google.com/g/2005#event.opaque".
                    equals(transparencyStr)) {
                transparency = EventEntry.TRANSPARENCY_OPAQUE;
            } else if ("http://schemas.google.com/g/2005#event.transparent".
                    equals(transparencyStr)) {
                transparency = EventEntry.TRANSPARENCY_TRANSPARENT;
            }
            eventEntry.setTransparency(transparency);
        } else if ("visibility".equals(name)) {
            String visibilityStr = parser.getAttributeValue(null, "value");
            byte visibility = EventEntry.VISIBILITY_DEFAULT;
            if ("http://schemas.google.com/g/2005#event.confidential".
                    equals(visibilityStr)) {
                visibility = EventEntry.VISIBILITY_CONFIDENTIAL;
            } else if ("http://schemas.google.com/g/2005#event.default"
                    .equals(visibilityStr)) {
                visibility = EventEntry.VISIBILITY_DEFAULT;
            } else if ("http://schemas.google.com/g/2005#event.private"
                    .equals(visibilityStr)) {
                visibility = EventEntry.VISIBILITY_PRIVATE;
            } else if ("http://schemas.google.com/g/2005#event.public"
                    .equals(visibilityStr)) {
                visibility = EventEntry.VISIBILITY_PUBLIC;
            }
            eventEntry.setVisibility(visibility);
        } else if ("who".equals(name)) {
            handleWho(eventEntry);
        } else if ("sendEventNotifications".equals(name)) {
	    // TODO: check that the namespace is gCal
	    String value = parser.getAttributeValue(null /* ns */, "value");
	    eventEntry.setSendEventNotifications("true".equals(value));
	} else if ("guestsCanModify".equals(name)) {
	    // TODO: check that the namespace is gCal
	    String value = parser.getAttributeValue(null /* ns */, "value");
	    eventEntry.setGuestsCanModify("true".equals(value));
	} else if ("guestsCanInviteOthers".equals(name)) {
	    // TODO: check that the namespace is gCal
	    String value = parser.getAttributeValue(null /* ns */, "value");
	    eventEntry.setGuestsCanInviteOthers("true".equals(value));
	} else if ("guestsCanSeeGuests".equals(name)) {
	    // TODO: check that the namespace is gCal
	    String value = parser.getAttributeValue(null /* ns */, "value");
	    eventEntry.setGuestsCanSeeGuests("true".equals(value));
	} else if ("when".equals(name)) {
            handleWhen(eventEntry);
        } else if ("reminder".equals(name)) {
            if (!hasSeenReminder) {
                // if this is the first <reminder> we've seen directly under the
                // entry, clear any previously seen reminders (under <when>s)
                eventEntry.clearReminders();
                hasSeenReminder = true;
            }
            handleReminder(eventEntry);
        } else if ("originalEvent".equals(name)) {
            handleOriginalEvent(eventEntry);
        } else if ("where".equals(name)) {
            String where = parser.getAttributeValue(null /* ns */,
                    "valueString");
            String rel = parser.getAttributeValue(null /* ns */,
                    "rel");
            if (StringUtils.isEmpty(rel) ||
                    "http://schemas.google.com/g/2005#event".equals(rel)) {
                eventEntry.setWhere(where);
            }
            // TODO: handle entryLink?
        } else if ("feedLink".equals(name)) {
            // TODO: check that the parent is a gd:comments            
            String commentsUri = parser.getAttributeValue(null /* ns */, "href");
            eventEntry.setCommentsUri(commentsUri);
        } else if ("extendedProperty".equals(name)) {
            String propertyName = parser.getAttributeValue(null /* ns */, "name");
            String propertyValue = parser.getAttributeValue(null /* ns */, "value");
            eventEntry.addExtendedProperty(propertyName, propertyValue);
        }
    }

    private void handleWho(EventEntry eventEntry)
            throws XmlPullParserException, IOException, ParseException {

        XmlPullParser parser = getParser();

        int eventType = parser.getEventType();
        String name = parser.getName();

        if (eventType != XmlPullParser.START_TAG ||
                (!"who".equals(parser.getName()))) {
            // should not happen.
            throw new
                    IllegalStateException("Expected <who>: Actual "
                    + "element: <"
                    + name + ">");
        }

        String email =
                parser.getAttributeValue(null /* ns */, "email");
        String relString =
                parser.getAttributeValue(null /* ns */, "rel");
        String value =
                parser.getAttributeValue(null /* ns */, "valueString");

        Who who = new Who();
        who.setEmail(email);
        who.setValue(value);
        byte rel = Who.RELATIONSHIP_NONE;
        if ("http://schemas.google.com/g/2005#event.attendee".equals(relString)) {
            rel = Who.RELATIONSHIP_ATTENDEE;
        } else if ("http://schemas.google.com/g/2005#event.organizer".equals(relString)) {
            rel = Who.RELATIONSHIP_ORGANIZER;
        } else if ("http://schemas.google.com/g/2005#event.performer".equals(relString)) {
            rel = Who.RELATIONSHIP_PERFORMER;
        } else if ("http://schemas.google.com/g/2005#event.speaker".equals(relString)) {
            rel = Who.RELATIONSHIP_SPEAKER;
        } else if (StringUtils.isEmpty(relString)) {
            rel = Who.RELATIONSHIP_ATTENDEE;
        } else {
            throw new ParseException("Unexpected rel: " + relString);
        }
        who.setRelationship(rel);

        eventEntry.addAttendee(who);

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if ("attendeeStatus".equals(name)) {
                        String statusString =
                                parser.getAttributeValue(null /* ns */, "value");
                        byte status = Who.STATUS_NONE;
                        if ("http://schemas.google.com/g/2005#event.accepted".
                                equals(statusString)) {
                            status = Who.STATUS_ACCEPTED;
                        } else if ("http://schemas.google.com/g/2005#event.declined".
                                equals(statusString)) {
                            status = Who.STATUS_DECLINED;
                        } else if ("http://schemas.google.com/g/2005#event.invited".
                                equals(statusString)) {
                            status = Who.STATUS_INVITED;
                        } else if ("http://schemas.google.com/g/2005#event.tentative".
                                equals(statusString)) {
                            status = Who.STATUS_TENTATIVE;
                        } else if (StringUtils.isEmpty(statusString)) {
                            status = Who.STATUS_TENTATIVE;
                        } else {
                            throw new ParseException("Unexpected status: " + statusString);
                        }
                        who.setStatus(status);
                    } else if ("attendeeType".equals(name)) {
                        String typeString= XmlUtils.extractChildText(parser);
                        byte type = Who.TYPE_NONE;
                        if ("http://schemas.google.com/g/2005#event.optional".equals(typeString)) {
                            type = Who.TYPE_OPTIONAL;
                        } else if ("http://schemas.google.com/g/2005#event.required".
                                equals(typeString)) {
                            type = Who.TYPE_REQUIRED;
                        } else if (StringUtils.isEmpty(typeString)) {
                            type = Who.TYPE_REQUIRED;
                        } else {
                            throw new ParseException("Unexpected type: " + typeString);
                        }
                        who.setType(type);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ("who".equals(name)) {
                        return;
                    }
                default:
                    // ignore
            }

            eventType = parser.next();
        }
    }

    private void handleWhen(EventEntry eventEntry)
            throws XmlPullParserException, IOException {

        XmlPullParser parser = getParser();

        int eventType = parser.getEventType();
        String name = parser.getName();

        if (eventType != XmlPullParser.START_TAG ||
                (!"when".equals(parser.getName()))) {
            // should not happen.
            throw new
                    IllegalStateException("Expected <when>: Actual "
                    + "element: <"
                    + name + ">");
        }

        String startTime =
                parser.getAttributeValue(null /* ns */, "startTime");
        String endTime =
                parser.getAttributeValue(null /* ns */, "endTime");

        When when = new When(startTime, endTime);
        eventEntry.addWhen(when);
        boolean firstWhen = eventEntry.getWhens().size() == 1;
        // we only parse reminders under the when if reminders have not already
        // been handled (directly under the entry, or in a previous when for
        // this entry)
        boolean handleReminders = firstWhen && !hasSeenReminder;

        eventType = parser.next();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if ("reminder".equals(name)) {
                        // only want to store reminders on the first when.  they
                        // should have the same values for all other instances.
                        if (handleReminders) {
                            handleReminder(eventEntry);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ("when".equals(name)) {
                        return;
                    }
                default:
                    // ignore
            }

            eventType = parser.next();
        }
    }

    private void handleReminder(EventEntry eventEntry) {
        XmlPullParser parser = getParser();

        Reminder reminder = new Reminder();
        eventEntry.addReminder(reminder);

        String methodStr = parser.getAttributeValue(null /* ns */,
                "method");
        String minutesStr = parser.getAttributeValue(null /* ns */,
                "minutes");
        String hoursStr = parser.getAttributeValue(null /* ns */,
                "hours");
        String daysStr = parser.getAttributeValue(null /* ns */,
                "days");

        if (!StringUtils.isEmpty(methodStr)) {
            if ("alert".equals(methodStr)) {
                reminder.setMethod(Reminder.METHOD_ALERT);
            } else if ("email".equals(methodStr)) {
                reminder.setMethod(Reminder.METHOD_EMAIL);
            } else if ("sms".equals(methodStr)) {
                reminder.setMethod(Reminder.METHOD_SMS);
            }
        }

        int minutes = Reminder.MINUTES_DEFAULT;
        if (!StringUtils.isEmpty(minutesStr)) {
            minutes = StringUtils.parseInt(minutesStr, minutes);
        } else if (!StringUtils.isEmpty(hoursStr)) {
            minutes = 60*StringUtils.parseInt(hoursStr, minutes);
        } else if (!StringUtils.isEmpty(daysStr)) {
            minutes = 24*60*StringUtils.parseInt(daysStr, minutes);
        }
        // TODO: support absolute times?
        if (minutes < 0) {
            minutes = Reminder.MINUTES_DEFAULT;
        }
        reminder.setMinutes(minutes);
    }

    private void handleOriginalEvent(EventEntry eventEntry)
            throws XmlPullParserException, IOException {

        XmlPullParser parser = getParser();

        int eventType = parser.getEventType();
        String name = parser.getName();

        if (eventType != XmlPullParser.START_TAG ||
                (!"originalEvent".equals(parser.getName()))) {
            // should not happen.
            throw new
                    IllegalStateException("Expected <originalEvent>: Actual "
                    + "element: <"
                    + name + ">");
        }

        eventEntry.setOriginalEventId(
                parser.getAttributeValue(null /* ns */, "href"));

        eventType = parser.next();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if ("when".equals(name)) {
                        eventEntry.setOriginalEventStartTime(
                                parser.getAttributeValue(null/*ns*/, "startTime"));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ("originalEvent".equals(name)) {
                        return;
                    }
                default:
                    // ignore
            }

            eventType = parser.next();
        }
    }
}
