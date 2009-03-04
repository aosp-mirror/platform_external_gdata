package com.google.wireless.gdata.contacts.serializer.xml;

import com.google.wireless.gdata.contacts.data.GroupEntry;
import com.google.wireless.gdata.contacts.parser.xml.XmlContactsGDataParser;
import com.google.wireless.gdata.parser.xml.XmlParserFactory;
import com.google.wireless.gdata.parser.ParseException;
import com.google.wireless.gdata.serializer.xml.XmlEntryGDataSerializer;
import com.google.wireless.gdata.data.StringUtils;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 *  Serializes Google Contact Group entries into the Atom XML format.
 */
public class XmlGroupEntryGDataSerializer extends XmlEntryGDataSerializer {

  public XmlGroupEntryGDataSerializer(XmlParserFactory factory, GroupEntry entry) {
    super(factory, entry);
  }

  protected GroupEntry getGroupEntry() {
    return (GroupEntry) getEntry();
  }

  @Override
  protected void declareExtraEntryNamespaces(XmlSerializer serializer) throws IOException {
    super.declareExtraEntryNamespaces(serializer);
    serializer.setPrefix(XmlContactsGDataParser.NAMESPACE_CONTACTS,
        XmlContactsGDataParser.NAMESPACE_CONTACTS_URI);
  }

  /* (non-Javadoc)
  * @see XmlEntryGDataSerializer#serializeExtraEntryContents
  */
  protected void serializeExtraEntryContents(XmlSerializer serializer, int format)
      throws ParseException, IOException {
    GroupEntry entry = getGroupEntry();
    entry.validate();

    serializeSystemGroup(entry, serializer);
  }

  private void serializeSystemGroup(GroupEntry entry, XmlSerializer serializer) throws IOException {
    final String systemGroup = entry.getSystemGroup();
    if (!StringUtils.isEmpty(systemGroup)) {
      serializer.startTag(null /* ns */, "systemGroup");
      serializer.attribute(null /* ns */, "id", systemGroup);
      serializer.endTag(null /* ns */, "systemGroup");
    }
  }
}
