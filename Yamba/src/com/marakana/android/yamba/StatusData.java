package com.marakana.android.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class StatusData {
	static final String TAG = "StatusData";
	
	// DB-related constants
	static final String DB_NAME = "timeline.db";
	static final int DB_VERSION = 1;
	static final String TABLE = "statuses";
	static final String C_ID = BaseColumns._ID;
	static final String C_CREATED_AT = "yabma_created_at";
	static final String C_USER = "yamba_user";
	static final String C_TEXT = "yamba_text";
	
	Context context;
	DbHelper dbHelper;
	
	/** Constructor */
	public StatusData(Context context) {
		this.context = context;
		dbHelper = new DbHelper(context);
	}
	
	/** Class to help us create and upgrade database. */
	class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			Log.d(TAG, "DbHelper() instantiated");
		}

		/** Called only once, first time we create the database file.*/
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = String.format("CREATE TABLE %s " +
					"(%s INT PRIMARY KEY, %s INT," +
					"%s TEXT, %s TEXT)",
					TABLE, C_ID, C_CREATED_AT, C_USER, C_TEXT);
			Log.d(TAG, "onCreate with SQL: "+sql);
			db.execSQL(sql);
		}

		/** Called when the old schema is different then new schema. */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Typically SQL such as: ALTER TABLE ADD COLUM ...
			db.execSQL("DROP TABLE IF EXISTS "+TABLE);
			onCreate(db);
		}
	}
}
