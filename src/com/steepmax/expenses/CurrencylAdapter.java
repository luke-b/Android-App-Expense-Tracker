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

public class CurrencylAdapter extends ArrayAdapter<String> {

	
	private int resId;
	private Context context;
	private String[] objects;
	
	
	public CurrencylAdapter(Context context, int resource,
			int textViewResourceId, String[] objects) {
		
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
			v = vi.inflate(R.layout.coloritem, parent, false);
        }
        
		String lr = this.objects[position];
        
        if (lr != null) {
 
        		TextView ln = (TextView) v.findViewById(R.id.colorItemName1); // label name
                
                if (ln != null)  { ln.setText(lr); }   
                    
        }
    
        return v;
	}


	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
        
		if (v == null) {
        
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.coloritemlarge, parent, false);
        }
        
		String lr = this.objects[position];
        
        if (lr != null) {
 
                TextView ln = (TextView) v.findViewById(R.id.colorItemName1); // label name
                
                if (ln != null)  { ln.setText(lr); }   
                    
        }
    
        return v;
		
	}
	
	
	
	
}
