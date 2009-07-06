// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when a retrieval fails because the specified ETag matches
 * the current ETag on the entry (which implies that the entry has not changed
 * on the server since it was last retrieved).
 */
public class ResourceNotModifiedException extends GDataException {

  /**
   * Creates a new ResourceNotModifiedException.
   */
  public ResourceNotModifiedException() {
  }

  /**
   * Creates a new ResourceNotModifiedException with a supplied message.
   * @param message The message for the exception.
   */
  public ResourceNotModifiedException(String message) {
    super(message);
  }

  /**
   * Creates a new ResourceNotModifiedException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public ResourceNotModifiedException(String message, Throwable cause) {
    super(message, cause);
  }
}
