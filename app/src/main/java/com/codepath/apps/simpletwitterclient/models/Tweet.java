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

    // Deserialize the JSON
    // Tweet.fromJSON("{...}") => Tweet
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract value from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.favouritesCount = jsonObject.getInt("favorite_count");
            if (hasJSONObject(jsonObject, "media")) {
                tweet.image = Image.fromJSONArray(jsonObject.getJSONObject("entities").getJSONArray("media"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Return the tweet object
        return tweet;
    }

    // Tweet.fromJSONArray([{...},{...},...]) => List<Tweets>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray, boolean saveToDB) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        // Interate the json array and create tweets
        for (int i = 0; i < jsonArray.length(); i++)
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);

                    // Add to db when it is added to ONLY to the HomeTimeline ArrayList
                    if (saveToDB) {
                        tweet.getUser().save();
                        tweet.save();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        // Return the finished list
        return tweets;

    }


    // Getters
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

    //Check if items exist in the json object
    private static boolean hasJSONObject(JSONObject json, String item) {
        return json.toString().contains(item);
    }

    public static ArrayList<Tweet> fromDB() {

        return (ArrayList) (new Select().from(Tweet.class).execute());

    }

    public Tweet() {
        super();
    }

}


