// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.data;

/**
 * Utility class for working with and manipulating Strings.
 */
public final class StringUtils {
    // utility class
    private StringUtils() {
    }
    
    /**
     * Returns whether or not the String is empty.  A String is considered to
     * be empty if it is null or if it has a length of 0.
     * @param string The String that should be examined.
     * @return Whether or not the String is empty.
     */
    public static boolean isEmpty(String string) {
        return ((string == null) || (string.length() == 0));
    }

    /**
     * Returns {@code true} if the given string is null, empty, or comprises only
     * whitespace characters, as defined by {@link isWhitespace(char)}.
     *
     * @param string The String that should be examined.
     * @return {@code true} if {@code string} is null, empty, or consists of
     *     whitespace characters only
     */
    public static boolean isEmptyOrWhitespace(String string) {
        if (string == null) {
            return true;
        }
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int parseInt(String string, int defaultValue) {
        if (string != null) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException nfe) {
                // ignore
            }
        }
        return defaultValue;
    }

    public static boolean isWhitespace(char c) {
    // Reference: http://en.wikipedia.org/wiki/Whitespace_%28computer_science%29
        return ('\u0009' <= c && c <= '\r') || c == '\u0020' || c == '\u0085'
            || c == '\u00A0' || c == '\u1680' || c == '\u180E'
            || ('\u2000' <= c && c <= '\u200A') || c == '\u2028' || c == '\u2029'
            || c == '\u202F' || c == '\u205F' || c == '\u3000';
    }

}
