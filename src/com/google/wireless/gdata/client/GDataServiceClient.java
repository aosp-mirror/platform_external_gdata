// Copyright 2008 The Android Open Source Project

package com.google.wireless.gdata.client;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.MediaEntry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.GDataSerializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract base class for service-specific clients to access GData feeds.
 */
public abstract class GDataServiceClient {
    private final GDataClient gDataClient;
    private final GDataParserFactory gDataParserFactory;

    public GDataServiceClient(GDataClient gDataClient,
                              GDataParserFactory gDataParserFactory) {
        this.gDataClient = gDataClient;
        this.gDataParserFactory = gDataParserFactory;
    }

    /**
     * Returns the {@link GDataClient} being used by this GDataServiceClient.
     * @return The {@link GDataClient} being used by this GDataServiceClient.
     */
    protected GDataClient getGDataClient() {
        return gDataClient;
    }

    /**
     * Returns the {@link GDataParserFactory} being used by this
     * GDataServiceClient.
     * @return The {@link GDataParserFactory} being used by this
     * GDataServiceClient.
     */
    protected GDataParserFactory getGDataParserFactory() {
        return gDataParserFactory;
    }

    /**
     * Returns the name of the service.  Used for authentication.
     * @return The name of the service.
     */
    public abstract String getServiceName();

    /**
     * Creates {@link QueryParams} that can be used to restrict the feed
     * contents that are fetched.
     * @return The QueryParams that can be used with this client.
     */
    public QueryParams createQueryParams() {
        return gDataClient.createQueryParams();
    }

    /**
     * Fetches a feed for this user.  The caller is responsible for closing the
     * returned {@link GDataParser}.
     *
     * @param feedEntryClass the class of Entry that is contained in the feed
     * @param feedUrl ThAe URL of the feed that should be fetched.
     * @param authToken The authentication token for this user.
     * @return A {@link GDataParser} for the requested feed.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws HttpException Thrown if the http response contains a result other than 2xx
     */
    public GDataParser getParserForFeed(Class feedEntryClass, String feedUrl, String authToken)
            throws ParseException, IOException, HttpException {
        InputStream is = gDataClient.getFeedAsStream(feedUrl, authToken);
        return gDataParserFactory.createParser(feedEntryClass, is);
    }

    /**
     * Fetches a media entry as an InputStream.  The caller is responsible for closing the
     * returned {@link InputStream}.
     *
     * @param mediaEntryUrl The URL of the media entry that should be fetched.
     * @param authToken The authentication token for this user.
     * @return A {@link InputStream} for the requested media entry.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     */
    public InputStream getMediaEntryAsStream(String mediaEntryUrl, String authToken)
            throws IOException, HttpException {
        return gDataClient.getMediaEntryAsStream(mediaEntryUrl, authToken);
    }

    /**
     * Creates a new entry at the provided feed.  Parses the server response
     * into the version of the entry stored on the server.
     *
     * @param feedUrl The feed where the entry should be created.
     * @param authToken The authentication token for this user.
     * @param entry The entry that should be created.
     * @return The entry returned by the server as a result of creating the
     * provided entry.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws HttpException if the service returns an error response
     */
    public Entry createEntry(String feedUrl, String authToken, Entry entry)
            throws ParseException, IOException, HttpException {
        GDataSerializer serializer = gDataParserFactory.createSerializer(entry);
        InputStream is = gDataClient.createEntry(feedUrl, authToken, serializer);
        return parseEntry(entry.getClass(), is);
    }

  /**
   * Fetches an existing entry.
   * @param entryClass the type of entry to expect
   * @param id of the entry to fetch.
   * @param authToken The authentication token for this user. @return The entry returned by the server.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws HttpException if the service returns an error response
   * @throws IOException Thrown if an error occurs while communicating with
   * the GData service.
   * @return The entry returned by the server
   */
    public Entry getEntry(Class entryClass, String id, String authToken)
          throws ParseException, IOException, HttpException {
        InputStream is = getGDataClient().getFeedAsStream(id, authToken);
        return parseEntry(entryClass, is);
    }

    /**
     * Updates an existing entry.  Parses the server response into the version
     * of the entry stored on the server.
     *
     * @param entry The entry that should be updated.
     * @param authToken The authentication token for this user.
     * @return The entry returned by the server as a result of updating the
     * provided entry.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws HttpException if the service returns an error response
     */
    public Entry updateEntry(Entry entry, String authToken)
            throws ParseException, IOException, HttpException {
        String editUri = entry.getEditUri();
        if (StringUtils.isEmpty(editUri)) {
            throw new ParseException("No edit URI -- cannot update.");
        }

        GDataSerializer serializer = gDataParserFactory.createSerializer(entry);
        InputStream is = gDataClient.updateEntry(editUri, authToken, serializer);
        return parseEntry(entry.getClass(), is);
    }

    /**
     * Updates an existing entry.  Parses the server response into the metadata
     * of the entry stored on the server.
     *
     * @param editUri The URI of the resource that should be updated.
     * @param inputStream The {@link java.io.InputStream} that contains the new value
     *   of the media entry
     * @param contentType The content type of the new media entry
     * @param authToken The authentication token for this user.
     * @return The entry returned by the server as a result of updating the
     * provided entry.
     * @throws HttpException if the service returns an error response
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     */
    public MediaEntry updateMediaEntry(String editUri, InputStream inputStream, String contentType,
            String authToken) throws IOException, HttpException, ParseException {
        if (StringUtils.isEmpty(editUri)) {
            throw new IllegalArgumentException("No edit URI -- cannot update.");
        }

        InputStream is = gDataClient.updateMediaEntry(editUri, authToken, inputStream, contentType);
        return (MediaEntry)parseEntry(MediaEntry.class, is);
    }

    /**
     * Deletes an existing entry.
     *
     * @param editUri The editUri for the entry that should be deleted.
     * @param authToken The authentication token for this user.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws HttpException if the service returns an error response
     */
    public void deleteEntry(String editUri, String authToken)
            throws IOException, HttpException {
        gDataClient.deleteEntry(editUri, authToken);
    }

    private Entry parseEntry(Class entryClass, InputStream is) throws ParseException, IOException {
        GDataParser parser = null;
        try {
            parser = gDataParserFactory.createParser(entryClass, is);
            return parser.parseStandaloneEntry();
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }
}
