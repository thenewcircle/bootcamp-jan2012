package com.marakana.android.yamba;

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

	/**
	 * Called when service is started to handle the intent. Runs on a separate,
	 * non-UI thread.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		// Get the friends timeline
		((YambaApp) getApplication()).fetchTimeline();
	}
}
