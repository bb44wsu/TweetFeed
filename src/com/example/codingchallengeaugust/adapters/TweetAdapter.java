package com.example.codingchallengeaugust.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.codingchallengeaugust.R;
import com.example.codingchallengeaugust.model.Result;

public class TweetAdapter extends ArrayAdapter<Result> {

	private List<Result> data;
	private LayoutInflater viewInflater;
	
	public TweetAdapter(Context context, final List<Result> data) {
		super(context, 0, data);
        this.data = data;
        viewInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		final Result item = data.get(position);
		view = viewInflater.inflate(R.layout.tweet_row, null);
		
		final TextView fromUserView = (TextView) view.findViewById(R.id.fromUser);
		final TextView fromUserTagView = (TextView) view.findViewById(R.id.fromUserTag);
		final TextView tweetContentView = (TextView) view.findViewById(R.id.tweetContent);
		
		fromUserView.setText(item.fromUserName);
		fromUserTagView.setText("@" + item.fromUser);
		tweetContentView.setText(item.text);
		
		return view;
	}
}
