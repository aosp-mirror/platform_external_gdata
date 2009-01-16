// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.calendar.client;

import com.google.wireless.gdata.calendar.data.CalendarEntry;
import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;
import com.google.wireless.gdata.client.HttpException;
import com.google.wireless.gdata.client.QueryParams;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

/**
 * GDataServiceClient for accessing Google Calendar.  This client can access and
 * parse both the meta feed (list of calendars for a user) and events feeds
 * (calendar entries for a specific user).  The parsers this class uses handle
 * the XML version of feeds.
 */
// TODO: add a method that applies projections such as cutting the attendees.
public class CalendarClient extends GDataServiceClient {
    /** Service value for calendar. */
    public static final String SERVICE = "cl";

    public static final String PROJECTION_PRIVATE_FULL = "/private/full";
    public static final String PROJECTION_PRIVATE_SELF_ATTENDANCE = "/private/full-selfattendance";

    /** Standard base url for a calendar feed. */
    private static final String CALENDAR_BASE_FEED_URL =
        "http://www.google.com/calendar/feeds/";

    /**
     * Create a new CalendarClient.  Uses the standard base URL for calendar feeds.
     * @param client The GDataClient that should be used to authenticate
     * requests, retrieve feeds, etc.
     * @param factory The factory that should be used to obtain {@link GDataParser}s used by this
     * client.
     */
    public CalendarClient(GDataClient client, GDataParserFactory factory) {
        super(client, factory);
    }

    /* (non-Javadoc)
     * @see GDataServiceClient#getServiceName
     */
    public String getServiceName() {
        return SERVICE;
    }

    /**
     * Returns the url for the default feed for a user, after applying the
     * provided QueryParams.
     * @param username The username for this user.
     * @param projection the projection to use
     * @param params The QueryParams that should be applied to the default feed.
     * @return The url that should be used to retrieve a user's default feed.
     */
    public String getDefaultCalendarUrl(String username, String projection, QueryParams params) {
        String feedUrl = CALENDAR_BASE_FEED_URL + getGDataClient().encodeUri(username);
        feedUrl += projection;
        if (params == null) {
            return feedUrl;
        }
        return params.generateQueryUrl(feedUrl);
    }

    /**
     * Returns the url for the metafeed for user, which contains the information about
     * the user's calendars.
     * @param username The username for this user.
     * @return The url that should be used to retrieve a user's default feed.
     */
    public String getUserCalendarsUrl(String username) {
        return CALENDAR_BASE_FEED_URL + getGDataClient().encodeUri(username);
    }

    /**
     * Fetches the meta feed containing the list of calendars for a user.  The
     * caller is responsible for closing the returned {@link GDataParser}.
     *
     * @param feedUrl the URL of the user calendars feed
     * @param authToken The authentication token for this user
     * @return A GDataParser with the meta feed containing the list of
     *   calendars for this user.
     * @throws ParseException Thrown if the feed could not be fetched.
     */
    public GDataParser getParserForUserCalendars(String feedUrl, String authToken)
            throws ParseException, IOException, HttpException {
        GDataClient gDataClient = getGDataClient();
        InputStream is = gDataClient.getFeedAsStream(feedUrl, authToken);
        return getGDataParserFactory().createParser(CalendarEntry.class, is);
    }
}
