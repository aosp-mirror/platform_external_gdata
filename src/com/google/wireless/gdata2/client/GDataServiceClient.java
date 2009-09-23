// Copyright 2008 The Android Open Source Project

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.ConflictDetectedException;
import com.google.wireless.gdata2.client.AuthenticationException;
import com.google.wireless.gdata2.client.HttpException;
import com.google.wireless.gdata2.client.ResourceGoneException;
import com.google.wireless.gdata2.client.ResourceNotFoundException;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.MediaEntry;
import com.google.wireless.gdata2.data.StringUtils;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.serializer.GDataSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * Abstract base class for service-specific clients to access GData feeds.
 */
public abstract class GDataServiceClient {

  /**
   * Default gdata protocol version that this library supports
   */
  protected static String DEFAULT_GDATA_VERSION = "2.0";

  private final GDataClient gDataClient;
  private final GDataParserFactory gDataParserFactory;

  public GDataServiceClient(GDataClient gDataClient, GDataParserFactory gDataParserFactory) {
    this.gDataClient = gDataClient;
    this.gDataParserFactory = gDataParserFactory;
  }

  /**
   * Returns the {@link GDataClient} being used by this GDataServiceClient.
   *
   * @return The {@link GDataClient} being used by this GDataServiceClient.
   */
  protected GDataClient getGDataClient() {
    return gDataClient;
  }

  /**
   * Returns the protocol version used by this GDataServiceClient, in the form
   * of a "2.1" string
   *
   * @return String
   */
  public abstract String getProtocolVersion();

  /**
   * Returns the {@link GDataParserFactory} being used by this
   * GDataServiceClient.
   *
   * @return The {@link GDataParserFactory} being used by this
   *         GDataServiceClient.
   */
  protected GDataParserFactory getGDataParserFactory() {
    return gDataParserFactory;
  }

  /**
   * Returns the name of the service. Used for authentication.
   *
   * @return The name of the service.
   */
  public abstract String getServiceName();

  /**
   * Creates {@link QueryParams} that can be used to restrict the feed contents
   * that are fetched.
   *
   * @return The QueryParams that can be used with this client.
   */
  public QueryParams createQueryParams() {
    return gDataClient.createQueryParams();
  }

  /**
   * Fetches a feed for this user. The caller is responsible for closing the
   * returned {@link GDataParser}.
   *
   * @param feedEntryClass the class of Entry that is contained in the feed
   * @param feedUrl ThAe URL of the feed that should be fetched.
   * @param authToken The authentication token for this user.
   * @param eTag The etag used for this query. Passing null will result in an
   *        unconditional query
   * @return A {@link GDataParser} for the requested feed.
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws ResourceGoneException Thrown if the server indicates that the
   *         resource is Gone. Currently used to indicate that some Tombstones
   *         are missing.
   * @throws ResourceNotModifiedException Thrown if the retrieval fails because
   *         the specified ETag matches the current ETag of the entry (i.e. the
   *         entry has not been modified since last retrieval).
   * @throws HttpException Thrown if the request returns an error response.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   */
  public GDataParser getParserForFeed(Class feedEntryClass, String feedUrl, String authToken,
      String eTag) throws AuthenticationException, ResourceGoneException,
          ResourceNotModifiedException, HttpException, ParseException, IOException,
          ForbiddenException {
    try {
      InputStream is = gDataClient.getFeedAsStream(feedUrl, authToken, eTag, getProtocolVersion());
      return gDataParserFactory.createParser(feedEntryClass, is);
    } catch (HttpException e) {
      convertHttpExceptionForFeedReads("Could not fetch feed " + feedUrl, e);
      return null; // never reached
    }
  }

