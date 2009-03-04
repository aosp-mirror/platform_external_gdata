// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.subscribedfeeds.client;

import com.google.wireless.gdata.client.GDataClient;
import com.google.wireless.gdata.client.GDataParserFactory;
import com.google.wireless.gdata.client.GDataServiceClient;

/**
 * GDataServiceClient for accessing Subscribed Feeds.  This client can access
 * subscribed feeds for specific users. The parser this class uses handles
 * the XML version of feeds.
 */
public class SubscribedFeedsClient extends GDataServiceClient {

    /** Service value for contacts. This is only used for downloads; uploads
     * are done using the service that corresponds to the subscribed feed. */
    public static final String SERVICE = "mail";

    /**
     * Create a new SubscribedFeedsClient.
     * @param client The GDataClient that should be used to authenticate
     * requests, retrieve feeds, etc.
     */
    public SubscribedFeedsClient(GDataClient client, GDataParserFactory factory) {
        super(client, factory);
    }

    /*
     * (non-Javadoc)
     * @see GDataServiceClient#getServiceName()
     */
    public String getServiceName() {
        return SERVICE;
    }
}
