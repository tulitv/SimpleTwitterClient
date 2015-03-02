package com.codepath.apps.simpletwitterclient;

import org.apache.http.protocol.RequestExpectContinue;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1/";
	public static final String REST_CONSUMER_KEY = "0uyHI5bMnHrl9PXavF4pXONcc";
	public static final String REST_CONSUMER_SECRET = "fXeNTPhD3GEmwqVZvGKWqS9coHnTiqhaTyT0jOC8C37RMKO7kv";
	public static final String REST_CALLBACK_URL = "oauth://simpletwitterclient";
    public static final int COUNT = 10;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    // METHOD == ENDPOINT

    // HomeTimeline - Gets the user's home timeline data
    public void getHomeTimeline(long page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", COUNT);
        // If not first request
        if (page > 0)
            params.put("max_id", page);
        // Execute the request
        getClient().get(apiUrl, params, handler);
    }

    // MentionsTimeline - Gets the user's mentions timeline data
    public void getMentionsTimeline(long page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", COUNT);
        // If not first request
        if (page > 0)
            params.put("max_id", page);
        // Execute the request
        getClient().get(apiUrl, params, handler);
    }

    // Post Tweet - Post a message to user's home timeline
    public void postTweet(String message, long uid, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", message);
        if (uid != 0)
            params.put("in_reply_to_status_id", uid);
        getClient().post(apiUrl, params, handler);
    }

    // Show Tweet - Gets a tweet with additional details
    public void showTweet( long id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/show.json");
        RequestParams params = new RequestParams();
        params.put("id", id);
        getClient().get(apiUrl, params, handler);
    }

    // User Timeline - Gets the user's posts' timeline
    public void getUserTimeline(String screenName, long page, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        // If not first page
        if (page > 0)
            params.put("max_id", page);
        params.put("count", COUNT);
        // Execute the request
        getClient().get(apiUrl, params, handler);
    }

    // User Info - Gets the user's detailed information
    // !!!This works only for the signed user
    public void getUserCredentialInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);
    }

    // User Info - Get detailed info of a user
    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

}