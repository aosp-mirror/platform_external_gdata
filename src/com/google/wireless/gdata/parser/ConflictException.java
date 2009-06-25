// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.parser;

import com.google.wireless.gdata.GDataException;

/**
 * Exception thrown if a GData entry cannot be modified due to a version conflict.
 */
public class ConflictException extends GDataException {

    /**
     * Creates a new empty ConflictException.
     */
    public ConflictException() {
        super();
    }

    /**
     * Creates a new ConflictException with the supplied message.
     * @param message The message for this ConflictException.
     */
    public ConflictException(String message) {
        super(message);
    }

    /**
     * Creates a new ConflictException with the supplied message and underlying
     * cause.
     *
     * @param message The message for this ConflictException.
     * @param cause The underlying cause that was caught and wrapped by this
     * ConflictException.
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}