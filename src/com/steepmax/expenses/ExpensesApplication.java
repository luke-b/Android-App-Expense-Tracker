package com.steepmax.expenses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.steepmax.expenses.R.drawable;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.widget.Spinner;
import android.widget.Toast;


public class ExpensesApplication extends Application implements OnLabelEditorFinished {
	
	SimpleDateFormat formatter = new SimpleDateFormat("MMMMM");
	
	private final static String[] months_cs = {
		"Leden","Únor","Bøezen","Duben","Kvìten","Èerven","Èervenec","Srpen","Záøí","Øíjen","Listopad","Prosinec"
	};
	
	public ExpensesDataSource datasource;
	public LabelsDataSource datasource2;
	
	private AppSQLHelper sqlHelper;
	private SQLiteDatabase db;
	
	public List<LabelRecord> palette;
	
	private OnLabelEditorFinished handler;
	private NewLabelDialog labelDialog;
	
	SharedPreferences preferences;
	private Editor editor;
	
	private Calendar currentDate;
	private int currentMonth;
	private int currentYear;
	
	private int startMonth;
	private int startYear;
	
	private Toast lastToast = null;
	
	
	public void setOnLabelEditorFinished(OnLabelEditorFinished handler) {
		this.handler = handler;
	}
	
	public String getCurrentMonthLabel() {
		
		if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("èeština")) {
			return months_cs[currentMonth]+" "+currentYear;
		} 
		
		Date date = new Date(currentYear, currentMonth, 1);
		return formatter.format(date) + " " + currentYear;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
	
		currentDate = Calendar.getInstance();
		currentMonth = currentDate.get(Calendar.MONTH);
		currentYear = currentDate.get(Calendar.YEAR);
		
		startMonth = currentMonth;
		startYear = currentYear;
		
		 preferences = PreferenceManager
		            .getDefaultSharedPreferences(getApplicationContext());
		 
		 editor = preferences.edit();
		 
		palette = new PaletteList().palette;
		
		sqlHelper = new AppSQLHelper(this);
		db = sqlHelper.getWritableDatabase();
	
		datasource2 = new LabelsDataSource(sqlHelper,db);
		
		LabelsList.initList(datasource2.getAllLabels(),this);
		
