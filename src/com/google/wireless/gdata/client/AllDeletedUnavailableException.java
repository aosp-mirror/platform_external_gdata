package com.google.wireless.gdata.client;

import com.google.wireless.gdata.GDataException;

/**
 * Exception thrown when the tombstones for a feed have expired. When the
 * client gets this it should refetch the entire feed.
 */
public class AllDeletedUnavailableException extends GDataException {

    /**
     * Creates a new AuthenticationException.
     */
    public AllDeletedUnavailableException() {
    }

    /**
     * Creates a new AllDeletedUnavailableException with a supplied message.
     * @param message The message for the exception.
     */
    public AllDeletedUnavailableException(String message) {
        super(message);
    }

    /**
     * Creates a new AllDeletedUnavailableException with a supplied message and
     * underlying cause.
     *
     * @param message The message for the exception.
     * @param cause Another throwable that was caught and wrapped in this
     * exception.
     */
    public AllDeletedUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