  /**
   * Fetches a media entry as an InputStream. The caller is responsible for
   * closing the returned {@link InputStream}.
   *
   * @param mediaEntryUrl The URL of the media entry that should be fetched.
   * @param authToken The authentication token for this user.
   * @param eTag The ETag associated with this request.
   * @return A {@link InputStream} for the requested media entry.
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws ResourceGoneException Thrown if the server indicates that the
   *         resource is Gone. Currently used to indicate that some Tombstones
   *         are missing.
   * @throws ResourceNotModifiedException Thrown if the retrieval fails because
   *         the specified ETag matches the current ETag of the entry (i.e. the
   *         entry has not been modified since last retrieval).
   * @throws ResourceNotFoundException Thrown if the resource was not found.
   * @throws HttpException Thrown if the request returns an error response.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   */
  public InputStream getMediaEntryAsStream(String mediaEntryUrl, String authToken, String eTag)
          throws AuthenticationException, ResourceGoneException, ResourceNotModifiedException,
          ResourceNotFoundException, HttpException, IOException, ForbiddenException {
    try {
      return gDataClient
          .getMediaEntryAsStream(mediaEntryUrl, authToken, eTag, getProtocolVersion());
    } catch (HttpException e) {
      convertHttpExceptionForEntryReads("Could not fetch media entry " + mediaEntryUrl, e);
      return null; // never reached
    }
  }

  /**
   * Creates a new entry at the provided feed. Parses the server response into
   * the version of the entry stored on the server.
   *
   * @param feedUrl The feed where the entry should be created.
   * @param authToken The authentication token for this user.
   * @param entry The entry that should be created.
   * @return The entry returned by the server as a result of creating the
   *         provided entry.
   * @throws ConflictDetectedException Thrown if the server detects an existing
   *         entry that conflicts with this one.
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws PreconditionFailedException Thrown if the update fails because the
   *         specified ETag does not match the current ETag of the
   * @throws HttpException Thrown if the request returns an error response.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   * @throws BadRequestException thrown if the server returns a 400
   * @throws ForbiddenException thrown if the server returns a 403
   */
  public Entry createEntry(String feedUrl, String authToken, Entry entry)
          throws ConflictDetectedException, AuthenticationException, PreconditionFailedException,
          HttpException, ParseException, IOException, ForbiddenException, BadRequestException {
    GDataSerializer serializer = gDataParserFactory.createSerializer(entry);
    try {
      InputStream is =
          gDataClient.createEntry(feedUrl, authToken, getProtocolVersion(), serializer);
      return parseEntry(entry.getClass(), is);
    } catch (HttpException e) {
        try {
            convertHttpExceptionForWrites(entry.getClass(),
                    "Could not create " + "entry " + feedUrl, e);
        } catch (ResourceNotFoundException e1) {
            // this should never happen
            throw e;
        }
        return null; // never reached.
    }
  }

  /**
   * Fetches an existing entry.
   *
   * @param entryClass the type of entry to expect
   * @param id of the entry to fetch.
   * @param authToken The authentication token for this user
   * @param eTag The etag used for this query. Passing null will result in an
   *        unconditional query
   * @return The entry returned by the server.
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws ResourceNotFoundException Thrown if the resource was not found.
   * @throws ResourceNotModifiedException Thrown if the retrieval fails because
   *         the specified ETag matches the current ETag of the entry (i.e. the
   *         entry has not been modified since last retrieval).
   * @throws HttpException Thrown if the request returns an error response.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   */
  public Entry getEntry(Class entryClass, String id, String authToken, String eTag)
          throws AuthenticationException, ResourceNotFoundException, ResourceNotModifiedException,
          HttpException, ParseException, IOException, ForbiddenException {
    try {
      InputStream is = getGDataClient().getFeedAsStream(id, authToken, eTag, getProtocolVersion());
      return parseEntry(entryClass, is);
    } catch (HttpException e) {
      convertHttpExceptionForEntryReads("Could not fetch entry " + id, e);
      return null; // never reached
    }
  }

  /**
   * Updates an existing entry. Parses the server response into the version of
   * the entry stored on the server.
   *
   * @param entry The entry that should be updated.
   * @param authToken The authentication token for this user.
   * @return The entry returned by the server as a result of updating the
   *         provided entry.
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws ConflictDetectedException Thrown if the server detects an existing
   *         entry that conflicts with this one, or if the server version of
   *         this entry has changed since it was retrieved.
   * @throws PreconditionFailedException Thrown if the update fails because the
   *         specified ETag does not match the current ETag of the entry.
   * @throws HttpException Thrown if the request returns an error response.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   * @throws BadRequestException thrown if the server returns a 400
   * @throws ForbiddenException thrown if the server returns a 403
   * @throws ResourceNotFoundException Thrown if the resource was not found.
   */
  public Entry updateEntry(Entry entry, String authToken) throws AuthenticationException,
          ConflictDetectedException, PreconditionFailedException, HttpException, ParseException,
          IOException, ForbiddenException, ResourceNotFoundException, BadRequestException {
    String editUri = entry.getEditUri();
    if (StringUtils.isEmpty(editUri)) {
      throw new ParseException("No edit URI -- cannot update.");
    }

    GDataSerializer serializer = gDataParserFactory.createSerializer(entry);
    try {
      InputStream is =
          gDataClient.updateEntry(editUri, authToken, entry.getETag(), getProtocolVersion(),
              serializer);
      return parseEntry(entry.getClass(), is);
    } catch (HttpException e) {
      convertHttpExceptionForWrites(entry.getClass(), "Could not update " + "entry " + editUri, e);
      return null; // never reached
    }
  }

