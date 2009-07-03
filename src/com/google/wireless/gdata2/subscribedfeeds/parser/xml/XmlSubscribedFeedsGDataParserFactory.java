package com.google.wireless.gdata2.subscribedfeeds.parser.xml;

import com.google.wireless.gdata2.client.GDataParserFactory;
import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.parser.GDataParser;
import com.google.wireless.gdata2.parser.ParseException;
import com.google.wireless.gdata2.parser.xml.XmlParserFactory;
import com.google.wireless.gdata2.serializer.GDataSerializer;
import com.google.wireless.gdata2.serializer.xml.XmlBatchGDataSerializer;
import com.google.wireless.gdata2.subscribedfeeds.data.SubscribedFeedsEntry;
import com.google.wireless.gdata2.subscribedfeeds.serializer.xml.XmlSubscribedFeedsEntryGDataSerializer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.Enumeration;

/**
 * GDataParserFactory that creates XML GDataParsers and GDataSerializers for
 * Subscribed Feeds.
 */
public class XmlSubscribedFeedsGDataParserFactory implements
        GDataParserFactory {
    private final XmlParserFactory xmlFactory;

    public XmlSubscribedFeedsGDataParserFactory(XmlParserFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    /*
     * (non-javadoc)
     * 
     * @see GDataParserFactory#createParser
     */
    public GDataParser createParser(InputStream is) throws ParseException {
        XmlPullParser xmlParser;
        try {
            xmlParser = xmlFactory.createParser();
        } catch (XmlPullParserException xppe) {
            throw new ParseException("Could not create XmlPullParser", xppe);
        }
        return new XmlSubscribedFeedsGDataParser(is, xmlParser);
    }

    /*
     * (non-Javadoc)
     * 
     * @see GDataParserFactory#createMetaFeedParser(int, java.io.InputStream)
     */
    public GDataParser createParser(Class entryClass, InputStream is)
            throws ParseException {
        if (entryClass != SubscribedFeedsEntry.class) {
            throw new IllegalArgumentException(
                    "SubscribedFeeds supports only a single feed type");
        }
        // we don't have feed sub-types, so just return the default
        return createParser(is);
    }


    /**
     * Creates a new {@link GDataSerializer} for the provided entry. The entry
     * <strong>must</strong> be an instance of {@link SubscribedFeedsEntry}.
     * 
     * @param entry The {@link SubscribedFeedsEntry} that should be
     *        serialized.
     * @return The {@link GDataSerializer} that will serialize this entry.
     * @throws IllegalArgumentException Thrown if entry is not a
     *         {@link SubscribedFeedsEntry}.
     * @see com.google.wireless.gdata2.client.GDataParserFactory#createSerializer
     */
    public GDataSerializer createSerializer(Entry entry) {
        if (!(entry instanceof SubscribedFeedsEntry)) {
            throw new IllegalArgumentException(
                    "Expected SubscribedFeedsEntry!");
        }
        SubscribedFeedsEntry subscribedFeedsEntry =
                (SubscribedFeedsEntry) entry;
        return new XmlSubscribedFeedsEntryGDataSerializer(xmlFactory,
                subscribedFeedsEntry);
    }

    /**
     * Creates a new {@link GDataSerializer} for the given batch.
     *
     * @param batch the {@link Enumeration} of entries in the batch.
     * @return The {@link GDataSerializer} that will serialize this batch.
     */
    public GDataSerializer createSerializer(Enumeration batch) {
        return new XmlBatchGDataSerializer(this, xmlFactory, batch);
    }
}
