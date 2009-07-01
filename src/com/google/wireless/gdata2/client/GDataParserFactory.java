// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.client;

import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.serializer.GDataSerializer;

import java.io.InputStream;
import java.util.Enumeration;

/**
 * Factory that creates {@link GDataParser}s and {@link GDataSerializer}s.
 */
public interface GDataParserFactory {
  /**
   * Creates a new {@link GDataParser} for the provided InputStream.
   *
   * @param entryClass Specify the class of Entry objects that are to be parsed. This
   *   lets createParser know which parser to create. 
   * @param is The InputStream that should be parsed. @return The GDataParser that will parse is.
   * @throws ParseException Thrown if the GDataParser could not be created.
   * @throws IllegalArgumentException if the feed type is unknown.
   */
  GDataParser createParser(Class entryClass, InputStream is)
      throws ParseException;

  /**
   * Creates a new {@link GDataParser} for the provided InputStream, using the
   * default feed type for the client.
   *
   * @param is The InputStream that should be parsed.
   * @return The GDataParser that will parse is.
   * @throws ParseException Thrown if the GDataParser could not be created.
   *         Note that this can occur if the feed in the InputStream is not of
   *         the default type assumed by this method.
   * @see #createParser(Class,InputStream)
   */
  GDataParser createParser(InputStream is) throws ParseException;

  /**
   * Creates a new {@link GDataSerializer} for the provided Entry.
   *
   * @param entry The Entry that should be serialized.
   * @return The GDataSerializer that will serialize entry.
   */
  GDataSerializer createSerializer(Entry entry);

  /**
   * Creates a new {@link GDataSerializer} for the provided batch of entries.
   *
   * @param batch An enumeration of entries comprising the batch.
   * @return The GDataSerializer that will serialize the batch.
   */
  GDataSerializer createSerializer(Enumeration batch);
}
