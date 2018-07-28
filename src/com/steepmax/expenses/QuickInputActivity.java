package com.steepmax.expenses;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class QuickInputActivity extends Activity implements
		OnLabelEditorFinished, OnItemSelectedListener {

	private BigDecimal value;
	private int currency;
//	private boolean initRun = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quickinput);

		final ExpensesApplication app = (ExpensesApplication) getApplication();

		Spinner s2 = (Spinner) findViewById(R.id.quickSmallSpinner);
		s2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				LabelRecord l = LabelsList.getLabel(arg2);

				if (l != null) {
					if (l.getLabelId() == LabelsList.ADD_NEW_LABEL) {
							app.showAddLabelDialog(QuickInputActivity.this);
					}
				} else {
					app.saveLabelIndex(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

				onNewLabelCancelled();
			}
		});

		LabelAdapter a2 = new LabelAdapter(this,
				android.R.layout.simple_spinner_item, R.id.labelItemName1,
				LabelsList.labels);
		s2.setAdapter(a2);
		app.setSpinnerIndex(s2, app.getDefaultLabelIndex());  // selecting stored label index
		
		
		
		Button add = (Button)findViewById(R.id.quickSmallAdd);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				if (!hasAddableValue()) {
			
					AlertDialog alertDialog = new AlertDialog.Builder(QuickInputActivity.this).create();
					alertDialog.setTitle(getString(R.string.warning));
					alertDialog.setMessage(getString(R.string.please_type_a_valid_number_));
					alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					    } });
					alertDialog.show();
				
					return;
				}
				
				addExpense();
				
				EditText et = (EditText)findViewById(R.id.quickSmallEdit);
				et.setText("");
				
				finish();
			}
		});
		
		
		Spinner s = (Spinner)findViewById(R.id.quickSmallSpinnerCurrency);
		s.setOnItemSelectedListener(this);
		
		CurrencylAdapter a = new CurrencylAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				android.R.layout.simple_spinner_item, 
				ExpensesCurrency.getCurrencyNames());
		
		s.setAdapter(a);
		s.setSelection(app.getCurrency());
		
		
		
//		TextView cr = (TextView)findViewById(R.id.quickCurrView);
//		cr.setText(ExpensesCurrency.symbols[app.getCurrency()]);

		app.setOnLabelEditorFinished(this);
		
	//	getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
	//	InputMethodManager inputManager = (InputMethodManager)
    //           getSystemService(INPUT_METHOD_SERVICE); 

	//	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
    //               InputMethodManager.HIDE_NOT_ALWAYS);
		
		
		finish();
	}


	public boolean hasAddableValue() {
		
		EditText et = (EditText)findViewById(R.id.quickSmallEdit);
		
		if (et.getText().toString().equals(""))
			return false;
		
		String fl = et.getText().toString();
		
		try {
			float f = Float.parseFloat(fl);
		} catch (NumberFormatException ne) {
			return false;
		}
		
		if (fl.length() > 15)
			return false;
		
		value = new BigDecimal(fl);
		
		if (value.doubleValue() <= 0d)
			return false;
		
		return true;
	}


	
	public void addExpense() {
		
		ExpensesApplication app = (ExpensesApplication)getApplication();
		EditText et = (EditText)findViewById(R.id.quickSmallEdit);
		
	    Calendar c = Calendar.getInstance();
		
		Spinner s = (Spinner)findViewById(R.id.quickSmallSpinner);
		
		LabelRecord l = (LabelRecord)s.getSelectedItem();
		app.saveLabelIndex(s.getSelectedItemPosition());
		//LabelRecord l = LabelsList.getLabel(s.getSelectedItemPosition());
		
		if (l != null) {
			if (l.getLabelId() == LabelsList.EMPTY_LABEL) l = null;
		}
		
		if (l != null) {
			if (l.getLabelId() == LabelsList.ADD_NEW_LABEL) l = null;
		}
		
		app.datasource.createExpense(new Date(), value, app.getCurrency(), c.get(Calendar.YEAR),  c.get(Calendar.MONTH),  c.get(Calendar.DAY_OF_MONTH),
									l);  // current label
		et.setText("");
		
		app.showToast(getString(R.string.expense_stored_));
	}
	

	@Override
	public void onNewLabelCancelled() {
		// set label spinner to "none"
		Spinner s2 = (Spinner) findViewById(R.id.quickSmallSpinner);
		ExpensesApplication app = (ExpensesApplication)getApplication();
	//	s2.setSelection(app.getDefaultLabelIndex());
		app.setSpinnerIndex(s2,app.getDefaultLabelIndex());
		
		EditText et = (EditText)findViewById(R.id.quickSmallEdit);
		if (et != null) {
			et.requestFocus();
		}
		
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	@Override
	public void onNewLabelCreated(int color, String name, LabelRecord lr) {

		Spinner s2 = (Spinner) findViewById(R.id.quickSmallSpinner);

		ExpensesApplication app = (ExpensesApplication) getApplication();
		app.reloadLabelsList();

		int pos = LabelsList.getLabelPosition(lr);

		LabelAdapter a2 = new LabelAdapter(this,
				android.R.layout.simple_spinner_item, R.id.labelItemName1,
				LabelsList.labels);
		s2.setAdapter(a2);
	//	s2.setSelection(pos);
		app.setSpinnerIndex(s2,pos);

		app.saveLabelIndex(pos);
	}



	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		currency = position;
		
		ExpensesApplication app = (ExpensesApplication)getApplication();
		app.saveCurrency(position);
		
	}



	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
