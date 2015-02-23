package com.codepath.apps.simpletwitterclient.models;


import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import java.text.ParseException;


/**
 * Created by vincetulit on 2/17/15.
 */
//Parse the JSON + Store the data, encapsulate state logic or display logic
@Table(name = "tweets")
public class Tweet extends Model {
    // Attributes
    @Column(name= "body")
    private String body;

    @Column(name= "uid")
    private long uid; //unique id for the tweet

    @Column(name= "user")
    private User user; // store embedded user object

    @Column(name= "createdat")
    private String createdAt;

    @Column(name= "retweetcount")
    private int retweetCount;

    @Column(name= "favouritescount")
    private int favouritesCount;

    private ArrayList<Image> image;

    // No need to save this
    public static long max_id = 0;

    // Deserialize the JSON
    // Tweet.fromJSON("{...}") => Tweet
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract value from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = tweet.getRelativeTimeAgo(jsonObject.getString("created_at"));
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.favouritesCount = jsonObject.getInt("favorite_count");
            JSONArray jsonMedia = jsonObject.getJSONObject("entities").getJSONArray("media");
            if (jsonMedia!=null) {
                tweet.image = Image.fromJSONArray(jsonMedia);
            }
            long id = jsonObject.getLong("id");
            if ((id < max_id) || (max_id == 0)) {
                max_id = id;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Return the tweet object
        return tweet;
    }

    // Tweet.fromJSONArray([{...},{...},...]) => List<Tweets>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // Interate the json array and create tweets
        for (int i = 0; i < jsonArray.length(); i++)
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                    // Add to db when it is added to the ArrayList
                    tweet.getUser().save();
                    tweet.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        // Return the finished list
        return tweets;

    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return  user;
    }

    public int getRetweetCount() { return retweetCount; }

    public int getFavouritesCount() { return favouritesCount; }

    public ArrayList<Image> getImage() { return image; }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static ArrayList<Tweet> fromDB() {

        return (ArrayList) (new Select().from(Tweet.class).execute());

    }

    public static String fromDBTest() {
        Tweet myTweet = new Select().from(Tweet.class).executeSingle();

      return myTweet.getUser().getName() + " - " +myTweet.getBody();
    }

    public Tweet() {
        super();
    }

}


