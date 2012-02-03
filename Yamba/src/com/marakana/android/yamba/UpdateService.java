package com.marakana.android.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {
	static final String TAG = "UpdateService";
	Twitter twitter;
	Thread updater;
	boolean shouldRun = true;

	@Override
	public void onCreate() {
		super.onCreate();

		// Start the updater thread
		updater = new Thread(new Updater());
		updater.start();

		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		shouldRun = false;

		// Stop the updater thread
		updater.interrupt();
		updater = null;

		// Clean up twitter object
		twitter = null;

		Log.d(TAG, "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/** Separate thread that pulls the timeline. */
	class Updater implements Runnable {

		@Override
		public void run() {
			YambaApp yambaApp = (YambaApp)getApplication();
			long intervalDelay = ((YambaApp)getApplication()).getInterval();
			
			while (shouldRun && intervalDelay != 0) {
				// Get the friends timeline
				try {
					List<Status> timeline = yambaApp.getTwitter().getHomeTimeline();
					for (Status status : timeline) {
						yambaApp.getStatusData().insert(status);
						Log.d(TAG, String.format("%s: %s", status.user.name,
								status.text));
					}
				} catch (TwitterException e1) {
					Log.e(TAG, "Failed to pull timeline", e1);
				}

				// Sleep
				try {
					intervalDelay = ((YambaApp)getApplication()).getInterval();
					Thread.sleep(intervalDelay * 1000);
				} catch (InterruptedException e) {
					shouldRun = false;
				}
			} // while
		}

	}
}
