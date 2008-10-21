package com.google.wireless.gdata.calendar.data;

import com.google.wireless.gdata.data.StringUtils;

/**
 * Contains information about a event attendee.
 */
public class Who {

    /**
     * No attendee relationhip set.  Used in {@link #setRelationship}
     * and {@link #getRelationship}.
     */
    public static final byte RELATIONSHIP_NONE = 0;

    /**
     * A general meeting/event attendee.  Used in {@link #setRelationship}
     * and {@link #getRelationship}.
     */
    public static final byte RELATIONSHIP_ATTENDEE = 1;

    /**
     * Event organizer.  An organizer is not necessary an attendee.
     * Used in {@link #setRelationship} and {@link #getRelationship}.
     */
    public static final byte RELATIONSHIP_ORGANIZER = 2;

    /**
     * Performer.  Similar to {@link #RELATIONSHIP_SPEAKER}, but with more emphasis on art than
     * speech delivery.
     * Used in {@link #setRelationship} and {@link #getRelationship}.
     */
    public static final byte RELATIONSHIP_PERFORMER = 3;

    /**
     * Speaker.  Used in {@link #setRelationship} and {@link #getRelationship}.
     */
    public static final byte RELATIONSHIP_SPEAKER = 4;

    /**
     * No attendee type set.  Used in {@link #setType} and {@link #getType}.
     */
    public static final byte TYPE_NONE = 0;

    /**
     * Optional attendee.  Used in {@link #setType} and {@link #getType}.
     */
    public static final byte TYPE_OPTIONAL = 1;

    /**
     * Required attendee.  Used in {@link #setType} and {@link #getType}.
     */
    public static final byte TYPE_REQUIRED = 2;

    /**
     * No attendee status set.  Used in {@link #setStatus} and {@link #getStatus}.
     */
    public static final byte STATUS_NONE = 0;


    /**
     * Attendee has accepted.  Used in {@link #setStatus} and {@link #getStatus}.
     */
    public static final byte STATUS_ACCEPTED = 1;

    /**
     * Attendee has declined.  Used in {@link #setStatus} and {@link #getStatus}.
     */
    public static final byte STATUS_DECLINED = 2;

    /**
     * Invitation has been sent, but the person has not accepted.
     * Used in {@link #setStatus} and {@link #getStatus}.
     */
    public static final byte STATUS_INVITED = 3;

    /**
     * Attendee has accepted tentatively.  Used in {@link #setStatus} and {@link #getStatus}.
     */
    public static final byte STATUS_TENTATIVE = 4;

    private String email;
    private String value;
    private byte relationship = RELATIONSHIP_NONE;
    private byte type = TYPE_NONE;
    private byte status = STATUS_NONE;

    /**
     * Creates a new Who, representing event attendee information.
     */
    public Who() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte getRelationship() {
        return relationship;
    }

    public void setRelationship(byte relationship) {
        this.relationship = relationship;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    protected void toString(StringBuffer sb) {
        if (!StringUtils.isEmpty(email)) {
            sb.append("EMAIL: " + email + "\n");
        }

        if (!StringUtils.isEmpty(value)) {
            sb.append("VALUE: " + value + "\n");
        }

        sb.append("RELATIONSHIP: " + relationship + "\n");
        sb.append("TYPE: " + type + "\n");
        sb.append("STATUS: " + status + "\n");
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }
}
