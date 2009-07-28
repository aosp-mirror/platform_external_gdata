/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.wireless.gdata2.parser.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * This is an abstraction of a pull parser that provides several benefits:<ul>
 *   <li>it is easier to use robustly because it makes it trivial to handle unexpected tags (which
 *   might have children)</li>
 *   <li>it makes the handling of text (cdata) blocks more convenient</li>
 *   <li>it provides convenient methods for getting a mandatory attribute (and throwing an exception
 *   if it is missing) or an optional attribute (and using a default value if it is missing)
 * </ul>
 */
public class SimplePullParser {
  public static final String TEXT_TAG = "![CDATA[";

  private final XmlPullParser mParser;
  private String mCurrentStartTag;

  /**
   * Constructs a new SimplePullParser to parse the xml
   * @param parser the underlying parser to use
   */
  public SimplePullParser(XmlPullParser parser) {
    mParser = parser;
    mCurrentStartTag = null;
  }

  /**
   * Returns the tag of the next element whose depth is parentDepth plus one
   * or null if there are no more such elements before the next start tag. When this returns,
   * getDepth() and all methods relating to attributes will refer to the element whose tag is
   * returned.
   *
   * @param parentDepth the depth of the parrent of the item to be returned
   * @param textBuffer if null then text blocks will be ignored. If
   *   non-null then text blocks will be added to the builder and TEXT_TAG
   *   will be returned when one is found
   * @return the next of the next child element's tag, TEXT_TAG if a text block is found, or null
   *   if there are no more child elements or DATA blocks
   * @throws IOException propogated from the underlying parser
   * @throws ParseException if there was an error parsing the xml.
   */
  public String nextTagOrText(int parentDepth, StringBuffer textBuffer)
      throws IOException, ParseException {
    while (true) {
      int eventType = 0;
      try {
        eventType = mParser.next();
      } catch (XmlPullParserException e) {
        throw new ParseException(e);
      }
      int depth = mParser.getDepth();
      mCurrentStartTag = null;

      if (eventType == XmlPullParser.START_TAG && depth == parentDepth + 1) {
        mCurrentStartTag = mParser.getName();
          // TODO: this is an example of how to do logging of the XML
//                if (mLogTag != null && Log.isLoggable(mLogTag, Log.DEBUG)) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < depth; i++) sb.append("  ");
//                    sb.append("<").append(mParser.getName());
//                    int count = mParser.getAttributeCount();
//                    for (int i = 0; i < count; i++) {
//                        sb.append(" ");
//                        sb.append(mParser.getAttributeName(i));
//                        sb.append("=\"");
//                        sb.append(mParser.getAttributeValue(i));
//                        sb.append("\"");
//                    }
//                    sb.append(">");
//                    Log.d(mLogTag, sb.toString());
//                }
        return mParser.getName();
      }

      if (eventType == XmlPullParser.END_TAG && depth == parentDepth) {
          // TODO: this is an example of how to do logging of the XML
//                if (mLogTag != null && Log.isLoggable(mLogTag, Log.DEBUG)) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < depth; i++) sb.append("  ");
//                    sb.append("</>"); // Not quite valid xml but it gets the job done.
//                    Log.d(mLogTag, sb.toString());
//                }
        return null;
      }

      if (eventType == XmlPullParser.END_DOCUMENT && parentDepth == 0) {
        return null;
      }

      if (eventType == XmlPullParser.TEXT && depth == parentDepth) {
        if (textBuffer == null) {
          continue;
        }
        String text = mParser.getText();
        textBuffer.append(text);
        return TEXT_TAG;
      }
    }
  }

  /**
   * The same as nextTagOrText(int, StringBuilder) but ignores text blocks.
   */
  public String nextTag(int parentDepth) throws IOException, ParseException {
    return nextTagOrText(parentDepth, null /* ignore text */);
  }

  /**
   * Returns the depth of the current element. The depth is 0 before the first
   * element has been returned, 1 after that, etc.
   *
   * @return the depth of the current element
   */
  public int getDepth() {
    return mParser.getDepth();
  }

  /**
   * Consumes the rest of the children, accumulating any text at this level into the builder.
   *
   * @param textBuffer
   * @throws IOException propogated from the XmlPullParser
   * @throws ParseException if there was an error parsing the xml.
   */
  public void readRemainingText(int parentDepth, StringBuffer textBuffer)
      throws IOException, ParseException {
    while (nextTagOrText(parentDepth, textBuffer) != null) {
    }
  }

