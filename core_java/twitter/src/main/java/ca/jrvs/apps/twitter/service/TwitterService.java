package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service {

  private CrdDao dao;

  //@Autowired
  public TwitterService(CrdDao dao) {this.dao = dao;}

  private void validatePostTweet(Tweet tweet) {
    try {
      if (tweet.getText().length() > 280) {
        throw new IllegalArgumentException("Number of characters in tweet must be less than or equal to 280");
      }
      if ((tweet.getCoordinates().getCoordinates().get(0) < -180) || (tweet.getCoordinates().getCoordinates().get(0) > 180)){
        throw new IllegalArgumentException("Longitude values must be between -180 and 180");
      }
      if ((tweet.getCoordinates().getCoordinates().get(1) < -90) || (tweet.getCoordinates().getCoordinates().get(1) > 90)){
        throw new IllegalArgumentException("Latitude values must be between -90 and 90");
      }
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Need to input values for longitude and latitude", e);
    }
  }
  @Override
  public Tweet postTweet(Tweet tweet) {
    //Business logic
    // e.g. text length, latitude and longitude range, id format
    validatePostTweet(tweet);
    //create tweet via dao
    return (Tweet) dao.create(tweet);
  }

  private void validateShowTweet(String id) {
    try {
      if (id.length() != 19){
        throw new IllegalArgumentException("Incorrect ID parameters.");
      }
    } catch (IllegalArgumentException e){
      throw new IllegalArgumentException("Incorrect ID Parameters.");
    }
  }
  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateShowTweet(id);
    Tweet tweet = (Tweet) dao.findById(id);

    List<String> list = Arrays.asList(fields);

    // setting fields not in the list to null values
    if (!list.contains("created_at")){
      tweet.setCreated_at(null);
    }
    if (!list.contains("id")) {
      tweet.setId(null);
    }
    if (!list.contains("id_str")) {
      tweet.setId_str(null);
    }
    if (!list.contains("text")) {
      tweet.setText(null);
    }
    if (!list.contains("entities")) {
      tweet.setEntities(null);
    }
    if (!list.contains("coordinates")) {
      tweet.setCoordinates(null);
    }
    if (!list.contains("retweet_count")) {
      tweet.setRetweet_count(null);
    }
    if (!list.contains("favorite_count")) {
      tweet.setFavorite_count(null);
    }
    if (!list.contains("favorited")) {
      tweet.setFavorited(null);
    }
    if (!list.contains("retweeted")) {
      tweet.setRetweeted(null);
  }
    return tweet;
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    for (String id: ids){
      validateShowTweet(id);
    }
    List<Tweet> tweets = new ArrayList<>();
    for (String id:ids){
      tweets.add((Tweet) dao.deleteById(id));
    }
    return tweets;
  }
}

