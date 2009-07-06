// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when a specified resource is gone
 */
public class ResourceGoneException extends GDataException {

  /**
   * Creates a new ResourceGoneException.
   */
  public ResourceGoneException() {
  }

  /**
   * Creates a new ResourceGoneException with a supplied message.
   * @param message The message for the exception.
   */
  public ResourceGoneException(String message) {
    super(message);
  }

  /**
   * Creates a new ResourceGoneException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public ResourceGoneException(String message, Throwable cause) {
    super(message, cause);
  }
}
