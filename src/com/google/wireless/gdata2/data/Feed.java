// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata2.data;

/**
 * Class containing information about a GData feed.  Note that this feed does
 * not contain any of the entries in that feed -- the entries are yielded
 * separately from this Feed.
 */
public class Feed {
    private int totalResults;
    private int startIndex;
    private int itemsPerPage;
    private String title;
    private String id;
    private String lastUpdated;
    private String category;
    private String categoryScheme;
    private String eTagValue;

    /**
     * Creates a new, empty feed.
     */
    public Feed() {
    }

    /** 
     * The approximate number of total results in the feed 
	 * @return totalResults
	 */
    public int getTotalResults() {
        return totalResults;
    }

    /** 
    *  The number of toal results in the feed Only used during
    *  parsing of the feed
	 * @param totalResults 
	 */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    /** 
     * The starting Index of the current result set 
	 * @return startIndex
	 */
    public int getStartIndex() {
        return startIndex;
    }

    /** 
    *  The starting index of the current result set Only used during
    *  parsing of the feed
	 * @param startIndex 
	 */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /** 
     * The number of items per Page in this result set 
	 * @return itemsPerPage
	 */
    public int getItemsPerPage() {
        return itemsPerPage;
    }

    /** 
    *  The number of items per page in this result set
    *  Only used during parsing of the feed
	 * @param itemsPerPage 
	 */
    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /** 
     * The category to set 
     * Only used during parsing of the feed 
     * @param category 
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
     * The categoryScheme to set 
     * Only used during parsing of the feed
     * @param categoryScheme 
     */
    public void setCategoryScheme(String categoryScheme) {
        this.categoryScheme = categoryScheme;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /** 
     * the id to set 
     * Only used during parsing of the feed 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /** 
     * The lastUpdated to set 
     * Only used during parsing of the feed 
     * @param lastUpdated 
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /** 
     * the title to set 
     * Only used during parsing of the feed 
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
    * @return the value of the parsed eTag attribute 
    */
    public String getETag() {
        return eTagValue;
    }

    /**
     * @param sets the eTag on the entry, used during 
     *           parsing
     */
    public void setETag(String eTag) {
        eTagValue = eTag;
    }
 
}
