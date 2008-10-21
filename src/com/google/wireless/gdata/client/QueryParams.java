// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.client;

/**
 * Class for specifying parameters and constraints for a GData feed.
 * These are used to modify the feed URL and add querystring parameters to the
 * feed URL.
 *
 * Note that if an entry ID has been set, no other query params can be set.
 *
 * @see QueryParams#generateQueryUrl(String)
 */
// TODO: add support for projections?
// TODO: add support for categories?
public abstract class QueryParams {

    /**
     * Param name constant for a search query.
     */
    public static final String QUERY_PARAM = "q";

    /**
     * Param name constant for filtering by author.
     */
    public static final String AUTHOR_PARAM = "author";

    /**
     * Param name constant for alternate representations of GData.
     */
    public static final String ALT_PARAM = "alt";
    public static final String ALT_RSS = "rss";
    public static final String ALT_JSON = "json";

    /**
     * Param name constant for the updated min.
     */
    public static final String UPDATED_MIN_PARAM = "updated-min";

    /**
     * Param name constant for the updated max.
     */
    public static final String UPDATED_MAX_PARAM = "updated-max";

    /**
     * Param name constant for the published min.
     */
    public static final String PUBLISHED_MIN_PARAM = "published-min";

    /**
     * Param name constant for the published max.
     */
    public static final String PUBLISHED_MAX_PARAM = "published-max";

    /**
     * Param name constant for the start index for results.
     */
    public static final String START_INDEX_PARAM = "start-index";

    /**
     * Param name constant for the max number of results that should be fetched.
     */
    public static final String MAX_RESULTS_PARAM = "max-results";

    private String entryId;

    /**
     * Creates a new empty QueryParams.
     */
    public QueryParams() {
    }

    /**
     * Generates the url that should be used to query a GData feed.
     * @param feedUrl The original feed URL.
     * @return The URL that should be used to query the GData feed.
     */
    public abstract String generateQueryUrl(String feedUrl);

    /**
     * Gets a parameter value from this QueryParams.
     * @param param The parameter name.
     * @return The parameter value.  Returns null if the parameter is not
     * defined in this QueryParams.
     */
    public abstract String getParamValue(String param);

    /**
     * Sets a parameter value in this QueryParams.
     * @param param The parameter name.
     * @param value The parameter value.
     */
    public abstract void setParamValue(String param, String value);

    /**
     * Clears everything in this QueryParams.
     */
    public abstract void clear();

    /**
     * @return the alt
     */
    public String getAlt() {
        return getParamValue(ALT_PARAM);
    }

    /**
     * @param alt the alt to set
     */
    public void setAlt(String alt) {
        setParamValue(ALT_PARAM, alt);
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return getParamValue(AUTHOR_PARAM);
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        setParamValue(AUTHOR_PARAM, author);
    }

    /**
     * @return the entryId
     */
    public String getEntryId() {
        return entryId;
    }

    /**
     * @param entryId the entryId to set
     */
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    /**
     * @return the maxResults
     */
    public String getMaxResults() {
        return getParamValue(MAX_RESULTS_PARAM);
    }

    // TODO: use an int!
    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(String maxResults) {
        setParamValue(MAX_RESULTS_PARAM, maxResults);
    }

    /**
     * @return the publishedMax
     */
    public String getPublishedMax() {
        return getParamValue(PUBLISHED_MAX_PARAM);
    }

    /**
     * @param publishedMax the publishedMax to set
     */
    public void setPublishedMax(String publishedMax) {
        setParamValue(PUBLISHED_MAX_PARAM, publishedMax);
    }

    /**
     * @return the publishedMin
     */
    public String getPublishedMin() {
        return getParamValue(PUBLISHED_MIN_PARAM);
    }

    /**
     * @param publishedMin the publishedMin to set
     */
    public void setPublishedMin(String publishedMin) {
        setParamValue(PUBLISHED_MIN_PARAM, publishedMin);
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return getParamValue(QUERY_PARAM);
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        setParamValue(QUERY_PARAM, query);
    }

    /**
     * @return the startIndex
     */
    public String getStartIndex() {
        return getParamValue(START_INDEX_PARAM);
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(String startIndex) {
        setParamValue(START_INDEX_PARAM, startIndex);
    }

    /**
     * @return the updatedMax
     */
    public String getUpdatedMax() {
        return getParamValue(UPDATED_MAX_PARAM);
    }

    /**
     * @param updatedMax the updatedMax to set
     */
    public void setUpdatedMax(String updatedMax) {
        setParamValue(UPDATED_MAX_PARAM, updatedMax);
    }

    /**
     * @return the updatedMin
     */
    public String getUpdatedMin() {
        return getParamValue(UPDATED_MIN_PARAM);
    }

    /**
     * @param updatedMin the updatedMin to set
     */
    public void setUpdatedMin(String updatedMin) {
        setParamValue(UPDATED_MIN_PARAM, updatedMin);
    }
}
