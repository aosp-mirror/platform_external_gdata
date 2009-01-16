// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.spreadsheets.client;

import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;
import com.google.wireless.gdata.client.HttpException;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.GDataSerializer;
import com.google.wireless.gdata.spreadsheets.data.CellEntry;
import com.google.wireless.gdata.spreadsheets.data.ListEntry;
import com.google.wireless.gdata.spreadsheets.data.SpreadsheetEntry;
import com.google.wireless.gdata.spreadsheets.data.WorksheetEntry;

import java.io.IOException;
import java.io.InputStream;

/**
 * GDataServiceClient for accessing Google Spreadsheets. This client can
 * access and parse all of the Spreadsheets feed types: Spreadsheets feed,
 * Worksheets feed, List feed, and Cells feed. Read operations are supported
 * on all feed types, but only the List and Cells feeds support write
 * operations. (This is a limitation of the protocol, not this API. Such write
 * access may be added to the protocol in the future, requiring changes to
 * this implementation.)
 * 
 * Only 'private' visibility and 'full' projections are currently supported.
 */
public class SpreadsheetsClient extends GDataServiceClient {
    /** The name of the service, dictated to be 'wise' by the protocol. */
    private static final String SERVICE = "wise";

    /** Standard base feed url for spreadsheets. */
    public static final String SPREADSHEETS_BASE_FEED_URL =
            "http://spreadsheets.google.com/feeds/spreadsheets/private/full";

    /** Base feed url for spreadsheets. */
    private final String baseFeedUrl;

    /**
     * Create a new SpreadsheetsClient.
     *
     * @param client The GDataClient that should be used to authenticate
     *        requests, retrieve feeds, etc.
     * @param spreadsheetFactory The GDataParserFactory that should be used to obtain GDataParsers
     * used by this client.
     * @param baseFeedUrl The base URL for spreadsheets feeds.
     */
    public SpreadsheetsClient(GDataClient client,
            GDataParserFactory spreadsheetFactory,
            String baseFeedUrl) {
        super(client, spreadsheetFactory);
        this.baseFeedUrl = baseFeedUrl;
    }

    /**
     * Create a new SpreadsheetsClient.  Uses the standard base URL for spreadsheets feeds.
     * 
     * @param client The GDataClient that should be used to authenticate
     *        requests, retrieve feeds, etc.
     */
    public SpreadsheetsClient(GDataClient client,
            GDataParserFactory spreadsheetFactory) {
        this(client, spreadsheetFactory, SPREADSHEETS_BASE_FEED_URL);
    }

    /* (non-Javadoc)
     * @see GDataServiceClient#getServiceName
     */
    public String getServiceName() {
        return SERVICE;
    }

    /**
     * Returns a parser for the specified feed type.
     * 
     * @param feedEntryClass the Class of entry type that will be parsed. This lets this
     *   method figure out which parser to create.
     * @param feedUri the URI of the feed to be fetched and parsed
     * @param authToken the current authToken to use for the request @return a parser for the indicated feed
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the response from the server could not be
     *         parsed
     */
    private GDataParser getParserForTypedFeed(Class feedEntryClass, String feedUri,
            String authToken) throws ParseException, IOException, HttpException {
        GDataClient gDataClient = getGDataClient();
        GDataParserFactory gDataParserFactory = getGDataParserFactory();

        InputStream is = gDataClient.getFeedAsStream(feedUri, authToken);
        return gDataParserFactory.createParser(feedEntryClass, is);
    }

    /* (non-javadoc)
     * @see GDataServiceClient#createEntry
     */
    public Entry createEntry(String feedUri, String authToken, Entry entry)
            throws ParseException, IOException, HttpException {

        GDataParserFactory factory = getGDataParserFactory();
        GDataSerializer serializer = factory.createSerializer(entry);

        InputStream is = getGDataClient().createEntry(feedUri, authToken, serializer);
        GDataParser parser = factory.createParser(entry.getClass(), is);
        try {
            return parser.parseStandaloneEntry();
        } finally {
            parser.close();
        }
    }

