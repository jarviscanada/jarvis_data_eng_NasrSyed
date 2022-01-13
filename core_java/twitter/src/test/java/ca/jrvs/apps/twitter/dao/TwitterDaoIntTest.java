package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {
  private TwitterDao dao;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    //setup dependency
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    //pass dependency
    this.dao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() throws Exception{
    String hashTag = "#snowboard #SwissAlps!";
    String text = "@NasrSyed2 not sure how I got here... " + hashTag + " " + System.currentTimeMillis();
    Double lat = 46.56137;
    Double lon = 8.56189;
    Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);
    System.out.println(JsonUtil.toJson(postTweet,true,true));


    Tweet tweet = dao.create(postTweet);

    assertEquals(text, tweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

    assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void findById() throws JsonProcessingException {
    String s = "1481015960656035841";
    Tweet tweet = dao.findById(s);
    assertEquals(s, tweet.getId_str());
    System.out.println(JsonUtil.toJson(tweet,true,true));
  }

  @Test
  public void deleteById() throws Exception{
    String s = "1481369514713432064"; //@3:57pm on Jan 12th
    Tweet tweet = dao.deleteById(s);
    assertEquals(s,tweet.getId_str());
    System.out.println(JsonUtil.toJson(tweet,true,true));
  }
}