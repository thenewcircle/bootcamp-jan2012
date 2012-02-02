package com.marakana.android.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class UpdateService extends Service {
	static final String TAG = "UpdateService";
	static final long DELAY = 30000; //30s
	Twitter twitter;
	Thread updater;
	boolean shouldRun = true;

	@Override
	public void onCreate() {
		super.onCreate();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		String server = prefs.getString("server", "");

		Log.d(TAG, String.format("%s/%s@%s", username, password, server));
		twitter = new Twitter(username, password);
		twitter.setAPIRootUrl(server);
		
		updater = new Thread(new Updater());
		updater.start();

		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		shouldRun = false;
		updater.interrupt();
		updater = null;

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
			while (shouldRun) {
				// Get the friends timeline
				try {
					List<Status> timeline = twitter.getHomeTimeline();
					for (Status status : timeline) {
						Log.d(TAG, String.format("%s: %s", status.user.name,
								status.text));
					}
				} catch (TwitterException e1) {
					Log.e(TAG, "Failed to pull timeline", e1);
				}
				
				// Sleep
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					shouldRun=false;
				}
			} //while
		}

	}
}
