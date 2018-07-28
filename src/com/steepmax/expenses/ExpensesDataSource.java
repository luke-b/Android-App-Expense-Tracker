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
import android.util.Log;

public class ExpensesDataSource {

	private SQLiteDatabase database;
	private AppSQLHelper dbHelper;
	
	private String[] allColumns = { AppSQLHelper.EXPENSES_COLUMN_ID,
									AppSQLHelper.EXPENSES_COLUMN_DATE,
									AppSQLHelper.EXPENSES_COLUMN_VALUE,
									AppSQLHelper.EXPENSES_COLUMN_CURRENCY,
									AppSQLHelper.EXPENSES_COLUMN_YEAR,
									AppSQLHelper.EXPENSES_COLUMN_MONTH,
									AppSQLHelper.EXPENSES_COLUMN_WEEK	};
	
	private String[] allColumnsLabel = { AppSQLHelper.EXPENSES_COLUMN_ID,
										 AppSQLHelper.EXPENSES_COLUMN_DATE,
										 AppSQLHelper.EXPENSES_COLUMN_VALUE,
										 AppSQLHelper.EXPENSES_COLUMN_CURRENCY,
										 AppSQLHelper.EXPENSES_COLUMN_YEAR,
										 AppSQLHelper.EXPENSES_COLUMN_MONTH,
										 AppSQLHelper.EXPENSES_COLUMN_WEEK,AppSQLHelper.EXPENSES_COLUMN_LABEL };
	
	public ExpensesDataSource(AppSQLHelper dbHelper, SQLiteDatabase db) {
		this.dbHelper = dbHelper;
		this.database = db;
	}
	
	
	
	public ExpenseRecord createExpense(Date date, BigDecimal value, int currency,
			int year, int month, int week, LabelRecord label) {
		
		ContentValues values = new ContentValues();
		values.put(AppSQLHelper.EXPENSES_COLUMN_DATE, date.toGMTString());
		values.put(AppSQLHelper.EXPENSES_COLUMN_VALUE, value.toString());
		values.put(AppSQLHelper.EXPENSES_COLUMN_CURRENCY, currency);
		values.put(AppSQLHelper.EXPENSES_COLUMN_YEAR, year);
		values.put(AppSQLHelper.EXPENSES_COLUMN_MONTH, month);
		values.put(AppSQLHelper.EXPENSES_COLUMN_WEEK, week);
		
		if (label != null) {
			values.put(AppSQLHelper.EXPENSES_COLUMN_LABEL, label.getLabelId());
		}
		
		long insertId = database.insert(AppSQLHelper.TABLE_EXPENSES, null, values);
		
		Cursor cursor = database.query(AppSQLHelper.TABLE_EXPENSES, 
									   ( label == null ? allColumns : allColumnsLabel ), 
									   AppSQLHelper.EXPENSES_COLUMN_ID + " = " + insertId, 
									   null, 
									   null, 
									   null, 
									   null);
		
		cursor.moveToFirst();
		ExpenseRecord newExpense = cursorToComment(cursor);
		cursor.close();
		
		return newExpense;
	}

	private ExpenseRecord cursorToComment(Cursor cursor) {
		
		int labelId = -1;
		boolean labeled = false;
		
		try {
		
			labelId = cursor.getInt(7);
			labeled = true;
		
		} catch (Throwable t) {
			
		}
		
		ExpenseRecord newRecord = new ExpenseRecord(cursor.getLong(0), 
													new Date(cursor.getString(1)), 
													new BigDecimal(cursor.getString(2)), 
													cursor.getInt(3), 
													cursor.getInt(4), 
													cursor.getInt(5), 
													cursor.getInt(6),
													LabelsList.getLabelById(labelId));
		
		
		
		return newRecord;
	}
	
	public void deleteExpense(ExpenseRecord expense) {
		long id = expense.getId();
		System.out.println("Expense record id " + id + " was deleted.");
		database.delete(AppSQLHelper.TABLE_EXPENSES, AppSQLHelper.EXPENSES_COLUMN_ID + " = " + id, null);
	}
	
	public List<ExpenseRecord> getAllComments(int month, int year) {
		List<ExpenseRecord> expenses = new ArrayList<ExpenseRecord>();
		
		String match = AppSQLHelper.EXPENSES_COLUMN_YEAR + "=" + year + " AND " + AppSQLHelper.EXPENSES_COLUMN_MONTH + "=" + month;
		
		Cursor cursor = database.query(AppSQLHelper.TABLE_EXPENSES, 
									   allColumnsLabel, 
									   match, 
									   null, 
									   null, 
									   null, 
									   AppSQLHelper.EXPENSES_COLUMN_ID + " DESC");
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ExpenseRecord expense = cursorToComment(cursor);
			expenses.add(expense);
			cursor.moveToNext();
		}
		cursor.close();
		return expenses;
	}

	
	
	public float getExpensesTotal(int labelId, int currencyId,int month,int year) {
		
		String match = AppSQLHelper.EXPENSES_COLUMN_LABEL + "=" + labelId + " AND " + AppSQLHelper.EXPENSES_COLUMN_CURRENCY + "=" + currencyId
				       + " AND " + AppSQLHelper.EXPENSES_COLUMN_YEAR + "=" + year + " AND " + AppSQLHelper.EXPENSES_COLUMN_MONTH + "=" + month;
		
		float columntotal = 0;
		
		Cursor cursor1 = database.rawQuery(
		     "SELECT SUM("+ AppSQLHelper.EXPENSES_COLUMN_VALUE  +") FROM " + AppSQLHelper.TABLE_EXPENSES + " WHERE " + match, null);
		 
		if(cursor1.moveToFirst()) {
		   columntotal = cursor1.getFloat(0);
		}
		cursor1.close();

		return columntotal;
	}
	
	
}
