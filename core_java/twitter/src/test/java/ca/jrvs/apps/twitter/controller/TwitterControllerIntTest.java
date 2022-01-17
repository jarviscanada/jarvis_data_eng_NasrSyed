package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {
  private TwitterDao dao;
  private Service service;
  private Controller controller;


  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    //setup dependencies
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
    //pass dependencies
    this.dao = new TwitterDao(httpHelper);
    this.service = new TwitterService(dao);
    this.controller = new TwitterController(service);
  }

  @Test
  public void postTweet() throws JsonProcessingException {
    String[] args = {"post","Testing my postTweet via controller.","46.56137:8.56189"};
    Tweet tweet = controller.postTweet(args);
    System.out.println(JsonUtil.toJson(tweet,true,true));
  }

  @Test
  public void showTweet() throws JsonProcessingException {
    String[] args = {"show","1481765333086134272","created_at,id"};
    Tweet tweet = controller.showTweet(args);
    System.out.println(JsonUtil.toJson(tweet,true,true));
  }

  @Test
  public void deleteTweet() throws JsonProcessingException{
    String[] delArgs = {"delete", "1481738732050989062,1481006119891918848"};
    List<Tweet> tweets = controller.deleteTweet(delArgs);
    for (Tweet tweet : tweets)
      System.out.println(JsonUtil.toJson(tweet,true,true));
  }
}