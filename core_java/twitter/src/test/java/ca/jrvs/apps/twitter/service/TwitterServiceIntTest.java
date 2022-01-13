package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {
  private TwitterDao dao;
  private TwitterService service;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    //setup dependency
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    //pass dependencies (there are 2 now)
    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(dao);
  }

  @Test
  public void postTweet() throws JsonProcessingException {
//    String hashTag = "#snowboard #SwissAlps!";
    String text = "Is it working????. #Jarvis";
    Double lat = 46.56137;
    Double lon = 8.56189;
    Tweet postTweet1 = TweetUtil.buildTweet(text,lon,lat);
    System.out.println(JsonUtil.toJson(postTweet1,true,true));

    Tweet tweet = service.postTweet(postTweet1);
  }

  @Test
  public void showTweet() throws JsonProcessingException {
    String s = "1481369632313380865";
    String[] fields = {"created_at","id"};

    Tweet tweet = service.showTweet(s, fields);

    System.out.println(JsonUtil.toJson(tweet,true,true));

  }

  @Test
  public void deleteTweets() throws JsonProcessingException {
    String[] ids = {"1481766010881552387","1481726143216635910"};
    List<Tweet> list1 = service.deleteTweets(ids);

    for (Tweet tweet: list1){
      System.out.println(JsonUtil.toJson(tweet,true,true));
    }

  }
}