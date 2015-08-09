package com.utopian.tools;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 6;

	// Database Name
	private static final String DATABASE_NAME = "taskerManager";

	// tasks table name
	private static final String TABLE_TASKS = "tasks";

	// tasks Table Columns names
	private static final String KEY_ID = "_id";
	private static final String KEY_ITEMNAME = "taskName";
	private static final String KEY_STATUS = "status";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ITEMNAME
				+ " TEXT, " + KEY_STATUS + " INTEGER)";
		db.execSQL(sql);
	
		//db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		// Create tables again
		onCreate(db);
	}

	// Adding new task
	public void addTask(DbBean bean) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ITEMNAME, bean.getTaskName()); // task name
		// status of task- can be 0 for not done and 1 for done
		values.put(KEY_STATUS, bean.getStatus());

		// Inserting Row
		db.insert(TABLE_TASKS, null, values);
		db.close(); // Closing database connection
	}

	public List<DbBean> getAllTasks() {
		List<DbBean> shoppingkList = new ArrayList<DbBean>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DbBean bean = new DbBean();
				bean.setId(cursor.getInt(0));
				bean.setTaskName(cursor.getString(1));
				bean.setStatus(cursor.getInt(2));
				// Adding contact to list
				shoppingkList.add(bean);
			} while (cursor.moveToNext());
		}

		// return task list
		return shoppingkList;
	}

	public void updateTask(DbBean bean) {
		// updating row
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ITEMNAME, bean.getTaskName());
		values.put(KEY_STATUS, bean.getStatus());
		db.update(TABLE_TASKS, values, KEY_ID + " = ?",new String[] {String.valueOf(bean.getId())});
		db.close();
	}
	

	

}
