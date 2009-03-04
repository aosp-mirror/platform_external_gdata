// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.client;

import com.google.wireless.gdata.parser.GDataParser;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.serializer.GDataSerializer;

import java.io.InputStream;

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
}
