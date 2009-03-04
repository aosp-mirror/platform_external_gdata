// Copyright 2007 The Android Open Source Project

package com.google.wireless.gdata.client;

import java.util.Hashtable;
import java.util.Vector;

/**
 * A concrete implementation of QueryParams that uses the encodeUri method of a
 * GDataClient (passed in at construction time) to URL encode parameters.
 *
 * This implementation maintains the order of parameters, which is useful for
 * testing.  Instances of this class are not thread safe.
 */
public class HttpQueryParams extends QueryParams {

  private GDataClient client;

  /* Used to store the mapping of names to values */
  private Hashtable params;

  /* Used to maintain the order of parameter additions */
  private Vector names;

  /**
   * Constructs a new, empty HttpQueryParams.
   *
   * @param client GDataClient whose encodeUri method is used for URL encoding.
   */
  public HttpQueryParams(GDataClient client) {
    this.client = client;
    // We expect most queries to have a relatively small number of parameters.
    names = new Vector(4);
    params = new Hashtable(7);
  }

  public String generateQueryUrl(String feedUrl) {
    StringBuffer url = new StringBuffer(feedUrl);
    url.append(feedUrl.indexOf('?') >= 0 ? '&' : '?');

    for (int i = 0; i < names.size(); i++) {
      if (i > 0) {
        url.append('&');
      }
      String name = (String) names.elementAt(i);
      url.append(client.encodeUri(name)).append('=');
      url.append(client.encodeUri(getParamValue(name)));
    }
    return url.toString();
  }

  public String getParamValue(String param) {
    return (String) params.get(param);
  }

  public void setParamValue(String param, String value) {
    if (value != null) {
      if (!params.containsKey(param)) {
        names.addElement(param);
      }
      params.put(param, value);
    } else {
      if (params.remove(param) != null) {
        names.removeElement(param);
      }
    }
  }

  public void clear() {
    names.removeAllElements();
    params.clear();
  }
}
