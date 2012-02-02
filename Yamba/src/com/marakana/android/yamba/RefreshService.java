package com.marakana.android.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {
	static final String TAG = "RefreshService";
	
	/** Required default constructor. */
	public RefreshService() {
		super(TAG);
		Log.d(TAG, "RefreshService instantiated");
	}

	/** Called when service is started to handle the intent.
	 * Runs on a separate, non-UI thread. */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Get the friends timeline
		try {
			YambaApp yambaApp = (YambaApp)getApplication();
			List<Status> timeline = yambaApp.getTwitter().getHomeTimeline();
			for (Status status : timeline) {
				Log.d(TAG, String.format("%s: %s", status.user.name,
						status.text));
			}
		} catch (TwitterException e1) {
			Log.e(TAG, "Failed to pull timeline", e1);
		}
	}
}