    /**
     * Returns a parser for a Cells-based feed.
     *
     * @param feedUri the URI of the feed to be fetched and parsed
     * @param authToken the current authToken to use for the request
     * @return a parser for the indicated feed
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the response from the server could not be
     *         parsed
     */
    public GDataParser getParserForCellsFeed(String feedUri, String authToken)
            throws ParseException, IOException, HttpException {
        return getParserForTypedFeed(CellEntry.class, feedUri, authToken);
    }

    /**
     * Fetches a GDataParser for the indicated feed. The parser can be used to
     * access the contents of URI. WARNING: because we cannot reliably infer
     * the feed type from the URI alone, this method assumes the default feed
     * type! This is probably NOT what you want. Please use the
     * getParserFor[Type]Feed methods.
     *
     * @param feedEntryClass
     * @param feedUri the URI of the feed to be fetched and parsed
     * @param authToken the current authToken to use for the request
     * @return a parser for the indicated feed
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the response from the server could not be
     *         parsed
     */
    public GDataParser getParserForFeed(Class feedEntryClass, String feedUri, String authToken)
            throws ParseException, IOException, HttpException {
        GDataClient gDataClient = getGDataClient();
        GDataParserFactory gDataParserFactory = getGDataParserFactory();
        InputStream is = gDataClient.getFeedAsStream(feedUri, authToken);
        return gDataParserFactory.createParser(feedEntryClass, is);
    }

    /**
     * Returns a parser for a List (row-based) feed.
     * 
     * @param feedUri the URI of the feed to be fetched and parsed
     * @param authToken the current authToken to use for the request
     * @return a parser for the indicated feed
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the response from the server could not be
     *         parsed
     */
    public GDataParser getParserForListFeed(String feedUri, String authToken)
            throws ParseException, IOException, HttpException {
        return getParserForTypedFeed(ListEntry.class, feedUri, authToken);
    }

    /**
     * Returns a parser for a Spreadsheets meta-feed.
     * 
     * @param feedUri the URI of the feed to be fetched and parsed
     * @param authToken the current authToken to use for the request
     * @return a parser for the indicated feed
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the response from the server could not be
     *         parsed
     */
    public GDataParser getParserForSpreadsheetsFeed(String feedUri, String authToken)
            throws ParseException, IOException, HttpException {
        return getParserForTypedFeed(SpreadsheetEntry.class, feedUri, authToken);
    }

    /**
     * Returns a parser for a Worksheets meta-feed.
     * 
     * @param feedUri the URI of the feed to be fetched and parsed
     * @param authToken the current authToken to use for the request
     * @return a parser for the indicated feed
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the response from the server could not be
     *         parsed
     */
    public GDataParser getParserForWorksheetsFeed(String feedUri, String authToken)
            throws ParseException, IOException, HttpException {
        return getParserForTypedFeed(WorksheetEntry.class, feedUri, authToken);
    }

    /**
     * Updates an entry. The URI to be updated is taken from
     * <code>entry</code>. Note that only entries in List and Cells feeds
     * can be updated, so <code>entry</code> must be of the corresponding
     * type; other types will result in an exception.
     * 
     * @param entry the entry to be updated; must include its URI
     * @param authToken the current authToken to be used for the operation
     * @return An Entry containing the re-parsed version of the entry returned
     *         by the server in response to the update.
     * @throws HttpException if an http error is encountered
     * @throws ParseException if the server returned an error, if the server's
     *         response was unparseable (unlikely), or if <code>entry</code>
     *         is of a read-only type
     * @throws IOException on network error
     */
    public Entry updateEntry(Entry entry, String authToken)
            throws ParseException, IOException, HttpException {
        GDataParserFactory factory = getGDataParserFactory();
        GDataSerializer serializer = factory.createSerializer(entry);

        String editUri = entry.getEditUri();
        if (StringUtils.isEmpty(editUri)) {
            throw new ParseException("No edit URI -- cannot update.");
        }

        InputStream is = getGDataClient().updateEntry(editUri, authToken, serializer);
        GDataParser parser = factory.createParser(entry.getClass(), is);
        try {
            return parser.parseStandaloneEntry();
        } finally {
            parser.close();
        }
    }

    public String getBaseFeedUrl() {
        return baseFeedUrl;
    }  
}
