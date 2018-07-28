package com.steepmax.expenses;

import android.database.Cursor;

public class LabelRecord {
	
	private int labelId;
	private int labelColor;
	private String labelName;
	
	public LabelRecord(Cursor cursor) {
		
		setRecord(cursor.getInt(0),cursor.getInt(1),cursor.getString(2));
	}
	
	public LabelRecord(int i, String string, int j) {
		this.labelId = i;
		this.labelName = string;
		this.labelColor = j;
	}

	private void setRecord(int labelId,int labelColor,String labelName) {
		
		this.labelId = labelId;
		this.labelColor = labelColor;
		this.labelName = labelName;
	}

	public int getLabelId() {
		return this.labelId;
	}

	public int getLabelColor() {
		return this.labelColor;
	}

	public String getLabelName() {
		return this.labelName;
	}

}
