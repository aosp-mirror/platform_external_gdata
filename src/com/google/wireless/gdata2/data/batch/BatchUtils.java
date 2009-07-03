// Copyright 2009 Google Inc. All Rights Reserved.

package com.google.wireless.gdata2.data.batch;

import com.google.wireless.gdata2.data.Entry;

/**
 * Utility methods for dealing with batch operations.
 */
public class BatchUtils {

  public static final String OPERATION_INSERT = "insert";

  public static final String OPERATION_UPDATE = "update";

  public static final String OPERATION_QUERY = "query";

  public static final String OPERATION_DELETE = "delete";

  private BatchUtils() {
  }

  /**
   * Returns the batch id of the given entry, or null if none.
   */
  public static String getBatchId(Entry entry) {
    BatchInfo info = entry.getBatchInfo();
    return info == null ? null : info.id;
  }

  /**
   * Sets the batch id of the given entry.
   */
  public static void setBatchId(Entry entry, String id) {
    getOrCreateBatchInfo(entry).id = id;
  }

  /**
   * Returns the batch operation of the given entry.
   */
  public static String getBatchOperation(Entry entry) {
    BatchInfo info = entry.getBatchInfo();
    return info == null ? null : info.operation;
  }

  /**
   * Sets the operation for the given batch entry.
   */
  public static void setBatchOperation(Entry entry, String operation) {
    getOrCreateBatchInfo(entry).operation = operation;
  }

  /**
   * Returns the status of the given batch entry.
   */
  public static BatchStatus getBatchStatus(Entry entry) {
    BatchInfo info = entry.getBatchInfo();
    return info == null ? null : info.status;
  }

  /**
   * Sets the status of the given batch entry.
   */
  public static void setBatchStatus(Entry entry, BatchStatus status) {
    getOrCreateBatchInfo(entry).status = status;
  }

  /**
   * Returns the interrupted status of the given entry, or null if none.
   */
  public static BatchInterrupted getBatchInterrupted(Entry entry) {
    BatchInfo info = entry.getBatchInfo();
    return info == null ? null : info.interrupted;
  }

  /**
   * Sets the interrupted status of the given entry.
   */
  public static void setBatchInterrupted(Entry entry,
      BatchInterrupted interrupted) {
    getOrCreateBatchInfo(entry).interrupted = interrupted;
  }

  private static BatchInfo getOrCreateBatchInfo(Entry entry) {
    BatchInfo info = entry.getBatchInfo();
    if (info == null) {
      info = new BatchInfo();
      entry.setBatchInfo(info);
    }
    return info;
  }

}
