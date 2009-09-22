// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.data;

import com.google.wireless.gdata2.data.batch.BatchInfo;
import com.google.wireless.gdata2.parser.ParseException;

/**
 * Entry in a GData feed. This is the rough equivalent of the atom:Entry
 * element. The "atom:entry" element represents an individual entry, 
 * acting as a container for metadata and data associated with the 
 * entry. This element can appear as a child of the atom:feed element, 
 * or it can appear as the document (i.e., top-level) element of a 
 * standalone Atom Entry Document.
 * The Entry class serves as a base class for Google service specific subclasses,
 * like a contact or a calendar event. As a base class it takes care of the default 
 * attributes and elements that are common to all entries.
 */
public class Entry {
    private String id = null;
    private String title = null;
    private String editUri = null;
    private String htmlUri = null;
    private String summary = null;
    private String content = null;
    private String author = null;
    private String email = null;
    private String category = null;
    private String categoryScheme = null;
    private String publicationDate = null;
    private String updateDate = null;
    private String eTagValue = null;
    private boolean deleted = false;
    private BatchInfo batchInfo = null;
    private String fields = null;
    private String contentSource = null;
    private String contentType = null;
    
    /**
     * Creates a new empty entry.
     */
    public Entry() {
    }

    /**
     * Clears all the values in this entry.
     */
    public void clear() {
        id = null;
        title = null;
        editUri = null;
        htmlUri = null;
        summary = null;
        content = null;
        contentType = null;
        contentSource = null;
        author = null;
        email = null;
        category = null;
        categoryScheme = null;
        publicationDate = null;
        updateDate = null;
        deleted = false;
        batchInfo = null;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the categoryScheme
     */
    public String getCategoryScheme() {
        return categoryScheme;
    }

    /**
     * @param categoryScheme the categoryScheme to set
     */
    public void setCategoryScheme(String categoryScheme) {
        this.categoryScheme = categoryScheme;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the contents type, either one of the default 
     * types (text, html, xhtml) or an atomMediaType 
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param type the contentType to set
     */
    public void setContentType(String type) {
        this.contentType = type;
    }

    /** 
     * If the content itself is empty, the src attribute 
     * points to the internet resource where the content 
     * can be loaded from 
     * @return the src attribute of the content element
     */
    public String getContentSource() {
        return contentSource;
    }

    /**
     * @param contentSource the url value to set
     */
    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }



    /**
     * @return the editUri
     */
    public String getEditUri() {
        return editUri;
    }

    /** 
     * Note that setting the editUri is only valid during parsing 
     * time, this is a server generated value and can not be changed
     * by the client normally 
     * @param editUri the editUri to set
     */
    public void setEditUri(String editUri) {
        this.editUri = editUri;
    }

    /**
     * @return The uri for the HTML version of this entry.
     */
    public String getHtmlUri() {
        return htmlUri;
    }

    /**
     * Set the uri for the HTML version of this entry.
     * @param htmlUri The uri for the HTML version of this entry.
     */
    public void setHtmlUri(String htmlUri) {
        this.htmlUri = htmlUri;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /** 
     * Note that setting the ID is only valid during parsing time, 
     * an ID is a server generated value and can not be changed by 
     * the client normally 
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the publicationDate
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /** 
     * Note that setting the publicationDate is only valid during 
     * parsing time, this is a server generated value and can not be 
     * changed by the client normally 
     * @param publicationDate the publicationDate to set
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /** 
     * Note that setting the updateDate is only valid during parsing
     * time, this is a server generated value and can not be changed 
     * by the client normally 
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return true if this entry represents a tombstone
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param isDeleted true if the entry is deleted
     */
    public void setDeleted(boolean isDeleted) {
        deleted = isDeleted;
    }

    /**
    * @return the value of the parsed eTag attribute 
    */
    public String getETag() {
        return eTagValue;
    }

    /** 
     * Note that setting the etag is only valid during parsing
     * time, this is a server generated value and can not be changed 
     * by the client normally 
     * @param eTag the eTag on the entry
     */
    public void setETag(String eTag) {
        eTagValue = eTag;
    }

    /**
    * @return the value of the parsed fields attribute 
    */
    public String getFields() {
        return fields;
    }

    /**
     * @param fields the fields expression on the entry, used during serialization
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * Used internally to access batch related properties.
     * Clients should use {@link com.google.wireless.gdata2.data.batch.BatchUtils} instead.
     */
    public BatchInfo getBatchInfo() {
        return batchInfo;
    }

    /**
     * Used internally to update batch related properties.
     * Clients should use {@link com.google.wireless.gdata2.data.batch.BatchUtils} instead.
     */
    public void setBatchInfo(BatchInfo batchInfo) {
        this.batchInfo = batchInfo;
    }

    /**
     * Appends the name and value to this StringBuffer, if value is not null.
     * Uses the format: "<NAME>: <VALUE>\n"
     * @param sb The StringBuffer in which the name and value should be
     * appended.
     * @param name The name that should be appended.
     * @param value The value that should be appended.
     */
    protected void appendIfNotNull(StringBuffer sb,
                                   String name, String value) {
        if (!StringUtils.isEmpty(value)) {
            sb.append(name);
            sb.append(": ");
            sb.append(value);
            sb.append("\n");
        }
    }

    /**
     * Helper method that creates the String representation of this Entry.
     * Called by {@link #toString()}.
     * Subclasses can add additional data to the StringBuffer.
     * @param sb The StringBuffer that should be modified to add to the String
     * representation of this Entry.
     */
    protected void toString(StringBuffer sb) {
        appendIfNotNull(sb, "ID", id);
        appendIfNotNull(sb, "TITLE", title);
        appendIfNotNull(sb, "EDIT URI", editUri);
        appendIfNotNull(sb, "HTML URI", htmlUri);        
        appendIfNotNull(sb, "SUMMARY", summary);
        appendIfNotNull(sb, "CONTENT", content);
        appendIfNotNull(sb, "AUTHOR", author);
        appendIfNotNull(sb, "CATEGORY", category);
        appendIfNotNull(sb, "CATEGORY SCHEME", categoryScheme);
        appendIfNotNull(sb, "PUBLICATION DATE", publicationDate);
        appendIfNotNull(sb, "UPDATE DATE", updateDate);
        appendIfNotNull(sb, "DELETED", String.valueOf(deleted));
        appendIfNotNull(sb, "ETAG", String.valueOf(eTagValue));
        if (batchInfo != null) {
          appendIfNotNull(sb, "BATCH", batchInfo.toString());
        }
    }

    /**
     * Creates a StringBuffer and calls {@link #toString(StringBuffer)}.  The
     * return value for this method is simply the result of calling
     * {@link StringBuffer#toString()} on this StringBuffer.  Mainly used for
     * debugging.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(sb);
        return sb.toString();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public void validate() throws ParseException {
    }
}
