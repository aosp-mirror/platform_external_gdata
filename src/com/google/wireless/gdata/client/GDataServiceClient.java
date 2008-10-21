// Copyright 2008 The Android Open Source Project

package com.google.wireless.gdata.client;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.data.MediaEntry;
import com.google.wireless.gdata.ConflictDetectedException;
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

    // TODO: remove this, after coordinating with other developers.
    private static final boolean PARSE_CONFLICTING_ENTRIES = false;

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
     * @param feedUrl The URL of the feed that should be fetched.
     * @param authToken The authentication token for this user.
     * @return A {@link GDataParser} for the requested feed.
     * @throws AuthenticationException Thrown if the server considers the
     * authToken invalid.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     */
    public GDataParser getParserForFeed(Class feedEntryClass, String feedUrl, String authToken)
            throws AuthenticationException, ParseException, IOException,
            AllDeletedUnavailableException {
        try {
            InputStream is = gDataClient.getFeedAsStream(feedUrl, authToken);
            return gDataParserFactory.createParser(feedEntryClass, is);
        } catch (HttpException e) {
            convertHttpExceptionForReads("Could not fetch feed.", e);
            return null; // never reached
        }
    }

    /**
     * Fetches a media entry as an InputStream.  The caller is responsible for closing the
     * returned {@link InputStream}.
     *
     * @param mediaEntryUrl The URL of the media entry that should be fetched.
     * @param authToken The authentication token for this user.
     * @return A {@link InputStream} for the requested media entry.
     * @throws AuthenticationException Thrown if the server considers the
     * authToken invalid.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     */
    public InputStream getMediaEntryAsStream(String mediaEntryUrl, String authToken)
            throws AuthenticationException, IOException, ResourceNotFoundException {
        try {
            return gDataClient.getMediaEntryAsStream(mediaEntryUrl, authToken);
        } catch (HttpException e) {
            convertHttpExceptionForMediaEntries("Could not fetch media entry " + mediaEntryUrl, e);
            return null; // never reached
        }
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
     * @throws AuthenticationException Thrown if the server considers the
     * authToken invalid.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws ConflictDetectedException Thrown if the server detects an
     * existing entry that conflicts with this one.
     */
    public Entry createEntry(String feedUrl, String authToken, Entry entry)
            throws AuthenticationException, ParseException, IOException,
            ConflictDetectedException {
        GDataSerializer serializer = gDataParserFactory.createSerializer(entry);
        try {
            InputStream is = gDataClient.createEntry(feedUrl, authToken, serializer);
            return parseEntry(entry.getClass(), is);
        } catch (HttpException e) {
            convertHttpExceptionForWrites(entry.getClass(), "Could not create entry.", e);
            return null; // never reached.
        }
    }

  /**
   * Fetches an existing entry.
   * @param entryClass the type of entry to expect
   * @param id of the entry to fetch.
   * @param authToken The authentication token for this user. @return The entry returned by the server.
   * @throws AuthenticationException Thrown if the server considers the
   * authToken invalid.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with
   * the GData service.
   */
    public Entry getEntry(Class entryClass, String id, String authToken)
          throws AuthenticationException, ParseException, IOException,
          AllDeletedUnavailableException {
        try {
            InputStream is = getGDataClient().getFeedAsStream(id, authToken);
            return parseEntry(entryClass, is);
        } catch (HttpException e) {
            convertHttpExceptionForReads("Could not fetch entry.", e);
            return null; // never reached
        }
    }

    /**
     * Updates an existing entry.  Parses the server response into the version
     * of the entry stored on the server.
     *
     * @param entry The entry that should be updated.
     * @param authToken The authentication token for this user.
     * @return The entry returned by the server as a result of updating the
     * provided entry.
     * @throws AuthenticationException Thrown if the server considers the
     * authToken invalid.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws ConflictDetectedException Thrown if the server detects an
     * existing entry that conflicts with this one, or if the server version of
     * this entry has changed since it was retrieved.
     */
    public Entry updateEntry(Entry entry, String authToken)
            throws AuthenticationException, ParseException, IOException,
            ConflictDetectedException {
        String editUri = entry.getEditUri();
        if (StringUtils.isEmpty(editUri)) {
            throw new ParseException("No edit URI -- cannot update.");
        }

        GDataSerializer serializer = gDataParserFactory.createSerializer(entry);
        try {
            InputStream is = gDataClient.updateEntry(editUri,
                                         authToken,
                                         serializer);
            return parseEntry(entry.getClass(), is);
        } catch (HttpException e) {
            if (e.getStatusCode() == HttpException.SC_NOT_FOUND) {
                throw new ParseException("Could not update entry.", e);
            }
            convertHttpExceptionForWrites(entry.getClass(), "Could not update entry.", e);
            return null; // never reached
        }
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
     * @throws AuthenticationException Thrown if the server considers the
     * authToken invalid.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws ConflictDetectedException Thrown if the server detects an
     * existing entry that conflicts with this one, or if the server version of
     * this entry has changed since it was retrieved.
     */
    public MediaEntry updateMediaEntry(String editUri, InputStream inputStream,
            String contentType,
            String authToken)
            throws AuthenticationException, ParseException, IOException,
            ConflictDetectedException {
        if (StringUtils.isEmpty(editUri)) {
            throw new ParseException("No edit URI -- cannot update.");
        }

        try {
            InputStream is = gDataClient.updateMediaEntry(editUri, authToken,
                    inputStream, contentType);
            return (MediaEntry)parseEntry(MediaEntry.class, is);
        } catch (HttpException e) {
            convertHttpExceptionForWrites(MediaEntry.class, "Could not update entry.", e);
            return null; // never reached
        }
    }

    /**
     * Deletes an existing entry.
     *
     * @param editUri The editUri for the entry that should be deleted.
     * @param authToken The authentication token for this user.
     * @throws AuthenticationException Thrown if the server considers the
     * authToken invalid.
     * @throws ParseException Thrown if the server response cannot be parsed.
     * @throws IOException Thrown if an error occurs while communicating with
     * the GData service.
     * @throws ConflictDetectedException Thrown if the server version of
     * this entry has changed since it was retrieved.
     */
    public void deleteEntry(String editUri, String authToken)
            throws AuthenticationException, ConflictDetectedException,
            ParseException, IOException {
        try {
            gDataClient.deleteEntry(editUri, authToken);
        } catch (HttpException e) {
            if (e.getStatusCode() == HttpException.SC_NOT_FOUND) {
                // the server does not know about this entry.
                // nothing to delete.
                return;
            }
            convertHttpExceptionForWrites(null, "Unable to delete", e);
        }
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

    protected void convertHttpExceptionForReads(String message, HttpException cause)
            throws AuthenticationException, IOException, AllDeletedUnavailableException {
        switch (cause.getStatusCode()) {
            case HttpException.SC_FORBIDDEN:
            case HttpException.SC_UNAUTHORIZED:
                throw new AuthenticationException(message, cause);
            case HttpException.SC_GONE:
                throw new AllDeletedUnavailableException(message, cause);
            default:
                throw new IOException(message + ": " + cause.getMessage());
        }
    }

    protected void convertHttpExceptionForMediaEntries(String message, HttpException cause)
            throws AuthenticationException, IOException, ResourceNotFoundException {
        switch (cause.getStatusCode()) {
            case HttpException.SC_FORBIDDEN:
            case HttpException.SC_UNAUTHORIZED:
                throw new AuthenticationException(message, cause);
            case HttpException.SC_NOT_FOUND:
                throw new ResourceNotFoundException(message, cause);
            default:
                throw new IOException(message + ": " + cause.getMessage());
        }
    }

    protected void convertHttpExceptionForWrites(Class entryClass, String message,
            HttpException cause) throws ConflictDetectedException,
            AuthenticationException, ParseException, IOException {
        switch (cause.getStatusCode()) {
            case HttpException.SC_CONFLICT:
                Entry entry = null;
                if (PARSE_CONFLICTING_ENTRIES) {
                    if (entryClass != null) {
                        InputStream is = cause.getResponseStream();
                        if (is != null) {
                            entry = parseEntry(entryClass, cause.getResponseStream());
                        }
                    }
                }
                throw new ConflictDetectedException(entry);
            case HttpException.SC_BAD_REQUEST:
                throw new ParseException(message + ": " + cause.getMessage());
            case HttpException.SC_FORBIDDEN:
            case HttpException.SC_UNAUTHORIZED:
                throw new AuthenticationException(message, cause);
            default:
                throw new IOException(message + ": " + cause.getMessage());
        }
    }
}
