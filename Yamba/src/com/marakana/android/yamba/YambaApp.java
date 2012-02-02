package com.marakana.android.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application {
	static final String TAG = "YambaApp";
	private Twitter twitter;
	private SharedPreferences prefs;

	/** Called when app is created. */
	@Override
	public void onCreate() {
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}

	/** Returns Twitter object, lazily initializing it when needed. */
	public Twitter getTwitter() {
		if (twitter == null) {
			// Read preferences
			String username = prefs.getString("username", "");
			String password = prefs.getString("password", "");
			String server = prefs.getString("server", "");
			Log.d(TAG, String.format("%s/%s@%s", username, password, server));
			
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl(server);

		}
		return twitter;
	}

	/** Returns the refresh interval preference. */
	public long getInterval() {
		return Long.parseLong(prefs.getString("interval", "0"));
	}
}
