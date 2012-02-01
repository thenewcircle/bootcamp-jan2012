package com.marakana.android.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {
	Button buttonUpdate;
	EditText editStatus;

	// --- Activity Lifecycle Callbacks
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Debug.startMethodTracing("Yamba.trace");

		setContentView(R.layout.status);

		// Find views
		buttonUpdate = (Button) findViewById(R.id.button_update);
		editStatus = (EditText) findViewById(R.id.edit_status);

		// Add button listener
		buttonUpdate.setOnClickListener(this);
	}

	/** Called when we leave this activity. */
	@Override
	protected void onStop() {
		super.onStop();
		// Debug.stopMethodTracing();
	}

	// ----- Menu Callbacks -----

	/** Called when menu button is pressed first time only. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/** Called each time a menu item is selected. */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch( item.getItemId() ) {
		case R.id.item_prefs:
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		case R.id.item_about:
			startActivity(new Intent(this, AboutActivity.class));			
			return true;
		}
		return false;
	}

	/** Called when the update button is clicked. */
	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();

		new PostToTwitter().execute(status);

		Log.d("Yamba", "onClicked with status: " + status);
	}

	/** AsyncTask responsible for posting to twitter. */
	class PostToTwitter extends AsyncTask<String, Void, String> {

		/** Work executed on a background thread. */
		@Override
		protected String doInBackground(String... status) {
			try {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(StatusActivity.this);
				String username = prefs.getString("username", "");
				String password = prefs.getString("password", "");
				String server = prefs.getString("server", "");
				
				Twitter twitter = new Twitter(username, password);
				twitter.setAPIRootUrl(server);
				twitter.setStatus(status[0]);
				return "Successfully posted: " + status[0];
			} catch (TwitterException e) {
				return "Failure to post";
			}
		}

		/** Called when we are done with the background job. */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}

	}

}