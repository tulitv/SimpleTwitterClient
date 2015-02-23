package com.codepath.apps.simpletwitterclient;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.codepath.apps.simpletwitterclient.R;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {
    private SwipeRefreshLayout swipeContainer;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private TextView tvNoInternet;

    public static final int TWEET_COMPOSE_ID = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActiveAndroid.initialize(this);

        tvNoInternet = (TextView) findViewById(R.id.tvNoInternet);

        // Find the listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        // Create the arraylist (datasource)
        tweets = new ArrayList<>();
        // Construct the adapter from the source
        aTweets = new TweetsArrayAdapter(this,tweets);
        // Connect adapter to list view
        lvTweets.setAdapter(aTweets);

        // Endless scrolling
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            // Triggered only when new data needs to be appended to the list
            // Add whatever code is needed to append new items to your AdapterView
            @Override
            public void onLoadMore(int page, int totalItemCount) {
                populateTimeline();
            }
        });

        // Swipe Container for refresh
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // To clear all
                Tweet.max_id = 0;
                // Populate latest tweets
                populateTimeline();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Get the client
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline();

        // Setup On Click Listener for Detailed View
        lvTweets.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TimelineActivity.this, DetailedViewActivity.class);
                i.putExtra("postId", tweets.get(position).getUid());
                startActivity(i);
            }
        });

        /* Did not work out
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(94,176,237)));
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowTitleEnabled(true); */
    }

    // Send and API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    private void populateTimeline() {

        if (Utility.isNetworkAvailable(getBaseContext())) {
            // First load / re-load => clear all
            if (Tweet.max_id == 0) {
                // Remove everything from the db
                new Delete().from(Tweet.class).execute();
                // Remove everything from the list
                tweets.clear();
            }
            // Get the tweets
            client.getHomeTimeline(new JsonHttpResponseHandler() {
                //SUCCESS
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    // Deserialize JSON and add them to adapter
                    aTweets.addAll(Tweet.fromJSONArray(json));
                    swipeContainer.setRefreshing(false);
                }

                //FAILURE
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    // Just in case our request ends up here
                    swipeContainer.setRefreshing(false);
                }

            });
        }
        else {
            // Post a short message that app is operating in offline mode
            Toast toast = Toast.makeText(getBaseContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            // Clear whatever we had, every time, then reload from db.
            tweets.clear();
            aTweets.addAll(Tweet.fromDB());
            // Anything we want to do with the list, wipe out offline data from the list
            Tweet.max_id = 0;
            swipeContainer.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compose) {
            // Launch the SearchSettingsActivity with existing SearchOptions
            Intent i = new Intent(this, ComposeActivity.class);
            startActivityForResult(i, TWEET_COMPOSE_ID);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((resultCode == RESULT_OK) && (requestCode == TWEET_COMPOSE_ID)) {
            // Reset the current timeline
            Tweet.max_id = 0;
            // Populate the timeline with the newly compose tweet
            populateTimeline();
        }
    }
}
