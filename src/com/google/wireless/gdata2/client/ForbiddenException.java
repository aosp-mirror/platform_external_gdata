// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when the server returns a 403 Forbidden.
 */
public class ForbiddenException extends GDataException {

  /**
   * Creates a new AuthenticationException.
   */
  public ForbiddenException() {
  }

  /**
   * Creates a new ForbiddenException with a supplied message.
   * @param message The message for the exception.
   */
  public ForbiddenException(String message) {
    super(message);
  }

  /**
   * Creates a new ForbiddenException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public ForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }
}