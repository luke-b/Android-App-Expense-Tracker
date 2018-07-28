package com.steepmax.expenses;

import java.util.Iterator;
import java.util.List;

import android.content.Context;

public class LabelsList {

	public final static int ADD_NEW_LABEL = -100;
	public final static int EMPTY_LABEL = -200;

	private static boolean loaded = false;
	public static List<LabelRecord> labels = null;

	public static void initList(List<LabelRecord> l, Context context) {

		labels = l;
	
		labels.add(0,new LabelRecord(ADD_NEW_LABEL, context.getString(R.string.add_new_label),0xff000000));
	//	labels.add(0,new LabelRecord(EMPTY_LABEL, " - ", 0xff000000));
			
		loaded = true;
	}
	
	
	
	public static String[] getCaptions() {
		
		if  (labels == null)
			return null;
		
		String[] output = new String[labels.size()];
		
		Iterator<LabelRecord> it = labels.iterator();
		
		int cnt = 0;
		while (it.hasNext()) {
			
			LabelRecord lr = it.next();
	        output[cnt] = lr.getLabelName();	
	        
	        cnt++;
		}
		
		return output;
	}

	public static LabelRecord getLabel(int labelId) {

		if (!loaded)
			return null;
		
		if (labels.size() != 0) {
			return labels.get(labelId);
		}

		return null;
	}
	
	public static void cleanLabels() {   // strips of functional labels
		
		if (labels == null)
			return;
		
		labels.remove(getLabelById(ADD_NEW_LABEL));
	//	labels.remove(getLabelById(EMPTY_LABEL));
		
	}
	

	public static LabelRecord getLabelById(int labelId) {

		if (labels == null)
			return null;

		Iterator<LabelRecord> it = labels.iterator();

		while (it.hasNext()) {

			LabelRecord r = it.next();
			if (labelId == r.getLabelId()) {
				return r;
			}
		}

		return null;
	}



	public static int getLabelPosition(LabelRecord lr) {
		
		if (!loaded)
			return 0;
		
		if (labels == null)
			return 0;
		
		int cnt = 0;
		
		Iterator<LabelRecord> it = labels.iterator();
		
		while (it.hasNext()) {
			
			LabelRecord r = it.next();
			
			if (r.getLabelId() == lr.getLabelId()) {
				return cnt;
			}
			
			cnt++;
		}
		
		
		return 0;
	}

}
