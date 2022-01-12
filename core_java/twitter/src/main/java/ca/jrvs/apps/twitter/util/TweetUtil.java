package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.List;
import java.util.ArrayList;


public class TweetUtil {


  public static Tweet buildTweet(String text, Double lon, Double lat){
    Tweet tweet = new Tweet();
    List<Double> list = new ArrayList<>();

    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String escapedText = percentEscaper.escape(text);
    tweet.setText(escapedText);

    list.add(lon);
    list.add(lat);

    Coordinates coordinates = new Coordinates();
    coordinates.setCoordinates(list);
    tweet.setCoordinates(coordinates);

    return tweet;
  }
}
