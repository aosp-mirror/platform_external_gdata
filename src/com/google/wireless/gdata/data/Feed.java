// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.data;

/**
 * Class containing information about a GData feed.  Note that this feed does
 * not contain any of the entries in that feed -- the entries are yielded
 * separately from this Feed.
 */
// TODO: add a createEntry method?
// TODO: comment that setters are only used for parsing code.
public class Feed {
    private int totalResults;
    private int startIndex;
    private int itemsPerPage;
    private String title;
    private String id;
    private String lastUpdated;
    private String category;
    private String categoryScheme;

    /**
     * Creates a new, empty feed.
     */
    public Feed() {
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

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
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
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
     * @param lastUpdated the lastUpdated to set
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
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
