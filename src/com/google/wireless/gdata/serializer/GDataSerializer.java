// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.serializer;

import com.google.wireless.gdata.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for serializing GData entries.
 */
public interface GDataSerializer {

    // TODO: I hope the three formats does not bite us.  Each serializer has
    // to pay attention to what "mode" it is in when serializing.
    
    /**
     * Serialize all data in the entry.  Used for debugging.
     */
    public static final int FORMAT_FULL = 0;
    
    /**
     * Serialize only the data necessary for creating a new entry.
     */
    public static final int FORMAT_CREATE = 1;
    
    /**
     * Serialize only the data necessary for updating an existing entry.
     */
    public static final int FORMAT_UPDATE = 2;

    /**
     * Returns the Content-Type for this serialization format.
     * @return The Content-Type for this serialization format.
     */
    String getContentType();
    
    /**
     * Serializes a GData entry to the provided {@link OutputStream}, using the
     * specified serialization format.
     * 
     * @see #FORMAT_FULL
     * @see #FORMAT_CREATE
     * @see #FORMAT_UPDATE
     * 
     * @param out The {@link OutputStream} to which the entry should be 
     * serialized.
     * @param format The format of the serialized output.
     * @throws IOException Thrown if there is an issue writing the serialized 
     * entry to the provided {@link OutputStream}.
     * @throws ParseException Thrown if the entry cannot be serialized.
     */
    void serialize(OutputStream out, int format)
        throws IOException, ParseException;
}
