// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.GDataException;

/**
 * Exception thrown when a specified resource does not exist
 */
public class ResourceNotFoundException extends GDataException {

  /**
   * Creates a new ResourceNotFoundException.
   */
  public ResourceNotFoundException() {
  }

  /**
   * Creates a new ResourceNotFoundException with a supplied message.
   * @param message The message for the exception.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates a new ResourceNotFoundException with a supplied message and
   * underlying cause.
   *
   * @param message The message for the exception.
   * @param cause Another throwable that was caught and wrapped in this
   * exception.
   */
  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
