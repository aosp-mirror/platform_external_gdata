// Copyright 2009 Google Inc. All Rights Reserved.

package com.google.wireless.gdata2.data.batch;

/**
 * Holds result status for an individual batch operation.
 */
public class BatchStatus {
  private int statusCode;
  private String reason;
  private String contentType;
  private String content;

  /**
   * Creates a new empty BatchStatus.
   */
  public BatchStatus() {
  }

  /**
   * Returns the status of this operation.
   */
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * Sets the status of this operation.
   */
  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * Returns the reason for this status.
   */
  public String getReason() {
    return reason;
  }

  /**
   * Sets the reason for this status.
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * Returns the content type of the response.
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * Sets the content type of the response.
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * Returns the response content, if any.
   */
  public String getContent() {
    return content;
  }

  /**
   * Sets the response content.
   */
  public void setContent(String content) {
    this.content = content;
  }
}
