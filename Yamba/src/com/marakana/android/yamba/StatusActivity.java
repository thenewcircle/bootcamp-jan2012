package com.marakana.android.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {
	Button buttonUpdate;
	EditText editStatus;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		Debug.startMethodTracing("Yamba.trace");
		
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
//		Debug.stopMethodTracing();
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
				Twitter twitter = new Twitter("student", "password");
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
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