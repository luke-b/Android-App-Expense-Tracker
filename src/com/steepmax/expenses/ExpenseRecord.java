package com.steepmax.expenses;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseRecord {

	private long id;
	private Date date;
	private BigDecimal value;
	private int currency;
	private int year;
	private int month;
	private int week;
	
	private LabelRecord label;
	
	public ExpenseRecord(long id, 
						 Date date, 
						 BigDecimal value, 
						 int currency,
						 int year, 
						 int month, 
						 int week,
						 LabelRecord label) {
		super();
		this.id = id;
		this.date = date;
		this.value = value;
		this.currency = currency;
		this.year = year;
		this.month = month;
		this.week = week;
		this.label = label;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}

	public int getLabelId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public LabelRecord getLabel() {
		// TODO Auto-generated method stub
		return label;
	}
	
}
