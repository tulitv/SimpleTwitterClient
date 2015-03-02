package com.codepath.apps.simpletwitterclient.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.codepath.apps.simpletwitterclient.TwitterApplication;
import com.codepath.apps.simpletwitterclient.TwitterClient;
import com.codepath.apps.simpletwitterclient.Utility;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by vincetulit on 2/25/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the client
        client = TwitterApplication.getRestClient();

        populateTimeline(0);
    }

    // Send and API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    @Override
    public void populateTimeline(long page) {

        if (Utility.isNetworkAvailable(getActivity())) {

            // First load / re-load => clear all
            if (page == 0) {
                removeAll();
            }
            // Get the tweets
            client.getMentionsTimeline(page, new JsonHttpResponseHandler() {
                //SUCCESS
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    // Deserialize JSON and add them to adapter
                    addAll(Tweet.fromJSONArray(json, false));
                    // Notify swipe container that update is finished
                    timelineUpdateFinished();
                }

                //FAILURE
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    // Just in case our request ends up here
                    // Notify swipe container that update is finished
                    timelineUpdateFinished();
                }

            });
        } else {
            // Show NO INTERNET message
            Utility.showNoInternet(getActivity());

            // Notify swipe container that update is finished
            timelineUpdateFinished();
        }
    }

    public void reloadTimeline() {
        populateTimeline(0);
    }
}
