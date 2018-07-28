package com.steepmax.expenses;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class InputActivity extends Activity  implements
											OnItemSelectedListener,
											OnLabelEditorFinished {

	
	private BigDecimal value;
	private int currency;
	private NewLabelDialog dialog;

	private List<ExpenseRecord> values;
//	private boolean initRun = true;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		final ExpensesApplication app = (ExpensesApplication)getApplication();
		app.reloadLabelsList();
		app.setOnLabelEditorFinished(this);
		
		Spinner s2 = (Spinner)findViewById(R.id.spinnerLabels1);
		LabelAdapter a2 = new LabelAdapter(this,android.R.layout.simple_spinner_item,R.id.labelItemName1,LabelsList.labels);
		s2.setAdapter(a2);
		app.setSpinnerIndex(s2,app.getDefaultLabelIndex());
	//	s2.setSelection(app.getDefaultLabelIndex());
		
		//resetList();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onResume();
		final ExpensesApplication app = (ExpensesApplication)getApplication();
		app.reloadLabelsList();
		app.setOnLabelEditorFinished(this);
		
		Spinner s2 = (Spinner)findViewById(R.id.spinnerLabels1);
		LabelAdapter a2 = new LabelAdapter(this,android.R.layout.simple_spinner_item,R.id.labelItemName1,LabelsList.labels);
		s2.setAdapter(a2);
		app.setSpinnerIndex(s2,app.getDefaultLabelIndex());
	//	s2.setSelection(app.getDefaultLabelIndex());
		
		//resetList();
	}
	
	
	
	private void showWelcomeDialog() {
		
		 final ExpensesApplication app = (ExpensesApplication)getApplication();
		
		 int flagId = app.getFlagResourceId();
		 
	     final Dialog dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
	     
	     if (flagId == -1) {
	    	 dialog.setContentView(R.layout.welcome);
	     } else {
	    	 dialog.setContentView(R.layout.welcome_local);
	     }
	     
         dialog.setCancelable(true);
         
         if (flagId != -1) {
        	 
        	 ImageView flagImage = (ImageView)dialog.findViewById(R.id.welcomeFlag1);
        	 TextView countryName = (TextView)dialog.findViewById(R.id.welcomeCountry1);
        	 TextView currencyName = (TextView)dialog.findViewById(R.id.welcomeCurrency1);
        	 
        	 if (flagImage != null) {
        		 flagImage.setImageResource(flagId);
        	 }
        	 
        	 if (countryName != null) {
        		 countryName.setText(app.getCountryDisplay());
        	 }
        	 
        	 if (currencyName != null) {
        		 currencyName.setText(app.getCurrencyDisplay());  //app.getCurrencyDisplay()
        	 }
        	 
         }
         
         Button closeButton = (Button) dialog.findViewById(R.id.welcomeCloseButton1);
         closeButton.setOnClickListener(new OnClickListener() {
         @Override
             public void onClick(View v) {
        	 	 app.firstRunFinished();
                 dialog.dismiss();
             }
         });
         
         dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				 app.firstRunFinished();
			}
         });
         
         dialog.show();
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		final ExpensesApplication app = (ExpensesApplication)getApplication();
		app.cancelLabelDialog();
	}
	
	
	@Override
	protected void onPause() {
		super.onStop();
		final ExpensesApplication app = (ExpensesApplication)getApplication();
		app.cancelLabelDialog();
	//    finish();	
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.input);
	    final ExpensesApplication app = (ExpensesApplication)getApplication();
	    
	    
	 //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 //   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    
	    
	    final EditText et = (EditText)findViewById(R.id.inputEdit1);
	    et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
		
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	    
	    
	    Button lbls = (Button)findViewById(R.id.inputLabls1);  // jump to label management
	    lbls.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							
							case DialogInterface.BUTTON_POSITIVE:

								addExpense();
								jumpToLabelManagement();
							break;

							case DialogInterface.BUTTON_NEGATIVE:
								jumpToLabelManagement();
							break;
						}
					}
				};

				
				if (hasAddableValue()) {
	
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle(R.string.warning_dialog_title);
					builder.setMessage(
							getString(R.string.you_entered_label) + getFormattedInput() + getString(R.string._wanna_add_it_label))
									.setPositiveButton(getString(R.string.add_button_label), dialogClickListener)
									.setNegativeButton(getString(R.string.discard_button_label), dialogClickListener).show();
				} else {
			
					jumpToLabelManagement();
				}
			
				
			}
		});
	 
	   
	
		Spinner s = (Spinner)findViewById(R.id.intputSpinnerCurrency1);
		s.setOnItemSelectedListener(this);
		
		CurrencylAdapter a = new CurrencylAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				android.R.layout.simple_spinner_item, 
				ExpensesCurrency.getCurrencyNames());
		
		s.setAdapter(a);
		s.setSelection(app.getCurrency());
		
		
		Spinner s2 = (Spinner)findViewById(R.id.spinnerLabels1);
		s2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			
				LabelRecord l = LabelsList.getLabel(arg2);
				
				if (l != null) {
						if (l.getLabelId() == LabelsList.ADD_NEW_LABEL) {
							app.showAddLabelDialog(InputActivity.this);
						}
				} else {
					app.saveLabelIndex(arg2); // save to preference for future use
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
		
				onNewLabelCancelled();
			}
		});
		
		LabelAdapter a2 = new LabelAdapter(this,android.R.layout.simple_spinner_item,R.id.labelItemName1,LabelsList.labels);
		s2.setAdapter(a2);
		app.setSpinnerIndex(s2,app.getDefaultLabelIndex());
		
		//	app.setSpinnerIndex(s2, app.getDefaultLabelIndex());  // selecting stored label index
	//	System.out.println("curr = " + app.getCurrency());
	
		
		
		Button back = (Button)findViewById(R.id.inputBack1);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				finish();
			}
		});
	
		
		Button add = (Button)findViewById(R.id.inputAdd1);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				if (!hasAddableValue()) {
			
					AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
					alertDialog.setTitle(getString(R.string.warning_dialog_title));
					alertDialog.setMessage(getString(R.string.please_type_a_valid_number_));
					alertDialog.setButton(getString(R.string.ok_button_caption), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					    } });
					alertDialog.show();
				
					return;
				}
				
				addExpense();
			}
		});
		
		
		Button add2 = (Button)findViewById(R.id.inputAdd2);
		add2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				
				if (!hasAddableValue()) {
			
					AlertDialog alertDialog = new AlertDialog.Builder(InputActivity.this).create();
					alertDialog.setTitle(getString(R.string.warning_dialog_title));
					alertDialog.setMessage(getString(R.string.please_type_a_valid_number_));
					alertDialog.setButton(getString(R.string.ok_button_caption), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int which) {
					    } });
					alertDialog.show();
				
					return;
				}
				
				addExpense();
			}
		});
		
		
		
		Button jump = (Button)findViewById(R.id.inputOverview);
		jump.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				  
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							
							case DialogInterface.BUTTON_POSITIVE:

								addExpense();
								jumpToExpenses();
							break;

							case DialogInterface.BUTTON_NEGATIVE:
								jumpToExpenses();
							break;
						}
					}
				};

				
				if (hasAddableValue()) {
	
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle(R.string.warning_dialog_title);
					builder.setMessage(
							getString(R.string.you_entered_label) + getFormattedInput() + getString(R.string._wanna_add_it_label))
									.setPositiveButton(getString(R.string.add_button_label), dialogClickListener)
									.setNegativeButton(getString(R.string.discard_button_label), dialogClickListener).show();
				} else {
			
					jumpToExpenses();
				}
			}
		});
		
		
		
		
		Button jumpStats = (Button)findViewById(R.id.inputStatistics);
		jumpStats.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				  
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							
							case DialogInterface.BUTTON_POSITIVE:

								addExpense();
								jumpToStatistics();
							break;

							case DialogInterface.BUTTON_NEGATIVE:
								jumpToStatistics();
							break;
						}
					}
				};

				
				if (hasAddableValue()) {
	
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle(R.string.warning_dialog_title);
					builder.setMessage(
							getString(R.string.you_entered_label) + getFormattedInput() + getString(R.string._wanna_add_it_label))
									.setPositiveButton(R.string.add_button_label, dialogClickListener)
									.setNegativeButton(R.string.discard_button_label, dialogClickListener).show();
				} else {
			
					jumpToStatistics();
				}
			}
		});
		
		app.setOnLabelEditorFinished(this);
		
		if (app.isFirstRun()) {
			showWelcomeDialog();
		}
		
		
	}
	
	public void addExpense() {
		
		ExpensesApplication app = (ExpensesApplication)getApplication();
		EditText et = (EditText)findViewById(R.id.inputEdit1);
		
	    Calendar c = Calendar.getInstance();
		
		Spinner s = (Spinner)findViewById(R.id.spinnerLabels1);
		
		LabelRecord l = (LabelRecord)s.getSelectedItem();
		app.saveLabelIndex(s.getSelectedItemPosition());
		//LabelRecord l = LabelsList.getLabel(s.getSelectedItemPosition());
		
		if (l != null) {
			if (l.getLabelId() == LabelsList.EMPTY_LABEL) l = null;
		}
		
		if (l != null) {
			if (l.getLabelId() == LabelsList.ADD_NEW_LABEL) l = null;
		}
		
		app.datasource.createExpense(new Date(), value, currency, c.get(Calendar.YEAR),  c.get(Calendar.MONTH),  c.get(Calendar.DAY_OF_MONTH),
									l);  // current label
		et.setText("");
		
		
		app.showToast(getString(R.string.expense_stored_toast_label));
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int s,
			long arg3) {
		
		currency = s;
		
		ExpensesApplication app = (ExpensesApplication)getApplication();
		app.saveCurrency(s);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	
	public boolean hasAddableValue() {
		
		EditText et = (EditText)findViewById(R.id.inputEdit1);
		
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
	
	public String getFormattedInput() {
		
		EditText et = (EditText)findViewById(R.id.inputEdit1);
		
		BigDecimal b = new BigDecimal(et.getText().toString());
		String number = ExpensesAdapter.valueFormat.format(b);
		
		Spinner s = (Spinner)findViewById(R.id.intputSpinnerCurrency1);
		int position = s.getSelectedItemPosition();
		
		String currency = "";
		
		if (position != -1)
			currency = ExpensesCurrency.getCurrencySymbols()[position]; 
		
		return number + " " + currency;
	}
	
	
	public void jumpToExpenses() {

		Intent myIntent = new Intent(this, ExpensesActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(myIntent, 0);
	}
	
	public void jumpToLabelManagement() {
		
		Intent myIntent = new Intent(InputActivity.this, LabelsActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(myIntent, 0);
		
	}

	
	public void jumpToStatistics() {

		Intent myIntent = new Intent(this, StatsActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(myIntent, 0);
	}
	
	
	@Override
	public void onNewLabelCreated(int color, String name, LabelRecord lr) {
		// refresh label spinner from db
		// set label spinner to newly created label
		
		Spinner s2 = (Spinner)findViewById(R.id.spinnerLabels1);
		
		ExpensesApplication app = (ExpensesApplication)getApplication();
		app.reloadLabelsList();
		
		int pos = LabelsList.getLabelPosition(lr);
				
		LabelAdapter a2 = new LabelAdapter(this,android.R.layout.simple_spinner_item,R.id.labelItemName1,LabelsList.labels);
		s2.setAdapter(a2);
	//	s2.setSelection(pos);
		app.setSpinnerIndex(s2,pos);
		
		app.saveLabelIndex(pos);  // save new label index to preferences for future use
		
	}

	@Override
	public void onNewLabelCancelled() {
		// set label spinner to "none"
		Spinner s2 = (Spinner)findViewById(R.id.spinnerLabels1);
		ExpensesApplication app = (ExpensesApplication)getApplication();
		
		app.setSpinnerIndex(s2,app.getDefaultLabelIndex());
	//	s2.setSelection(app.getDefaultLabelIndex());
		
		
		EditText et = (EditText)findViewById(R.id.inputEdit1);
		if (et != null) {
			et.requestFocus();
		}
		
		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

		
	}
	
}


