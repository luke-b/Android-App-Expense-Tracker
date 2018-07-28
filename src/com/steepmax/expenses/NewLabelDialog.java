package com.steepmax.expenses;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;



public class NewLabelDialog extends Dialog {

	private Spinner palette;
	private Button button;
	private Button button2;
	
	public ArrayList<LabelRecord> data;
	public LabelAdapter la; 

	public Context context;
	private EditText input;
	
	private OnLabelEditorFinished handler = null;
	
	public NewLabelDialog(final Context context, boolean cancelable,
			OnCancelListener cancelListener,final LabelAdapter la, final OnLabelEditorFinished handler) {
		
		super(context, cancelable, cancelListener);
			 
		this.handler = handler;
		
		this.context = context;
		
         setContentView(R.layout.addlabel);
         setTitle(R.string.create_new_label_act_title);
         setCancelable(true);
         setOnCancelListener(cancelListener);
        
        this.la = la;
       
        
        input = (EditText)findViewById(R.id.labelAddName1);
         
         palette = (Spinner)findViewById(R.id.labelSpinner1);
         palette.setAdapter(this.la);
         
         
         
         button = (Button) findViewById(R.id.labelAddButton);
         button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
	
				if (!isNameValid()) {  // show alert
					
					AlertDialog alertDialog = new AlertDialog.Builder(context).create();
					alertDialog.setTitle(getContext().getString(R.string.warning));
					alertDialog.setMessage(getContext().getString(R.string.label_name_is_invalid_));
					alertDialog.setButton(getContext().getString(R.string.ok), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					    } });
					alertDialog.show();
					
				} else {
					
					if (handler != null) {
						
						//LabelRecord lr = data.get(palette.getSelectedItemPosition());
						LabelRecord lr = la.getItem(palette.getSelectedItemPosition());
						
						
						int color = lr.getLabelColor();
						String name =  input.getText().toString().trim();
						
						handler.onNewLabelCreated(color, name, null);
					}
					
					dismiss();
				}
			}
	     });
         
         
         button2 = (Button) findViewById(R.id.labelCancelButton);
         button2.setOnClickListener(new View.OnClickListener() {

 			@Override
 			public void onClick(View v) {
 				cancel();
 				
 				if (handler != null) {
 					handler.onNewLabelCancelled();	
 				}
 			}
 	     });
         
         show();
		
	}
	
	
	public boolean isNameValid() {
		
		if (input != null) {
		
			String name = input.getText().toString().trim();
			
			if (name.equals("") || (name.length() == 0) || (name.length() > 15)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
}
