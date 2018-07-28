package com.steepmax.expenses;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatsAdapter  extends ArrayAdapter<StatsItem> {

	private List<StatsItem> objects;
	private Context context;



	public StatsAdapter(Context context, int textViewResourceId,
			List<StatsItem> objects) {
		super(context, textViewResourceId, objects);

		this.objects = objects;
		this.context = context;
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
        
		if (v == null) {
        
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.statitem, parent, false);
        }
        
		StatsItem si = this.objects.get(position);
        
        if (si != null) {
 
        		
        		TextView p1 = (TextView)v.findViewById(R.id.statItemPercent);  // large number XX%
        		TextView p2 = (TextView)v.findViewById(R.id.statItemBar);	   // chart bar, bg+weight
        		TextView p3 = (TextView)v.findViewById(R.id.statItemLabel);	   // label + total + currency
        	
                
        		if (p1 != null) {
        			p1.setText(si.getPercentageLabel());
        		}
        		
        		if (p2 != null) {
        			p2.setBackgroundColor(si.getColor());
        			p2.setLayoutParams(new LinearLayout.LayoutParams(0, 
        															 LayoutParams.WRAP_CONTENT, 
        															 si.getPercentage()));
        		}
        		
               if (p3 != null) {
            	
            	   p3.setText(si.getDescription() + StatsItem.formatFloat(si.getSum(), si.getCurrencyId()));
               }
                      
        }
    
        return v;
	}

}