  /**
   * Returns the number of attributes on the current element.
   *
   * @return the number of attributes on the current element
   */
  public int numAttributes() {
    return mParser.getAttributeCount();
  }

  /**
   * Returns the name of the nth attribute on the current element.
   *
   * @return the name of the nth attribute on the current element
   */
  public String getAttributeName(int i) {
    return mParser.getAttributeName(i);
  }

  /**
   * Returns the namespace of the nth attribute on the current element.
   *
   * @return the namespace of the nth attribute on the current element
   */
  public String getAttributeNamespace(int i) {
    return mParser.getAttributeNamespace(i);
  }

  /**
   * Returns the string value of the named attribute.
   *
   * @param namespace the namespace of the attribute
   * @param name the name of the attribute
   * @param defaultValue the value to return if the attribute is not specified
   * @return the value of the attribute
   */
  public String getStringAttribute(
      String namespace, String name, String defaultValue) {
    String value = mParser.getAttributeValue(namespace, name);
    if (null == value) return defaultValue;
    return value;
  }

  /**
   * Returns the string value of the named attribute. An exception will
   * be thrown if the attribute is not present.
   *
   * @param namespace the namespace of the attribute
   * @param name the name of the attribute @return the value of the attribute
   * @throws ParseException thrown if the attribute is missing
   */
  public String getStringAttribute(String namespace, String name) throws ParseException {
    String value = mParser.getAttributeValue(namespace, name);
    if (null == value) {
      throw new ParseException(
          "missing '" + name + "' attribute on '" + mCurrentStartTag + "' element");
    }
    return value;
  }

  /**
   * Returns the string value of the named attribute. An exception will
   * be thrown if the attribute is not a valid integer.
   *
   * @param namespace the namespace of the attribute
   * @param name the name of the attribute
   * @param defaultValue the value to return if the attribute is not specified
   * @return the value of the attribute
   * @throws ParseException thrown if the attribute not a valid integer.
   */
  public int getIntAttribute(String namespace, String name, int defaultValue)
      throws ParseException {
    String value = mParser.getAttributeValue(namespace, name);
    if (null == value) return defaultValue;
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new ParseException("Cannot parse '" + value + "' as an integer");
    }
  }

  /**
   * Returns the string value of the named attribute. An exception will
   * be thrown if the attribute is not present or is not a valid integer.
   *
   * @param namespace the namespace of the attribute
   * @param name the name of the attribute @return the value of the attribute
   * @throws ParseException thrown if the attribute is missing or not a valid integer.
   */
  public int getIntAttribute(String namespace, String name)
      throws ParseException {
    String value = getStringAttribute(namespace, name);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new ParseException("Cannot parse '" + value + "' as an integer");
    }
  }

  /**
   * Returns the string value of the named attribute. An exception will
   * be thrown if the attribute is not a valid long.
   *
   * @param namespace the namespace of the attribute
   * @param name the name of the attribute @return the value of the attribute
   * @throws ParseException thrown if the attribute is not a valid long.
   */
  public long getLongAttribute(String namespace, String name, long defaultValue)
      throws ParseException {
    String value = mParser.getAttributeValue(namespace, name);
    if (null == value) return defaultValue;
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new ParseException("Cannot parse '" + value + "' as a long");
    }
  }

  /**
   * Returns the string value of the named attribute. An exception will
   * be thrown if the attribute is not present or is not a valid long.
   *
   * @param namespace the namespace of the attribute
   * @param name the name of the attribute @return the value of the attribute
   * @throws ParseException thrown if the attribute is missing or not a valid long.
   */
  public long getLongAttribute(String namespace, String name)
      throws ParseException {
    String value = getStringAttribute(namespace, name);
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new ParseException("Cannot parse '" + value + "' as a long");
    }
  }

  public static final class ParseException extends Exception {
    public ParseException(String message) {
      super(message);
    }

    public ParseException(String message, Throwable cause) {
      // constructor Exception(String, Throwable) is not supported in J2ME
      super(message + ", Cause: " + String.valueOf(cause));
    }

    public ParseException(Throwable cause) {
      // constructor Exception(Throwable) is not supported in J2ME
      super("Cause: " + String.valueOf(cause));
    }
  }
}
