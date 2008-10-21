// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.data;

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
     * whitespace characters, as defined by {@link Character#isWhitespace(char)}.
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
            if (!Character.isWhitespace(string.charAt(i))) {
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
}
