package com.steepmax.expenses;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LabelsActivity extends Activity implements OnLabelEditorFinished, OnItemClickListener  {

	private List<LabelRecord> labels;
	private LargeLabelAdapter la;
	
	
	private void resetList() {

		ExpensesApplication app = (ExpensesApplication) getApplication();	
		app.reloadLabelsList();
		labels = app.datasource2.getAllLabels();
		
		ListView list = (ListView)findViewById(R.id.labelsListView1);
		la = new LargeLabelAdapter(this,R.layout.labelitem,R.id.labelItemName1,labels);
		list.setAdapter(la);
		list.setOnItemClickListener(this);
		
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		final ExpensesApplication app = (ExpensesApplication) getApplication();
		app.setOnLabelEditorFinished(this);
		resetList();
		
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
	//	finish();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.labels);
		resetList();
		
		Button back = (Button)findViewById(R.id.manageBack1);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				finish();
			}
		});
		
		final ExpensesApplication app = (ExpensesApplication) getApplication();
		app.setOnLabelEditorFinished(this);
		
		
		Button add = (Button)findViewById(R.id.manageAdd1);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				app.showAddLabelDialog(LabelsActivity.this);
			}
		});
		
	
	}


	@Override
	public void onNewLabelCreated(int color, String name, LabelRecord lr) {
		
		resetList();
	}


	@Override
	public void onNewLabelCancelled() {
		
		resetList();
	}

	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		if (labels == null)
			return;

		if (labels.size() == 0)
			return;

		final LabelRecord rec = labels.get(arg2);

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					if (rec != null) {
						ExpensesApplication app = (ExpensesApplication) getApplication();
						app.datasource2.deleteExpense(rec);

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
		builder.setTitle(R.string.are_you_sure_title_dialog);
		builder.setMessage(
				getString(R.string.delete_label_text) + rec.getLabelName() + getString(R.string.question_mark))
				.setPositiveButton(getString(R.string.yes_option_button), dialogClickListener)
				.setNegativeButton(getString(R.string.no_option_button_label), dialogClickListener).show();

	}

	
	
}
