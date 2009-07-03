// Copyright 2009 Google Inc. All Rights Reserved.

package com.google.wireless.gdata2.data.batch;

/**
 * Holds status information about a batch that was interrupted.
 */
public class BatchInterrupted {
  private String reason;
  private int total;
  private int success;
  private int error;

  /**
   * Creates a new empty BatchInterrupted.
   */
  public BatchInterrupted() {
  }

  /**
   * Returns the reason for this failure.
   */
  public String getReason() {
    return reason;
  }

  /**
   * Sets the reason for this failure.
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * Gets the total number of entries read.
   */
  public int getTotalCount() {
    return total;
  }

  /**
   * Sets the number of entries read.
   */
  public void setTotalCount(int total) {
    this.total = total;
  }

  /**
   * Gets the number of entries that were processed successfully.
   */
  public int getSuccessCount() {
    return success;
  }

  /**
   * Sets the number of entries successfuly processed.
   */
  public void setSuccessCount(int success) {
    this.success = success;
  }

  /**
   * Gets the number of entries that were rejected.
   */
  public int getErrorCount() {
    return error;
  }

  /**
   * Sets the number of entries that failed.
   */
  public void setErrorCount(int error) {
    this.error = error;
  }
}
