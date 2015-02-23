package com.codepath.apps.simpletwitterclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeActivity extends ActionBarActivity {
    private TwitterClient client;
    private EditText etMessage;
    private TextView tvCharCounter;
    private final TextWatcher tweetTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvCharCounter.setText(String.valueOf(140 - etMessage.getText().toString().length())+"/140");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

    etMessage = (EditText) findViewById(R.id.etMessage);
    tvCharCounter = (TextView) findViewById(R.id.tvCharCounter);

    etMessage.addTextChangedListener(tweetTextWatcher);

    // Show soft keyboard automatically
    etMessage.requestFocus();
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    // Get the client
    client = TwitterApplication.getRestClient(); //singleton client

    Tweet.max_id = 0;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tweet) {

            // Post a tweet
            client.postTweet(new JsonHttpResponseHandler() {
                //SUCCESS
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    // Tiny info, so we wait in this activity until we get response (good or bad)
                    setResult(RESULT_OK, null);
                    // Take back user to the timeline only if message could be submitted
                    finish();
                }

                //FAILURE
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    //TODO: If message couldn't be submitted stay in this activity and notify user.
                    //TODO: Why? because he/she might just forgot to turn on network.
                }
            }, etMessage.getText().toString(), 0);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
