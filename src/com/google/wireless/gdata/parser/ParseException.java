// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.parser;

import com.google.wireless.gdata.GDataException;

/**
 * Exception thrown if a GData feed cannot be parsed.
 */
public class ParseException extends GDataException {
    
    /**
     * Creates a new empty ParseException.
     */
    public ParseException() {
        super();
    }
    
    /**
     * Creates a new ParseException with the supplied message.
     * @param message The message for this ParseException.
     */
    public ParseException(String message) {
        super(message);
    }
    
    /**
     * Creates a new ParseException with the supplied message and underlying
     * cause.
     * 
     * @param message The message for this ParseException.
     * @param cause The underlying cause that was caught and wrapped by this
     * ParseException.
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
