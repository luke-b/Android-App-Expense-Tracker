package com.steepmax.expenses;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ExpensesWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
												     ExpensesWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		
		int i = 0;
		
		for (int widgetId : allWidgetIds) {
		
			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
													  R.layout.widget);
				
		//	remoteViews.setTextViewText(R.id.widgetTextView1, String.valueOf(i));
			
			Intent intentAdd = new Intent(context, QuickInputActivity.class);
			intentAdd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intentAdd.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intentAdd.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			intentAdd.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intentAdd.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			
			Intent intentStats = new Intent(context, ExpensesActivity.class);
			intentStats.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intentStats.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intentStats.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			Intent intentStatsNew = new Intent(context, StatsActivity.class);
			intentStatsNew.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intentStatsNew.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intentStatsNew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			PendingIntent pendingIntentAdd = PendingIntent.getActivity(context, 0, intentAdd, intentAdd.getFlags());
			PendingIntent pendingIntentStats = PendingIntent.getActivity(context, 0, intentStats, intentStats.getFlags());
			PendingIntent pendingIntentStatsNew = PendingIntent.getActivity(context, 0, intentStatsNew, intentStats.getFlags());
			
			remoteViews.setOnClickPendingIntent(R.id.widgetAdd, pendingIntentAdd);
			remoteViews.setOnClickPendingIntent(R.id.widgetStats, pendingIntentStats);
			remoteViews.setOnClickPendingIntent(R.id.widgetStatsNew, pendingIntentStatsNew);
			
			i++;
			
			// Register an onClickListener
			Intent intent = new Intent(context, ExpensesWidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
																	 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			remoteViews.setOnClickPendingIntent(R.id.widgetTextView1, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}