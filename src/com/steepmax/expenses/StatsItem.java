package com.steepmax.expenses;

import java.math.BigDecimal;

public class StatsItem {
	
	private int labelId;
	private int currencyId;
	
	private String percentageLabel;
	private float percentage;
	
	private String description;
	private float sum;
	
	private int color;
	
	
	public static String formatFloat(float f,int currency) {
		
		BigDecimal b = new BigDecimal(f);
		String s = ExpensesAdapter.valueFormat.format(b);
		s += " " + ExpensesCurrency.getCurrencySymbols()[currency];
		
		return s;
	}
	
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return this.color;
	}
	
	
	public int getLabelId() {
		return labelId;
	}
	public void setLabelId(int labelId) {
		this.labelId = labelId;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}
	public String getPercentageLabel() {
		return percentageLabel;
	}
	public void setPercentageLabel(String percentageLabel) {
		this.percentageLabel = percentageLabel;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
		this.percentageLabel = "";
		
		int i = (int)(percentage * 100);
		
		if (percentage < 0.01f) this.percentageLabel = " <1%";
		if (percentage == 0) this.percentageLabel = "";
		if (percentage >= 0.01f) this.percentageLabel = i + "%";
		
	//	if (percentageLabel.length() < 4) this.percentageLabel = " " + this.percentageLabel;
	//	if (percentageLabel.length() < 4) this.percentageLabel = " " + this.percentageLabel;
	//	if (percentageLabel.length() < 4) this.percentageLabel = " " + this.percentageLabel;
		
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		
		String d = description.substring(0, 1).toUpperCase() + description.substring(1);
		this.description = d + ": ";
	}
	public float getSum() {
		return sum;
	}
	public void setSum(float sum) {
		this.sum = sum;
	}
	
	

}
