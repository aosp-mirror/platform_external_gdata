// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

/**
 * The ImAddress gdata type
 */
public class ImAddress extends ContactsElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_OTHER = 3;

  public static final byte PROTOCOL_CUSTOM = 1;
  public static final byte PROTOCOL_AIM = 2;
  public static final byte PROTOCOL_MSN = 3;
  public static final byte PROTOCOL_YAHOO = 4;
  public static final byte PROTOCOL_SKYPE = 5;
  public static final byte PROTOCOL_QQ = 6;
  public static final byte PROTOCOL_GOOGLE_TALK = 7;
  public static final byte PROTOCOL_ICQ = 8;
  public static final byte PROTOCOL_JABBER = 9;
  public static final byte PROTOCOL_NETMEETING = 10;
  public static final byte PROTOCOL_NONE = 11;

  private byte protocolPredefined;
  private String protocolCustom;
  private String address;

  /**
   * default empty constructor
   */
  public ImAddress() {}
  public ImAddress(String address, byte protocolPredefined, String protocolCustom,
          byte type, String label, boolean isPrimary) {
      super(type, label, isPrimary);
      this.address = address;
      this.protocolPredefined = protocolPredefined;
      this.protocolCustom = protocolCustom;
  }

  public byte getProtocolPredefined() {
    return protocolPredefined;
  }

  public void setProtocolPredefined(byte protocolPredefined) {
    this.protocolPredefined = protocolPredefined;
  }

  public String getProtocolCustom() {
    return protocolCustom;
  }

  public void setProtocolCustom(String protocolCustom) {
    this.protocolCustom = protocolCustom;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void toString(StringBuffer sb) {
    sb.append("ImAddress");
    super.toString(sb);
    sb.append(" protocolPredefined:").append(protocolPredefined);
    if (protocolCustom != null) sb.append(" protocolCustom:").append(protocolCustom);
    if (address != null) sb.append(" address:").append(address);
  }
}
