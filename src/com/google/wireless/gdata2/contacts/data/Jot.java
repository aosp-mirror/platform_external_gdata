/**
 * Copyright (C) 2009 The Android Open Source Project
 */
package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.parser.ParseException;


/**
 * Storage for arbitrary pieces of information about the 
 * contact. Each jot has a type specified by the rel attribute 
 * and a text value. The element can be repeated. 
 */
public class Jot extends TypedElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_KEYWORDS = 3;
  public static final byte TYPE_USER = 4;
  public static final byte TYPE_OTHER = 5;

  /**
   * default empty constructor
   */
  public Jot() {}
}
