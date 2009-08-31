// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.serializer;

import com.google.wireless.gdata2.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Interface for serializing GData entries. A serializer has to be aware 
 * what mode it is in while it is serializing.
 */
public interface GDataSerializer {

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
     * Serialize the entry as part of a batch of operations.
     */
    public static final int FORMAT_BATCH = 3;

    /**
     * Returns the Content-Type for this serialization format.
     * @return The Content-Type for this serialization format.
     */
    String getContentType();

    /**
     * Returns if this serializer supports a partial representation 
     * of the underlying content 
     * 
     * @return boolean True if a partial representation will be 
     *         created
     */
    boolean getSupportsPartial();
    
    /**
     * Serializes a GData entry to the provided {@link OutputStream}, using the
     * specified serialization format.
     * 
     * @see #FORMAT_FULL
     * @see #FORMAT_CREATE
     * @see #FORMAT_UPDATE
     * @see #FORMAT_BATCH 
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
