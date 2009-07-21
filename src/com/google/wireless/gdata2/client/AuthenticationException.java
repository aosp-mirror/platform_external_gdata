// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when a user's credentials could not be authenticated.
 */
public class AuthenticationException extends GDataException {

  /**
   * Creates a new AuthenticationException.
   */
  public AuthenticationException() {
  }

  /**
   * Creates a new AuthenticationException with a supplied message.
   * @param message The message for the exception.
   */
  public AuthenticationException(String message) {
    super(message);
  }

  /**
   * Creates a new AuthenticationException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