  /**
   * Updates an existing entry. Parses the server response into the metadata of
   * the entry stored on the server.
   *
   * @param editUri The URI of the resource that should be updated.
   * @param inputStream The {@link java.io.InputStream} that contains the new
   *        value of the media entry
   * @param contentType The content type of the new media entry
   * @param authToken The authentication token for this user.
   * @return The entry returned by the server as a result of updating the
   *         provided entry
   * @param eTag The etag used for this query. Passing null will result in an
   *        unconditional query
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws ConflictDetectedException Thrown if the server detects an existing
   *         entry that conflicts with this one, or if the server version of
   *         this entry has changed since it was retrieved.
   * @throws PreconditionFailedException Thrown if the update fails because the
   *         specified ETag does not match the current ETag of the entry.
   * @throws HttpException Thrown if the request returns an error response.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   * @throws BadRequestException thrown if the server returns a 400
   * @throws ForbiddenException thrown if the server returns a 403
   * @throws ResourceNotFoundException Thrown if the resource was not found.
   */
  public MediaEntry updateMediaEntry(String editUri, InputStream inputStream, String contentType,
      String authToken, String eTag) throws AuthenticationException, ConflictDetectedException,
          PreconditionFailedException, HttpException, ParseException, IOException,
          ForbiddenException, ResourceNotFoundException, BadRequestException {
    if (StringUtils.isEmpty(editUri)) {
      throw new IllegalArgumentException("No edit URI -- cannot update.");
    }

    try {
      InputStream is =
          gDataClient.updateMediaEntry(editUri, authToken, eTag, getProtocolVersion(), inputStream,
              contentType);
      return (MediaEntry) parseEntry(MediaEntry.class, is);
    } catch (HttpException e) {
      convertHttpExceptionForWrites(MediaEntry.class, "Could not update entry " + editUri, e);
      return null; // never reached
    }
  }

