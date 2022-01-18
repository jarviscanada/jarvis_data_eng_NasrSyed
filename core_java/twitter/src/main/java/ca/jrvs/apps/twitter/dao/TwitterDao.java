package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterDao implements CrdDao<Tweet, String> {

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy/";
  //URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";
  //Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {this.httpHelper = httpHelper;}

  /**
   * Check response status code and convert response entity to tweet object.
   */

  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
    Tweet tweet = null;

    //Check response status
    int status = response.getStatusLine().getStatusCode();

    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        System.out.println("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status code:" + status);
    }

    if (response.getEntity() == null) {
      throw new RuntimeException("Empty response body");
    }

    //Convert Response Entity to json string
    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e){
      throw new RuntimeException("Failed to convert entity to String", e);
    }
    //Convert json string to Java Tweet object
    try {
      tweet = JsonUtil.toObjectFromJson(jsonStr,Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to convert JSON str to Object", e);
    }
    return tweet;
  }

  public URI getPostUri(Tweet tweet) throws URISyntaxException {
    return new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + tweet.getText() + AMPERSAND + "long" + EQUAL +
        tweet.getCoordinates().getCoordinates().get(0) + AMPERSAND + "lat" + EQUAL + tweet.getCoordinates().getCoordinates().get(1));
  }
  public URI getShowUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id);

  }
  public URI getDeleteUri(String id) throws URISyntaxException {
    return new URI(API_BASE_URI + DELETE_PATH + id + ".json");
  }

  @Override
  public Tweet create(Tweet tweet) {
    //Construct URI
    URI uri;
    try {
      uri = getPostUri(tweet);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input", e);
    }
    //Execute HTTP Request
    HttpResponse response = httpHelper.httpPost(uri);
    //Validate response and deserialize response to Tweet object
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet findById(String s) {
    //Construct URI
    URI uri;
    try {
      uri = getShowUri(s);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet id", e);
    }
    //Execute HTTP Request
    HttpResponse response = httpHelper.httpGet(uri);
    //Validate response and deserialize response to Tweet object
    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String s) {
    //Construct URI
    URI uri;
    try {
      uri = getDeleteUri(s);
    } catch (URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet id", e);
    }
    //Execute HTTP Request
    HttpResponse response = httpHelper.httpPost(uri);
    //Validate response and deserialize response to Tweet object
    return parseResponseBody(response, HTTP_OK);
  }
}
