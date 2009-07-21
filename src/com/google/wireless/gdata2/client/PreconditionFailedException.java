// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when an update fails because the specified ETag doesn't
 * match the current ETag on the entry (which implies that the entry has changed
 * on the server since it was last retrieved).
 */
public class PreconditionFailedException extends GDataException {

  /**
   * Creates a new PreconditionFailedException.
   */
  public PreconditionFailedException() {
  }

  /**
   * Creates a new PreconditionFailedException with a supplied message.
   * @param message The message for the exception.
   */
  public PreconditionFailedException(String message) {
    super(message);
  }

  /**
   * Creates a new PreconditionFailedException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public PreconditionFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
