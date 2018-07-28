package com.steepmax.expenses;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ExpensesActivity extends Activity implements OnItemClickListener {

	private final static long DELAY = 500;
	
	private List<ExpenseRecord> values;
	private AsyncOperation async;
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	finish();
	}
	
	private void resetList() {
		resetList(0);
	}
	
	
	private void resetList(long delay) {
		
		final ExpensesApplication app = (ExpensesApplication) getApplication();
		
		if (async != null) {
			async.setCancelled();
		}
		
		boolean isCurrentMonth = app.isCurrentMonth();
		
		TextView tvl = (TextView)findViewById(R.id.navigationLabel1);
		
		if (tvl != null) {
			tvl.setText(app.getCurrentMonthLabel());
		}
		
		
		async = new AsyncOperation(this, 
								   isCurrentMonth, 
								   R.id.hintHeader1, 
								   R.id.warningHeader1, 
								   R.id.navigationProgressBar1, 
								   delay, 
							       new OperationCommand() {
									
									@Override
									public boolean command() {
		
										app.reloadLabelsList();
										
										values = app.datasource.getAllComments(app.getMonth(),app.getYear());
										
										ExpensesAdapter adapter = new ExpensesAdapter(getBaseContext(),
												R.id.recordCurrency, R.id.recordsListView1, values);
										ListView lv = (ListView) findViewById(R.id.recordsListView1);
										lv.setAdapter(adapter);
										lv.setOnItemClickListener(ExpensesActivity.this);
										
										if (values != null) {
											if (values.size() != 0) {
												return true;
											}
										}
										
										return false;
									}
								});
	
	}
	
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		resetList();
	}




	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.records);

		final ExpensesApplication app = (ExpensesApplication) getApplication();
		
		Button navToggle = (Button)findViewById(R.id.recordsFilterToggle);
		
		navToggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				View header = (View)findViewById(R.id.navigationHeader1);
				
				if (header.getVisibility() == View.VISIBLE) {
					header.setVisibility(View.GONE);
				} else {
					header.setVisibility(View.VISIBLE);
				}
				
			}
		});
		
		
		Button prev = (Button)findViewById(R.id.navigationPrevious);
		prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.setPreviousMonth();
				resetList(DELAY);
			}
		});
		
		Button next = (Button)findViewById(R.id.navigationRight);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.setNextMonth();
				resetList(DELAY);
			}
		});
		
		
		resetList();

		Button add = (Button) findViewById(R.id.recordAdd1);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent myIntent = new Intent(v.getContext(),
						InputActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		
		Button stats = (Button) findViewById(R.id.recordsStats);
		stats.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				jumpToStatistics();
			}
		});
		
		
		Button hintClose = (Button)findViewById(R.id.hintButton2);
		hintClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				View hint = (View)findViewById(R.id.hintHeader1);
				if (hint != null) {
					hint.setVisibility(View.GONE);
				}
				
			}
		});
		
		
		Button warningClose = (Button)findViewById(R.id.warningButton2);
		warningClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				View warning = (View)findViewById(R.id.warningHeader1);
				if (warning != null) {
					warning.setVisibility(View.GONE);
				}
				
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		if (values == null)
			return;

		if (values.size() == 0)
			return;

		final ExpenseRecord rec = values.get(arg2);

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					if (rec != null) {
						ExpensesApplication app = (ExpensesApplication) getApplication();
						app.datasource.deleteExpense(rec);

						resetList();
					}
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					// No button clicked
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.are_you_sure_dialog_title));
		builder.setMessage(
				getString(R.string.delete_expense_dialog_label) + ExpensesAdapter.valueFormat.format(rec.getValue()) + " "
						+ ExpensesCurrency.getCurrencySymbols()[rec.getCurrency()] + " ?")
				.setPositiveButton(getString(R.string.yes_choice_label), dialogClickListener)
				.setNegativeButton(getString(R.string.no_choice_label), dialogClickListener).show();

	}

	
	
	public void jumpToStatistics() {

		Intent myIntent = new Intent(this, StatsActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(myIntent, 0);
	}

	
}