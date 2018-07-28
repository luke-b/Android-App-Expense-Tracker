package com.steepmax.expenses;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LargeLabelAdapter extends ArrayAdapter<LabelRecord> {

	
	private int resId;
	private Context context;
	private List<LabelRecord> objects;
	
	
	public LargeLabelAdapter(Context context, int resource,
			int textViewResourceId, List<LabelRecord> objects) {
		
		super(context, resource, textViewResourceId, objects);
		
		this.resId = resource;
		this.context = context;
		this.objects = objects;
		
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
        
		if (v == null) {
        
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.labelitemlargeblack, parent, false);
        }
        
		LabelRecord lr = this.objects.get(position);
        
        if (lr != null) {
 
        		Button lb = (Button) v.findViewById(R.id.labelItemColor1);    // color
                TextView ln = (TextView) v.findViewById(R.id.labelItemName1); // label name
                
                if (lb != null)  { lb.setBackgroundColor(lr.getLabelColor()); }              
                if (ln != null)  { ln.setText(lr.getLabelName().toString()); }   
                
                if (lr.getLabelName().trim().equals("-") || lr.getLabelName().equals(context.getString(R.string.add_new_label))) {
                	lb.setVisibility(View.GONE);
                } else {
                	lb.setVisibility(View.VISIBLE);
                }
                    
        }
    
        return v;
	}


	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
        
		if (v == null) {
        
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.labelitemlargeblack, parent, false);
        }
        
		LabelRecord lr = this.objects.get(position);
        
        if (lr != null) {
 
        		Button lb = (Button) v.findViewById(R.id.labelItemColor1);    // color
                TextView ln = (TextView) v.findViewById(R.id.labelItemName1); // label name
                
                if (lb != null)  { lb.setBackgroundColor(lr.getLabelColor()); }              
                if (ln != null)  { ln.setText(lr.getLabelName().toString()); }   
                
            
                if (lr.getLabelName().trim().equals("-") || lr.getLabelName().equals(context.getString(R.string.add_new_label))) {
                	lb.setVisibility(View.GONE);
                } else {
                	lb.setVisibility(View.VISIBLE);
                }
                    
        }
    
        return v;
		
	}
	
	
	
	
}
