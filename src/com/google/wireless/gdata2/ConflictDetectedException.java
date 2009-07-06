// Copyright 2009 The Android Open Source Project.

package com.google.wireless.gdata2;

import com.google.wireless.gdata2.data.Entry;

/**
 * A ConflictDetectedException is thrown when the server detects a conflict
 * between the Entry which the client is trying to insert or modify and an
 * existing Entry.  Typically this is because the version of the Entry being
 * uploaded by the client is older than the version on the server, but it may
 * also indicate the violation of some other constraint (e.g., key uniqueness).
 */
public class ConflictDetectedException extends GDataException {

  private final Entry conflictingEntry;

  /**
   * Creates a new ConflictDetectedException with the given entry.
   * @param conflictingEntry the conflicting entry state returned by the server.
   */
  public ConflictDetectedException(Entry conflictingEntry) {
    this.conflictingEntry = conflictingEntry;
  }

  /**
   * @return the conflicting Entry returned by the server.
   */
  public Entry getConflictingEntry() {
    return conflictingEntry;
  }
}
