package com.steepmax.expenses;

public interface OnLabelEditorFinished {

	public void onNewLabelCreated(int color,String name, LabelRecord lr);
	public void onNewLabelCancelled();
	
}
