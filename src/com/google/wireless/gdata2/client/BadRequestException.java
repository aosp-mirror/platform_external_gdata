// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when the server returns a 400 Bad Request.
 */
public class BadRequestException extends GDataException {

  /**
   * Creates a new AuthenticationException.
   */
  public BadRequestException() {
  }

  /**
   * Creates a new BadRequestException with a supplied message.
   * @param message The message for the exception.
   */
  public BadRequestException(String message) {
    super(message);
  }

  /**
   * Creates a new BadRequestException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}