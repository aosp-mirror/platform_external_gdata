// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.spreadsheets.data;

import com.google.wireless.gdata.GDataException;
import com.google.wireless.gdata.data.Entry;
import com.google.wireless.gdata.data.StringUtils;

/**
 * Represents an entry in a GData Spreadsheets meta-feed.
 */
public class SpreadsheetEntry extends Entry {
    /** The URI of the worksheets meta-feed for this spreadsheet */
    private String worksheetsUri = null;

    /**
     * Fetches the URI of the worksheets meta-feed (that is, list of worksheets)
     * for this spreadsheet.
     * 
     * @return the worksheets meta-feed URI
     * @throws GDataException if the unique key is not set
     */
    public String getWorksheetFeedUri() throws GDataException {
        if (StringUtils.isEmpty(worksheetsUri)) {
            throw new GDataException("worksheet URI is not set");
        }
        return worksheetsUri;
    }

    /**
     * Sets the URI of the worksheet meta-feed corresponding to this
     * spreadsheet.
     * 
     * @param href
     */
    public void setWorksheetFeedUri(String href) {
        worksheetsUri = href;
    }
}
