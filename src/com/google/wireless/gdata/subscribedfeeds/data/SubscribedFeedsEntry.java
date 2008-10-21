// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.subscribedfeeds.data;

import com.google.wireless.gdata.data.Entry;

/**
 * Entry containing information about a contact.
 */
public class SubscribedFeedsEntry extends Entry {
    private FeedUrl feedUrl;
    private String routingInfo;
    private String clientToken;

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public SubscribedFeedsEntry() {
        super();
    }

    public FeedUrl getSubscribedFeed() {
        return feedUrl;
    }

    public void setSubscribedFeed(FeedUrl feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getRoutingInfo() {
        return routingInfo;
    }

    public void setRoutingInfo(String routingInfo) {
        this.routingInfo = routingInfo;
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.data.Entry#clear()
     */
    public void clear() {
        super.clear();
    }

    public void toString(StringBuffer sb) {
        super.toString(sb);
    }
}
