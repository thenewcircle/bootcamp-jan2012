package com.marakana.android.yamba;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class StatusProvider extends ContentProvider {
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.marakana.android.yamba.provider/status");
	StatusData statusData;

	@Override
	public boolean onCreate() {
		statusData = new StatusData(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = statusData.dbHelper.getWritableDatabase();
		long id = db.insertWithOnConflict(StatusData.TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		return Uri.withAppendedPath(uri, Long.toString(id) );
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = statusData.dbHelper.getReadableDatabase();
		return db.query(StatusData.TABLE, projection, selection, selectionArgs,
				null, null, sortOrder);
	}
}
