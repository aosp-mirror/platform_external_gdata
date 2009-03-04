// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.subscribedfeeds.parser.xml;

import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.Feed;
import com.google.wireless.gdata.data.XmlUtils;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.parser.xml.XmlGDataParser;
import com.google.wireless.gdata.subscribedfeeds.data.FeedUrl;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsEntry;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsFeed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * GDataParser for a subscribed feeds feed.
 */
public class XmlSubscribedFeedsGDataParser extends XmlGDataParser {
    /**
     * Creates a new XmlSubscribedFeedsGDataParser.
     * @param is The InputStream that should be parsed.
     * @throws ParseException Thrown if a parser cannot be created.
     */
    public XmlSubscribedFeedsGDataParser(InputStream is, XmlPullParser parser)
            throws ParseException {
        super(is, parser);
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createFeed()
     */
    protected Feed createFeed() {
        return new SubscribedFeedsFeed();
    }

    /*
     * (non-Javadoc)
     * @see com.google.wireless.gdata.parser.xml.XmlGDataParser#createEntry()
     */
    protected Entry createEntry() {
        return new SubscribedFeedsEntry();
    }

  protected void handleExtraElementInEntry(Entry entry)
      throws XmlPullParserException, IOException {
        XmlPullParser parser = getParser();

        if (!(entry instanceof SubscribedFeedsEntry)) {
          throw new IllegalArgumentException("Expected SubscribedFeedsEntry!");
        }
        SubscribedFeedsEntry subscribedFeedsEntry =
                (SubscribedFeedsEntry) entry;
        String name = parser.getName();
        if ("feedurl".equals(name)) {
          FeedUrl feedUrl = new FeedUrl();
          feedUrl.setFeed(parser.getAttributeValue(null  /* ns */, "value"));
          feedUrl.setService(parser.getAttributeValue(null  /* ns */, "service"));
          feedUrl.setAuthToken(parser.getAttributeValue(null  /* ns */, "authtoken"));
          subscribedFeedsEntry.setSubscribedFeed(feedUrl);
        }
        if ("routingInfo".equals(name)) {
            subscribedFeedsEntry.setRoutingInfo(
                    XmlUtils.extractChildText(parser));
        }
        if ("clientToken".equals(name)) {
            subscribedFeedsEntry.setClientToken(
                    XmlUtils.extractChildText(parser));
        }
    }
}
