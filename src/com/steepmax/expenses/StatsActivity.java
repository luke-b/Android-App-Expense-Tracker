package com.steepmax.expenses;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class StatsActivity extends Activity implements OnItemSelectedListener {

	
	private int currencyId;
	private List<StatsItem> labels;
	private Object sums;
	
	private float total;
	private AsyncOperation async;
	
	private Comparator<StatsItem> comparator = new Comparator<StatsItem>() {
		
		@Override
		public int compare(StatsItem lhs, StatsItem rhs) {
		
			if (lhs.getPercentage() < rhs.getPercentage())
				return 1;
			
			if (lhs.getPercentage() > rhs.getPercentage()) 
				return -1;
			
			return 0;
		}
	};
	
	public synchronized void recreateStatistics(final int currencyId, long delay) {
		
		
		if (async != null) {
			async.setCancelled();
		}
		
		this.currencyId = currencyId;
		
		
		async = new AsyncOperation(this, 
								   false, 
								   R.id.hintHeader1, 
								   R.id.statsWarningHeader1, 
								   R.id.navigationProgressBar1, 
								   R.id.generatingHeader1,
								   delay, 
								   new OperationCommand() {
									
									@Override
									public boolean command() {
		
										
										ExpensesApplication app = (ExpensesApplication) getApplication();
										labels = app.datasource2.getAllStats(currencyId);
										Iterator<StatsItem> si = labels.iterator();
										
										total = 0;
										
										while (si.hasNext()) {  // iterate for total and label sums
											
											StatsItem i = si.next();
											float delta = app.datasource.getExpensesTotal(i.getLabelId(), 
																						  i.getCurrencyId(), 
																						  app.getMonth(), 
																						  app.getYear());
											total += delta;
											i.setSum(delta);
										}
										
										si = labels.iterator();
										
										while (si.hasNext()) {
											
											StatsItem i = si.next();
											
											float deltaPerc = i.getSum() / total;
											if (deltaPerc < 0f) deltaPerc = 0;
											if (deltaPerc > 1f) deltaPerc = 1f;
											
											i.setPercentage(deltaPerc);
											
										}
										
										Collections.sort(labels,comparator);  // sort by percentage - descending
										
										TextView t = (TextView)findViewById(R.id.currencyTotal);
										t.setText(StatsItem.formatFloat(total, currencyId));
										
										renderStatistics();
										
										if (total > 0) {
											return true;
										} else {
											return false;
										}
									}
								});
		
		
	}
	
	
	private void renderStatistics() {
	
		if (labels != null) {
		
			StatsAdapter a = new StatsAdapter(this, R.id.statItemLabel, labels);
			
			ListView lv = (ListView)findViewById(R.id.statsListView1);
			lv.setAdapter(a);
		}
	}

	private void initDefaultCurrencyId() {
		
		ExpensesApplication app = (ExpensesApplication) getApplication();
		
		currencyId = app.getCurrency();
		
	}
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stats);
		
		final ExpensesApplication app = (ExpensesApplication) getApplication();
		
		
		TextView tvl = (TextView)findViewById(R.id.navigationLabel1);
		
		if (tvl != null) {
			tvl.setText(app.getCurrentMonthLabel());
		}
		
		
		
		Button navToggle = (Button)findViewById(R.id.statsFilter);
		
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
				
				TextView tvl = (TextView)findViewById(R.id.navigationLabel1);
				
				if (tvl != null) {
					tvl.setText(app.getCurrentMonthLabel());
				}
				
				recreateStatistics(currencyId,500);
			}
		});
		
		Button next = (Button)findViewById(R.id.navigationRight);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.setNextMonth();
				
				TextView tvl = (TextView)findViewById(R.id.navigationLabel1);
				
				if (tvl != null) {
					tvl.setText(app.getCurrentMonthLabel());
				}
				
				recreateStatistics(currencyId,500);
			}
		});
		
		
				
		initDefaultCurrencyId();
		
		recreateStatistics(currencyId,0);
		
		
		Spinner s = (Spinner)findViewById(R.id.currencySpinner2);
		s.setOnItemSelectedListener(this);
		
		CurrencylAdapter a = new CurrencylAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				android.R.layout.simple_spinner_item, 
				ExpensesCurrency.getCurrencyNames());
		
		s.setAdapter(a);
		s.setSelection(currencyId);
		
		
		
		Button back = (Button)findViewById(R.id.statsBack); // back button
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				finish();
			}
		});
		
		
		Button closeWarning = (Button)findViewById(R.id.statsWarningButton2);
		closeWarning.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				View warning = (View)findViewById(R.id.statsWarningHeader1);
				if (warning != null) {
					warning.setVisibility(View.GONE);
				}
			}
		});
		
		
		Button closeGenerating = (Button)findViewById(R.id.generatingButton2);
		closeGenerating.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				View generating = (View)findViewById(R.id.generatingHeader1);
				if (generating != null) {
					generating.setVisibility(View.GONE);
				}
			}
		});
		
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
	//	total = 0;
		 recreateStatistics(position,0);
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
}