		datasource = new ExpensesDataSource(sqlHelper,db);
		
		
	}
	
	
	public void setPreviousMonth() {
		
		currentDate.add(Calendar.MONTH, -1);
		currentMonth = currentDate.get(Calendar.MONTH);
		currentYear = currentDate.get(Calendar.YEAR);
		
	}
	
	public void setNextMonth() {
		
		currentDate.add(Calendar.MONTH, 1);
		currentMonth = currentDate.get(Calendar.MONTH);
		currentYear = currentDate.get(Calendar.YEAR);
		
	}
	
	
	public int getDefaultLabelIndex() {
		
		return preferences.getInt("defaultLabel", 1);
	}
	
	public void saveLabelIndex(int labelIndex) {
		
		editor.putInt("defaultLabel", labelIndex);
		editor.commit();
	}
	
	
	public void showToast(String text) {
		
		if (lastToast != null) {
			lastToast.cancel();
		}
		
		lastToast = Toast.makeText(getApplicationContext(), text,
				Toast.LENGTH_LONG);
		
		if (lastToast != null) {
			lastToast.show();
		}
		
	}
	
	
	public void setSpinnerIndex(Spinner sp,int spinIndex) {
		
		int max = sp.getAdapter().getCount();
		
		int safeIndex = spinIndex;
		
		if (safeIndex <= 0) {
			safeIndex = 2;
		}
		
		if (safeIndex >= max) {
			safeIndex = max - 1;
		}
		
		if (max != 0) {
			sp.setSelection(safeIndex);
			saveLabelIndex(safeIndex);
		}
		
	}
	

	
	public void saveCurrency(int position) {
		editor.putInt("currency", position);
		editor.commit();
	}
	
	
	public int getCurrency() {
	
		if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("èeština")) {
			return preferences.getInt("currency", 17); 
		} else {
			
			if (getFlagResourceId() != -1) {
				return preferences.getInt("currency", getCurrencyIndex()); 
			}
			
		}
		
		return preferences.getInt("currency", 0); 
	}
	
		
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	
		db.close();
	}



	public void showAddLabelDialog(Context baseContext) {

		
		OnCancelListener cancelListener = new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				
			   onNewLabelCancelled();
			}
		};
		
		LabelAdapter la = new LabelAdapter(baseContext, R.layout.labelitem, R.id.labelItemName1, palette);
		
		labelDialog = new NewLabelDialog(baseContext, false, cancelListener, la, this);
		
		
	}

	
	public void cancelLabelDialog() {
		
		if (labelDialog != null) {
			labelDialog.dismiss();
		}
		
	}




	@Override
	public void onNewLabelCreated(int color, String name, LabelRecord lrx) {
		
		LabelRecord lr = datasource2.createExpense(color, name);
		reloadLabelsList();
		
		if (handler != null) {
			handler.onNewLabelCreated(color, name, lr);
		}
		
	}

	@Override
	public void onNewLabelCancelled() {
		
		if (handler != null) {
			handler.onNewLabelCancelled();
		}
		
	}
	
	
	public void reloadLabelsList() {
		
		LabelsList.initList(datasource2.getAllLabels(),this);
	}


	public int getMonth() {
		// TODO Auto-generated method stub
		return currentMonth;
	}


	public int getYear() {
		// TODO Auto-generated method stub
		return currentYear;
	}

	public boolean isCurrentMonth() {
		
		if (currentMonth == startMonth &&
			currentYear == startYear) {
		
			return true;
		}
		
		return false;
	}
	
	public boolean isFirstRun() {
		
		return preferences.getBoolean("firstRun", true); 
	}
 	

	public void firstRunFinished() {
		
		editor.putBoolean("firstRun", false);
		editor.commit();
	}

	
	// Locale helper methods
	


	public String getCountryDisplay() {  // Vrací zobrazitelný název zemì
		
		return Locale.getDefault().getDisplayCountry();
	}
	
	public int getCurrencyIndex() { // vrací int index mìny podle ExpensesCurrency
	
		String cIso3 = Locale.getDefault().getDisplayCountry();
		return ExpensesCurrency.getSymbolIndex(cIso3);
	
	}
		
	public String getCurrencyDisplay() { // vrací zobrazitelný název mìny podle ExpensesCurrency
	
		String cIso3 = Locale.getDefault().getISO3Country();
		int index = ExpensesCurrency.getSymbolIndex(cIso3);
	
		return ExpensesCurrency.getCurrencyNames()[index];
	}
	
	public int getFlagResourceId() {; // vrací int id vlajky podle zemì nebo default
	
		String cIso3 = Locale.getDefault().getISO3Country();
			
	
		//australia,britain,canada,hongkong,ireland,newzealand,norway,singapore,southafrica,taiwan,usa
		
		if (cIso3.equalsIgnoreCase("AUS")) {  // Australia
			return R.drawable.au_flag;
		}
		if (cIso3.equalsIgnoreCase("GBR")) {  // Britain
			return R.drawable.uk_flag;
		}
		if (cIso3.equalsIgnoreCase("CAN")) {  // Canada
			return R.drawable.ca_flag;
		}
		if (cIso3.equalsIgnoreCase("HKG")) {  // Hong Kong
			return R.drawable.hk_flag;
		}
		if (cIso3.equalsIgnoreCase("IRL")) {  // Ireland 
			return R.drawable.ir_flag;
		}
		if (cIso3.equalsIgnoreCase("NZL")) {  // New Zealand
			return R.drawable.nz_flag;
		}
		if (cIso3.equalsIgnoreCase("NOR")) {  // Norway
			return R.drawable.no_flag;
		}
		if (cIso3.equalsIgnoreCase("SGP")) {  // Singapore
			return R.drawable.sg_flag;
		}
		if (cIso3.equalsIgnoreCase("ZAF")) {  // South Africa
			return R.drawable.sa_flag;
		}
		if (cIso3.equalsIgnoreCase("TWN")) {  // Taiwan
			return R.drawable.tw_flag;
		}
		if (cIso3.equalsIgnoreCase("USA")) {  // United States
			return R.drawable.us_flag;
		}
		
		return -1;
	}
	
}
