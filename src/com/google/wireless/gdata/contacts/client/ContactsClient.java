// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.contacts.client;

import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;

/**
 * GDataServiceClient for accessing Google Contacts.  This client can access and
 * parse the contacts feeds for specific user. The parser this class uses handle
 * the XML version of feeds.
 */
public class ContactsClient extends GDataServiceClient {
    /** Service value for contacts. */
  public static final String SERVICE = "cp";

  /**
   * Create a new ContactsClient.
   * @param client The GDataClient that should be used to authenticate
   * if we are using the caribou feed
   */
  public ContactsClient(GDataClient client, GDataParserFactory factory) {
    super(client, factory);
  }

  /* (non-Javadoc)
  * @see GDataServiceClient#getServiceName
  */
  public String getServiceName() {
    return SERVICE;
  }
}
