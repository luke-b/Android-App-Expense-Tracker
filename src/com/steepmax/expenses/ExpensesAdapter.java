package com.steepmax.expenses;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpensesAdapter extends ArrayAdapter<ExpenseRecord> {

	
	private int resId;
	private Context context;
	private List<ExpenseRecord> objects;
	
	SimpleDateFormat formatter = new SimpleDateFormat("EEE d, HH:mm");
	
	public final static DecimalFormat valueFormat = new DecimalFormat("#,##0.00");
	
	private DateFormat df = new DateFormat();
	
	public ExpensesAdapter(Context context, int resource,
			int textViewResourceId, List<ExpenseRecord> objects) {
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
            v = vi.inflate(R.layout.recorditem, null);
        }
        
        ExpenseRecord er = objects.get(position);
        
        if (er != null) {
 
        		TextView rd = (TextView) v.findViewById(R.id.recordDate);    // recordDate
                TextView rv = (TextView) v.findViewById(R.id.recordValue); // recordValue
                TextView rc = (TextView) v.findViewById(R.id.recordCurrency); // recordCurrency
                
                TextView l = (TextView) v.findViewById(R.id.recordLabelSmall);
                LabelRecord r = er.getLabel();
                
                if (rd != null)  rd.setText(formatter.format(er.getDate()));               
                if (rv != null)  rv.setText(valueFormat.format(er.getValue()));               
                if (rc != null)  rc.setText(ExpensesCurrency.getCurrencySymbols()[er.getCurrency()]);  
                
                if (r != null) {
                	l.setText(r.getLabelName());
                	l.setBackgroundColor(r.getLabelColor());
                	l.setVisibility(View.VISIBLE);
                } else {
                	l.setVisibility(View.GONE);
                }
        }

        return v;
	}
	
}
