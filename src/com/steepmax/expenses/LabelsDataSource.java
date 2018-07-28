package com.steepmax.expenses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LabelsDataSource {
	
	private SQLiteDatabase database;
	private AppSQLHelper dbHelper;
	
	private String[] allColumns = { AppSQLHelper.LABELS_COLUMN_ID,
									AppSQLHelper.LABELS_COLUMN_COLOR,
									AppSQLHelper.LABELS_COLUMN_NAME
									};
	
	
	public LabelsDataSource(AppSQLHelper dbHelper, SQLiteDatabase db) {
		this.dbHelper = dbHelper;
		this.database = db;
	}
	
	
	
	public LabelRecord createExpense(int color, String name ) {
		
		ContentValues values = new ContentValues();
		values.put(AppSQLHelper.LABELS_COLUMN_COLOR, color);
		values.put(AppSQLHelper.LABELS_COLUMN_NAME, name);
		
		long insertId = database.insert(AppSQLHelper.TABLE_LABELS, null, values);
		
		Cursor cursor = database.query(AppSQLHelper.TABLE_LABELS, 
									   allColumns, 
									   AppSQLHelper.LABELS_COLUMN_ID + " = " + insertId, 
									   null, 
									   null, 
									   null, 
									   null);
		
		cursor.moveToFirst();
		LabelRecord newLabel = new LabelRecord(cursor);
		cursor.close();
		
		return newLabel;
	}

	
	public void deleteExpense(LabelRecord label) {
		long id = label.getLabelId();
		System.out.println("Label record id " + id + " was deleted.");
		database.delete(AppSQLHelper.TABLE_LABELS, AppSQLHelper.LABELS_COLUMN_ID + " = " + id, null);
	}
	
	public List<LabelRecord> getAllLabels() {
		List<LabelRecord> labels = new ArrayList<LabelRecord>();
		Cursor cursor = database.query(AppSQLHelper.TABLE_LABELS, 
									   allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			LabelRecord label = new LabelRecord(cursor);
			labels.add(label);
			cursor.moveToNext();
		}
		cursor.close();
		return labels;
	}
	
	
	public List<StatsItem> getAllStats(int currencyId) {
		
		ArrayList<StatsItem> stats = new ArrayList<StatsItem>();
		
		Cursor cursor = database.query(AppSQLHelper.TABLE_LABELS, 
									   allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			LabelRecord label = new LabelRecord(cursor);
		
			StatsItem s = new StatsItem();
			s.setLabelId(label.getLabelId());
			s.setDescription(label.getLabelName());
			s.setCurrencyId(currencyId);	
			s.setColor(label.getLabelColor());
						
			stats.add(s);
			cursor.moveToNext();
		}
		cursor.close();
		return stats;
	}

}
