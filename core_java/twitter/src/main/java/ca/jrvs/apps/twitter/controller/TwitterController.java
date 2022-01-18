package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import static ca.jrvs.apps.twitter.util.TweetUtil.buildTweet;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  @Autowired
  public TwitterController(Service service) {this.service = service;}


  @Override
  public Tweet postTweet(String[] args) {
    if (args.length != 3){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }
    String tweet_text = args[1];
    String coordinates = args[2];
    String[] coordinatesArray = coordinates.split(COORD_SEP);
    if (coordinatesArray.length !=2 || StringUtils.isEmpty(tweet_text)){
      throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }
    Double lat = null;
    Double lon = null;
    try {
      lat = Double.parseDouble(coordinatesArray[0]);
      lon = Double.parseDouble(coordinatesArray[1]);
    } catch (Exception e){
      throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"",e);
    }
    Tweet postTweet = buildTweet(tweet_text,lon,lat);
    return service.postTweet(postTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if (args.length !=3){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"[field1, field2]]\"");
    }
    String tweet_id = args[1];
    String fields = args[2];
    String[] listOfFields = fields.split(COMMA);
    if (tweet_id.length() != 19 || StringUtils.isEmpty(tweet_id) || StringUtils.isEmpty(fields)){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp show \"tweet_id\" \"[field1, field2]]\"");
    }
    return service.showTweet(tweet_id,listOfFields);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length !=2){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"[tweet id's]\"");
    }
    String tweet_ids = args[1];
    String[] tweet_id_list = tweet_ids.split(COMMA);
    if (StringUtils.isEmpty(tweet_ids)){
      throw new IllegalArgumentException("USAGE: TwitterCLIApp delete \"[tweet id's]\"");
    }
    return service.deleteTweets(tweet_id_list);
  }
}
