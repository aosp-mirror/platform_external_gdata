// Copyright 2007 The Android Open Source Project
package com.google.wireless.gdata.spreadsheets.data;

import com.google.wireless.gdata.data.Entry;

/**
 * Represents an entry in a GData Spreadsheets Cell-based feed.
 */
public class CellEntry extends Entry {
    /** The spreadsheet column of the cell. */
    private int col = -1;

    /** The cell entry's inputValue attribute */
    private String inputValue = null;

    /** The cell entry's numericValue attribute */
    private String numericValue = null;

    /** The spreadsheet row of the cell */
    private int row = -1;

    /** The cell entry's text sub-element */
    private String value = null;

    /** Default constructor. */
    public CellEntry() {
        super();
    }
    
    /**
     * Fetches the cell's spreadsheet column.
     * 
     * @return the cell's spreadsheet column
     */
    public int getCol() {
        return col;
    }

    /**
     * Fetches the cell's inputValue attribute, which is the actual user input
     * rather (such as a formula) than computed value of the cell.
     * 
     * @return the cell's inputValue
     */
    public String getInputValue() {
        return inputValue;
    }

    /**
     * Fetches the cell's numericValue attribute, which is a decimal
     * representation.
     * 
     * @return the cell's numericValue
     */
    public String getNumericValue() {
        return numericValue;
    }

    /**
     * Fetches the cell's spreadsheet row.
     * 
     * @return the cell's spreadsheet row
     */
    public int getRow() {
        return row;
    }

    /**
     * Fetches the cell's contents, after any computation. For example, if the
     * cell actually contains a formula, this will return the formula's computed
     * value.
     * 
     * @return the computed value of the cell
     */
    public String getValue() {
        return value;
    }

    /**
     * Indicates whether the cell's contents are numeric.
     * 
     * @return true if the contents are numeric, or false if not
     */
    public boolean hasNumericValue() {
        return numericValue != null;
    }

    /**
     * Sets the cell's spreadsheet column.
     * 
     * @param col the new spreadsheet column of the cell
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets the cell's actual contents (such as a formula, or a raw value.)
     * 
     * @param inputValue the new inputValue of the cell
     */
    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    /**
     * Sets the cell's numeric value. This can be different from the actual
     * value; for instance, the actual value may be a thousands-delimited pretty
     * string, while the numeric value could be the raw decimal.
     * 
     * @param numericValue the cell's new numericValue
     */
    public void setNumericValue(String numericValue) {
        this.numericValue = numericValue;
    }

    /**
     * Sets the cell's spreadsheet row.
     * 
     * @param row the new spreadsheet row of the cell
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the cell's computed value.
     * 
     * @param value the new value of the cell
     */
    public void setValue(String value) {
        this.value = value;
    }
}
