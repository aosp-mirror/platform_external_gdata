// Copyright 2009 The Android Open Source Project

package com.google.wireless.gdata2.contacts.data;

/**
 * Postal address split into components. It allows to store the 
 * address in locale independent format. The fields can be 
 * interpreted and used to generate formatted, locale dependent 
 * address. 
 * Not all the properties of gd:structuredPostalAddress are supported 
 * by the Contacts API. The unsupported properties are the
 * gd:agent, gd:housename, and gd:subregion subelements, and the
 * attributes mailClass and usage. 
 */
public class StructuredPostalAddress extends ContactsElement {
  public static final byte TYPE_HOME = 1;
  public static final byte TYPE_WORK = 2;
  public static final byte TYPE_OTHER = 3;

  private String street;
  private String pobox;
  private String neighborhood;
  private String city;
  private String region;
  private String postcode;
  private String country;
  private String formattedAddress;

  /**
   * default empty constructor
   */
  public StructuredPostalAddress() {}
    public StructuredPostalAddress(String street, String pobox, String city, String postcode,
            String country, String region, String neighborhood, String formattedAddress,
        byte type, String label, boolean isPrimary) {
    super(type, label, isPrimary);
    this.street = street;
    this.pobox = pobox;
    this.city = city;
    this.postcode = postcode;
    this.country = country;
    this.region = region;
    this.neighborhood = neighborhood;
    this.formattedAddress = formattedAddress;
  }

  /**
   * Getter for street 
   * Can be street, avenue, road, etc. This element also includes 
   * the house number and room/apartment/flat/floor number 
   */
  public String getStreet() {
      return this.street;
  }
  
  /**
   * Setter for street 
   * Can be street, avenue, road, etc. This element also includes 
   * the house number and room/apartment/flat/floor number 
  */
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * Getter for pobox 
   * Covers actual P.O. boxes, drawers, locked bags, etc. This is 
   * usually but not always mutually exclusive with street. 
   */
  public String getPobox() {
      return this.pobox;
  }
  
  /**
   * Setter for pobox 
   * Covers actual P.O. boxes, drawers, locked bags, etc. This is 
   * usually but not always mutually exclusive with street. 
   */
  public void setPobox(String pobox) {
    this.pobox = pobox;
  }

 /**
   * Getter for neighborhood 
   * This is used to disambiguate a street address when a city 
   * contains more than one street with the same name, or to 
   * specify a small place whose mail is routed through a larger 
   * postal town. In China it could be a county or a minor city. 
   */
  public String getNeighborhood() {
      return this.neighborhood;
  }
  
  /**
   * Setter for neighborhood 
   * This is used to disambiguate a street address when a city 
   * contains more than one street with the same name, or to 
   * specify a small place whose mail is routed through a larger 
   * postal town. In China it could be a county or a minor city. 
   */
  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  /**
   * Getter for city 
   * Can be city, village, town, borough, etc. This is the postal 
   * town and not necessarily the place of residence or place of 
   * business. 
   */
  public String getCity() {
      return this.city;
  }
  
  /**
   * Setter for city 
   * Can be city, village, town, borough, etc. This is the postal 
   * town and not necessarily the place of residence or place of 
   * business. 
   */
  public void setCity(String city) {
    this.city = city;
  }


  /**
   * Getter for region 
   * A state, province, county (in Ireland), Land (in Germany), 
   * departement (in France), etc. 
   */
  public String getRegion() {
      return this.region;
  }
  
  /**
   * Setter for region 
   * A state, province, county (in Ireland), Land (in Germany), 
   * departement (in France), etc. 
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * Getter for postcode 
   * Postal code. Usually country-wide, but sometimes specific to 
   * the city (e.g. "2" in "Dublin 2, Ireland" addresses). 
   */
  public String getPostcode() {
      return this.postcode;
  }
  
  /**
   * Setter for postcode 
   * Postal code. Usually country-wide, but sometimes specific to 
   * the city (e.g. "2" in "Dublin 2, Ireland" addresses). 
   */
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  /**
   * Getter for country 
   * The name or code of the country. 
   */
  public String getCountry() {
      return this.country;
  }
  
  /**
   * Setter for country 
   * The name or code of the country. 
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Getter for formatedAddress 
   * The full, unstructured postal address. 
   */
  public String getFormattedAddress() {
      return this.formattedAddress;
  }
  
  /**
   * Setter for formatedAddress 
   * The full, unstructured postal address. 
   */
  public void setFormattedAddress(String formattedAddress) {
    this.formattedAddress = formattedAddress;
  }

  public void toString(StringBuffer sb) {
    sb.append("PostalAddress");
    super.toString(sb);
    if (street != null) sb.append(" street:").append(street);
    if (pobox != null) sb.append(" pobox:").append(pobox);
    if (neighborhood != null) sb.append(" neighborhood:").append(neighborhood);
    if (city != null) sb.append(" city:").append(city);
    if (region != null) sb.append(" region:").append(region);
    if (postcode != null) sb.append(" postcode:").append(postcode);
    if (country != null) sb.append(" country:").append(country);
    if (formattedAddress != null) sb.append(" formattedAddress:").append(formattedAddress);
  }
}
