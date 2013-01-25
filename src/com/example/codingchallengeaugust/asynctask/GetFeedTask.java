package com.example.codingchallengeaugust.asynctask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.example.codingchallengeaugust.model.SearchResponse;
import com.google.gson.Gson;

public class GetFeedTask extends AsyncTask <String, Void, Boolean>{
	
	public interface ActivityCallback {
		void onGetFeedTaskCompleted(SearchResponse searchResponse);
	}

	public static final String TAG = "getFeedTask";
	
	private ActivityCallback activityCallback;
	private InputStream inputStream;
	private SearchResponse searchResponse;
	
	private HttpEntity responseEntity;
	private DefaultHttpClient client;
	private HttpGet getRequest;
	private Reader reader;
	private Gson gson;
	
	public GetFeedTask(ActivityCallback activity) {
		this.activityCallback = activity;
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		boolean successfull = true;
		
		String url = params[0];
		
		gson = new Gson();
		client = new DefaultHttpClient();
		getRequest = new HttpGet(url);

		try {
			Log.v(TAG, "Making the call out.");
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url);
				return null;
			}

			responseEntity = getResponse.getEntity();
			inputStream = responseEntity.getContent();
			reader = new InputStreamReader(inputStream);
			searchResponse = gson.fromJson(reader, SearchResponse.class);

		} catch (IOException e) {
			successfull = false;
			getRequest.abort();
			Log.v(TAG, "Error for URL " + url, e);
		}

		return successfull;
	}
	
	@Override
	protected void onPostExecute(Boolean succeeded) {
		this.activityCallback.onGetFeedTaskCompleted(searchResponse);
	}
	
}
