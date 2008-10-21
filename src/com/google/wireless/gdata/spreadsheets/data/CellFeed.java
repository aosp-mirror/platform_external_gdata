// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.spreadsheets.data;

import com.google.wireless.gdata.data.Feed;

/**
 * A feed handler for GData Spreadsheets cell-based feeds.
 */
public class CellFeed extends Feed {
    private String editUri;

    /** Default constructor. */
    public CellFeed() {
        super();
    }

    /**
     * Fetches the URI to which edits (such as cell content updates) should
     * go.
     * 
     * @return the edit URI for this feed
     */
    public String getEditUri() {
        return editUri;
    }

    /**
     * Sets the URI to which edits (such as cell content updates) should go.
     * 
     * @param editUri the new edit URI for this feed
     */
    public void setEditUri(String editUri) {
        this.editUri = editUri;
    }
}
