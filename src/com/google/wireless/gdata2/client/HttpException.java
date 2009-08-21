// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

import java.io.InputStream;

/**
 * A class representing exceptional (i.e., non 200) responses from an HTTP
 * Server.
 */
public class HttpException extends GDataException {

  public static final int SC_NOT_MODIFIED = 304;

  public static final int SC_BAD_REQUEST = 400;

  public static final int SC_UNAUTHORIZED = 401;

  public static final int SC_FORBIDDEN = 403;

  public static final int SC_NOT_FOUND = 404;

  public static final int SC_CONFLICT = 409;

  public static final int SC_GONE = 410;

  public static final int SC_PRECONDITION_FAILED = 412;

  public static final int SC_INTERNAL_SERVER_ERROR = 500;

  private final int statusCode;

  private final InputStream responseStream;

  /**
   * Creates an HttpException with the given message, statusCode and
   * responseStream.
   */
  public HttpException(String message, int statusCode,
      InputStream responseStream) {
    super(message);
    this.statusCode = statusCode;
    this.responseStream = responseStream;
  }

  /**
   * Gets the status code associated with this exception.
   * @return the status code returned by the server, typically one of the SC_*
   * constants.
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * @return the error response stream from the server.
   */
  public InputStream getResponseStream() {
    return responseStream;
  }
}
