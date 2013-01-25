package com.example.codingchallengeaugust;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.example.codingchallengeaugust.adapters.TweetAdapter;
import com.example.codingchallengeaugust.asynctask.GetFeedTask;
import com.example.codingchallengeaugust.model.Result;
import com.example.codingchallengeaugust.model.SearchResponse;

import java.util.List;

public class TweetFeedActivity extends Activity implements GetFeedTask.ActivityCallback {

	public static final String TAG = "TweetFeedActivity";
    private static final String MENU_REFRESH = "Refresh";

	private StringBuffer twitterUrl = new StringBuffer("http://search.twitter.com/search.json?q=");
	private String twitterHandle;
	
	private ProgressDialog dialog;
	private GetFeedTask getFeedTask;
	private TweetAdapter tweetAdapter;
	private ListView tweetListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(MENU_REFRESH).setIcon(R.drawable.ic_action_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        this.twitterHandle = (String) getIntent().getExtras().get(MainActivity.TWITTER_HANDLE);
        twitterUrl.append(this.twitterHandle);

        actionBar.setTitle("@" + this.twitterHandle + " Twitter Feed");
        actionBar.setDisplayHomeAsUpEnabled(true);

        tweetListView = (ListView) findViewById(android.R.id.list);
        getFeed();
    }
    
    private void getFeed() {
        dialog = ProgressDialog.show(this, "", "Fetching tweets...", true);
    	
    	getFeedTask = new GetFeedTask(this);
    	getFeedTask.execute(twitterUrl.toString());
    }
    
	private void displayResults(List<Result> results) {
		dialog.cancel();
		
		tweetAdapter = new TweetAdapter(this, results);
		tweetListView.setAdapter(tweetAdapter);
	}

    public void onGetFeedTaskCompleted(SearchResponse searchResults) {
    	displayResults(searchResults.results);
    }
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getTitle().equals(MENU_REFRESH)) {
			getFeed();
		} else if (item.getItemId() == android.R.id.home) {
            finish();
        }
		
		return true;
	}
}
