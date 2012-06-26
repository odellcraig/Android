package com.fivethreeapps.chore.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.fivethreeapps.chore.R;
import com.fivethreeapps.chore.adapter.CustomSpinnerAdapter;

public class ChoreEditActivity extends Activity {
	
	private String[] tempChildArray = {"None", "Logan", "Maximus", "Jeffrey", "Ryan", "Chris"};
    private final int PRIMARY_SPINNER_STARTING_POSITION = 1;
    private final int SECONDARY_SPINNER_STARTING_POSITION = 6;
    
    private final int PRIMARY_SCHEDULE_ONCE 	= 0;
    private final int PRIMARY_WEEKLY 			= 1;
    private final int PRIMARY_EVERY_OTHER_WEEK  = 2;
    private final int PRIMARY_MONTHLY 			= 3;
    
    private Context mContext;
    private Spinner mPrimarySpinner;
    private Spinner mSecondarySpinner;
    private CustomSpinnerAdapter mPrimarySpinnerAdapter;
    private CustomSpinnerAdapter mSecondarySpinnerAdapter;
    private DatePicker mDatePicker;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editchore);
		
		// Store an accessable context for subclasses
		mContext = this;
		
		
		// Get the date picker for future reference
		mDatePicker = (DatePicker)findViewById(R.id.scheduleOnceDatePicker);
		
		// Setup Primary Spinner
		mPrimarySpinner = (Spinner)findViewById(R.id.primarySpinner);
		String[] primaryItems = getResources().getStringArray(R.array.primarySpinnerItems);
		mPrimarySpinnerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, primaryItems, getString(R.string.repeat));
		mPrimarySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mPrimarySpinner.setAdapter(mPrimarySpinnerAdapter);
		mPrimarySpinner.setSelection(PRIMARY_SPINNER_STARTING_POSITION);
		mPrimarySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(position == PRIMARY_SCHEDULE_ONCE) {
					// Need to set the primary text for secondary spinner and make it go to to date picker
					
					Animation slideOut = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right);
					Animation slideIn  = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
					mSecondarySpinner.startAnimation(slideOut);
					mDatePicker.startAnimation(slideIn);
					mSecondarySpinner.setVisibility(View.GONE);
					mDatePicker.setVisibility(View.VISIBLE);
					return;
				}
				//Otherwise we want to make the spinner visible - with a fun animation
				Animation slideOut = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right);
				Animation slideIn  = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
				mSecondarySpinner.startAnimation(slideIn);
				mDatePicker.startAnimation(slideOut);
				mSecondarySpinner.setVisibility(View.VISIBLE);
				mDatePicker.setVisibility(View.GONE);
				if(position == PRIMARY_WEEKLY) {
					//Do stuff for weekly
					return;
				}
				if(position == PRIMARY_EVERY_OTHER_WEEK) {
					//Do stuff for every other week
					return;
				}
				if(position == PRIMARY_MONTHLY) {
					//Do stuff for monthly
					return;
				}
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
			}
		});
	
		
		// Setup Secondary Spinner
		mSecondarySpinner = (Spinner)findViewById(R.id.secondarySpinner);
		String[] secondaryItems = getResources().getStringArray(R.array.daysOfWeek);
		mSecondarySpinnerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, secondaryItems, getString(R.string.on));
		mSecondarySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSecondarySpinner.setAdapter(mSecondarySpinnerAdapter);
		mSecondarySpinner.setSelection(SECONDARY_SPINNER_STARTING_POSITION);
		
		// Setup Default Child Spinner TODO: make this adapt to child database instead of pulling a list
		Spinner defaultChildSpinner = (Spinner)findViewById(R.id.defaultChildSpinner);
		String[] childList = tempChildArray; //get child list
		CustomSpinnerAdapter defauldChildCustomSpinnerAdapter = new CustomSpinnerAdapter(this, android.R.layout.simple_spinner_item, childList, getString(R.string.defaultChild));
		defauldChildCustomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		defaultChildSpinner.setAdapter(defauldChildCustomSpinnerAdapter);
		
	}
}
