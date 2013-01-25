package com.example.codingchallengeaugust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String TAG = "mainActivity";
	public static final String TWITTER_HANDLE = "twitterHandle";
	
	private EditText twitterHandle;
    private Button findButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        getActionBar().setTitle("Tweet Feed - beta");
        
        twitterHandle = (EditText) findViewById(R.id.twitter_handle);
        findButton = (Button) findViewById(R.id.find_button);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this.twitterHandle.getText().toString().length() != 0) {
                    Log.i(TAG, "Twitter Handle entered: " + MainActivity.this.twitterHandle.getText().toString());
                    fetchTwitterFeed();
                }
            }
        });
    }

    private void fetchTwitterFeed() {
    	Intent intent = new Intent(this, TweetFeedActivity.class);
    	intent.putExtra(MainActivity.TWITTER_HANDLE, this.twitterHandle.getText().toString());
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
