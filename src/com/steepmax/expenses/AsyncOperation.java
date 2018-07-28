package com.steepmax.expenses;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class AsyncOperation extends Thread {

	
	private View header1;
	private View header2;
	private ProgressBar spinner1;
	private long delay;
	private OperationCommand command;
	private boolean currentMonth;
	private boolean cancelled = false;

	private Handler handler;
	private boolean operationResult;
	private int generatingHeaderId;
	private View header3;
	
	public AsyncOperation(Activity context,
			  boolean currentMonth,
			  int hintHeaderId,
			  int waringHeaderId,
			  int spinnerId,
			  int generatingHeaderId,
			  long delay,
			  OperationCommand command) {
		
		this.generatingHeaderId = generatingHeaderId;
		startOperation(context,currentMonth,hintHeaderId,waringHeaderId,spinnerId,delay,command);
		this.start();
		
	}
	
	public AsyncOperation(Activity context,
			  boolean currentMonth,
			  int hintHeaderId,
			  int waringHeaderId,
			  int spinnerId,
			  long delay,
			  OperationCommand command) {

		this.generatingHeaderId = -1;
		startOperation(context,currentMonth,hintHeaderId,waringHeaderId,spinnerId,delay,command);
		this.start();
		
	}
	
	
	
	
	public void startOperation(Activity context,
						  boolean currentMonth,
						  int hintHeaderId,
						  int waringHeaderId,
						  int spinnerId,
						  long delay,
						  OperationCommand command) {
	
		this.delay = delay;
		this.command = command;
		this.currentMonth = currentMonth;
		
		header1 = (View)context.findViewById(hintHeaderId);  // 'hint' header
		header2 = (View)context.findViewById(waringHeaderId); // 'warning' header
		spinner1 = (ProgressBar)context.findViewById(spinnerId);
	
		if (generatingHeaderId != -1) {
			header3 = (View)context.findViewById(generatingHeaderId);
		} else {
			header3 = null;
		}
		
		handler = new Handler();
		
	}
	
	
	public void setCancelled() {
		this.cancelled = true;
	}
	
	
	public void run() {
		
		makeViewGone(header1);
		makeViewGone(header2);
		makeViewGone(spinner1);
	
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (command != null && !cancelled) {
	
			makeViewVisible(spinner1);

			if (generatingHeaderId != -1) {
				makeViewVisible(header3);
			}
			
			operationResult = false;

			/*
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					operationResult = command.command();
	
					makeViewGone(spinner1);
					if (generatingHeaderId != -1) {
						makeViewGone(header3);
					}
					
					
					if (operationResult) {  // has results, everything ok, no view to show
						
						makeViewGone(header1);
						makeViewGone(header2);
						
					} else {  // no results
						
						if (currentMonth) {  // show hint
							
							makeViewVisible(header1);
							makeViewGone(header2);
						} else {	// show warning
						
							makeViewGone(header1);
							makeViewVisible(header2);
						}
					}
			
				
				
				}
			});
			
			
		} else {
			
			makeViewGone(spinner1);
			if (generatingHeaderId != -1) {
				makeViewGone(header3);
			}
		}
	}
	
	
	
	private void makeViewGone(final View v) {
		if (v != null) {

			handler.post(new Runnable() {
				
				@Override
				public void run() {
					v.setVisibility(View.GONE);
				}
			});
		}
	}
	
	private void makeViewVisible(final View v) {
		if (v != null) {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					v.setVisibility(View.VISIBLE);
				}
			});
		}
	}
	
}