  /**
   * Deletes an existing entry.
   *
   * @param editUri The editUri for the entry that should be deleted.
   * @param authToken The authentication token for this user.
   * @param eTag The etag used for this query. Passing null will result in an
   *        unconditional query
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws ConflictDetectedException Thrown if the server version of this
   *         entry has changed since it was retrieved.
   * @throws PreconditionFailedException Thrown if the update fails because the
   *         specified ETag does not match the current ETag of the entry.
   * @throws HttpException Thrown if the request returns an error response.
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   * @throws BadRequestException thrown if the server returns a 400
   * @throws ForbiddenException thrown if the server returns a 403
   * @throws ResourceNotFoundException Thrown if the resource was not found.
   */
  public void deleteEntry(String editUri, String authToken, String eTag)
          throws AuthenticationException, ConflictDetectedException, PreconditionFailedException,
          HttpException, ParseException, IOException, ForbiddenException,
          ResourceNotFoundException, BadRequestException {
    try {
      gDataClient.deleteEntry(editUri, authToken, eTag);
    } catch (HttpException e) {
      if (e.getStatusCode() == HttpException.SC_NOT_FOUND) {
        // the server does not know about this entry.
        // nothing to delete.
        return;
      }
      convertHttpExceptionForWrites(null, "Unable to delete " + editUri, e);
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

  /**
   * Submits a batch of operations.
   *
   * @param feedEntryClass the type of the entry to expect.
   * @param batchUrl The url to which the batch is submitted.
   * @param authToken The authentication token for this user.
   * @param entries an enumeration of the entries to submit.
   * @throws AuthenticationException Thrown if the server considers the
   *         authToken invalid.
   * @throws HttpException if the service returns an error response
   * @throws ParseException Thrown if the server response cannot be parsed.
   * @throws IOException Thrown if an error occurs while communicating with the
   *         GData service.
   * @throws BadRequestException thrown if the server returns a 400
   * @throws ForbiddenException thrown if the server returns a 403
   */
  public GDataParser submitBatch(Class feedEntryClass, String batchUrl, String authToken,
      Enumeration entries) throws AuthenticationException, HttpException, ParseException,
          IOException, ForbiddenException, BadRequestException {
    GDataSerializer serializer = gDataParserFactory.createSerializer(entries);
    try {
      InputStream is =
          gDataClient.submitBatch(batchUrl, authToken, getProtocolVersion(), serializer);
      return gDataParserFactory.createParser(feedEntryClass, is);
    } catch (HttpException e) {
      convertHttpExceptionsForBatches("Could not submit batch " + batchUrl, e);
      return null; // never reached.
    }
  }

  protected void convertHttpExceptionForFeedReads(String message, HttpException cause)
          throws AuthenticationException, ResourceGoneException, ResourceNotModifiedException,
          HttpException, ForbiddenException {
    switch (cause.getStatusCode()) {
      case HttpException.SC_FORBIDDEN:
          throw new ForbiddenException(message, cause);
      case HttpException.SC_UNAUTHORIZED:
        throw new AuthenticationException(message, cause);
      case HttpException.SC_GONE:
        throw new ResourceGoneException(message, cause);
      case HttpException.SC_NOT_MODIFIED:
        throw new ResourceNotModifiedException(message, cause);
      default:
        throw new HttpException(message + ": " + cause.getMessage(), cause.getStatusCode(), cause
            .getResponseStream());
    }
  }

  protected void convertHttpExceptionForEntryReads(String message, HttpException cause)
          throws AuthenticationException, HttpException, ResourceNotFoundException,
          ResourceNotModifiedException, ForbiddenException {
    switch (cause.getStatusCode()) {
      case HttpException.SC_FORBIDDEN:
          throw new ForbiddenException(message, cause);
      case HttpException.SC_UNAUTHORIZED:
        throw new AuthenticationException(message, cause);
      case HttpException.SC_NOT_FOUND:
        throw new ResourceNotFoundException(message, cause);
      case HttpException.SC_NOT_MODIFIED:
        throw new ResourceNotModifiedException(message, cause);
      default:
        throw new HttpException(message + ": " + cause.getMessage(), cause.getStatusCode(), cause
            .getResponseStream());
    }
  }

  protected void convertHttpExceptionsForBatches(String message, HttpException cause)
          throws AuthenticationException, ParseException, HttpException, ForbiddenException,
          BadRequestException {
    switch (cause.getStatusCode()) {
      case HttpException.SC_FORBIDDEN:
          throw new ForbiddenException(message, cause);
      case HttpException.SC_UNAUTHORIZED:
        throw new AuthenticationException(message, cause);
      case HttpException.SC_BAD_REQUEST:
        throw new BadRequestException(message, cause);
      default:
        throw new HttpException(message + ": " + cause.getMessage(), cause.getStatusCode(), cause
            .getResponseStream());
    }
  }

  protected void convertHttpExceptionForWrites(Class entryClass, String message,
          HttpException cause)
          throws ConflictDetectedException, AuthenticationException, PreconditionFailedException,
          ParseException, HttpException, IOException, ForbiddenException,
          ResourceNotFoundException, BadRequestException {
    switch (cause.getStatusCode()) {
      case HttpException.SC_CONFLICT:
        Entry entry = null;
        if (entryClass != null) {
          InputStream is = cause.getResponseStream();
          if (is != null) {
            entry = parseEntry(entryClass, cause.getResponseStream());
          }
        }
        throw new ConflictDetectedException(entry);
      case HttpException.SC_BAD_REQUEST:
        throw new BadRequestException(message, cause);
      case HttpException.SC_FORBIDDEN:
        throw new ForbiddenException(message, cause);
      case HttpException.SC_UNAUTHORIZED:
        throw new AuthenticationException(message, cause);
      case HttpException.SC_PRECONDITION_FAILED:
        throw new PreconditionFailedException(message, cause);
      case HttpException.SC_NOT_FOUND:
        throw new ResourceNotFoundException(message, cause);
      default:
        throw new HttpException(message + ": " + cause.getMessage(), cause.getStatusCode(), cause
            .getResponseStream());
    }
  }
}
