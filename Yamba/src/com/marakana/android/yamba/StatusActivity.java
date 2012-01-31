package com.marakana.android.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.os.Bundle;
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
		setContentView(R.layout.status);

		// Find views
		buttonUpdate = (Button) findViewById(R.id.button_update);
		editStatus = (EditText) findViewById(R.id.edit_status);

		// Add button listener
		buttonUpdate.setOnClickListener(this);
	}

	/** Called when the update button is clicked. */
	@Override
	public void onClick(View v) {
		final String status = editStatus.getText().toString();

		new Thread() {
			public void run() {
				Twitter twitter = new Twitter("student", "password");
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				twitter.setStatus(status);
				Log.d("Yamba", "onClicked with status: " + status);
				Toast.makeText(StatusActivity.this,
						"Successfully posted: " + status, Toast.LENGTH_LONG)
						.show();
			}
		}.start();

	}
}