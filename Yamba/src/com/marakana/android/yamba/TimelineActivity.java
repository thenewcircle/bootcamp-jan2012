package com.marakana.android.yamba;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineActivity extends ListActivity {
	static final String TAG = "TimelineActivity";

	String[] FROM = { StatusData.C_USER, StatusData.C_TEXT,
			StatusData.C_CREATED_AT };
	int[] TO = { R.id.text_user, R.id.text_text, R.id.text_createdAt };

	YambaApp yambaApp;
	SimpleCursorAdapter adapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		yambaApp = (YambaApp) getApplication();

		// Get the timeline
		cursor = yambaApp.getStatusData().query();

		// Setup the adapter
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
		adapter.setViewBinder(VIEW_BINDER);
		setListAdapter(adapter);
	}
	
	/** Custom ViewBinder to convert timestamp to relative time. */
	static final ViewBinder VIEW_BINDER = new ViewBinder() {
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			// Ignore custom binding for user and text values
			if(view.getId() != R.id.text_createdAt) return false;
			
			// Custom binding for timestamp to relative time
			long timestamp = cursor.getLong(columnIndex);
			CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView)view).setText(relativeTime);
			return true;
		}
		
	};

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
		switch (item.getItemId()) {
		case R.id.item_update:
			startActivity(new Intent(this, StatusActivity.class));
			return true;
		case R.id.item_prefs:
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		case R.id.item_about:
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		case R.id.item_start:
			startService(new Intent("com.marakana.action.UPDATE_SERVICE"));
			return true;
		case R.id.item_stop:
			stopService(new Intent("com.marakana.action.UPDATE_SERVICE"));
			return true;
		case R.id.item_refresh:
			startService(new Intent("com.marakana.action.REFRESH"));
			return true;
		}
		return false;
	}
}
