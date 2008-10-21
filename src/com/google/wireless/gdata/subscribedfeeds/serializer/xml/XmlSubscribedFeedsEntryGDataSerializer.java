package com.google.wireless.gdata.subscribedfeeds.serializer.xml;

import com.google.wireless.gdata.data.StringUtils;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import com.google.wireless.gdata.subscribedfeeds.data.FeedUrl;
import com.google.wireless.gdata.subscribedfeeds.data.SubscribedFeedsEntry;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 *  Serializes the SubscribedFeedEntry into the Atom XML format.
 */
public class XmlSubscribedFeedsEntryGDataSerializer extends
        XmlEntryGDataSerializer {
    public static final String NAMESPACE_GSYNC = "gsync";
    public static final String NAMESPACE_GSYNC_URI =
        "http://schemas.google.com/gsync/data";

    public XmlSubscribedFeedsEntryGDataSerializer(XmlParserFactory factory,
                                                  SubscribedFeedsEntry entry) {
        super(factory, entry);
    }

    protected SubscribedFeedsEntry getSubscribedFeedsEntry() {
        return (SubscribedFeedsEntry) getEntry();
    }

    protected void declareExtraEntryNamespaces(XmlSerializer serializer)
        throws IOException {
        serializer.setPrefix(NAMESPACE_GSYNC, NAMESPACE_GSYNC_URI);
    }

    /* (non-Javadoc)
     * @see XmlEntryGDataSerializer#serializeExtraEntryContents
     */
    protected void serializeExtraEntryContents(XmlSerializer serializer,
                                               int format)
        throws IOException {
        SubscribedFeedsEntry entry = getSubscribedFeedsEntry();

        serializeFeedUrl(serializer,  entry.getSubscribedFeed());
        serializeClientToken(serializer, entry.getClientToken());
        serializeRoutingInfo(serializer, entry.getRoutingInfo());
    }

    private static void serializeFeedUrl(XmlSerializer serializer,
            FeedUrl feedUrl)
            throws IOException {
        serializer.startTag(NAMESPACE_GSYNC_URI, "feedurl");
        serializer.attribute(null /* ns */, "value", feedUrl.getFeed());
        serializer.attribute(null /* ns */, "service", feedUrl.getService());
        serializer.attribute(null /* ns */, "authtoken", feedUrl.getAuthToken());
        serializer.endTag(NAMESPACE_GSYNC_URI, "feedurl");
    }

    private static void serializeClientToken(XmlSerializer serializer,
            String clientToken)
            throws IOException {
        if (StringUtils.isEmpty(clientToken)) {
            clientToken = "";
        }
        serializer.startTag(NAMESPACE_GSYNC_URI, "clientToken");
        serializer.text(clientToken);
        serializer.endTag(NAMESPACE_GSYNC_URI, "clientToken");
    }

    private static void serializeRoutingInfo(XmlSerializer serializer,
            String routingInfo)
            throws IOException {
        if (StringUtils.isEmpty(routingInfo)) {
            routingInfo = "";
        }
        serializer.startTag(NAMESPACE_GSYNC_URI, "routingInfo");
        serializer.text(routingInfo);
        serializer.endTag(NAMESPACE_GSYNC_URI, "routingInfo");
    }
}
