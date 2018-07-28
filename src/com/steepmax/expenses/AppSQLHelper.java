package com.steepmax.expenses;

import java.util.Locale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppSQLHelper  extends SQLiteOpenHelper {
	
	public final static String DATABASE_NAME = "expenses.db";
	public final static int DATABASE_VERSION = 1;
	
	public static String TABLE_EXPENSES = "expenses";
	public final static String EXPENSES_COLUMN_ID = "_id";
	public final static String EXPENSES_COLUMN_DATE = "date";
	public final static String EXPENSES_COLUMN_VALUE = "value";
	public final static String EXPENSES_COLUMN_CURRENCY = "currency";
	public final static String EXPENSES_COLUMN_YEAR = "year";
	public final static String EXPENSES_COLUMN_MONTH = "month";
	public final static String EXPENSES_COLUMN_WEEK = "week";
	public final static String EXPENSES_COLUMN_LABEL = "label";
	
	public static String TABLE_LABELS = "labels";
	public final static String LABELS_COLUMN_ID = "_id";
	public final static String LABELS_COLUMN_COLOR = "color";
	public final static String LABELS_COLUMN_NAME = "name";
	
	
	public final static String DATABASE_CREATE1 = "create table " +
												   TABLE_EXPENSES + "(" + EXPENSES_COLUMN_ID + " integer primary key autoincrement, " +
												   						  EXPENSES_COLUMN_DATE + " text not null," +
												   						  EXPENSES_COLUMN_VALUE + " float not null," +
												   						  EXPENSES_COLUMN_CURRENCY + " integer not null," +
												   						  EXPENSES_COLUMN_YEAR + " integer not null," +
												   						  EXPENSES_COLUMN_MONTH + " integer not null," +
												   						  EXPENSES_COLUMN_WEEK + " integer not null," +
												   						  EXPENSES_COLUMN_LABEL + " integer);";
	
	public final static String DATABASE_CREATE2 = "create table " +
													TABLE_LABELS + "(" + LABELS_COLUMN_ID + " integer primary key autoincrement, " +
																		 LABELS_COLUMN_NAME + " text not null," +
																		 LABELS_COLUMN_COLOR + " integer not null);";
	
	
	public AppSQLHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(DATABASE_CREATE1);
		db.execSQL(DATABASE_CREATE2);
		
		// add 10 default labels
		insertDefaultData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(AppSQLHelper.class.getName(),"Upgrading table " + TABLE_EXPENSES + " version " + oldVersion + " to version " +  newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
		
		Log.w(AppSQLHelper.class.getName(),"Upgrading table " + TABLE_LABELS + " version " + oldVersion + " to version " +  newVersion);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABELS);
		
		onCreate(db);
	}
	
	
	private void insertDefaultData(SQLiteDatabase db) {

		String langCode = Locale.getDefault().getDisplayLanguage();
		
		if (langCode.equalsIgnoreCase("èeština")) {
		
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFFFF0000+",'Jídlo venku')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF800000+",'Dárky')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFFB22222+",'Elektronika')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF808000+",'Mazlíèek, hraèky, hobby')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFFFF8C00+",'Hotely a dovolená')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF008000+",'Vstupné a poplatky')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF7B68EE+",'Alkohol')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF008080+",'Zábava')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF0000FF+",'Cigarety a tabák')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF000080+",'Obleèení a doplòky')");
			
		} else {
			
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFFFF0000+",'Food outside')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF800000+",'Gifts')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFFB22222+",'Electronics')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF808000+",'Pets, toys, hobbies')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFFFF8C00+",'Hotels & vacations')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF008000+",'Fees & admissions')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF7B68EE+",'Alcohol')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF008080+",'Entertainment')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF0000FF+",'Tobacco')");
			db.execSQL("INSERT INTO " + TABLE_LABELS + " (" + LABELS_COLUMN_COLOR + "," + LABELS_COLUMN_NAME + 
					   ") VALUES ("+0xFF000080+",'Apparel')");
		}
		
		
		
		
	}

}
