// Copyright 2009 Google Inc. All Rights Reserved.

package com.google.wireless.gdata2.data.batch;

/**
 * Opaque container for batch related info associated with an entry.
 * Clients should use {@link BatchUtils} to access this data instead.
 */
public class BatchInfo {
  String id;
  String operation;
  BatchStatus status;
  BatchInterrupted interrupted;

  /* package */ BatchInfo() {
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("id: ").append(id);
    sb.append(" op: ").append(operation);
    if (status != null) {
      sb.append(" sc: ").append(status.getStatusCode());
    }
    if (interrupted != null) {
      sb.append(" interrupted: ").append(interrupted.getReason());
    }
    return sb.toString();
  }
}
