// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata;

/**
 * The base exception for GData operations.
 */
public class GDataException extends Exception {

    private final Throwable cause;

    /**
     * Creates a new empty GDataException.
     */
    public GDataException() {
        super();
        cause = null;
    }

    /**
     * Creates a new GDataException with the supplied message.
     * @param message The message for this GDataException.
     */
    public GDataException(String message) {
        super(message);
        cause = null;
    }

    /**
     * Creates a new GDataException with the supplied message and underlying
     * cause.
     *
     * @param message The message for this GDataException.
     * @param cause The underlying cause that was caught and wrapped by this
     * GDataException.
     */
    public GDataException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * Creates a new GDataException with the underlying cause.
     *
     * @param cause The underlying cause that was caught and wrapped by this
     * GDataException.
     */
    public GDataException(Throwable cause) {
        this("", cause);
    }

    /**
     * @return the cause of this GDataException or null if the cause is unknown.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * @return a string representation of this exception.
     */
    public String toString() {
        return super.toString() + (cause != null ? " " + cause.toString() : "");
    }
}
