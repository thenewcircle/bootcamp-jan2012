package com.marakana.android.yamba;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class TimelineActivity extends ListActivity {
	static final String TAG = "TimelineActivity";

	String[] FROM = { StatusData.C_USER, StatusData.C_TEXT };
	int[] TO = { R.id.text_user, R.id.text_text };

	YambaApp yambaApp;
	ListAdapter adapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		yambaApp = (YambaApp) getApplication();

		// Get the timeline
		cursor = yambaApp.getStatusData().query();

		// Setup the adapter
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
		setListAdapter(adapter);
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
			startService(new Intent(this, UpdateService.class));
			return true;
		case R.id.item_stop:
			stopService(new Intent(this, UpdateService.class));
			return true;
		case R.id.item_refresh:
			startService(new Intent(this, RefreshService.class));
			return true;
		}
		return false;
	}
}